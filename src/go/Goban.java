package go;

import go.players.ConsolePlayer;
import go.players.RandomPlayer;

import java.util.*;

public class Goban {
    // Nombres par défaut, mais pourront évoluer si on appelle boardsize
    private static int NB_BOXES = 19, INDEX_SHOW_CAPTURED_WHITE = 10, INDEX_SHOW_CAPTURED_BLACK = 9;
    private static final int MIN_BOXES = 2, MAX_BOXES = 25;
    private static final int INDEX_BEGINNING_ALPHABET = 'A';
    private static final int NB_ARGUMENTS_PLAY = 2;
    private static final int INDEX_COLOR_PLAY = 0, INDEX_COORDINATES_PLAY = 1;
    private static final int INDEX_COLOR_PLAYER = 0, INDEX_PLAYER_TYPE = 1;
    private static final int INDEX_COLUMNS = 0, INDEX_LINES = 1;
    private static final int INDEX_SHOW_CAPTURED = 11;
    private static String headerLetters; // Ligne composée de NB_CASES lettres
    private Piece[][] board;
    private IPlayer white, black;
    public Goban() {
        headerLetters = getHeader();
        clear_board();
    }

    public Goban(int size, String string) {
        white = new ConsolePlayer(Color.WHITE,false);
        black = new ConsolePlayer(Color.BLACK,false);
        boardsize(size);
        String[] moves = string.split(" ");
        black.setIsTurn(true);
        for (String move : moves) {
            playSGF(move);
        }
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
        board = new Piece[NB_BOXES][NB_BOXES];
        for (int row = 0; row < NB_BOXES; row++) {
            for (int column = 0; column < NB_BOXES; column++) {
                board[row][column] = new Piece(new Coordinates(row, column), Color.UNDEFINED);
            }
        }
        if (white != null)
            white.resetNbCaptured();
        if (black != null)
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
                sb.append(' ').append(board[j][i].getColor());

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
        message[INDEX_COLOR_PLAY] = arguments[INDEX_COLOR_PLAY];
        message[INDEX_COORDINATES_PLAY] = arguments[INDEX_COORDINATES_PLAY];
        return message;
    }

    public Coordinates getCoordinates(String coordinatesArg) {
        char[] lineArg = new char[coordinatesArg.length() - 1];
        coordinatesArg.getChars(1,coordinatesArg.length(),lineArg,0);
        int row = coordinatesArg.charAt(INDEX_COLUMNS) - INDEX_BEGINNING_ALPHABET;
        int column;
        if (Character.isDigit(coordinatesArg.charAt(INDEX_LINES)))
            column = Integer.parseInt(String.valueOf(lineArg)) - 1;
        else
            column = coordinatesArg.charAt(INDEX_LINES) - INDEX_BEGINNING_ALPHABET;
        return new Coordinates(row, column);
    }

    public IPlayer[] getPlayers(String color) {
        IPlayer[] players = new IPlayer[2];
        players[0] = color.equals("BLACK") ? black : color.equals("WHITE") ? white : null;
        players[1] = color.equals("BLACK") ? white : color.equals("WHITE") ? black : null;
        return players;
    }

    public void player(String[] parameters) throws IllegalArgumentException {
        if (Objects.equals(parameters[INDEX_COLOR_PLAYER],"WHITE") && white == null)
            white = definePlayer(Color.WHITE, parameters[INDEX_PLAYER_TYPE]);
        else if (Objects.equals(parameters[INDEX_COLOR_PLAYER],"BLACK") && black == null)
            black = definePlayer(Color.BLACK, parameters[INDEX_PLAYER_TYPE]);
        else
            throw new IllegalArgumentException("you didn't define correctly your player's color");
    }

    private IPlayer definePlayer(Color color, String playerType) {
        boolean isTurn = (color == Color.BLACK); // Les noirs jouent en premier
        if (Objects.equals(playerType, "CONSOLE"))
            return new ConsolePlayer(color, isTurn);
        if (Objects.equals(playerType,"RANDOM"))
            return new RandomPlayer(color, isTurn);
        throw new IllegalArgumentException("you didn't define correctly your player's color");
    }

