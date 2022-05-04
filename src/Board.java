public class Board {

    private Position[][] fullBoard;
    private int rowNum; //x
    private int columnNum; //y

    public Board(int rows, int columns) {
        this.rowNum = rows;
        this.columnNum = columns;
        
        fullBoard = new Position[rows][columns];
        for (int i=0;i<rows;i++) {
            for (int j=0;j<columns;j++) {
                fullBoard[i][j] = new Position(i,j);
            }
        }

    }

    public void display() {
        
        for (int i=rowNum;i>=0;i--) {
            for (int j=0;j<columnNum;j++){
                System.out.print("----");
            }
            System.out.println("");
            
            if (i==0){
                
                System.out.print("* |");
                for(int j=0;j<columnNum;j++) {
                    System.out.print(Character.toString((char) j+65 )+"  |");
                }
                System.out.println("");

            } else {
            
                System.out.print(i +" |");
                for(int j=0;j<columnNum;j++) {
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

    public Position getPosition(int x, int y) {
        return fullBoard[x][y];
    }

    public boolean insideBoard(int x, int y) {
        if (x > rowNum-1 || x<0 || y > columnNum-1 || y<0) {
            return false;
        }
        return true;
    }

    public boolean insideBoard(Position position) {
        int x = position.getX;
        int y = position.getY;

        if ( x > rowNum-1 || x<0 || y > columnNum-1 || y<0) {
            return false;
        }
        return true;
    }

}