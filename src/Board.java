public class Board {

    private Position[][] fullBoard;
    private int totalRow; //columnNumber
    private int totalColumn; //rowNumber

    public int getRows(){
        return totalRow;
    }

    public int getColumns(){
        return totalColumn;
    }

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

    public void display() {
        
        for (int i=totalRow;i>=0;i--) {
            for (int j=0;j<totalColumn;j++){
                System.out.print("----");
            }
            System.out.println("");
            
            if (i==0){
                
                System.out.print("* |");
                for(int j=0;j<totalColumn;j++) {
                    System.out.print(Character.toString((char) j+65 )+"  |");
                }
                System.out.println("");

            } else {
            
                System.out.print(i +" |");
                for(int j=0;j<totalColumn;j++) {
                    if (fullBoard[i-1][j].getCurrentPiece()!=null) {
                        System.out.print(fullBoard[i-1][j].getCurrentPiece().toString());
                        System.out.print(" |");
                    } else {
                        System.out.print("   |");
                    }
                }
                System.out.println("");
            }
        }
        
        

    }

    public Position getPosition(int rowNumber , int columnNumber) {
        return fullBoard[rowNumber][columnNumber];
    }

    public boolean insideBoard(int rowNumber, int columnNumber) {
        if (rowNumber > totalRow-1 || rowNumber<0 || columnNumber > totalColumn-1 || columnNumber<0) {
            return false;
        }
        return true;
    }

    public boolean insideBoard(Position position) {
        int rowNumber = position.getX;
        int columnNumber = position.getY;

        if ( rowNumber > totalRow-1 || rowNumber<0 || columnNumber > totalColumn-1 || columnNumber<0) {
            return false;
        }
        return true;
    }

    public void setPiece(Piece newPiece, int rowNumber, int columnNumber) {
        fullBoard[rowNumber][columnNumber].setCurrentPiece(newPiece);
    }

    public void setPiece(Piece newPiece, Position position) {
        int columnNumber = position.getX();
        int rowNumber = position.getY();
        fullBoard[rowNumber][columnNumber].setCurrentPiece(newPiece);
    }

}