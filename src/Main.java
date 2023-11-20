import Goban.Goban;

import java.util.Scanner;

public class Main {
    private static int nb_commands = 0;
    public static void main(String[] args)throws IllegalArgumentException {
        Scanner sc = new Scanner(System.in);
        String command = "";
        Goban goban = new Goban();
        do {
            nb_commands++;
            command = sc.next(); // Lire la prochaine ligne
            System.out.println("=" + nb_commands);
            if (command.equals("boardsize")) {
                try { // Si le prochain paramètre est un entier, on appelle la fonction boardsize()
                    goban.boardsize(Integer.parseInt(sc.next()));
                } catch (IllegalArgumentException illegalArgumentException) {
                    System.out.println("Veuillez saisir un entier après la commande boardsize");
                }
            } else if (command.equals("clear_board")) {
                goban.clear_board();
            } else if (command.equals("showboard")) {
                System.out.println(goban.showboard());
            } else {
                System.out.println("Commande invalide");
            }
        } while (!command.equals("quit"));
        sc.close();
    }
}
