import Goban.Goban;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int nb_commands = 0;
    public static String getCommand(String[] arguments) {
        StringBuilder tmp = new StringBuilder();
        boolean first = true;
        for (String s: arguments) {
            if (!first)
                tmp.append(" ");
            tmp.append(s);
            first = false;
        }
        try { // S'il y a un nombre avant la commande
            Integer.parseInt(arguments[0]);
        } catch (NumberFormatException e) {
            return tmp.toString();
        }
        tmp.delete(0,2); // On supprime le 1er argument
        return tmp.toString();
    }

    public static void main(String[] args)throws IllegalArgumentException {
        Scanner sc = new Scanner(System.in);
        String input = "", command;
        Goban goban = null;
        do {
            nb_commands++;
            input = sc.nextLine(); // Lire la prochaine ligne
            input = input.replaceAll("\\s+", " ").trim().toLowerCase();
            String[] arguments = input.split(" ");
            command = getCommand(arguments);
            arguments = command.split(" ");
            if (arguments[0].equals("boardsize")) {
                if (arguments.length > 1) { // S'il y a quelque chose après "boardsize"
                    try { // Si le prochain paramètre est un entier, on appelle la fonction boardsize()
                        System.out.println("=" + nb_commands);
                        goban = new Goban();
                        goban.boardsize(Integer.parseInt(command.split(" ")[1]));
                    } catch (IllegalArgumentException exception) {
                        System.out.println("?" + nb_commands);
                    }
                } else
                    System.out.println("?" + nb_commands);
            } else if (command.equals("quit")) {
                System.out.println("="+nb_commands);
                break;
            } else if (goban == null) {
                System.out.println("?" + nb_commands);
            } else if (command.equals("clear_board")) {
                System.out.println("=" + nb_commands);
                goban.clear_board();
            } else if (command.equals("showboard")) {
                System.out.println("=" + nb_commands);
                System.out.println(goban.showboard());
            } else
                System.out.println("?" + nb_commands);
        } while (true);
        sc.close();
    }
}