/**
 * Class representing a chess board.
 * @author UO-BS
 */
public class Board {

    private Position[][] fullBoard;
    private int totalRow; //columnNumber
    private int totalColumn; //rowNumber
    private Position vulnerableToEnPassant;

    /**
     * Generates a new chess board.
     * 
     * @param rows The number of rows on this board.
     * @param columns The number of columns on this board.
     */
    public Board(int rows, int columns) {
        this.totalRow = rows;
        this.totalColumn = columns;
        
        fullBoard = new Position[rows][columns];
        for (int i=0;i<rows;i++) {
            for (int j=0;j<columns;j++) {
                fullBoard[i][j] = new Position(i,j);
            }
        }

    }

    public Position getVulnerableToEnPassant(){
        return vulnerableToEnPassant;
    }

    public void setVulnerableToEnPassant(Position vulnerablePosition){
        vulnerableToEnPassant = vulnerablePosition;
    }

    public int getRows(){
        return totalRow;
    }

    public int getColumns(){
        return totalColumn;
    }

    /**
     * Returns the position at the specified row and column.
     * 
     * @param rowNumber The row of the requested position.
     * @param columnNumber The column of the requested postion.
     * @return The Position on row: rowNumber and column: columnNumber
     */
    public Position getPosition(int rowNumber , int columnNumber) {
        return fullBoard[rowNumber][columnNumber];
    }

    /**
     * Determines wether the position at the specified row and column is within the board.
     * 
     * @param rowNumber The row of the requested position.
     * @param columnNumber The column of the requested postion.
     * @return Boolean representing if the position at row:rowNumber and column:columnNumber is within the current board.
     */
    public boolean insideBoard(int rowNumber, int columnNumber) {
        if (rowNumber > totalRow-1 || rowNumber<0 || columnNumber > totalColumn-1 || columnNumber<0) {
            return false;
        }
        return true;
    }
    
    /**
     * Determines wether the position is within the board.
     * 
     * @param position 
     * @return Boolean representing if the position is within the current board.
     */
    public boolean insideBoard(Position position) {
        int rowNumber = position.getX;
        int columnNumber = position.getY;

        if ( rowNumber > totalRow-1 || rowNumber<0 || columnNumber > totalColumn-1 || columnNumber<0) {
            return false;
        }
        return true;
    }

    /**
     * Sets a piece onto the position at the specified row and column.
     * 
     * @param rowNumber The row of the position.
     * @param columnNumber The column of the postion.
     * @param newPiece The piece that is being placed on the position
     */
    public void setPiece(Piece newPiece, int rowNumber, int columnNumber) {
        fullBoard[rowNumber][columnNumber].setCurrentPiece(newPiece);
        this.getPosition(rowNumber, columnNumber).setCurrentPiece(newPiece);
        if (newPiece !=null) {
            newPiece.setPosition(this.getPosition(rowNumber, columnNumber));
        }
    }

    /**
     * Sets a piece onto a position.
     * 
     * @param position The position where the piece is being placed.
     * @param newPiece The piece that is being placed on the position
     */
    public void setPiece(Piece newPiece, Position position) {
        int columnNumber = position.getX();
        int rowNumber = position.getY();
        fullBoard[rowNumber][columnNumber].setCurrentPiece(newPiece);
        position.setCurrentPiece(newPiece);
        if (newPiece !=null) {
            newPiece.setPosition(position);
        }
        
    }

}