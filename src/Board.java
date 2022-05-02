public class Board {

    private Position[][] fullBoard;

    public Board(int rows, int columns) {
        fullBoard = new Position[rows][columns];
        for (int i=0;i<rows;i++) {
            for (int j=0;j<columns;j++) {
                fullBoard[i][j] = new Position(i,j);
            }
        }
    }


}