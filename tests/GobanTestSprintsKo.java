import go.Goban;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GobanTestSprintsKo {
    private static Goban goban = new Goban();

    public void defineHumanPlayers() {
        goban.player(new String[] {"WHITE","CONSOLE"});
        goban.player(new String[] {"BLACK","CONSOLE"});
    }

    @Test
    void boardsize() {
        assertThrows(NumberFormatException.class, () -> goban.boardsize(1));
        assertThrows(NumberFormatException.class, () -> goban.boardsize(26));
    }

    @Test
    public void placePiece() {
        defineHumanPlayers();
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
        goban = new Goban();
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
    public void AIvsAI() {
        goban = new Goban();
        goban.boardsize(4);
        goban.player(new String[] {"BLACK","RANDOM"});
        assertThrows(IndexOutOfBoundsException.class, () -> goban.player(new String[] {"WHITE","RANDOM"}));
    }
}