package ihm;

import go.Goban;
import java.util.Scanner;

public class Main {
    private static boolean showNbCommands = false;
    private static int nb_commands;
    private static Goban goban = new Goban();
    public static String getCommand(String[] arguments) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        int i = 0;
        try { // S'il y a un nombre avant la commande
            nb_commands = Integer.parseInt(arguments[0]);
            showNbCommands = true;
            i = 1;
        } catch (NumberFormatException e) {
            showNbCommands = false;
        }

        for (;i < arguments.length; i ++) {
            if (!first)
                sb.append(" ");
            sb.append(arguments[i]);
            first = false;
        }
        return sb.toString();
    }

    public static void callBoardsize(String[] arguments, String command) {
        if (arguments.length > 1) { // S'il y a quelque chose après "boardsize"
            try { // Si le prochain paramètre est un entier, on appelle la fonction boardsize()
                goban.boardsize(Integer.parseInt(command.split(" ")[1]));
                System.out.println(displayOutput("") + '\n');
            } catch (NumberFormatException exception) {
                System.out.println(displayOutput("boardsize not an integer") + '\n');
            } catch (IllegalArgumentException exception) {
                System.out.println(displayOutput("unacceptable size") + '\n');
            }
        } else
            System.out.println(displayOutput("boardsize not an integer") + '\n');
    }

    public static String displayOutput(String error) {
        StringBuilder sb = new StringBuilder();
        if (!error.isEmpty())
            sb.append("? ").append(error);
        else
            sb.append('=');
        if (showNbCommands)
            sb.append(nb_commands);
        return sb.toString();
    }

    public static void main(String[] args) throws IllegalArgumentException {
        Scanner sc = new Scanner(System.in);
        String input = "", command;
        do {
            input = sc.nextLine(); // Lire la prochaine ligne
            input = input.replaceAll("\\s+", " ").trim().toUpperCase();
            String[] arguments = input.split(" ");
            command = getCommand(arguments); // @FIXME Vraiment utile ?
            arguments = command.split(" ");
            if (arguments[0].equals("BOARDSIZE")) {
                callBoardsize(arguments, command);
            } else if (command.equals("QUIT")) {
                System.out.println(displayOutput("") + '\n');
                break;
            } else if (command.equals("CLEAR_BOARD")) {
                System.out.println(displayOutput("") + '\n');
                goban.clear_board();
            } else if (command.equals("SHOWBOARD")) {
                System.out.println(displayOutput(""));
                System.out.println(goban.showboard());
            } else if (arguments[0].equals("PLAY")) {
                System.out.println(displayOutput(goban.play(arguments)) + '\n');
            } else
                System.out.println(displayOutput("unknown command") + '\n');
        } while (true);
        sc.close();
    }
}