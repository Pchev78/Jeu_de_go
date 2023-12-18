import go.Goban;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GobanTestSprintsOk {
    private static Goban goban = new Goban();

    @Test
    void boardsize() {
        String basicBoardsize =
                        "   A B C D E F G H I J K L M N O P Q R S\n" +
                        "19 . . . . . . . . . . . . . . . . . . . 19\n" +
                        "18 . . . . . . . . . . . . . . . . . . . 18\n" +
                        "17 . . . . . . . . . . . . . . . . . . . 17\n" +
                        "16 . . . . . . . . . . . . . . . . . . . 16\n" +
                        "15 . . . . . . . . . . . . . . . . . . . 15\n" +
                        "14 . . . . . . . . . . . . . . . . . . . 14\n" +
                        "13 . . . . . . . . . . . . . . . . . . . 13\n" +
                        "12 . . . . . . . . . . . . . . . . . . . 12\n" +
                        "11 . . . . . . . . . . . . . . . . . . . 11     WHITE (O) has captured 0 stones\n" +
                        "10 . . . . . . . . . . . . . . . . . . . 10     BLACK (X) has captured 0 stones\n" +
                        " 9 . . . . . . . . . . . . . . . . . . . 9\n" +
                        " 8 . . . . . . . . . . . . . . . . . . . 8\n" +
                        " 7 . . . . . . . . . . . . . . . . . . . 7\n" +
                        " 6 . . . . . . . . . . . . . . . . . . . 6\n" +
                        " 5 . . . . . . . . . . . . . . . . . . . 5\n" +
                        " 4 . . . . . . . . . . . . . . . . . . . 4\n" +
                        " 3 . . . . . . . . . . . . . . . . . . . 3\n" +
                        " 2 . . . . . . . . . . . . . . . . . . . 2\n" +
                        " 1 . . . . . . . . . . . . . . . . . . . 1\n" +
                        "   A B C D E F G H I J K L M N O P Q R S\n";
        assertEquals(basicBoardsize, goban.showboard());

        String boardsize4 =
                "   A B C D\n" +
                " 4 . . . . 4\n" +
                " 3 . . . . 3\n" +
                " 2 . . . . 2     WHITE (O) has captured 0 stones\n" +
                " 1 . . . . 1     BLACK (X) has captured 0 stones\n" +
                "   A B C D\n";
        goban.boardsize(4);
        assertEquals(boardsize4, goban.showboard());

        String boardsize11 =
                        "   A B C D E F G H I J K\n" +
                        "11 . . . . . . . . . . . 11\n" +
                        "10 . . . . . . . . . . . 10\n" +
                        " 9 . . . . . . . . . . . 9\n" +
                        " 8 . . . . . . . . . . . 8\n" +
                        " 7 . . . . . . . . . . . 7\n" +
                        " 6 . . . . . . . . . . . 6\n" +
                        " 5 . . . . . . . . . . . 5\n" +
                        " 4 . . . . . . . . . . . 4\n" +
                        " 3 . . . . . . . . . . . 3     WHITE (O) has captured 0 stones\n" +
                        " 2 . . . . . . . . . . . 2     BLACK (X) has captured 0 stones\n" +
                        " 1 . . . . . . . . . . . 1\n" +
                        "   A B C D E F G H I J K\n";
        goban.boardsize(11);
        assertEquals(boardsize11, goban.showboard());
    }

    @Test
    void play() {
        goban.boardsize(5);
        goban.play(new String[]{"PLAY","WHITE", "A1"});
        assertEquals("   A B C D E\n" +
                " 5 . . . . . 5\n" +
                " 4 . . . . . 4\n" +
                " 3 . . . . . 3\n" +
                " 2 . . . . . 2     WHITE (O) has captured 0 stones\n" +
                " 1 O . . . . 1     BLACK (X) has captured 0 stones\n" +
                "   A B C D E\n", goban.showboard());

        goban.play(new String[]{"PLAY","BLACK", "E5"});
        assertEquals("   A B C D E\n" +
                " 5 . . . . X 5\n" +
                " 4 . . . . . 4\n" +
                " 3 . . . . . 3\n" +
                " 2 . . . . . 2     WHITE (O) has captured 0 stones\n" +
                " 1 O . . . . 1     BLACK (X) has captured 0 stones\n" +
                "   A B C D E\n", goban.showboard());
    }

    @Test
    public void playCaptureWithoutChains() {
        goban.boardsize(5);

        goban.play(new String[]{"PLAY","WHITE", "A1"});
        goban.play(new String[]{"PLAY","BLACK", "A2"});
        goban.play(new String[]{"PLAY","BLACK", "B1"});
        assertEquals("   A B C D E\n" +
                " 5 . . . . . 5\n" +
                " 4 . . . . . 4\n" +
                " 3 . . . . . 3\n" +
                " 2 X . . . . 2     WHITE (O) has captured 0 stones\n" +
                " 1 . X . . . 1     BLACK (X) has captured 1 stones\n" +
                "   A B C D E\n", goban.showboard());

        goban.play(new String[]{"PLAY","WHITE", "A3"});
        goban.play(new String[]{"PLAY","WHITE", "B2"});
        goban.play(new String[]{"PLAY","WHITE", "C1"});
        assertEquals("   A B C D E\n" +
                " 5 . . . . . 5\n" +
                " 4 . . . . . 4\n" +
                " 3 O . . . . 3\n" +
                " 2 X O . . . 2     WHITE (O) has captured 0 stones\n" +
                " 1 . X O . . 1     BLACK (X) has captured 1 stones\n" +
                "   A B C D E\n", goban.showboard());

        goban.play(new String[]{"PLAY","BLACK", "B3"});
        goban.play(new String[]{"PLAY","WHITE", "C3"});
        goban.play(new String[]{"PLAY","WHITE", "B4"});
        assertEquals("   A B C D E\n" +
                " 5 . . . . . 5\n" +
                " 4 . O . . . 4\n" +
                " 3 O . O . . 3\n" +
                " 2 X O . . . 2     WHITE (O) has captured 1 stones\n" +
                " 1 . X O . . 1     BLACK (X) has captured 1 stones\n" +
                "   A B C D E\n", goban.showboard());

        goban.play(new String[]{"PLAY","WHITE", "B3"});
        assertEquals("   A B C D E\n" +
                " 5 . . . . . 5\n" +
                " 4 . O . . . 4\n" +
                " 3 O O O . . 3\n" +
                " 2 X O . . . 2     WHITE (O) has captured 1 stones\n" +
                " 1 . X O . . 1     BLACK (X) has captured 1 stones\n" +
                "   A B C D E\n", goban.showboard());

        goban.play(new String[]{"PLAY","BLACK", "C2"});
        goban.play(new String[]{"PLAY","WHITE", "D2"});
        /*
        assertEquals("   A B C D E\n" +
                " 5 . . . . . 5\n" +
                " 4 . O . . . 4\n" +
                " 3 O O O . . 3\n" +
                " 2 X O . O . 2     WHITE (O) has captured 2 stones\n" +
                " 1 . X O . . 1     BLACK (X) has captured 1 stones\n" +
                "   A B C D E\n", goban.showboard());
        */
    }

    @Test
    public void clear_board() {
        goban.boardsize(5);
        assertEquals("   A B C D E\n" +
                " 5 . . . . . 5\n" +
                " 4 . . . . . 4\n" +
                " 3 . . . . . 3\n" +
                " 2 . . . . . 2     WHITE (O) has captured 0 stones\n" +
                " 1 . . . . . 1     BLACK (X) has captured 0 stones\n" +
                "   A B C D E\n", goban.showboard());

        goban.play(new String[]{"PLAY","WHITE", "D2"});
        goban.boardsize(7);
        assertEquals("   A B C D E F G\n" +
                " 7 . . . . . . . 7\n" +
                " 6 . . . . . . . 6\n" +
                " 5 . . . . . . . 5\n" +
                " 4 . . . . . . . 4\n" +
                " 3 . . . . . . . 3\n" +
                " 2 . . . . . . . 2     WHITE (O) has captured 0 stones\n" +
                " 1 . . . . . . . 1     BLACK (X) has captured 0 stones\n" +
                "   A B C D E F G\n", goban.showboard());
    }

    /*
    @Test
    public void playCaptureWithChains() {
        goban.boardsize(4);
        goban.play(new String[]{"PLAY","WHITE", "B2"});
        goban.play(new String[]{"PLAY","WHITE", "C2"});
        goban.play(new String[]{"PLAY","BLACK", "B1"});
        goban.play(new String[]{"PLAY","BLACK", "C1"});
        goban.play(new String[]{"PLAY","BLACK", "A2"});
        goban.play(new String[]{"PLAY","BLACK", "D2"});
        goban.play(new String[]{"PLAY","BLACK", "B3"});
        assertEquals("   A B C D\n" +
                " 4 . . . . 4\n" +
                " 3 . X . . 3\n" +
                " 2 X O O X 2     WHITE (O) has captured 0 stones\n" +
                " 1 . X X . 1     BLACK (X) has captured 0 stones\n" +
                "   A B C D\n", goban.showboard());

        goban.play(new String[]{"PLAY","BLACK", "C3"});
        assertEquals("   A B C D\n" +
                " 4 . . . . 4\n" +
                " 3 . X X . 3\n" +
                " 2 X . . X 2     WHITE (O) has captured 0 stones\n" +
                " 1 . X X . 1     BLACK (X) has captured 2 stones\n" +
                "   A B C D\n", goban.showboard());
    }
     */

    /*
    @Test
    public void getNbLiberties() {
        Goban g = new Goban(6, "bb ab ac aa");
        System.out.println(g);
        assertEquals(2, g.getNbLiberties(0, 2));
        assertEquals(1, g.getNbLiberties(0, 1));
        assertEquals(1, g.getNbLiberties(0, 0));
        assertEquals(3, g.getNbLiberties(1, 1));
        g.play(1,0);
        System.out.println(g);
    }
    */
}
