package go.players;

import go.Color;
import go.Coordinates;
import go.IPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Player implements IPlayer {
    protected boolean isTurn;
    protected int nbCaptured;
    protected final Color color;

    /**
     * Constructeur de la classe Player.
     * @param color Couleur du joueur.
     * @param isTurn Booléen qui indique si c'est le tour du joueur.
     */
    public Player(Color color, boolean isTurn) {
        assert (color != Color.UNDEFINED);
        this.color = color;
        this.isTurn = isTurn;
        nbCaptured = 0;
    }

    /**
     * @return La couleur du joueur.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Méthode qui réinitialise le nombre de pierres capturées.
     */
    public void resetNbCaptured() {
        nbCaptured = 0;
    }

    /**
     * Méthode qui incrémente le nombre de pierres capturées.
     */
    public void incrementNbCaptured() {
        nbCaptured++;
    }

    /**
     * @param isTurn Booléen qui indique si c'est le tour du joueur.
     */
    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    /**
     * @return Booléen qui indique si c'est le tour du joueur.
     */
    public boolean getIsTurn() {
        return isTurn;
    }

    /**
     * @return Une chaîne de caractères qui décrit le nombre de pierres capturées.
     */
    public String stringifyNbCaptured() {
        if (color == Color.BLACK)
            return "BLACK (X) a capturé " + nbCaptured + " pierres";
        return "WHITE (O) a capturé " + nbCaptured + " pierres";
    }

    /**
     * @return Le type de joueur.
     */
    public abstract String getPlayerType();

    /**
     * Méthode abstraite qui permet au joueur de jouer un coup.
     * @param emptyBoxes Liste des cases vides.
     * @return Les coordonnées du coup joué.
     * @throws IndexOutOfBoundsException Si la liste des cases vides est vide.
     */
    public abstract Coordinates play(HashMap<Integer, ArrayList<Integer>> emptyBoxes) throws IndexOutOfBoundsException;
}
