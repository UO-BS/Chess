public class Board {

    private Position[][] fullBoard;
    private int rowNum;
    private int columnNum;

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
            System.out.println("---------------------------------");
            
            if (i==0){
                
                System.out.print("* |");
                for(int j=0;j<columnNum;j++) {
                    System.out.print(Character.toString((char) j+65 )+"  |");
                }
                System.out.println("");

            } else {
            
                System.out.print(i +" |");
                for(int j=0;j<columnNum;j++) {
                    System.out.print("   |");
                    //To be added: checking the current spot for a piece and displaying it
                }
                System.out.println("");
            }
        }
        
        

    }

    public Position getPosition(int x, int y) {
        return fullBoard[x][y];
    }

}