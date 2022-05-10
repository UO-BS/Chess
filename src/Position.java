/**
 * Class for a position on a chess board
 * @author UO-BS
 */
public class Position{

    private int xPos;
    private int yPos;
    private Piece currentPiece;

    /**
     * Generates a Position on a chess board.
     * 
     * @param y The y coordinate of the Position.
     * @param x The x coordinate of the Position.
     */
    public Position(int y, int x){ //NOTE: y comes first since we are using the array[row][column] convention
        xPos = x;
        yPos = y;
    }

    /**
     * Generates a copy of a Position on a chess board.
     * 
     * @param original The original Position being copied
     */
    public Position(Position original){
        xPos = original.xPos;
        yPos = original.yPos;
        if (original.currentPiece != null) {
            currentPiece = original.currentPiece.copyPiece();
            currentPiece.setPosition(this);
        }
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    /**
     * Determines the x-axis distance between 2 positions
     * 
     * @param other The position we are trying to find the distance to.
     * @return The x-axis distance between this position and other. A positive value represents going right on the board
     */
    public int getXDistance(Position other){ 
        return (other.xPos - this.xPos);
    }

    /**
     * Determines the y-axis distance between 2 positions
     * 
     * @param other The position we are trying to find the distance to.
     * @return The y-axis distance between this position and other. A positive value represents going up on the board
     */
    public int getYDistance(Position other){ 
        return (other.yPos - this.yPos);
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece newPiece) {
        currentPiece = newPiece;
    }

    public static int stringToXPosition(String position) {
        return ((int)position.charAt(0)-65);
    }

    public static int stringToYPosition(String position) {
        return Character.getNumericValue(position.charAt(1))-1;
    }

    public String toString() {
        char xChar = ((char) (xPos+65));
        char yChar = Character.forDigit(yPos+1, 10);
        return new String(new char[] {xChar,yChar});
    }

    public boolean equalsXY(Position other) {
        if (this.xPos==other.getX() && this.yPos==other.getY()) {
            return true;
        }
        return false;
    }

    public boolean equals(Position other) {
        if (this.xPos==other.getX() && this.yPos==other.getY()) {
            if (this.currentPiece==null && other.getCurrentPiece()==null) {
                return true;
            } else if (this.currentPiece.equals(other.getCurrentPiece())) {
                return true;
            }
        }
        return false;
    }

}