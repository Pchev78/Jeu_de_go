package go.players;

import go.Color;
import go.Coordinates;
import go.IPlayer;

import java.util.ArrayList;
import java.util.HashMap;

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

    public Color getColor() {
        return color;
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

    public abstract String getPlayerType();
    public abstract Coordinates play(HashMap<Integer, ArrayList<Integer>> emptyBoxes) throws IndexOutOfBoundsException;
}
