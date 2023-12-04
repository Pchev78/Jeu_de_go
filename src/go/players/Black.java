package go.players;

import go.Player;

public class Black implements Player {
    private int nbCaptured = 0;
    private char color = 'B';
    public int getNbCaptured() {
        return 0;
    }

    public String toString() {
        return "BLACK (X) has captured " + nbCaptured + " pieces\n";
    }
}
