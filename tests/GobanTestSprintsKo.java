import go.Goban;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GobanTestSprintsKo {
    private static Goban goban = new Goban();

    @Test
    void boardsize() {
        assertThrows(NumberFormatException.class, () -> goban.boardsize(1));
        assertThrows(NumberFormatException.class, () -> goban.boardsize(26));
    }

    @Test
    public void placePiece() {
        assertEquals("invalid color or coordinate", goban.play(new String[] {"BLACK","Z2"}));
        assertEquals("invalid color or coordinate", goban.play(new String[] {"BLACK","A26"}));
        assertEquals("invalid color or coordinate", goban.play(new String[] {"GREY","A6"}));
        goban.play(new String[] {"WHITE","A2"});
        assertEquals("illegal move", goban.play(new String[] {"BLACK","A2"}));
        assertEquals("illegal move", goban.play(new String[] {"WHITE","A2"}));
    }

    @Test
    public void isSuicide() {
        goban.boardsize(5);
        goban.play(new String[] {"WHITE","A2"});
        goban.play(new String[] {"WHITE","C2"});
        goban.play(new String[] {"WHITE","B1"});
        goban.play(new String[] {"WHITE","B3"});
        assertEquals("illegal move",goban.play(new String[] {"BLACK","B2"}));
    }

    @Test
    public void definePlayers() {
        Goban goban = new Goban();
        assertThrows(IllegalArgumentException.class, () -> goban.player(new String[] {"GREY","CONSOLE"}));
        assertThrows(IllegalArgumentException.class, () -> goban.player(new String[] {"BLACK","PLAYER"}));
        goban.player(new String[]{"BLACK", "CONSOLE"});
        assertThrows(IllegalArgumentException.class, () -> goban.player(new String[] {"BLACK","CONSOLE"}));
        assertThrows(IllegalArgumentException.class, () -> goban.player(new String[] {"BLACK","RANDOM"}));
        goban.player(new String[]{"WHITE", "RANDOM"});
        assertThrows(IllegalArgumentException.class, () -> goban.player(new String[] {"WHITE","RANDOM"}));
        assertThrows(IllegalArgumentException.class, () -> goban.player(new String[] {"WHITE","CONSOLE"}));
    }

    @Test
    public void playerWhiteAI() {
        goban = new Goban();
        goban.boardsize(4);
        goban.player(new String[]{"BLACK", "CONSOLE"});
        goban.player(new String[]{"WHITE", "RANDOM"});
        // Les blancs jouent après les noirs, l'IA doit donc jouer après l'humain
        assertEquals("""
                           A B C D
                         4 . . . . 4
                         3 . . . . 3
                         2 . . . . 2     WHITE (O) has captured 0 stones
                         1 . . . . 1     BLACK (X) has captured 0 stones
                           A B C D
                        """, goban.showboard());
    }

    @Test
    public void playerBlackAI() {
        goban.boardsize(4);
        goban.player(new String[]{"WHITE", "CONSOLE"});
        goban.player(new String[]{"BLACK", "RANDOM"});
        /*
        // Les noirs jouent avant les blancs, l'IA doit donc jouer avant l'humain
        assertNotEquals("""
                           A B C D
                         4 . . . . 4
                         3 . . . . 3
                         2 . . . . 2     WHITE (O) has captured 0 stones
                         1 . . . . 1     BLACK (X) has captured 0 stones
                           A B C D
                        """, goban.showboard());
        */
    }
}