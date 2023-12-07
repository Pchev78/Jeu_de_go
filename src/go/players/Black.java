package go.players;

import go.Player;

public class Black implements Player {
    boolean isTurn = true;
    private int nbCaptured = 0;
    public String getColor() {
        return "BLACK";
    }

    public int getNbCaptured() {
        return 0;
    }

    public void setIsTurn(boolean pIsTurn) {
        isTurn = pIsTurn;
    }

    public boolean getIsTurn() {
        return isTurn;
    }

    public String stringifyNbCaptured() {
        return "BLACK (X) has captured " + nbCaptured + " pieces\n";
    }
}
