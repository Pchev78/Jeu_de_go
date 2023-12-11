package go;

public enum Stone {
    BLACK,
    WHITE,
    UNDEFINED;
    @Override
    public String toString() {
        return switch (this) {
            case BLACK -> "X";
            case WHITE -> "O";
            case UNDEFINED -> "."; // Représente une case non occupée par un joueur
            default -> throw new IllegalArgumentException();
        };
    }
};
