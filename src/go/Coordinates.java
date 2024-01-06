package go;

public record Coordinates(int row, int column) {
    private static final int INDEX_COLUMNS = 0, INDEX_LINES = 1;
    private static final char INDEX_BEGINNING_ALPHABET = 'A';
    public static Coordinates getCoordinates(String message) {
        char[] lineArg = new char[message.length() - 1];
        message.getChars(1,message.length(),lineArg,0);
        int row = message.charAt(INDEX_COLUMNS) - INDEX_BEGINNING_ALPHABET;
        int column;
        if (Character.isDigit(message.charAt(INDEX_LINES)))
            column = Integer.parseInt(String.valueOf(lineArg)) - 1;
        else
            column = message.charAt(INDEX_LINES) - INDEX_BEGINNING_ALPHABET;
        return new Coordinates(row, column);
    }
}