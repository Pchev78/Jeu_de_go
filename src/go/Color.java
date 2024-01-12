package go;

public enum Color {
    // Les trois couleurs possibles : NOIR, BLANC et NON-DÉFINI
    BLACK,
    WHITE,
    UNDEFINED;
    /**
     * Renvoie une représentation textuelle de la couleur.
     *
     * @return Une chaîne de caractères représentant la couleur.
     */
    @Override
    public String toString() {
        return switch (this) {
            case BLACK -> "X";
            case WHITE -> "O";
            case UNDEFINED -> ".";       // Représente une case non occupée par un joueur par "."
        };
    }
}
