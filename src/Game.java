public class Game {
    
    private Board board;
    private Player[] playerList;
    private Player currentTurn;
    private boolean gameOver;

    public Game(Player[] playerList,int boardHeight,int boardWidth) {
        this.playerList = playerList;
        board = new Board(boardHeight,boardWidth);
        gameOver = false;
    }

    public Game(Player[] playerList) {
        this.playerList = playerList;
        board = new Board(8,8);
        gameOver = false;
    }

    public Player runGame() { //returns the winner of the game
        
        while (!gameOver) {
            for (int i = 0;i<playerList.length;i++) {
                System.out.println("Player "+i+"'s turn");
            }
            checkWin();
        }
        return playerList[0];
    }

    private void checkWin() {
        gameOver = true; //temporary statement
    }

    private void movePiece() {

    }

}