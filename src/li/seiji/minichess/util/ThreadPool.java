package li.seiji.minichess.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ThreadPool {

    private ArrayBlockingQueue<Task> tasks = new ArrayBlockingQueue<>(1000);
    private ThreadPoolThread[] threadPool;
    private Object addLock = new Object();
    private volatile boolean running = false;

    /* CONTROLLING */
    public ThreadPool() {
        this(Runtime.getRuntime().availableProcessors());
    }
    public ThreadPool(int amountOfWorkers) {
        threadPool = new ThreadPoolThread[amountOfWorkers];
    }

    public int getThreadCount() {
        return threadPool.length;
    }

    /**
     * Boot up dem threads!
     */
    public void start() {
        running = true;
        for(int i = 0; i < threadPool.length; ++i) {
            threadPool[i] = new ThreadPoolThread();
            threadPool[i].start();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void join() {
        synchronized (addLock) {
            waitUntilQueueEmpty();
            interruptThreads();
        }
    }

    public void abort() {
        interruptThreads();
        //cancel remaining tasks
        while(!tasks.isEmpty()) {
            try {
                tasks.take().result.cancel(false);
            } catch (Exception e) {}
        }
    }


    public <T> Future<T> addTask (Callable<T> task) throws InterruptedException {
        synchronized (addLock) {
            Task internalTask = new Task(task);
            this.tasks.put(internalTask);
            return internalTask.result;
        }
    }




    /* RUN CONTROL */
    private void waitUntilQueueEmpty() {
        while(!tasks.isEmpty()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
    }
    private void interruptThreads() {
        running = false;
        for(int i = 0; i < threadPool.length; ++i) {
            try {
                threadPool[i].interrupt();
                threadPool[i].join();
                threadPool[i] = null;
            } catch (InterruptedException e) {}
        }
    }



    private class Task <T> {
        public Callable<T> task;
        public CompletableFuture<T> result;

        public Task(Callable<T> task) {
            this.task = task;
            this.result = new CompletableFuture<>();
        }
    }

    private class ThreadPoolThread extends Thread {

        @Override
        public void run() {
            while(running) {
                try {
                    Task task = tasks.take();
                    task.result.complete(task.task.call());
                } catch (Exception e) {}

            }
        }

    }

}
