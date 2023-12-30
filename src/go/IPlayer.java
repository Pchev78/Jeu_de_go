package go;

public interface IPlayer {
    String getColor();
    void resetNbCaptured();
    void incrementNbCaptured();
    void setIsTurn(boolean isTurn);
    boolean getIsTurn();
    String stringifyNbCaptured();
}
