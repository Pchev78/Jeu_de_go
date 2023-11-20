package Goban;

public class Goban {
    private static int NB_BOXES = 19;
    private static final int INDEX_BEGINNING_ALPHABET = (int)'A';
    private static String headerLetters; // Ligne composée de NB_CASES lettres
    private Stone[][] board;
    public Goban() {
        this.board = new Stone[NB_BOXES][NB_BOXES];
        headerLetters = getHeader();
    }
    private static String getHeader() {
        StringBuilder headerLetters= new StringBuilder();
        headerLetters.append(' '); // Correspond à la 1ʳᵉ colonne de la colonne. L'espace pourrait être remplacé par un /
        for (int i = INDEX_BEGINNING_ALPHABET; i < INDEX_BEGINNING_ALPHABET + NB_BOXES; i++) {
            headerLetters.append(' ');
            headerLetters.append((char) i);
        }
        return headerLetters.toString();
    }
    public static void boardsize(int nbBoxes) {
        if (nbBoxes > 25)
            System.out.println("Nombre de cases trop grand");
        else if (nbBoxes < 2)
            System.out.println("Nombre de cases trop petit");
        else {
            NB_BOXES = nbBoxes;
            headerLetters = getHeader();
        }
    }

    public void clear_board() {
        //@TODO Peut mieux faire en complexité
        for (int i = 0; i < NB_BOXES; i++) { // Ligne
            for (int j = 0; j < NB_BOXES; j++) { // Colonne
                board[i][j] = Stone.UNDEFINED;
            }
        }
    }

    public static String showboard() {
        // @TODO La fonction peut être optimisée
        StringBuilder sb = new StringBuilder();
        sb.append(headerLetters); // 1ʳᵉ ligne

        //@TODO Peut mieux faire en complexité
        for (int i = 0; i < NB_BOXES; i++) { // Ligne
            sb.append("\n");
            sb.append(i);
            for (int j = 0; j < NB_BOXES; j++) { // Colonne
                sb.append(' ');
                sb.append(Stone.UNDEFINED);
            }
            sb.append(' ');
            sb.append(i);
        }
        sb.append("\n");
        sb.append(headerLetters); // Dernière ligne
        return sb.toString();
    }


}