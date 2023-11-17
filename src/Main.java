import java.util.Scanner;

public class Main {
    private static final int MAX_COMMANDS = 10; // @TODO Valeur à changer
    public static void main(String[] args) {
        int nb_commands = 0;
        Scanner sc = new Scanner(System.in);
        String command = "";
        int param = 0;
        Goban goban = new Goban();
        do {
            nb_commands++;
            command = sc.next(); // Lire la prochaine ligne
            if (command.equals("boardsize")) {
                param = Integer.parseInt(sc.next()); // Lire le prochain paramètre @TODO A changer, vérifier que ce soit bien un entier après "boardsize"
                goban.boardsize(param);
            } else if (command.equals("showboard")) {
                System.out.println(goban.showboard());
            } else {
                System.out.println("Commande invalide");
            }
        } while (!command.equals("quit") || nb_commands < MAX_COMMANDS);
    }
}
