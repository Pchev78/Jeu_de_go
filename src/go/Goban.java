package go;

import go.players.Black;
import go.players.White;

import java.util.Arrays;

public class Goban {
    private static int NB_BOXES = 19; // Nombre par défaut, mais pourra évoluer si on appelle boardsize
    private static final int FIRST_LINE = 1;
    private static final int MIN_BOXES = 2, MAX_BOXES = 25;
    private static final int INDEX_BEGINNING_ALPHABET = 'A';
    private static final int NB_ARGUMENTS_PLAY = 2, NB_COORDONNEES = 2; // 2 coordonnées : la colonne et la ligne
    private static final int INDEX_COLOR_PLAY = 1, INDEX_COORDONNEES_PLAY = 2;
    private static final int INDEX_COLUMNS = 0, INDEX_LINES = 1;
    private static String headerLetters; // Ligne composée de NB_CASES lettres
    private Stone[][] board;
    private White white;
    private Black black;
    public Goban() {
        white = new White();
        black = new Black();
        headerLetters = getHeader();
        clear_board();
    }

    public Stone[][] getBoard() {
        return board;
    }
    private static String getHeader() {
        StringBuilder headerLetters= new StringBuilder();
        headerLetters.append('\t'); // Correspond à la 1ʳᵉ colonne de la colonne. L'espace pourrait être remplacé par un /
        if (NB_BOXES > 9)
            headerLetters.append(' ');
        for (int i = INDEX_BEGINNING_ALPHABET; i < INDEX_BEGINNING_ALPHABET + NB_BOXES; i++)
            headerLetters.append((char) i).append(' ');
        return headerLetters.toString();
    }
    public void boardsize(int nbBoxes) throws IllegalArgumentException{
        if (nbBoxes > MAX_BOXES)
            throw new IllegalArgumentException("Nombre de cases trop grand");
        else if (nbBoxes < MIN_BOXES)
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
        sb.append("\t").append("WHITE (0) has captured ").append(white.getNbCaptured()).append(" pieces\n");
        sb.append("\t").append("BLACK (X) has captured ").append(black.getNbCaptured()).append(" pieces\n");

        sb.append("\n").append(headerLetters); // 1ʳᵉ ligne

        for (int i = NB_BOXES - 1; i >= 0; i--) { // Ligne
            sb.append("\n  ").append(i + 1);
            if (i < 9 && NB_BOXES >= 10)
                sb.append(' ');
            for (int j = 0; j < NB_BOXES; j++) // Colonne
                sb.append(' ').append(board[j][i]);
            if (i < 9 && NB_BOXES >= 10)
                sb.append(' ');
            sb.append(' ').append(i + 1);
        }
        sb.append("\n").append(headerLetters).append("\n"); // Dernière ligne
        return sb.toString();
    }

    public void play(String[] arguments) {
        String[] message = getMessage(arguments);
        String color = message[INDEX_COLOR_PLAY - 1]; // @FIXME Renommer la variable
        String coordonneesString = message[INDEX_COORDONNEES_PLAY - 1]; // @FIXME Renommer la variable
        char[] coordonneesLine = new char[coordonneesString.length() - 1];
        coordonneesString.getChars(1,coordonneesString.length(),coordonneesLine,0);
        char column = coordonneesString.charAt(0);
        int columnInt = column - INDEX_BEGINNING_ALPHABET; // @FIXME Renommer variable
        int line = Integer.parseInt(String.valueOf(coordonneesLine)) - 1;
        if (!isPlayable(color,column, columnInt, line)) {
            return; // @TODO Changer l'affichage de "="" à "?"
        }
        addPiece(color, columnInt, line);
        System.out.println(showboard());

        // @TODO Ajouter Stone de la couleur du joueur qui a lancé play
        // @TODO Vérifier si une pièce ne doit pas être prise
    }

    public boolean isPlayable(String color, char column, int columnInt, int line) {
        return !(checkMessage(color, column, line) && !boxIsEmpty(columnInt, line)
                && !checkSuicide());
    }

    private boolean checkSuicide() { // @TODO (Sprint 3) Implémenter la méthode
        return false;
    }

    private boolean boxIsEmpty(int column, int line) {
        return getPiece(column, line) == Stone.UNDEFINED;
    }

    public void addPiece (String player, int column, int line) { // @TODO Remplacer String player par IPlayer player
        Stone color;
        if (player.equals("BLACK"))
            color = Stone.BLACK;
        else
            color = Stone.WHITE;
        board[column][line] = color;
    }

    public Stone getPiece(int column, int line) {
        return board[column][line];
    }

    private String[] getMessage(String[] arguments) {
        String[] message = new String[NB_ARGUMENTS_PLAY];
        message[INDEX_COLOR_PLAY - 1] = arguments[INDEX_COLOR_PLAY];
        message[INDEX_COORDONNEES_PLAY - 1] = arguments[INDEX_COORDONNEES_PLAY];
        return message;
    }

    private boolean checkMessage(String color, char column, int line) {
        try {
            if (!color.equals("BLACK") && !color.equals("WHITE"))
                throw new IllegalArgumentException("Pas BLACK ou WHITE");
            // @TODO Vérifier que c'est bien le tour de la personne qui a appelé la commande
            if (column < (char) INDEX_BEGINNING_ALPHABET || column > (char) (INDEX_BEGINNING_ALPHABET + NB_BOXES))
                throw new IndexOutOfBoundsException("Lettre pas dans les bornes");
            if (line < FIRST_LINE - 1 || line > NB_BOXES)
                throw new IndexOutOfBoundsException("Nombre pas dans les bornes");
            return true;
        } catch (Exception e) {
            System.out.println("Mauvais paramètres. Erreur : " + e.getLocalizedMessage());
            return false;
        }
    }
}