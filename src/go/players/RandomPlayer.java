package go.players;

import go.Coordinates;
import go.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomPlayer extends Player {
    /**
     * Constructeur de la classe RandomPlayer.
     * @param color Couleur du joueur.
     * @param isTurn Booléen qui indique si c'est le tour du joueur.
     */
    public RandomPlayer(Color color, boolean isTurn) {
        super(color, isTurn);
    }

    /**
     * Permet au joueur de jouer un coup aléatoire.
     * @param emptyBoxes Liste des cases vides.
     * @return Les coordonnées du coup joué.
     * @throws IndexOutOfBoundsException Si la liste des cases vides est vide.
     */
    public Coordinates play(HashMap<Integer, ArrayList<Integer>> emptyBoxes) throws IndexOutOfBoundsException {
        if (emptyBoxes.isEmpty()) {
            throw new IndexOutOfBoundsException("Board is full. End of game.");
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(emptyBoxes.size());
            int randomLine = (int) emptyBoxes.keySet().toArray()[randomIndex];
            ArrayList<Integer> possibleLines = emptyBoxes.get(randomLine);
            int randomCol = possibleLines.get(random.nextInt(possibleLines.size()));
            return new Coordinates(randomLine, randomCol);
        }
    }

    /**
     * @return Le type de joueur.
     */
    @Override
    public String getPlayerType() {
        return "AI";
    }
}