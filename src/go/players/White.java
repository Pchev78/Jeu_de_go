package go.players;

import go.Goban;
import go.Player;
import go.Stone;

public class White implements Player {
    private int nbCaptured = 0;
    private char color = 'W';

    public int getNbCaptured() {
        return nbCaptured;
    }

    public String toString() {
        return "WHITE (0) has captured " + nbCaptured + " pieces\n";
    }
}
