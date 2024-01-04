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

    public boolean canMove() {
        // TODO si le voisin est égal à UNDEFINED
        Coordinates coordinatesNeighborL = new Coordinates(coordinates.row() - 1, coordinates.column());
        Coordinates coordinatesNeighborR = new Coordinates(coordinates.row() + 1, coordinates.column());
        Coordinates coordinatesNeighborT = new Coordinates(coordinates.row(), coordinates.column() - 1);
        Coordinates coordinatesNeighborB = new Coordinates(coordinates.row(), coordinates.column() + 1);
        return true;
    }
}
