package ihm;

import go.Goban;

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
            nb_commands = Integer.parseInt(arguments[0]);
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
            input = input.replaceAll("\\s+", " ").trim().toUpperCase();
            String[] arguments = input.split(" ");
            command = getCommand(arguments); // @FIXME Vraiment utile ?
            arguments = command.split(" ");
            goban = new Goban();
            if (arguments[0].equals("BOARDSIZE")) {
                // @TODO Créer méthode callBoardsize
                if (arguments.length > 1) { // S'il y a quelque chose après "boardsize"
                    try { // Si le prochain paramètre est un entier, on appelle la fonction boardsize()
                        goban.boardsize(Integer.parseInt(command.split(" ")[1]));
                        System.out.println("=" + nb_commands + '\n');
                    } catch (IllegalArgumentException exception) {
                        System.out.println("?" + nb_commands + " invalid number\n");
                    }
                } else
                    System.out.println("?" + nb_commands + " invalid command\n");
            } else if (command.equals("QUIT")) {
                System.out.println("=" + nb_commands + '\n');
                break;
            } else if (command.equals("CLEAR_BOARD")) {
                System.out.println("=" + nb_commands + '\n');
                goban.clear_board();
            } else if (command.equals("SHOWBOARD")) {
                System.out.println("=" + nb_commands);
                System.out.println(goban.showboard());
            } else if (arguments[0].equals("PLAY")) {
                System.out.println("=" + nb_commands + '\n');
                goban.play(arguments);
            } else
                System.out.println("?" + nb_commands + '\n');
        } while (true);
        sc.close();
    }
}