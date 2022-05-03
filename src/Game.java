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
        //To be added: Validate user input
        String movingPiece = Menu.getStringInput(reader, "What piece do you want to move?");
        Position movingPiecePosition = board.getPosition(Position.stringToXPosition(movingPiece), Position.stringToYPosition(movingPiece));

        String movedPiece = Menu.getStringInput(reader, "Where do you want to move?");
        Position movedPiecePosition = board.getPosition(Position.stringToXPosition(movedPiece), Position.stringToYPosition(movedPiece));

        movePiece(movingPiecePosition,movedPiecePosition);
    }

}