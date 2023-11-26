package Goban;

import java.util.Arrays;

public class Goban {
    private static int NB_BOXES = 19;
    private static final int INDEX_BEGINNING_ALPHABET = (int)'A';
    private static String headerLetters; // Ligne composée de NB_CASES lettres
    private Stone[][] board;
    public Goban() {
        headerLetters = getHeader();
        clear_board();
    }
    private static String getHeader() {
        StringBuilder headerLetters= new StringBuilder();
        headerLetters.append(' '); // Correspond à la 1ʳᵉ colonne de la colonne. L'espace pourrait être remplacé par un /
        if (NB_BOXES > 9)
            headerLetters.append(' ');
        for (int i = INDEX_BEGINNING_ALPHABET; i < INDEX_BEGINNING_ALPHABET + NB_BOXES; i++)
            headerLetters.append(' ').append((char) i);
        return headerLetters.toString();
    }
    public void boardsize(int nbBoxes) throws IllegalArgumentException{
        if (nbBoxes > 25)
            throw new IllegalArgumentException("Nombre de cases trop grand");
        else if (nbBoxes < 2)
            throw new IllegalArgumentException("Nombre de cases trop petit");
        else {
            NB_BOXES = nbBoxes;
            headerLetters = getHeader();
            clear_board();
        }
    }

    public void clear_board() {
        board = new Stone[NB_BOXES][NB_BOXES];
        //@TODO Peut mieux faire en complexité
        for (Stone[] ligne : board) {
            Arrays.fill(ligne, Stone.UNDEFINED);
        }
    }

    public String showboard() {
        StringBuilder sb = new StringBuilder();
        sb.append(headerLetters); // 1ʳᵉ ligne

        for (int i = NB_BOXES - 1; i >= 0; i--) { // Ligne
            sb.append("\n").append(i + 1);
            if (i < 9 && NB_BOXES >= 10)
                sb.append(' ');
            for (int j = 0; j < NB_BOXES; j++) // Colonne
                sb.append(' ').append(board[i][j]);
            if (i < 9 && NB_BOXES >= 10)
                sb.append(' ');
            sb.append(' ').append(i + 1);
        }
        sb.append("\n").append(headerLetters).append("\n"); // Dernière ligne
        return sb.toString();
    }
}