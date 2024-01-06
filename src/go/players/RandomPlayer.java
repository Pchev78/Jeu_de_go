package go.players;

import go.Coordinates;
import go.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomPlayer extends Player {
    public RandomPlayer(Color color, boolean isTurn) {
        super(color, isTurn);
    }

    public Coordinates play(HashMap<Integer, ArrayList<Integer>> emptyBoxes) throws Exception {
        if (emptyBoxes.isEmpty())
            throw new Exception("Board is full. End of game.");
        else {
            Random random = new Random();
            int randomIndex = random.nextInt(emptyBoxes.size());
            int randomLine = (int) emptyBoxes.keySet().toArray()[randomIndex];
            ArrayList<Integer> possibleLines = emptyBoxes.get(randomLine);
            int randomCol = possibleLines.get(random.nextInt(possibleLines.size()));
            isTurn = false;
            return new Coordinates(randomLine, randomCol);
        }
    }
}
