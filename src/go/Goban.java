package go;

import go.players.Black;
import go.players.White;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Goban {
    // Nombres par défaut, mais pourront évoluer si on appelle boardsize
    private static int NB_BOXES = 19, INDEX_SHOW_CAPTURED_WHITE = 10, INDEX_SHOW_CAPTURED_BLACK = 9;
    private static final int FIRST_LINE = 1;
    private static final int MIN_BOXES = 2, MAX_BOXES = 25;
    private static final int INDEX_BEGINNING_ALPHABET = 'A';
    private static final int NB_ARGUMENTS_PLAY = 2;
    private static final int INDEX_COLOR_PLAY = 1, INDEX_COORDINATES_PLAY = 2;
    private static final int INDEX_COLUMNS = 0, INDEX_LINES = 1;
    private static final int INDEX_SHOW_CAPTURED = 11;
    private static String headerLetters; // Ligne composée de NB_CASES lettres
    private Stone[][] board;
    private Player white, black;
    public Goban() {
        white = new White();
        black = new Black();
        headerLetters = getHeader();
        clear_board();
    }

    public Goban(int boardsize, String playArguments) {
        super();
        boardsize(boardsize);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("PLAY");
        // @FIXME La méthode ne marche pas : il faut appeler un par un la méthode play, et passer en arguments le joueur
        arrayList.addAll(Arrays.asList(playArguments.split(" ")));
        play(arrayList.toArray(new String[0]));
    }

    private static String getHeader() {
        StringBuilder headerLetters= new StringBuilder();
        headerLetters.append("   ");
        boolean first = true;
        for (int i = INDEX_BEGINNING_ALPHABET; i < INDEX_BEGINNING_ALPHABET + NB_BOXES; i++) {
            if (!first)
                headerLetters.append(' ');
            headerLetters.append((char) i);
            first = false;
        }
        return headerLetters.toString();
    }
    public void boardsize(int nbBoxes) {
        if (nbBoxes > MAX_BOXES)
            throw new NumberFormatException("Nombre de cases trop grand");
        else if (nbBoxes < MIN_BOXES)
            throw new NumberFormatException("Nombre de cases trop petit");
        else {
            if (nbBoxes >= INDEX_SHOW_CAPTURED) {
                INDEX_SHOW_CAPTURED_WHITE = nbBoxes - INDEX_SHOW_CAPTURED + 2;
                INDEX_SHOW_CAPTURED_BLACK = nbBoxes - INDEX_SHOW_CAPTURED + 1;
            } else {
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
        for (Stone[] ligne : board)
            Arrays.fill(ligne, Stone.UNDEFINED);
        white.resetNbCaptured();
        black.resetNbCaptured();
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
        message[INDEX_COORDINATES_PLAY - 1] = arguments[INDEX_COORDINATES_PLAY];
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

    private boolean checkMessage(char column, int line, Player player, Player player2) {
        try {
            if (player == null || column < (char) INDEX_BEGINNING_ALPHABET ||
                    column > (char) (INDEX_BEGINNING_ALPHABET + NB_BOXES) || line < FIRST_LINE - 1 || line > NB_BOXES)
                throw new IndexOutOfBoundsException("invalid color or coordinate");
//            if (!changeTurn(player, player2)) // FIXME Pas nécessaire tant qu'il n'y pas D'IA ?
//                throw new IndexOutOfBoundsException("Ce n'est pas votre tour, soyez patient.");
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
        //@TODO Changer les coordonnées par un record
        char[] line = getCoordinates(coordinates);
        char column = coordinates.charAt(INDEX_COLUMNS);

        int columnIndex = column - INDEX_BEGINNING_ALPHABET;
        int lineIndex = Integer.parseInt(String.valueOf(line)) - 1;
        String output = checkMove(column, columnIndex, lineIndex, player, player2);
        if (Objects.equals(output, "")) { // S'il n'y a pas eu d'erreur
            addPiece(player, columnIndex, lineIndex);
            checkCaptured();
        }
        return output;
    }

    public String checkMove(char column, int columnInt, int line, Player player, Player player2) {
        if (!checkMessage(column, line, player, player2))
            return "invalid color or coordinate";
        else if (!(board[columnInt][line] == Stone.UNDEFINED) || isSuicide(columnInt, line, getStoneByPlayer(player)))
            return "illegal move";
        return "";
    }

    private boolean isSuicide(int column, int line, Stone color) {
        return getNbLiberties(column, line, color) == 0;
    }

    public Stone getStoneByPlayer(Player player) {
        if (player.getColor().equals("BLACK"))
            return Stone.BLACK;
        else if (player.getColor().equals("WHITE"))
            return Stone.WHITE;
        return Stone.UNDEFINED;
    }

    public Stone getEnnemyStone(Stone color) {
        if (color == Stone.BLACK)
            return Stone.WHITE;
        else if (color == Stone.WHITE)
            return Stone.BLACK;
        return Stone.UNDEFINED;
    }

    public void addPiece (Player player, int column, int line) {
        Stone piece = getStoneByPlayer(player);
        board[column][line] = piece;
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

    private boolean isLiberty(int column, int line) {
        return inBounds(column,line) && board[column][line] == Stone.UNDEFINED;
    }

    public int getNbLiberties(int column, int line, boolean[][] visited) {
        return getNbLibertiesHelper(column, line, board[column][line], visited);
    }

    public int getNbLiberties(int column, int line, Stone color) {
        boolean[][] visited = new boolean[NB_BOXES][NB_BOXES]; // Pour garder une trace des pions déjà visités
        return getNbLibertiesHelper(column, line, color, visited);
    }

    private int getNbLibertiesHelper(int column, int line, Stone stone, boolean[][] visited) {
        if (!inBounds(column, line)) return 0;

        // Si la case a déjà été visitée ou n'est pas de la même couleur, on retourne 0
        if (visited[column][line] || board[column][line] == getEnnemyStone(stone)) return 0;
        visited[column][line] = true;

        int liberties = 0;
        if (isLiberty(column - 1, line)) liberties++;
        if (isLiberty(column + 1, line)) liberties++;
        if (isLiberty(column, line - 1)) liberties++;
        if (isLiberty(column, line + 1)) liberties++;

        // Appel récursif pour les cases adjacentes de la même couleur
        if (inBounds(column - 1, line) && board[column - 1][line] == stone)
            liberties += getNbLibertiesHelper(column - 1, line, stone, visited);
        if (inBounds(column + 1, line) && board[column + 1][line] == stone)
            liberties += getNbLibertiesHelper(column + 1, line, stone, visited);
        if (inBounds(column, line - 1) && board[column][line - 1] == stone)
            liberties += getNbLibertiesHelper(column, line - 1, stone, visited);
        if (inBounds(column, line + 1) && board[column][line + 1] == stone)
            liberties += getNbLibertiesHelper(column, line + 1, stone, visited);

        if(liberties == 0)
            removePiece(column, line);
        return liberties;
    }

    private void removePiece(int column, int line) {
        assert (inBounds(column, line));
        if (board[column][line] != Stone.UNDEFINED)
            getOpponentPlayer(board[column][line]).incrementNbCaptured();
        board[column][line] = Stone.UNDEFINED;
    }

    private Player getOpponentPlayer(Stone stone) {
        if (stone == Stone.WHITE)
            return black;
        else
            return white;
    }

    private boolean inBounds(int column, int line) {
        return column < NB_BOXES && line < NB_BOXES && column >= 0 && line >= 0;
    }

    private void checkCaptured() {
        //@FIXME Améliorer la complexité
        // @TODO Faire un Set
        boolean[][] visited = new boolean[NB_BOXES][NB_BOXES];
        for (int column = 0; column < NB_BOXES; column++)
            for (int line = 0; line < NB_BOXES; line++) {
                if (board[column][line] != Stone.UNDEFINED)
                    getNbLiberties(column, line, visited);
            }
    }

    public String toString() {
        return showboard();
    }
}