package go.players;

import go.Player;

public class White implements Player {
    boolean isTurn = false;
    private int nbCaptured = 0;
    public String getColor() {
        return "WHITE";
    }

    public int getNbCaptured() {
        return nbCaptured;
    }

    public void resetNbCaptured() {
        nbCaptured = 0;
    }

    public void incrementNbCaptured() {
        nbCaptured++;
    }

    public void setIsTurn(boolean pIsTurn) {
        isTurn = pIsTurn;
    }

    public boolean getIsTurn() {
        return isTurn;
    }

    public String stringifyNbCaptured() {
        return "WHITE (O) has captured " + nbCaptured + " stones";
    }
}
