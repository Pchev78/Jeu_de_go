import java.util.Scanner;

public class main {
    private static int NB_CASES = 13;
    private static int NB_COMMANDES = 0;
    private static void boardsize(int nbCases) {
        if (nbCases > 25)
            System.out.println("Nombre de cases trop grand");
        else if (nbCases < 2)
            System.out.println("Nombre de cases trop petit");
        else
            NB_CASES = nbCases;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String commande = "";
        int parametre = 0;
        do {
            commande = sc.next();
            if (commande.equals("showboard")) {
                parametre = Integer.parseInt(sc.next());
                boardsize(parametre);
            }
        } while (!commande.equals("quit"));
    }
}