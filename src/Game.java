import java.util.Scanner;
public class Game {
    
    private Board board;
    private Player[] playerList;
    private Player currentTurn;
    private boolean gameOver;

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

    public Player runGame(Scanner reader) { //returns the winner of the game
        
        while (!gameOver) {
            for (int i = 0;i<playerList.length;i++) {
                currentTurn = playerList[i];
                System.out.println(currentTurn.getName()+"'s turn");
                board.display();
                doTurn(reader);
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

    private void doTurn(Scanner reader) {
        //To be added: Stalemate checking
        Position start = new Position(-1,-1);
        Position end = new Position(-1,-1);

        do {
            start = this.getStartMove(reader);
            end = this.getEndMove(reader);
        } while (!this.isValidMove(start,end));
        movePiece(start,end);
    }

    private boolean isValidMove(Position startPosition,Position endPosition) {
        return true; //placeholder statement
    }

    private Position getStartMove(Scanner reader) {
        String movingPiece ="";
        Position movingPiecePosition;
        int x = -1;
        int y = -1;

        while (movingPiece.length()!=2 || !board.insideBoard(x,y)) {
            movingPiece = Menu.getStringInput(reader, "What piece do you want to move?");
            if (movingPiece.length()!=2) {
                System.out.println("Not a Position");
            } else {
                x = Position.stringToXPosition(movingPiece);
                y = Position.stringToYPosition(movingPiece);
                if (!board.insideBoard(x,y)) {
                    System.out.println("Position is ouside of the board");
                }
            }
        }
        movingPiecePosition = board.getPosition(x,y);
        return movingPiecePosition;
    }

    private Position getEndMove(Scanner reader) {
        String movedPiece ="";
        Position movedPiecePosition;
        int x = -1;
        int y = -1;

        while (movedPiece.length()!=2 || !board.insideBoard(x,y)) {
            movedPiece = Menu.getStringInput(reader, "Where do you want to move?");
            if (movedPiece.length()!=2) {
                System.out.println("Not a Position");
            } else {
                x = Position.stringToXPosition(movedPiece);
                y = Position.stringToYPosition(movedPiece);
                if (!board.insideBoard(x,y)) {
                    System.out.println("Position is ouside of the board");
                }
            }
        }
        movedPiecePosition = board.getPosition(x,y);
        return movedPiecePosition;
    }

}