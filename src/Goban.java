public class Goban {
    private static int NB_BOXES = 19;
    private static final int INDEX_BEGINNING_ALPHABET = 65; // 65 correspond à la 1ʳᵉ lettre en majuscule de l'alphabet (A)
    private static String headerLetters; // Ligne composée de NB_CASES lettres
    public Goban() {
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
        else
            NB_BOXES = nbBoxes;
    }

    public static String showboard() {
        // @TODO La fonction peut être optimisée
        StringBuilder sb = new StringBuilder();
        /*
          A B C D E
        1           1
        2           2
        3           3
        4           4
        5           5
          A B C D E
         */
        sb.append(headerLetters); // 1ʳᵉ ligne
        for (int i = 1; i < NB_BOXES; i++) { // Ligne
            sb.append("\n");
            sb.append(i);
            //@TODO Peut mieux faire en complexité
            for (int j = 0; j < NB_BOXES; j++) { // Colonne
                sb.append(' ');
                sb.append('v'); // Remplacer le v par la valeur qu'il y a dans cette case
            }
            sb.append(' ');
            sb.append(i);
        }
        sb.append("\n");
        sb.append(headerLetters); // Dernière ligne
        return sb.toString();
    }


}