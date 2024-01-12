package go;

import go.players.ConsolePlayer;
import go.players.RandomPlayer;

import java.util.*;

public class Goban {
    // Nombres par défaut, mais pourront évoluer si on appelle boardsize
    private int NB_BOXES = 19, INDEX_SHOW_CAPTURED_WHITE = 10, INDEX_SHOW_CAPTURED_BLACK = 9;
    private static final int MIN_BOXES = 2, MAX_BOXES = 25; // Constantes pour les tailles minimale et maximale du Goban.
    private static final int INDEX_BEGINNING_ALPHABET = 'A';
    private static final int INDEX_COLOR_PLAY = 0, INDEX_COORDINATES_PLAY = 1; // Indices pour la couleur et les coordonnées du coup joué.
    private static final int INDEX_COLOR_PLAYER = 0, INDEX_PLAYER_TYPE = 1; // Indices pour la couleur et le type du joueur.
    private static final int INDEX_SHOW_CAPTURED = 11; // Indice pour savoir où afficher le score des joueurs
    private static String headerLetters; // En-tête composé de lettres correspondant à la taille du Goban.
    private Piece[][] board;
    private IPlayer white, black;

    /**
     * Constructeur par défaut de la classe Goban
     */
    public Goban() {
        boardsize(19);
        white = null;
        black = null;
    }

    /**
     * Constructeur pour tester plus rapidement
     * @param size : la taille du tableau
     * @param string : les coups joués alternativement par les joueurs, au format SGF
     * @see playSGF(String)
     */
    public Goban(int size, String string) {
        white = new ConsolePlayer(Color.WHITE,false);
        black = new ConsolePlayer(Color.BLACK,false);
        boardsize(size);
        String[] moves = string.split(" ");
        black.setIsTurn(true);
        for (String move : moves) {
            playSGF(move); // Jouer les mouvements spécifiés dans la chaine
        }
    }

    /**
     * @return la chaîne de lettres qui sera affichée pour représenter les colonnes
     */
    private String getHeader() {
        StringBuilder headerLetters= new StringBuilder();
        headerLetters.append("   "); // Espaces initiaux pour l'alignement.
        boolean first = true;
        for (int i = INDEX_BEGINNING_ALPHABET; i < INDEX_BEGINNING_ALPHABET + NB_BOXES; i++) {
            if (!first)
                headerLetters.append(' ');
            headerLetters.append((char) i);
            first = false;
        }
        return headerLetters.toString();
    }

    /**
     * Redéfinit la taille du tableau
     * @param nbBoxes : nombre de cases que l'utilisateur veut
     * @throws NumberFormatException : si l'utilisateur a demandé une taille trop grande ou trop petite
     */
    public void boardsize(int nbBoxes) {
        if (nbBoxes > MAX_BOXES) {
            throw new NumberFormatException("Nombre de cases trop grand");
        }
        if (nbBoxes < MIN_BOXES) {
            throw new NumberFormatException("Nombre de cases trop petit");
        }

        // Calcul des indices pour l'affichage des pierres capturées en fonction de la taille du plateau.
        if (nbBoxes >= INDEX_SHOW_CAPTURED) {
            INDEX_SHOW_CAPTURED_WHITE = nbBoxes - INDEX_SHOW_CAPTURED + 2;
            INDEX_SHOW_CAPTURED_BLACK = nbBoxes - INDEX_SHOW_CAPTURED + 1;
        } else {
            INDEX_SHOW_CAPTURED_WHITE = 1;
            INDEX_SHOW_CAPTURED_BLACK = 0;
        }
        NB_BOXES = nbBoxes;
        headerLetters = getHeader(); // Mise à jour de l'en-tête.
        clear_board();
    }

    /**
     * Initialise le tableau avec des pièces de couleur indéfinie
     */
    public void clear_board() {
        board = new Piece[NB_BOXES][NB_BOXES];
        for (int column = 0; column < NB_BOXES; column++) {
            for (int row = 0; row < NB_BOXES; row++) {
                // Chaque case du plateau reçoit une nouvelle pièce de couleur UNDEFINED avec ses coordonnées.
                board[column][row] = new Piece(new Coordinates(column, row), Color.UNDEFINED);
            }
        }
        if (white != null)
            white.resetNbCaptured();
        if (black != null)
            black.resetNbCaptured();
    }

