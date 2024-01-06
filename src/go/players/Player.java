package go.players;

import go.Color;
import go.IPlayer;

public abstract class Player implements IPlayer {
    protected boolean isTurn;
    protected int nbCaptured;
    protected final Color color;

    public Player(Color color, boolean isTurn) {
        assert (color != Color.UNDEFINED);
        this.color = color;
        this.isTurn = isTurn;
        nbCaptured = 0;
    }

    public String getColor() {
        return color == Color.BLACK ? "BLACK" : "WHITE";
    }

    public void resetNbCaptured() {
        nbCaptured = 0;
    }

    public void incrementNbCaptured() {
        nbCaptured++;
    }

    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public boolean getIsTurn() {
        return isTurn;
    }

    public String stringifyNbCaptured() {
        if (color == Color.BLACK)
            return "BLACK (X) has captured " + nbCaptured + " stones";
        return "WHITE (O) has captured " + nbCaptured + " stones";
    }
}
