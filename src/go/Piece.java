package go;

public class Piece {
    private Color color;
    private Coordinates coordinates;
    public Piece(Coordinates coordinates, Color color) {
        this.coordinates = coordinates;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
