package go;

import java.util.ArrayList;
import java.util.HashMap;

public interface IPlayer {
    Color getColor();
    void resetNbCaptured();
    void incrementNbCaptured();
    void setIsTurn(boolean isTurn);
    boolean getIsTurn();
    String stringifyNbCaptured();
    String getPlayerType();
    Coordinates play(HashMap<Integer, ArrayList<Integer>> emptyBoxes) throws IndexOutOfBoundsException;
}
