public class Position{

    private int xPos;
    private int yPos;
    private Piece currentPiece;

    public Position(int y, int x){ //NOTE: y comes first since we are using the array[row][column] convention
        xPos = x;
        yPos = y;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
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