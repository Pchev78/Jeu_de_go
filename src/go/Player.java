package go;

public interface Player {
    String getColor();
    int getNbCaptured();
    void resetNbCaptured();
    void incrementNbCaptured();
    void setIsTurn(boolean pIsTurn);
    boolean getIsTurn();
    String stringifyNbCaptured();
}
