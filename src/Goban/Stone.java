package Goban;

public enum Stone {
    BLACK,
    WHITE,
    UNDEFINED;
    @Override
    public String toString() {
        return switch (this) {
            case BLACK -> "B";
            case WHITE -> "W";
            case UNDEFINED -> "."; // Using '+' or any other character to represent an empty space
            default -> throw new IllegalArgumentException();
        };
    }
};