    private boolean checkMessage(Coordinates coordinates, IPlayer player, IPlayer player2) {
        try {
            if (player == null || coordinates.row() + 1 < 0 ||
                    coordinates.row() + 1 > NB_BOXES || coordinates.column() < 0 || coordinates.column() > NB_BOXES)
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
        String messageColor = message[INDEX_COLOR_PLAY];
        IPlayer[] players = getPlayers(messageColor);
        IPlayer player = players[0], player2 = players[1];

        Coordinates coordinates = getCoordinates(message[INDEX_COORDINATES_PLAY]);
        String output = checkMove(coordinates, player, player2);
        if (Objects.equals(output, "")) { // S'il n'y a pas eu d'erreur
            addPiece(player, coordinates);
            checkCaptured();
        }
        return output;
    }

    public void playSGF (String move) {
        if(move.length() == 2) {
            if (changeTurn(black, white))
                play(new String[]{"BLACK",move.toUpperCase()});
            else {
                changeTurn(white, black);
                play(new String[]{"WHITE",move.toUpperCase()});
            }
        } else
            throw new IllegalArgumentException("Illegal sgf move : " + move);
    }

    public String checkMove(Coordinates coordinates, IPlayer player, IPlayer player2) {
        if (!checkMessage(coordinates, player, player2))
            return "invalid color or coordinate";
        else if (!(board[coordinates.row()][coordinates.column()].getColor() == Color.UNDEFINED) ||
                isSuicide(coordinates, new Piece(coordinates,getColorByPlayer(player))))
            return "illegal move";
        return "";
    }

    private boolean isSuicide(Coordinates coordinates, Piece piece) {
        return getNbLiberties(coordinates, piece.getColor()) == 0;
    }

    public Color getColorByPlayer(IPlayer player) {
        if (player.getColor().equals("BLACK"))
            return Color.BLACK;
        else if (player.getColor().equals("WHITE"))
            return Color.WHITE;
        return Color.UNDEFINED;
    }

    public Color getEnemyColor(Color color) {
        if (color == Color.BLACK)
            return Color.WHITE;
        else if (color == Color.WHITE)
            return Color.BLACK;
        return Color.UNDEFINED;
    }

    public void addPiece (IPlayer player, Coordinates coordinates) {
        Color color = getColorByPlayer(player);
        board[coordinates.row()][coordinates.column()] = new Piece(coordinates, color);
    }

    /**
     * Permet d'inverser les tours.
     * @param player : joueur qui va terminer son tour
     * @param player2 : joueur qui va jouer au tour suivant
     * @return true si c'est le tour de player, false sinon
     */
    public boolean changeTurn(IPlayer player, IPlayer player2) {
        if (!player.getIsTurn())
            return false;
        else {
            player.setIsTurn(false);
            player2.setIsTurn(true);
            return true;
        }
    }

    private boolean isLiberty(int row, int column) {
        return inBounds(row,column) && board[row][column].getColor() == Color.UNDEFINED;
    }

    public int getNbLiberties(int row, int column) {
        return getNbLiberties(new Coordinates(row, column), board[row][column].getColor());
    }

    public int getNbLiberties(Coordinates coordinates, Color color) {
        Set<Coordinates> ignore = new HashSet<>(); // Pour garder une trace des pions déjà visités
        return getNbLibertiesHelper(coordinates, color, ignore, 0);
    }

    public int getNbLiberties(Coordinates coordinates, Set<Coordinates> ignore) {
        return getNbLibertiesHelper(coordinates, board[coordinates.row()][coordinates.column()].getColor(), ignore, 0);
    }

    private int getNbLibertiesHelper(Coordinates coordinates, Color color, Set<Coordinates> visited, int liberties) {
        int row = coordinates.row(), column = coordinates.column();
        if (!inBounds(row, column)) return 0;

        // Utiliser une variable locale pour stocker la couleur de la case courante
        Color currentColor = board[row][column].getColor();

        if (visited.contains(coordinates) ||
                currentColor == getEnemyColor(color)) return 0;
        visited.add(coordinates);

        // Utiliser une boucle for pour parcourir les quatre directions
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int newRow = row + dx[i];
            int newColumn = column + dy[i];
            if (isLiberty(newRow, newColumn)) liberties++;
            // Appel récursif pour les cases adjacentes de la même couleur
            if (inBounds(newRow, newColumn) && board[newRow][newColumn].getColor() == color)
                liberties += getNbLibertiesHelper(new Coordinates(newRow, newColumn), color, visited, liberties);
        }

        if (liberties == 0) {
            removePiece(coordinates);
        }
        return liberties;
    }


    private void removePiece(Coordinates coordinates) {
        assert (inBounds(coordinates.row(), coordinates.column()));
        int row = coordinates.row(), column = coordinates.column();
        if (board[row][column].getColor() != Color.UNDEFINED)
            getOpponentPlayer(board[row][column]).incrementNbCaptured();
        board[row][column] = new Piece(coordinates,Color.UNDEFINED);
    }

    private IPlayer getOpponentPlayer(Piece stone) {
        return stone.getColor() == Color.WHITE ? black : white;
    }

    private boolean inBounds(int row, int column) {
        return row < NB_BOXES && column < NB_BOXES && row >= 0 && column >= 0;
    }

    private void checkCaptured() {
        //@FIXME Améliorer la complexité
        Set<Coordinates> ignore = new HashSet<>();
        for (int row = 0; row < NB_BOXES; row++)
            for (int column = 0; column < NB_BOXES; column++) {
                if (board[row][column].getColor() != Color.UNDEFINED)
                    getNbLiberties(new Coordinates(row, column), ignore);
            }
    }

    public HashMap<Integer, ArrayList<Integer>> getEmptyBoxes(Piece stone) {
        HashMap<Integer, ArrayList<Integer>> emptyBoxes = new HashMap<>();
        for (int row = 0; row < NB_BOXES; row++) {
            ArrayList<Integer> columnsList = new ArrayList<>();
            for (int column = 0; column < NB_BOXES; column++) {
                if (board[row][column].getColor() == Color.UNDEFINED &&
                        !isSuicide(new Coordinates(row, column), stone)) {
                    columnsList.add(column);
                }
            }
            if (!columnsList.isEmpty())
                emptyBoxes.put(row, columnsList);
        }
        return emptyBoxes;
    }

    public String toString() {
        return showboard();
    }
}