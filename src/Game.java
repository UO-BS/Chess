public class Game {
    
    private Board board;
    private Player[] playerList;

    public Game(Player[] playerList,int boardHeight,int boardWidth) {
        this.playerList = playerList;
        board = new Board(boardHeight,boardWidth);
    }

    public Game(Player[] playerList) {
        this.playerList = playerList;
        board = new Board(8,8);
    }

    public Player runGame() {
        Boolean gameOver = false;
        while (!gameOver) {
            for (int i = 0;i<playerList.length;i++) {
                System.out.println("Player "+i+"'s turn");
            }
            gameOver = true; //temporary statement to stop infinite loop
        }
        return playerList[0];
    }


}