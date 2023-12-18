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
        assertEquals("invalid color or coordinate", goban.play(new String[] {"PLAY","BLACK","Z2"}));
        assertEquals("invalid color or coordinate", goban.play(new String[] {"PLAY","BLACK","A26"}));
        assertEquals("invalid color or coordinate", goban.play(new String[] {"PLAY","GREY","A6"}));
        goban.play(new String[] {"PLAY","WHITE","A2"});
        assertEquals("illegal move", goban.play(new String[] {"PLAY","BLACK","A2"}));
        assertEquals("illegal move", goban.play(new String[] {"PLAY","WHITE","A2"}));
    }

    @Test
    public void isSuicide() {
        goban.boardsize(5);
        goban.play(new String[] {"PLAY","WHITE","A2"});
        goban.play(new String[] {"PLAY","WHITE","C2"});
        goban.play(new String[] {"PLAY","WHITE","B1"});
        goban.play(new String[] {"PLAY","WHITE","B3"});
        assertEquals("illegal move",goban.play(new String[] {"PLAY","BLACK","B2"}));
    }
}