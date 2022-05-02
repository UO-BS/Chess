public class Position{

    private int xPos;
    private int yPos;
    private Piece currentPiece;

    public Position(int x, int y){
        xPos = x;
        yPos = y;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return xPos;
    }

    public int getXDistance(Position other){ //A positive value means going right on the board
        return (other.xPos - this.xPos);
    }

    public int getYDistance(Position other){ //A positive value means going up on the board
        return (other.yPos - this.yPos);
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece newPiece) {
        currentPiece = newPiece;
    }

}