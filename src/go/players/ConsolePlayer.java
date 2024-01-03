package go.players;

import go.IPlayer;
import go.Color;

public class ConsolePlayer implements IPlayer {
    boolean isTurn;
    private int nbCaptured;
    private final Color color;
    public ConsolePlayer(Color color, boolean isTurn) {
        assert(color != Color.UNDEFINED);
        this.color = color;
        this.isTurn = isTurn;
        /*
        // @TODO Meilleure méthode, mais moins sûre : et s'il y avait deux players de la même couleur ?
        if (color == Stone.BLACK)
            isTurn = true;
        */
        nbCaptured = 0;
    }
    @Override
    public String getColor() {
        return color == Color.BLACK ? "BLACK" : "WHITE";
    }

    @Override
    public void resetNbCaptured() {
        nbCaptured = 0;
    }

    @Override
    public void incrementNbCaptured() {
        nbCaptured++;
    }

    @Override
    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    @Override
    public boolean getIsTurn() {
        return isTurn;
    }

    @Override
    public String stringifyNbCaptured() {
        if (color == Color.BLACK)
            return "BLACK (X) has captured " + nbCaptured + " stones";
        return "WHITE (O) has captured " + nbCaptured + " stones";
    }
}
