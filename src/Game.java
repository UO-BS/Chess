import java.util.ArrayList;
/**
 * Class for a single game of chess.
 * @author UO-BS
 */
public class Game {
    
    private Board board;
    private Player[] playerList;
    private Player currentTurn;
    private boolean gameOver;
    private Piece[] kingList;

    /**
     * Generates a Custom game of Chess.
     * 
     * @param playerList The list containing the opponents in this game of Chess.
     * @param boardHeight The Height of a custom game board.
     * @param boardWidth The Width of a custom game board.
     */
    public Game(Player[] playerList,int boardHeight,int boardWidth) { //constructor for custom games
        this.playerList = playerList;
        board = new Board(boardHeight,boardWidth);
        gameOver = false;
    }

    /**
     * Generates the standard game of Chess.
     * 
     * @param playerList The list containing the 2 opponents in this game of Chess.
     */
    public Game(Player[] playerList) {
        this.playerList = playerList;
        this.kingList = new Piece[2];
        board = new Board(8,8);
        gameOver = false;
        
        for (int p=0;p<playerList.length ;p++) {    //This is standard board generation
            for (int i=0;i<8;i++) {
                Position pawnPosition = new Position(((8+ 2*playerList[p].getOrientation()) %9), i);
                Piece pawnPiece = new Pawn(playerList[p],null);
                board.setPiece(pawnPiece, pawnPosition);
                pawnPiece.setPosition(pawnPosition);
                playerList[p].addPiece(pawnPiece);
            }

            Position kingPosition = new Position(((8+ 1*playerList[p].getOrientation()) %9),3);
            Piece kingPiece = new King(playerList[p],null);
            board.setPiece(kingPiece, kingPosition);
            kingPiece.setPosition(kingPosition);
            playerList[p].addPiece(kingPiece);
            kingList[p] = kingPiece;
            
            Position queenPosition = new Position(((8+ 1*playerList[p].getOrientation()) %9), 4);
            Piece queenPiece = new Queen(playerList[p],null);
            board.setPiece(queenPiece, queenPosition);
            queenPiece.setPosition(queenPosition);
            playerList[p].addPiece(queenPiece);
            
            Position bishopPosition1 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 5);
            Position bishopPosition2 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 2);
            Piece bishopPiece1 = new Bishop(playerList[p],null);
            Piece bishopPiece2 = new Bishop(playerList[p],null);
            board.setPiece(bishopPiece1, bishopPosition1);
            board.setPiece(bishopPiece2, bishopPosition2);
            bishopPiece1.setPosition(bishopPosition1);
            bishopPiece2.setPosition(bishopPosition2);
            playerList[p].addPiece(bishopPiece1);
            playerList[p].addPiece(bishopPiece2);
            
