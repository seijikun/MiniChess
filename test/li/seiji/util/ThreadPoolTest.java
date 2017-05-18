package li.seiji.util;

import li.seiji.minichess.util.ThreadPool;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThreadPoolTest {

    @Test
    public void testStartStopAbort() {
        ThreadPool pool = new ThreadPool();
        assertFalse(pool.isRunning());
        pool.start();
        assertTrue(pool.isRunning());
        pool.abort();
        assertFalse(pool.isRunning());
        pool.start();
        assertTrue(pool.isRunning());
        pool.join();
        assertFalse(pool.isRunning());
    }

    @Test
    public void testOrder() {
        ThreadPool pool = new ThreadPool();
        List<Integer> order = Collections.synchronizedList(new ArrayList<>());

        for(int i = 0; i < 1000; ++i) {
            try {
                pool.addTask(new OrderCallable(order, i));
            } catch (InterruptedException e) {}
        }
        pool.start();
        pool.join();

        for(int i = 0; i < order.size(); ++i) {
            assertTrue(Math.abs(order.get(i) - i) <= pool.getThreadCount());
        }
    }

    @Test
    public void testAbort() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        for(int i = 0; i < pool.getThreadCount(); ++i) {
            pool.addTask(new SuperSlowTask());
        }

        List<Integer> abortedTaskResults = new ArrayList<>();
        for(int i = 0; i < 500; ++i) {
            pool.addTask(new OrderCallable(abortedTaskResults, i));
        }
        pool.start();
        pool.abort();
        assertEquals(0, abortedTaskResults.size());
    }




    private static class OrderCallable implements Callable<Integer> {

        private List<Integer> order;
        private int id = -1;
        public OrderCallable(List<Integer> order, int id){
            this.order = order;
            this.id = id;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(50);
            order.add(id);
            return id;
        }
    }

    private static class SuperSlowTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(250);
            return 0;
        }
    }

}
