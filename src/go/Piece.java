package go;

public class Piece {
    private Color color;
    private Coordinates coordinates; // TODO Les coordonnées auraient pu être utilisées par la suite pour la vérification des libertés
    public Piece(Coordinates coordinates, Color color) {
        this.coordinates = coordinates;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
