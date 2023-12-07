package go.players;

import go.Goban;
import go.Player;
import go.Stone;

public class White implements Player {
    boolean isTurn = true;
    private int nbCaptured = 0;
    public String getColor() {
        return "WHITE";
    }

    public int getNbCaptured() {
        return nbCaptured;
    }

    public void setIsTurn(boolean pIsTurn) {
        isTurn = pIsTurn;
    }

    public boolean getIsTurn() {
        return isTurn;
    }

    public String stringifyNbCaptured() {
        return "WHITE (0) has captured " + nbCaptured + " pieces\n";
    }
}