    /**
     * Affiche le goban
     * @return le tableau et le score de ses joueurs
     */
    public String showboard() {
        StringBuilder sb = new StringBuilder();
        sb.append(headerLetters); // 1ʳᵉ ligne

        // Parcours des lignes du plateau à partir du haut.
        for (int column = NB_BOXES - 1; column >= 0; column--) { // Ligne
            sb.append("\n");
            if (column < 9)
                sb.append(' ');
            sb.append(column + 1);
            for (int row = 0; row < NB_BOXES; row++)
                sb.append(' ').append(board[row][column].getColor());

            sb.append(' ').append(column + 1);

            if(column == INDEX_SHOW_CAPTURED_WHITE) {
                if (white == null)
                    sb.append("     ").append("WHITE (O) has captured 0 stones");
                else
                    sb.append("     ").append(white.stringifyNbCaptured());
            }
            else if (column == INDEX_SHOW_CAPTURED_BLACK) {
                if (black == null)
                    sb.append("     ").append("BLACK (X) has captured 0 stones");
                else
                    sb.append("     ").append(black.stringifyNbCaptured());
            }
        }
        sb.append("\n").append(headerLetters).append("\n"); // Dernière ligne
        return sb.toString();
    }

    /**
     * @param color : la couleur selon laquelle on retourne les joueurs
     * @return les joueurs en fonction de color
     */
    private IPlayer[] getPlayers(String color) {
        IPlayer[] players = new IPlayer[2];
        players[0] = color.equals("BLACK") ? black : color.equals("WHITE") ? white : null;
        players[1] = color.equals("BLACK") ? white : color.equals("WHITE") ? black : null;
        return players;
    }

    /**
     * Permet de définir les joueurs
     * @param parameters : la couleur et le type du joueur
     * @throws IllegalArgumentException si l'utilisateur a mal défini son player
     */
    public void player(String[] parameters) throws IllegalArgumentException {
        if (Objects.equals(parameters[INDEX_COLOR_PLAYER],"WHITE") && white == null)
            white = definePlayer(Color.WHITE, parameters[INDEX_PLAYER_TYPE]);
        else if (Objects.equals(parameters[INDEX_COLOR_PLAYER],"BLACK") && black == null)
            black = definePlayer(Color.BLACK, parameters[INDEX_PLAYER_TYPE]);
        else
            throw new IllegalArgumentException("you didn't define correctly your player's color");
        if (white != null && black != null && Objects.equals(black.getPlayerType(), "AI")) {
            playAI(black);
        }
    }

    /**
     * Définit un player en fonction de son type
     * @param color : la couleur du joueur
     * @param playerType : le type de joueur (CONSOLE ou RANDOM)
     * @throws IllegalArgumentException si l'utilisateur a mal défini son player
     * @return le joueur créé
     */
    private IPlayer definePlayer(Color color, String playerType) {
        boolean isTurn = (color == Color.BLACK); // Les noirs jouent en premier
        if (Objects.equals(playerType, "CONSOLE"))
            return new ConsolePlayer(color, isTurn);
        if (Objects.equals(playerType,"RANDOM"))
            return new RandomPlayer(color, isTurn);
        throw new IllegalArgumentException("you didn't define correctly your player's color");
    }

