package go.players;

import go.IPlayer;
import go.Stone;

import java.util.Objects;

public class ConsolePlayer implements IPlayer {
    boolean isTurn = false;
    private int nbCaptured;
    private final Stone color;
    public ConsolePlayer(Stone color, boolean isTurn) {
        assert(color != Stone.UNDEFINED);
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
        return color == Stone.BLACK ? "BLACK" : "WHITE";
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
        String color = getColor();
        if (Objects.equals(color, "BLACK"))
            return "BLACK (X) has captured " + nbCaptured + " stones";
        else
            return "WHITE (O) has captured " + nbCaptured + " stones";
    }
}
