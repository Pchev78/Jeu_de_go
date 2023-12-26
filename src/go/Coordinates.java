package go;

public record Coordinates(int column, int line) {
    // @TODO Privilégier les assertions ici plutôt que dans le goban (pas supérieur à NB_BOXES, etc)
}
