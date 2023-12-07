package go;

public interface Player {
    String getColor();
    int getNbCaptured();
    void setIsTurn(boolean pIsTurn);
    boolean getIsTurn();
    String stringifyNbCaptured();
}