            Position knightPosition1 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 1);
            Position knightPosition2 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 6);
            Piece knightPiece1 = new Knight(playerList[p],null);
            Piece knightPiece2 = new Knight(playerList[p],null);
            board.setPiece(knightPiece1,knightPosition1 );
            board.setPiece(knightPiece2,knightPosition2 );
            knightPiece1.setPosition(knightPosition1);
            knightPiece2.setPosition(knightPosition2);
            playerList[p].addPiece(knightPiece1);
            playerList[p].addPiece(knightPiece2);
            
            Position rookPosition1 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 0);
            Position rookPosition2 = new Position(((8+ 1*playerList[p].getOrientation()) %9), 7);
            Piece rookPiece1 = new Rook(playerList[p],null);
            Piece rookPiece2 = new Rook(playerList[p],null);
            board.setPiece(rookPiece1,rookPosition1 );
            board.setPiece(rookPiece2,rookPosition2 );
            rookPiece1.setPosition(rookPosition1);
            rookPiece2.setPosition(rookPosition2);
            playerList[p].addPiece(rookPiece1);
            playerList[p].addPiece(rookPiece2);

        }

    }

    /**
     * Calls all the helper methods to run a game of Chess and returns the winning player.
     * 
     * @return The player that won the game of chess.
     */
    public Player runGame() { //returns the winner of the game
        
        while (!gameOver) {
            for (int i = 0;i<playerList.length;i++) {
                currentTurn = playerList[i];
                if (checkWin()){
                    return currentTurn;
                }
                System.out.println(currentTurn.getName()+"'s turn");
                board.display();
                doTurn();
                
            }
            
        }
        return playerList[0];
    }

    /**
     * Method that checks if there is only 1 player left in the game. 
     * 
     * @return Boolean for when the game is over.
     */
    private boolean checkWin(){
        int counter = 0;
        for (int i=0;i<playerList.length;i++) { //Counts the number of players left in the game
            if (!playerList[i].getCheckmated()) {
                counter++;
            }
        }
        if (counter!=1) {
            return false;
        }
        return true;
    }

    /**
     * Moves a piece and cleans up any "dead" pieces created from this move.
     * 
     * @param move The move that is being made.
     */
    private void movePiece(Move move) {
        Position initial = move.getStartPosition();
        Position end = move.getEndPosition();
        
        initial.getCurrentPiece().setHasMoved(true); 
        if (end.getCurrentPiece()!=null) {
            System.out.println(end.getCurrentPiece()+" has been removed"); //Later this will be added to a score system
            end.getCurrentPiece().setState(false);
        }
        end.setCurrentPiece(initial.getCurrentPiece());
        end.getCurrentPiece().setPosition(end);
        initial.setCurrentPiece(null);
    }

    /**
     * Determines if a move would place the current/moving player in check (making it an invalid move).
     * 
     * @param testMove The move that is being tested.
     * @return Boolean representing if the move passed the test. False means that the tested move placed the moving player into check.
     */
    private boolean testMoveCheck(Move testMove) { //This method tests if making a move would put the current player in check
        Piece startPiece = testMove.getStartPosition().getCurrentPiece();
        Piece endPiece = testMove.getEndPosition().getCurrentPiece();
        
        startPiece.setPosition(testMove.getEndPosition());
        board.setPiece(null, testMove.getStartPosition());
        board.setPiece(startPiece, testMove.getEndPosition());
        if (endPiece!=null) endPiece.setState(false);

        if (this.inCheck(currentTurn)) { 
            startPiece.setPosition(testMove.getStartPosition());
            board.setPiece(startPiece, testMove.getStartPosition());
            board.setPiece(endPiece, testMove.getEndPosition());
            if (endPiece!=null) endPiece.setState(true);
            return false;
        } else {
            startPiece.setPosition(testMove.getStartPosition());
            board.setPiece(startPiece, testMove.getStartPosition());
            board.setPiece(endPiece, testMove.getEndPosition());
            if (endPiece!=null) endPiece.setState(true);
            return true;
        }
        
    }

    /**
     * Determines wether the current player is in check, checkmate or stalemate, then allows the player to make a move if the game is not over.
     */
    private void doTurn() {
        Move newMove = new Move(new Position(-1, -1),new Position(-1, -1));
        ArrayList<Move> moveOptions = this.allValidMoves(currentTurn, this.allPossibleMoves(currentTurn));
        boolean checked = false; //Variable so that we do not have to look for check more than once per turn

        if (this.inCheck(currentTurn)){
            System.out.println(currentTurn.getName()+" is in check...");
            checked = true;
        }

        if (moveOptions.size()==0) {
            if (checked) {
                System.out.println(currentTurn.getName()+" has been checkmated!");
                currentTurn.setCheckmated(true);
            } else {
                System.out.println(currentTurn.getName()+" has been stalemated!");
                currentTurn.setCheckmated(true);
            }
            
        } else {
            do {
                newMove.setStartPosition(this.getStartMove());
                newMove.setEndPosition(this.getEndMove());
            } while (!this.isValidMove(newMove,moveOptions));
            movePiece(newMove);
        }
    }

    /**
     * Determines if a player is in check.
     * 
     * @param checkedPlayer The player that is being determined.
     * @return Boolean representing wether the player is in check.
     */
    private boolean inCheck(Player checkedPlayer){
        Piece checkedKing = null;
        for (int i=0;i<kingList.length;i++) {
            if (kingList[i].getOwner()==checkedPlayer) {
                checkedKing = kingList[i];
            }
        }

        
        for (int i =0;i<playerList.length;i++) {
            if (playerList[i]!=checkedPlayer) {
                ArrayList<Move> possibleDangers = this.allPossibleMoves(playerList[i]);
                for (int j=0;j<possibleDangers.size();j++){
                    if (possibleDangers.get(j).getEndPosition().getCurrentPiece()!=null) {
                        if (possibleDangers.get(j).getEndPosition().getCurrentPiece().equals(checkedKing)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Generates a single ArrayList that contains every possible move that a player's pieces can make.
     * 
     * @param player The player that the moves are being generated for
     * @return ArrayList of every possible move that a player's pieces can make.
     */
    private ArrayList<Move> allPossibleMoves(Player player){
        ArrayList<Move> fullList = new ArrayList<>();
        for (int i=0;i<player.getPieceList().size();i++) {
            fullList.addAll(player.getPieceList().get(i).getPossibleMoves(board));
        }
        return fullList;
    }

    /**
     * Generates a single ArrayList that contains every valid move that a player's pieces can make.
     * <p>
     * A valid move cannot end on a piece owned by the same player, and cannot put the moving player in check.
     * </p>
     * 
     * @param player The player that the moves are being generated for
     * @param possibleMoves An ArrayList of all possible moves that the pieces can make
     * @return ArrayList of every valid move that a player's pieces can make.
     */
    private ArrayList<Move> allValidMoves(Player player, ArrayList<Move> possibleMoves){
        ArrayList<Move> fullValidList = new ArrayList<>();
        
        for (int i=0;i<possibleMoves.size();i++) {
            
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

    /**
     * Tests if a given move is in the list of valid moves.
     * 
     * @param move Any move that you want to check.
     * @param validMoves An ArrayList of all valid moves.
     * @return Boolean representing if the passed move is one of the player's validMoves.
     */
    private boolean isValidMove(Move move,ArrayList<Move> validMoves) {
        for (int i=0;i<validMoves.size();i++) {
            if (move.equals(validMoves.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the start position of a move from the user
     * 
     * @return Start position of the user's move
     */
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

    /**
     * Gets the end position of a move from the user
     * 
     * @return End position of the user's move
     */
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