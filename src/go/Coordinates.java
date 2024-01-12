package go;

public record Coordinates(int column, int row) {
    private static final int INDEX_COLUMNS = 0, INDEX_LINES = 1;
    // Constante pour le début de l'alphabet utilisé dans les coordonnées.
    private static final char INDEX_BEGINNING_ALPHABET = 'A';
    /**
     * Convertit un message sous forme de chaîne de caractères en un objet Coordinates.
     * @param message La chaîne de caractères représentant les coordonnées, où
     *                le premier caractère est la colonne (lettre) et les caractères suivants
     *                sont la ligne (nombre).
     * @return Un nouvel objet Coordinates représentant la position décrite par le message.
     */
    public static Coordinates getCoordinates(String message) {
        char[] lineArg = new char[message.length() - 1];
        message.getChars(1,message.length(),lineArg,0);
        int column = message.charAt(INDEX_COLUMNS) - INDEX_BEGINNING_ALPHABET;
        int row;
        if (Character.isDigit(message.charAt(INDEX_LINES)))
            row = Integer.parseInt(String.valueOf(lineArg)) - 1;
        else
            row = message.charAt(INDEX_LINES) - INDEX_BEGINNING_ALPHABET;
        return new Coordinates(column, row);
    }
}