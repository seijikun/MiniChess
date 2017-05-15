package li.seiji.minichess;

import static org.junit.Assert.assertEquals;

import li.seiji.minichess.figure.*;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void testIdentifierParsing() {
        assertEquals(Player.parseIdentifier(Bishop.identifier), Player.BLACK);
        assertEquals(Player.parseIdentifier(King.identifier), Player.BLACK);
        assertEquals(Player.parseIdentifier(Knight.identifier), Player.BLACK);
        assertEquals(Player.parseIdentifier(Pawn.identifier), Player.BLACK);
        assertEquals(Player.parseIdentifier(Queen.identifier), Player.BLACK);
        assertEquals(Player.parseIdentifier(Rook.identifier), Player.BLACK);

        assertEquals(Player.parseIdentifier(Character.toUpperCase(Bishop.identifier)), Player.WHITE);
        assertEquals(Player.parseIdentifier(Character.toUpperCase(King.identifier)), Player.WHITE);
        assertEquals(Player.parseIdentifier(Character.toUpperCase(Knight.identifier)), Player.WHITE);
        assertEquals(Player.parseIdentifier(Character.toUpperCase(Pawn.identifier)), Player.WHITE);
        assertEquals(Player.parseIdentifier(Character.toUpperCase(Queen.identifier)), Player.WHITE);
        assertEquals(Player.parseIdentifier(Character.toUpperCase(Rook.identifier)), Player.WHITE);
    }

}
