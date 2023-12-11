package go;

import go.players.Black;
import go.players.White;

import java.util.Arrays;

public class Goban {
    // Nombres par défaut, mais pourront évoluer si on appelle boardsize
    private static int NB_BOXES = 19, INDEX_SHOW_CAPTURED_WHITE = 10, INDEX_SHOW_CAPTURED_BLACK = 9;
    private static final int FIRST_LINE = 1;
    private static final int MIN_BOXES = 2, MAX_BOXES = 19;
    private static final int INDEX_BEGINNING_ALPHABET = 'A';
    private static final int NB_ARGUMENTS_PLAY = 2;
    private static final int INDEX_COLOR_PLAY = 1, INDEX_COORDONNEES_PLAY = 2;
    private static final int INDEX_COLUMNS = 0, INDEX_LINES = 1;
    private static final int INDEX_SHOW_CAPTURED = 10;
    private static String headerLetters; // Ligne composée de NB_CASES lettres
    private Stone[][] board;
    private Player white, black;
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
        headerLetters.append("   ");
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
            if (nbBoxes > INDEX_SHOW_CAPTURED) {
                INDEX_SHOW_CAPTURED_WHITE -= NB_BOXES - nbBoxes;
                INDEX_SHOW_CAPTURED_BLACK -= NB_BOXES - nbBoxes;
            } else {
                // On ne peut pas aller en dessous de ces valeurs !
                INDEX_SHOW_CAPTURED_WHITE = 1;
                INDEX_SHOW_CAPTURED_BLACK = 0;
            }
            NB_BOXES = nbBoxes;
            headerLetters = getHeader();
            clear_board();
        }
    }

    public void clear_board() {
        board = new Stone[NB_BOXES][NB_BOXES];
        for (Stone[] ligne : board) {
            Arrays.fill(ligne, Stone.UNDEFINED);
        }
    }

    public String showboard() {
        StringBuilder sb = new StringBuilder();
        sb.append(headerLetters); // 1ʳᵉ ligne

        for (int i = NB_BOXES - 1; i >= 0; i--) { // Ligne
            sb.append("\n");
            if (i < 9)
                sb.append(' ');
            sb.append(i + 1);
            for (int j = 0; j < NB_BOXES; j++)
                sb.append(' ').append(board[j][i]);

            sb.append(' ').append(i + 1);

            if(i == INDEX_SHOW_CAPTURED_WHITE)
                sb.append("     ").append(white.stringifyNbCaptured());
            else if (i == INDEX_SHOW_CAPTURED_BLACK)
                sb.append("     ").append(black.stringifyNbCaptured());
        }
        sb.append("\n").append(headerLetters).append("\n"); // Dernière ligne
        return sb.toString();
    }



    private String[] getMessage(String[] arguments) {
        String[] message = new String[NB_ARGUMENTS_PLAY];
        message[INDEX_COLOR_PLAY - 1] = arguments[INDEX_COLOR_PLAY];
        message[INDEX_COORDONNEES_PLAY - 1] = arguments[INDEX_COORDONNEES_PLAY];
        return message;
    }

    public char[] getCoordinates(String coordinatesArg) {
        char[] coordinates = new char[coordinatesArg.length() - 1];
        coordinatesArg.getChars(1,coordinatesArg.length(),coordinates,0);
        return coordinates;
    }

    public Player[] getPlayers(String color) {
        Player[] players = new Player[2];
        players[0] = color.equals("BLACK") ? black : color.equals("WHITE") ? white : null;
        players[1] = color.equals("BLACK") ? white : color.equals("WHITE") ? black : null;
        return players;
    }

    public Stone getPiece(int column, int line) {
        try {
            return board[column][line];
        } catch (Exception e) { // Si la case est en dehors des limites du tableau
            return Stone.UNDEFINED;
        }
    }

    private boolean checkMessage(String color, char column, int line, Player player, Player player2) {
        try {
            if (player == null || column < (char) INDEX_BEGINNING_ALPHABET ||
                    column > (char) (INDEX_BEGINNING_ALPHABET + NB_BOXES) || line < FIRST_LINE - 1 || line > NB_BOXES)
                throw new IndexOutOfBoundsException("invalid color or coordinate");
//            if (!changeTurn(player, player2)) // FIXME Pas nécessaire tant qu'il n'y pas D'IA ?
//                throw new IllegalArgumentException("Ce n'est pas votre tour, soyez patient.");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String play(String[] arguments) {
        String[] message = getMessage(arguments);
        String messageColor = message[INDEX_COLOR_PLAY - 1];
        Player[] players = getPlayers(messageColor);
        Player player = players[0], player2 = players[1];

        String coordinates = message[INDEX_LINES];
        char[] line = getCoordinates(coordinates);
        char column = coordinates.charAt(INDEX_COLUMNS);

        int columnIndex = column - INDEX_BEGINNING_ALPHABET;
        int lineIndex = Integer.parseInt(String.valueOf(line)) - 1;
        boolean playable = isPlayable(messageColor,column, columnIndex, lineIndex, player, player2);
        String output = "";
        if (playable)
            addPiece(player, columnIndex, lineIndex);
        else
            output = "invalid color or coordinate";


        // @TODO Vérifier si une pièce ne doit pas être prise
        //if (isCaptured())
        return output;
    }



    public boolean isPlayable(String color, char column, int columnInt, int line, Player player, Player player2) {
        return (checkMessage(color, column, line, player, player2) && boxIsEmpty(columnInt, line)
                && !checkSuicide());
    }

    private boolean checkSuicide() { // @TODO (Sprint 3) Implémenter la méthode
        return false;
    }

    private boolean boxIsEmpty(int column, int line) {
        return getPiece(column, line) == Stone.UNDEFINED;
    }

    public void addPiece (Player player, int column, int line) {
        Stone piece = Stone.UNDEFINED;
        if (player.getColor().equals("BLACK"))
            piece = Stone.BLACK;
        else if (player.getColor().equals("WHITE"))
            piece = Stone.WHITE;
        board[column][line] = piece;
    }



    public Stone getNeighbor(Stone color, int column, int line) {
        return board[column][line];
    }

    private boolean hasEmptyNeighbor(int column, int line) {
        return boxIsEmpty(column - 1, line) && boxIsEmpty(column, line - 1)
                && boxIsEmpty(column + 1, line) && boxIsEmpty(column, line + 1);
    }

    public boolean checkNeighbor(Player player, int column, int line) {
        Stone color = player.getColor().equals("BLACK") ? Stone.BLACK : Stone.WHITE;
        return board[column][line] != color;
    }

    /**
     * Permet d'inverser les tours.
     * @param player : joueur qui va terminer son tour
     * @param player2 : joueur qui va jouer au tour suivant
     * @return true si c'est le tour de player, false sinon
     */
    public boolean changeTurn(Player player, Player player2) {
        if (!player.getIsTurn())
            return false;
        else {
            player.setIsTurn(false);
            player2.setIsTurn(true);
            return true;
        }
    }
    public boolean isCaptured(Stone color,int column, int line) {
        boolean[][] visited = new boolean[NB_BOXES][NB_BOXES]; // Tableau pour suivre les pierres visitées
        return isCapturedHelper(color, column, line, visited);
    }

    private boolean isCapturedHelper(Stone color, int column, int line, boolean[][] visited) {
        if (column < 0 || column >= NB_BOXES || line < 0 || line >= NB_BOXES) {
            return false; // En dehors des limites, donc pas capturé
        }

        // Si déjà visité ou si la case n'est pas de la couleur spécifiée, retournez false
        if (visited[column][line] || board[column][line] != color) {
            return false;
        }

        // Marquez cette pierre comme visitée
        visited[column][line] = true;

        // Si la pierre a une liberté, elle n'est pas capturée
        if (hasLiberty(column, line)) {
            return false;
        }

        // Vérifiez les pierres connectées dans toutes les directions
        // Si l'une des directions renvoie false, cela signifie que la pierre n'est pas entièrement capturée
        return isCapturedHelper(color, column - 1, line, visited) &&
                isCapturedHelper(color, column + 1, line, visited) &&
                isCapturedHelper(color, column, line - 1, visited) &&
                isCapturedHelper(color, column, line + 1, visited);
    }




    private boolean hasLiberty(int column, int line) {
        if (column < 0 || column >= NB_BOXES || line < 0 || line >= NB_BOXES) {
            return false;
        }
        return boxIsEmpty(column, line);
    }

}