import go.Goban;
import go.Stone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GobanTestSprintsOk {
    private static Goban goban = new Goban();

    @Test
    void boardsize() {
        assertEquals("""
                           A B C D E F G H I J K L M N O P Q R S
                        19 . . . . . . . . . . . . . . . . . . . 19
                        18 . . . . . . . . . . . . . . . . . . . 18
                        17 . . . . . . . . . . . . . . . . . . . 17
                        16 . . . . . . . . . . . . . . . . . . . 16
                        15 . . . . . . . . . . . . . . . . . . . 15
                        14 . . . . . . . . . . . . . . . . . . . 14
                        13 . . . . . . . . . . . . . . . . . . . 13
                        12 . . . . . . . . . . . . . . . . . . . 12
                        11 . . . . . . . . . . . . . . . . . . . 11     WHITE (O) has captured 0 stones
                        10 . . . . . . . . . . . . . . . . . . . 10     BLACK (X) has captured 0 stones
                         9 . . . . . . . . . . . . . . . . . . . 9
                         8 . . . . . . . . . . . . . . . . . . . 8
                         7 . . . . . . . . . . . . . . . . . . . 7
                         6 . . . . . . . . . . . . . . . . . . . 6
                         5 . . . . . . . . . . . . . . . . . . . 5
                         4 . . . . . . . . . . . . . . . . . . . 4
                         3 . . . . . . . . . . . . . . . . . . . 3
                         2 . . . . . . . . . . . . . . . . . . . 2
                         1 . . . . . . . . . . . . . . . . . . . 1
                           A B C D E F G H I J K L M N O P Q R S
                        """, goban.showboard());

        goban.boardsize(4);
        assertEquals("""
                           A B C D
                         4 . . . . 4
                         3 . . . . 3
                         2 . . . . 2     WHITE (O) has captured 0 stones
                         1 . . . . 1     BLACK (X) has captured 0 stones
                           A B C D
                        """, goban.showboard());
        goban.boardsize(11);
        assertEquals("""
                           A B C D E F G H I J K
                        11 . . . . . . . . . . . 11
                        10 . . . . . . . . . . . 10
                         9 . . . . . . . . . . . 9
                         8 . . . . . . . . . . . 8
                         7 . . . . . . . . . . . 7
                         6 . . . . . . . . . . . 6
                         5 . . . . . . . . . . . 5
                         4 . . . . . . . . . . . 4
                         3 . . . . . . . . . . . 3     WHITE (O) has captured 0 stones
                         2 . . . . . . . . . . . 2     BLACK (X) has captured 0 stones
                         1 . . . . . . . . . . . 1
                           A B C D E F G H I J K
                        """, goban.showboard());
    }

    @Test
    public void clear_board() {
        goban.boardsize(5);
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . . . . . 4
                 3 . . . . . 3
                 2 . . . . . 2     WHITE (O) has captured 0 stones
                 1 . . . . . 1     BLACK (X) has captured 0 stones
                   A B C D E
                """, goban.showboard());

        goban.play(new String[]{"WHITE", "D2"});
        goban.boardsize(7);
        assertEquals("""
                   A B C D E F G
                 7 . . . . . . . 7
                 6 . . . . . . . 6
                 5 . . . . . . . 5
                 4 . . . . . . . 4
                 3 . . . . . . . 3
                 2 . . . . . . . 2     WHITE (O) has captured 0 stones
                 1 . . . . . . . 1     BLACK (X) has captured 0 stones
                   A B C D E F G
                """, goban.showboard());
    }

    @Test
    void placePiece() {
        goban.boardsize(5);
        goban.play(new String[]{"WHITE", "A1"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . . . . . 4
                 3 . . . . . 3
                 2 . . . . . 2     WHITE (O) has captured 0 stones
                 1 O . . . . 1     BLACK (X) has captured 0 stones
                   A B C D E
                """, goban.showboard());

        goban.play(new String[]{"BLACK", "E5"});
        assertEquals("""
                   A B C D E
                 5 . . . . X 5
                 4 . . . . . 4
                 3 . . . . . 3
                 2 . . . . . 2     WHITE (O) has captured 0 stones
                 1 O . . . . 1     BLACK (X) has captured 0 stones
                   A B C D E
                """, goban.showboard());
    }

    @Test
    public void playCaptureWithoutChains() {
        goban.boardsize(5);

        goban.play(new String[]{"WHITE", "A1"});
        goban.play(new String[]{"BLACK", "A2"});
        goban.play(new String[]{"BLACK", "B1"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . . . . . 4
                 3 . . . . . 3
                 2 X . . . . 2     WHITE (O) has captured 0 stones
                 1 . X . . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());

        goban.play(new String[]{"WHITE", "A3"});
        goban.play(new String[]{"WHITE", "B2"});
        goban.play(new String[]{"WHITE", "C1"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . . . . . 4
                 3 O . . . . 3
                 2 X O . . . 2     WHITE (O) has captured 0 stones
                 1 . X O . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());

        goban.play(new String[]{"BLACK", "B3"});
        goban.play(new String[]{"WHITE", "C3"});
        goban.play(new String[]{"WHITE", "B4"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . O . . . 4
                 3 O . O . . 3
                 2 X O . . . 2     WHITE (O) has captured 1 stones
                 1 . X O . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());

        goban.play(new String[]{"WHITE", "B3"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . O . . . 4
                 3 O O O . . 3
                 2 X O . . . 2     WHITE (O) has captured 1 stones
                 1 . X O . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());
    }

    @Test
    public void playCaptureWithChainsBS4() {
        goban.boardsize(4);
        goban.play(new String[]{"WHITE", "B2"});
        goban.play(new String[]{"WHITE", "C2"});
        goban.play(new String[]{"BLACK", "B1"});
        goban.play(new String[]{"BLACK", "C1"});
        goban.play(new String[]{"BLACK", "A2"});
        goban.play(new String[]{"BLACK", "D2"});
        goban.play(new String[]{"BLACK", "B3"});
        assertEquals("   A B C D\n" +
                " 4 . . . . 4\n" +
                " 3 . X . . 3\n" +
                " 2 X O O X 2     WHITE (O) has captured 0 stones\n" +
                " 1 . X X . 1     BLACK (X) has captured 0 stones\n" +
                "   A B C D\n", goban.showboard());

        goban.play(new String[]{"BLACK", "C3"});
        assertEquals("   A B C D\n" +
                " 4 . . . . 4\n" +
                " 3 . X X . 3\n" +
                " 2 X . . X 2     WHITE (O) has captured 0 stones\n" +
                " 1 . X X . 1     BLACK (X) has captured 2 stones\n" +
                "   A B C D\n", goban.showboard());
    }

    @Test
    public void playWithChainsBS5() {
        goban.boardsize(5);
        goban.play(new String[]{"WHITE", "A1"});
        goban.play(new String[]{"BLACK", "A2"});
        goban.play(new String[]{"BLACK", "B1"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . . . . . 4
                 3 . . . . . 3
                 2 X . . . . 2     WHITE (O) has captured 0 stones
                 1 . X . . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());

        goban.play(new String[]{"WHITE", "A3"});
        goban.play(new String[]{"WHITE", "B2"});
        goban.play(new String[]{"WHITE", "C1"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . . . . . 4
                 3 O . . . . 3
                 2 X O . . . 2     WHITE (O) has captured 0 stones
                 1 . X O . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());

        goban.play(new String[]{"BLACK", "B3"});
        goban.play(new String[]{"WHITE", "C3"});
        goban.play(new String[]{"WHITE", "B4"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . O . . . 4
                 3 O . O . . 3
                 2 X O . . . 2     WHITE (O) has captured 1 stones
                 1 . X O . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());

        goban.play(new String[]{"WHITE", "B3"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . O . . . 4
                 3 O O O . . 3
                 2 X O . . . 2     WHITE (O) has captured 1 stones
                 1 . X O . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());
        /*
        goban.play(new String[]{"BLACK", "C2"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . O . . . 4
                 3 O O O . . 3
                 2 X O X . . 2     WHITE (O) has captured 1 stones
                 1 . X O . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());

        goban.play(new String[]{"WHITE", "D2"});
        assertEquals("""
                   A B C D E
                 5 . . . . . 5
                 4 . O . . . 4
                 3 O O O . . 3
                 2 X O . O . 2     WHITE (O) has captured 2 stones
                 1 . X O . . 1     BLACK (X) has captured 1 stones
                   A B C D E
                """, goban.showboard());
         */
    }


    @Test
    public void getNbLibertiesWithoutChains() {
        goban.boardsize(5);
        goban.play(new String[]{"WHITE", "A1"});
        assertEquals(2,goban.getNbLiberties(0,0, Stone.WHITE));
        goban.play(new String[]{"BLACK", "A2"});
        assertEquals(1,goban.getNbLiberties(0,0, Stone.WHITE));
        goban.play(new String[]{"BLACK", "B2"});
        assertEquals(1,goban.getNbLiberties(0,0, Stone.WHITE));
    }



    @Test
    public void getNbLiberties() {
        Goban g = new Goban(6, "bb ab ac aa");
        System.out.println(g);
        /*
        assertEquals(2, g.getNbLiberties(0, 2));
        assertEquals(1, g.getNbLiberties(0, 1));
        assertEquals(1, g.getNbLiberties(0, 0));
        assertEquals(3, g.getNbLiberties(1, 1));
        g.play(1, 0);
        System.out.println(g);
         */
    }

}
