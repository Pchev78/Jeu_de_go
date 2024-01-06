package go.players;

import go.Color;
import go.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsolePlayer extends Player {
    public ConsolePlayer(Color color, boolean isTurn) {
        super(color, isTurn);
    }

    public String getPlayerType() {
        return "Human";
    }

    @Override
    public Coordinates play(HashMap<Integer, ArrayList<Integer>> emptyBoxes) throws IndexOutOfBoundsException {
        throw new IllegalArgumentException("Not an AI");
    }
}
