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
        
        for (int p=0;p<playerList.length ;p++) {    //This is standard board generation
            for (int i=0;i<8;i++) {
                Pawn newPawn = new Pawn(playerList[p]);
                board.setPiece(newPawn, ((8+ 2*playerList[p].getOrientation()) %9), i); //in this formula: 2* is the position of the row for the white pieces
            }
            board.setPiece(new King(playerList[p]), ((8+ 1*playerList[p].getOrientation()) %9), 3);
            board.setPiece(new Queen(playerList[p]), ((8+ 1*playerList[p].getOrientation()) %9), 4);
            board.setPiece(new Bishop(playerList[p]), ((8+ 1*playerList[p].getOrientation()) %9), 5);
            board.setPiece(new Bishop(playerList[p]), ((8+ 1*playerList[p].getOrientation()) %9), 2);
            board.setPiece(new Knight(playerList[p]), ((8+ 1*playerList[p].getOrientation()) %9), 1);
            board.setPiece(new Knight(playerList[p]), ((8+ 1*playerList[p].getOrientation()) %9), 6);
            board.setPiece(new Rook(playerList[p]), ((8+ 1*playerList[p].getOrientation()) %9), 0);
            board.setPiece(new Rook(playerList[p]), ((8+ 1*playerList[p].getOrientation()) %9), 7);

        }

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
        return false; //temporary placeholder statement
    }

    private void movePiece(Position initial, Position end) {
        initial.getCurrentPiece().setHasMoved(true);
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
        Position start = new Position(-1,-1);
        Position end = new Position(-1,-1);

        do {
            start = this.getStartMove();
            end = this.getEndMove();
        } while (!this.isValidMove(start,end));
        movePiece(start,end);
    }

    private boolean isValidMove(Position startPosition,Position endPosition) {
        //To be added: check if the player is in check
        if (startPosition.getCurrentPiece()==null) { //validating start position
            System.out.println("No piece to move");
            return false;
        } else {
            if (currentTurn!=startPosition.getCurrentPiece().getOwner()) {
                System.out.println("You cannot move this piece");
                return false;
            }
            if (!startPosition.getCurrentPiece().canMove(board, startPosition, endPosition)) {
                System.out.println("This piece cannot move to that position");
                return false;
            }
        }
        
        if (endPosition.getCurrentPiece()!=null) { //Validating end position
            if (currentTurn==endPosition.getCurrentPiece().getOwner()){
                System.out.println("Position is occupied");
                return false;
            }
        }
        return true;
    }

    private Position getStartMove() {
        String movingPiece ="";
        Position movingPiecePosition;
        int x = -1;
        int y = -1;

        while (movingPiece.length()!=2 || !board.insideBoard(y,x)) {
            movingPiece = UserInterface.getStringInput("From what position do you want to move a piece?");
            if (movingPiece.length()!=2) {
                System.out.println("Not a Position");
            } else {
                x = Position.stringToXPosition(movingPiece);
                y = Position.stringToYPosition(movingPiece);
                if (!board.insideBoard(y,x)) {
                    System.out.println("Position is ouside of the board");
                }
            }
        }
        movingPiecePosition = board.getPosition(y,x); //NOTE: y comes first here since our array is board[rows][columns], but y=row and x=column
        return movingPiecePosition;
    }

    private Position getEndMove() {
        String movedPiece ="";
        Position movedPiecePosition;
        int x = -1;
        int y = -1;

        while (movedPiece.length()!=2 || !board.insideBoard(x,y)) {
            movedPiece = UserInterface.getStringInput("Where do you want to move?");
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
        movedPiecePosition = board.getPosition(y,x); //NOTE: y comes first here since our array is board[rows][columns], but y=row and x=column
        return movedPiecePosition;
    }

}