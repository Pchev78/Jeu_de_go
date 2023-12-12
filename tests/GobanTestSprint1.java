import go.Goban;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GobanTestSprint1 {
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

//    @Test
//    void play() {    }
}
