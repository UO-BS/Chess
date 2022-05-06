import java.util.ArrayList;
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
                Position pawnPosition = new Position(((8+ 2*playerList[p].getOrientation()) %9), i);
                Piece pawnPiece = new Pawn(playerList[p],pawnPosition);
                board.setPiece(pawnPiece, pawnPosition); 
                playerList[p].addPiece(pawnPiece);
            }

            Position kingPosition = new Position(((8+ 1*playerList[p].getOrientation()) %9),3);
            Piece kingPiece = new King(playerList[p],kingPosition);
            board.setPiece(kingPiece, kingPosition);
            playerList[p].addPiece(kingPiece);
            
            Position queenPosition = new Position(((8+ 1*playerList[p].getOrientation()) %9), 4);
            Piece queenPiece = new Queen(playerList[p],queenPosition);
            board.setPiece(queenPiece, queenPosition);
            playerList[p].addPiece(queenPiece);
            
            Position bishopPosition1 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 5);
            Position bishopPosition2 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 2);
            Piece bishopPiece1 = new Bishop(playerList[p],bishopPosition1);
            Piece bishopPiece2 = new Bishop(playerList[p],bishopPosition2);
            board.setPiece(bishopPiece1, bishopPosition1);
            board.setPiece(bishopPiece2, bishopPosition2);
            playerList[p].addPiece(bishopPiece1);
            playerList[p].addPiece(bishopPiece2);
            
            Position knightPosition1 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 1);
            Position knightPosition2 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 6);
            Piece knightPiece1 = new Knight(playerList[p],knightPosition1);
            Piece knightPiece2 = new Knight(playerList[p],knightPosition2);
            board.setPiece(knightPiece1,knightPosition1 );
            board.setPiece(knightPiece2,knightPosition2 );
            playerList[p].addPiece(knightPiece1);
            playerList[p].addPiece(knightPiece2);
            
            Position rookPosition1 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 0);
            Position rookPosition2 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 7);
            Piece rookPiece1 = new Rook(playerList[p],rookPosition1);
            Piece rookPiece2 = new Rook(playerList[p],rookPosition2);
            board.setPiece(rookPiece1,rookPosition1 );
            board.setPiece(rookPiece2,rookPosition2 );
            playerList[p].addPiece(rookPiece1);
            playerList[p].addPiece(rookPiece2);

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
        initial.getCurrentPiece().setHasMoved(true); //May have to change this?
        if (end.getCurrentPiece()!=null) {
            System.out.println(end.getCurrentPiece()+" has been removed"); //Later this will be added to a score system
            end.getCurrentPiece().setState(false);
        }
        initial.getCurrentPiece().setPosition(end);
        end.setCurrentPiece(initial.getCurrentPiece());
        initial.setCurrentPiece(null);
        //To be added: removing from player's piece list
    }

    private boolean testMoveCheck(Move testMove) { //This method tests if making a move would put the current player in check
        Position initial = testMove.getStartPosition();
        Position end = testMove.getEndPosition();
        Piece startPiece = initial.getCurrentPiece();
        Piece endPiece = end.getCurrentPiece();

        startPiece.setPosition(end);
        initial.setCurrentPiece(null);
        end.setCurrentPiece(initial.getCurrentPiece());

        if (this.inCheck()) { //this function has not been made yet
            startPiece.setPosition(initial);
            initial.setCurrentPiece(startPiece);
            end.setCurrentPiece(endPiece);
            return false;
        } else {
            startPiece.setPosition(initial);
            initial.setCurrentPiece(startPiece);
            end.setCurrentPiece(endPiece);
            return true;
        }
        
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

    private ArrayList<Move> allPossibleMoves(Player player){
        ArrayList<Move> fullList = new ArrayList<>();
        for (int i=0;i<player.getPieceList().length;i++) {
            fullList.addAll(player.getPieceList().get(i).getPossibleMoves(board));
        }
        return fullList;
    }

    private ArrayList<Move> allValidMoves(Player player, ArrayList<Move> possibleMoves){
        ArrayList<Move> fullValidList = new ArrayList<>();
        
        for (int i=0;i<possibleMoves.length;i++) {
            
            if (possibleMoves.get(i).getEndPosition().getCurrentPiece()!=null) {    
                if (player != possibleMoves.get(i).getEndPosition().getCurrentPiece().getOwner()) {
                    if (testMoveCheck(possibleMoves.get(i))) { //Looking for "check" is costly, I put it in a new if{} so that it isnt called unless necessary
                        fullValidList.add(possibleMoves.get(i));
                    }
                }
            } else {
                if (testMoveCheck(possibleMoves.get(i))) {
                    fullValidList.add(possibleMoves.get(i));
                }
            }

        }
        
        return fullValidList;
    }

    private boolean isValidMove(Move move) {
        //This method will be removed soon
        if (startPosition.getCurrentPiece()==null) { 
            System.out.println("No piece to move");
            return false;
        } else {
            if (currentTurn!=startPosition.getCurrentPiece().getOwner()) {
                System.out.println("You cannot move this piece");
                return false;
            }
            if (!startPosition.getCurrentPiece().canMove(board, move)) {
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