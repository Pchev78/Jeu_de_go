package go.players;

import go.Color;
import go.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsolePlayer extends Player {
    /**
     * Constructeur de la classe ConsolePlayer.
     * @param color Couleur du joueur.
     * @param isTurn Booléen qui indique si c'est le tour du joueur.
     */
    public ConsolePlayer(Color color, boolean isTurn) {
        super(color, isTurn);
    }

    /**
     * @return Le type de joueur.
     */
    public String getPlayerType() {
        return "Human";
    }

    /**
     * Méthode qui ne doit jamais être utilisée par un humain, car elle n'est utilisée que pour les IA
     * @param emptyBoxes Liste des cases vides.
     * @return Les coordonnées du coup joué.
     * @throws IndexOutOfBoundsException est toujours throw, parce qu'on n'a pas le droit de l'utiliser en tant qu'humain
     */
    @Override
    public Coordinates play(HashMap<Integer, ArrayList<Integer>> emptyBoxes) throws IndexOutOfBoundsException {
        throw new IllegalArgumentException("Not an AI");
    }
}
