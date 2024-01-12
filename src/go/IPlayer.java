package go;

import java.util.ArrayList;
import java.util.HashMap;

public interface IPlayer {
    /**
     * @return La couleur du joueur.
     */
    Color getColor();

    /**
     * Méthode qui réinitialise le nombre de pierres capturées.
     */
    void resetNbCaptured();

    /**
     * Méthode qui incrémente le nombre de pierres capturées.
     */
    void incrementNbCaptured();

    /**
     * Méthode qui définit si c'est le tour du joueur.
     * @param isTurn Booléen qui indique si c'est le tour du joueur.
     */
    void setIsTurn(boolean isTurn);

    /**
     * @return Booléen qui indique si c'est le tour du joueur.
     */
    boolean getIsTurn();

    /**
     * @return Une chaîne de caractères qui décrit le nombre de pierres capturées.
     */
    String stringifyNbCaptured();

    /**
     * @return Le type de joueur.
     */
    String getPlayerType();

    /**
     * Méthode qui permet au joueur de type IA de jouer un coup.
     * @param emptyBoxes Liste des cases vides.
     * @return Les coordonnées du coup joué.
     * @throws IndexOutOfBoundsException Si la liste des cases vides est vide.
     */
    Coordinates play(HashMap<Integer, ArrayList<Integer>> emptyBoxes) throws IndexOutOfBoundsException;
}