    /**
     * Vérifie que le message envoyé par l'humain est correct
     * @param coordinates : l'endroit où la personne veut jouer
     * @param player : le joueur qui va jouer le coup
     * @return true si le message est correct, false sinon
     */
    private boolean checkMessage(Coordinates coordinates, IPlayer player) {
        try {
            if (player == null || coordinates.column() + 1 < 0 ||
                    coordinates.column() + 1 > NB_BOXES || coordinates.row() < 0 || coordinates.row() > NB_BOXES)
                throw new IndexOutOfBoundsException("invalid color or coordinate");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param coordinates : coordonnées auxquelles le joueur veut ajouter la pièce
     * @param player : le joueur qui va jouer
     * @param player2 : l'ennemi de player
     * @return le message d'erreur s'il y en a un, une chaine vide sinon.
     */
    private String play(Coordinates coordinates, IPlayer player, IPlayer player2) {
        try {
            checkMove(coordinates, player);
            addPiece(player, coordinates);
            checkCaptured();
            changeTurn(player, player2);
            // Joue automatiquement si le deuxième joueur est une IA.
            if (Objects.equals(player2.getPlayerType(), "AI"))
                playAI(player2);
            return "";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /**
     * Permet de récupérer les coordonnées et les joueurs en fonction d'arguments
     * @param arguments : la couleur du joueur et les coordonnées auxquelles il veut jouer
     * @return une erreur s'il y en a une, une chaine vide sinon.
     */
    public String play(String[] arguments) {
        String color = arguments[INDEX_COLOR_PLAY];
        String coordinatesText = arguments[INDEX_COORDINATES_PLAY];
        IPlayer[] players = getPlayers(color);
        IPlayer player = players[0], player2 = players[1];

        Coordinates coordinates = Coordinates.getCoordinates(coordinatesText);
        return play(coordinates, player, player2);
    }

    /**
     * Joue un coup à la position spécifiée en fonction de la colonne et de la ligne.
     * @param column la colonne de la position
     * @param row la ligne de la position
     * @return une erreur s'il y en a une, une chaine vide sinon.
     */
    public String play(int column, int row) {
        Coordinates coordinates = new Coordinates(column, row);
        IPlayer player, player2;
        if (changeTurn(black, white)) {
            player = black;
            player2 = white;
        } else {
            player = white;
            player2 = black;
        }
        return play(coordinates, player, player2);
    }

    /**
     * Joue un coup pour l'IA.
     * @param ai le joueur IA
     * @return une erreur s'il y en a une, une chaine vide sinon.
     */
    private String playAI(IPlayer ai) {
        IPlayer player2 = getOpponentPlayer(ai.getColor());
        System.out.println(showboard());
        if (Objects.equals(ai.getPlayerType(), "AI") && ai.getIsTurn()) {
            Coordinates coordinates = ai.play(getEmptyBoxes());
            return play(coordinates, ai, player2);
        }
        return "";
    }

    /**
     * Joue un coup à partir du constructeur SGF.
     * @see Goban(int, String)
     * @param move le coup à jouer
     * @throws IllegalArgumentException si le coup est illégal
     */
    public void playSGF (String move) {
        if(move.length() != 2) {
            throw new IllegalArgumentException("Illegal sgf move : " + move);
        }

        if (changeTurn(black, white))
            play(new String[]{"BLACK",move.toUpperCase()});
        else {
            changeTurn(white, black);
            play(new String[]{"WHITE",move.toUpperCase()});
        }
    }

    /**
     * Vérifie si le coup est légal.
     * @param coordinates les coordonnées du coup
     * @param player le joueur qui joue le coup
     * @throws IllegalArgumentException si le coup est illégal
     */
    private void checkMove(Coordinates coordinates, IPlayer player) {
        if (!checkMessage(coordinates, player)) {
            throw new IllegalArgumentException("invalid color or coordinate");
        }

        if (!(board[coordinates.column()][coordinates.row()].getColor() == Color.UNDEFINED) || isSuicide(coordinates, new Piece(coordinates,player.getColor()))) {
            throw new IllegalArgumentException("illegal move");
        }
    }

    /**
     * Vérifie si le coup est un suicide.
     * @param coordinates les coordonnées du coup
     * @param piece la pièce jouée
     * @return true si le coup est un suicide, false sinon
     */
    private boolean isSuicide(Coordinates coordinates, Piece piece) {
        return getNbLiberties(coordinates, piece.getColor()) == 0;
    }

    /**
     * Ajoute une pièce sur le plateau.
     * @param player le joueur qui joue le coup
     * @param coordinates les coordonnées du coup
     */
    private void addPiece (IPlayer player, Coordinates coordinates) {
        Color color = player.getColor();
        board[coordinates.column()][coordinates.row()] = new Piece(coordinates, color);
    }

    /**
     * Permet d'inverser les tours.
     * @param player : joueur qui va terminer son tour
     * @param player2 : joueur qui va jouer au tour suivant
     * @return true si c'est le tour de player, false sinon
     */
    private boolean changeTurn(IPlayer player, IPlayer player2) {
        if (!player.getIsTurn())
            return false;
        else {
            player.setIsTurn(false);
            player2.setIsTurn(true);
            return true;
        }
    }

    /**
     * Vérifie si une case est libre.
     * @param column la colonne de la case
     * @param row la ligne de la case
     * @return true si la case est libre, false sinon
     */
    private boolean isLiberty(int column, int row) {
        return inBounds(column, row) && board[column][row].getColor() == Color.UNDEFINED;
    }

    /**
     * @param column la colonne de la case
     * @param row la ligne de la case
     * @return le nombre de libertés de la case
     */
    public int getNbLiberties(int column, int row) {
        return getNbLiberties(new Coordinates(column, row), board[column][row].getColor());
    }

    /**
     * Renvoie le nombre de libertés d'une case en créant un Set.
     * @param coordinates les coordonnées de la case
     * @param color la couleur du joueur
     * @return le nombre de libertés de la case
     */
    public int getNbLiberties(Coordinates coordinates, Color color) {
        Set<Coordinates> ignore = new HashSet<>(); // Pour garder une trace des pions déjà visités
        return getNbLibertiesHelper(coordinates, color, ignore, 0);
    }

    /**
     * @param coordinates les coordonnées de la case
     * @param ignore les cases déjà visitées
     * @return le nombre de libertés de la case
     */
    private int getNbLiberties(Coordinates coordinates, Set<Coordinates> ignore) {
        return getNbLibertiesHelper(coordinates, board[coordinates.column()][coordinates.row()].getColor(), ignore, 0);
    }

    /**
     * @param coordinates les coordonnées de la case
     * @param color la couleur du joueur
     * @param visited les cases déjà visitées
     * @param liberties le nombre de libertés
     * @return le nombre de libertés de la case
     */
    private int getNbLibertiesHelper(Coordinates coordinates, Color color, Set<Coordinates> visited, int liberties) {
        int column = coordinates.column(), row = coordinates.row();
        if (!inBounds(column, row) || visited.contains(coordinates)) {
            return 0;
        }

        visited.add(coordinates);

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int newRow = column + dx[i];
            int newColumn = row + dy[i];
            if (isLiberty(newRow, newColumn)) {
                liberties++;
            }
            // Appel récursif pour les cases adjacentes de la même couleur
            if (inBounds(newRow, newColumn) && board[newRow][newColumn].getColor() == color)
                liberties += getNbLibertiesHelper(new Coordinates(newRow, newColumn), color, visited, liberties);
        }

        if (liberties == 0) {
            removePiece(coordinates);
        }
        return liberties;
    }


    /**
     * Supprime une pièce du plateau.
     * @param coordinates les coordonnées de la pièce à supprimer
     */
    private void removePiece(Coordinates coordinates) {
        assert (inBounds(coordinates.column(), coordinates.row()));
        int column = coordinates.column(), row = coordinates.row();
        if (board[column][row].getColor() != Color.UNDEFINED)
            getOpponentPlayer(board[column][row].getColor()).incrementNbCaptured();
        board[column][row] = new Piece(coordinates,Color.UNDEFINED);
    }

    /**
     * @param color la couleur du joueur courant
     * @return le joueur adverse
     */
    private IPlayer getOpponentPlayer(Color color) {
        return color == Color.WHITE ? black : white;
    }

    /**
     * Vérifie si une case est dans les limites du plateau.
     * @param column la colonne de la case
     * @param row la ligne de la case
     * @return true si la case est dans les limites du plateau, false sinon
     */
    private boolean inBounds(int column, int row) {
        return column < NB_BOXES && row < NB_BOXES && column >= 0 && row >= 0;
    }

    /**
     * Vérifie si des pions ont été capturés.
     */
    private void checkCaptured() {
        Set<Coordinates> ignore = new HashSet<>();
        for (int column = 0; column < NB_BOXES; column++)
            for (int row = 0; row < NB_BOXES; row++) {
                if (board[column][row].getColor() != Color.UNDEFINED)
                    getNbLiberties(new Coordinates(column, row), ignore);

            }
    }

    /**
     * @return les cases vides du plateau
     */
    private HashMap<Integer, ArrayList<Integer>> getEmptyBoxes() {
        HashMap<Integer, ArrayList<Integer>> emptyBoxes = new HashMap<>();
        for (int column = 0; column < NB_BOXES; column++) {
            ArrayList<Integer> columnsList = new ArrayList<>();
            for (int row = 0; row < NB_BOXES; row++) {
                Piece stone = board[column][row];
                if (stone.getColor() == Color.UNDEFINED &&
                        !isSuicide(new Coordinates(column, row), stone)) {
                    columnsList.add(row);
                }
            }
            if (!columnsList.isEmpty())
                emptyBoxes.put(column, columnsList);
        }
        return emptyBoxes;
    }

    /**
     * @return une représentation textuelle du plateau
     */
    public String toString() {
        return showboard();
    }

}