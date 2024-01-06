package go;

public enum Color {
    BLACK,
    WHITE,
    UNDEFINED;
    @Override
    public String toString() {
        return switch (this) {
            case BLACK -> "X";
            case WHITE -> "O";
            case UNDEFINED -> "."; // Représente une case non occupée par un joueur
        };
    }
}