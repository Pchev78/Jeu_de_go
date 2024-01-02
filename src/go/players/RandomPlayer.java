package go.players;

import go.Goban;
import go.IPlayer;
import go.Stone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class RandomPlayer implements IPlayer {
    boolean isTurn;
    private int nbCaptured;
    private final Stone color;
    public RandomPlayer(Stone color, boolean isTurn) {
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

    public int[] playRandomly(Goban goban) throws Exception {
        HashMap<Integer, ArrayList<Integer>> emptyBoxes = goban.getEmptyBoxes();
        if (emptyBoxes.isEmpty())
            throw new Exception("Board is full. End of game.");
        else {
            Random random = new Random();
            int randomIndex = random.nextInt(emptyBoxes.size());
            int randomLine = (int) emptyBoxes.keySet().toArray()[randomIndex];
            ArrayList<Integer> possibleLines = emptyBoxes.get(randomLine);
            int randomCol = possibleLines.get(random.nextInt(possibleLines.size()));
            return new int[]{randomLine, randomCol};
        }
    }
}