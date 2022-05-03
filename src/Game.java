import java.util.Scanner;
public class Game {
    
    private Board board;
    private Player[] playerList;
    private Player currentTurn;
    private boolean gameOver;
    Scanner reader = new Scanner(System.in);

    public Game(Player[] playerList,int boardHeight,int boardWidth) { //constructor for custom games
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
                currentTurn = playerList[i];
                System.out.println(currentTurn.getName()+"'s turn");
                board.display();
                doTurn();
                if (checkWin()){
                    return currentTurn;
                }
            }
            
        }
        return playerList[0];
    }

    private boolean checkWin() {
        return true; //temporary placeholder statement
    }

    private void movePiece(Position initial, Position end) {
        if (end.getCurrentPiece()!=null) {
            System.out.println(end.getCurrentPiece()+" has been removed"); //Later this will be added to a score system
            end.getCurrentPiece().setState(false);
        }
        end.setCurrentPiece(initial.getCurrentPiece());
        initial.setCurrentPiece(null);
        //To be added: adding move to a move history
    }

    private void doTurn() {
        //To be added: Stalemate checking
        //To be added: Get and validate user input
        movePiece(board.getPosition(0, 0),board.getPosition(0, 0)); //temporary placeholder statement
    }

}