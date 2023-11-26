import Goban.Goban;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int nb_commands = 0;
    public static boolean isValid(String command) {
        List<String> validCommands = Arrays.asList("boardsize", "clear_board", "showboard", "quit");
        return validCommands.contains(command);
    }
    public static void main(String[] args)throws IllegalArgumentException {
        Scanner sc = new Scanner(System.in);
        String command = "";
        Goban goban = new Goban();
        do {
            nb_commands++;
            command = sc.next(); // Lire la prochaine ligne
            if (isValid(command)) {
                System.out.println("=" + nb_commands);
                if (command.equals("boardsize")) {
                    try { // Si le prochain paramètre est un entier, on appelle la fonction boardsize()
                        goban.boardsize(Integer.parseInt(sc.next()));
                    } catch (IllegalArgumentException illegalArgumentException) {
                        System.out.println(illegalArgumentException.getLocalizedMessage());
                    }
                } else if (command.equals("clear_board")) {
                    goban.clear_board();
                } else if (command.equals("showboard")) {
                    System.out.println(goban.showboard());
                } else if (command.equals("quit")) {
                    break;
                }
            } else
                System.out.println("?" + nb_commands);
        } while (!command.equals("quit"));
        sc.close();
    }
}