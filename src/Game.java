import java.util.ArrayList;
import java.util.LinkedList;
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
    private LinkedList<Move> moveHistory;

    /**
     * Generates a Custom game of Chess.
     * 
     * @param playerList The list containing the opponents in this game of Chess.
     * @param boardHeight The Height of a custom game board.
     * @param boardWidth The Width of a custom game board.
     */
    public Game(Player[] playerList,int boardHeight,int boardWidth) { //constructor for custom games
        this.playerList = playerList;
        this.kingList = new Piece[2];
        board = new Board(boardHeight,boardWidth);
        gameOver = false;
        moveHistory = new LinkedList<Move>();

        for (int p=0;p<playerList.length ;p++) {
            System.out.println("Placing "+playerList[p].getName()+"'s pieces");
            String[] validOptions = new String[]{"Pawn","King","Queen","Bishop","Knight","Rook","Done"};
            String userChoice="";

            while (!userChoice.equals("Done")) {
                UserInterface.displayASCII(board);
                userChoice = UserInterface.getStringInput("What type of piece would you like to place? (Write Done when you want to go to the next player)", validOptions);
                switch(userChoice) {
                    case "Pawn":
                        Position pawnPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                        Piece pawnPiece = new Pawn(playerList[p],null);
                        board.setPiece(pawnPiece, pawnPosition);
                        playerList[p].addPiece(pawnPiece);
                        break;
                    case "King":
                        if (kingList[p]==null) {
                            Position kingPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                            Piece kingPiece = new King(playerList[p],null);
                            board.setPiece(kingPiece, kingPosition);
                            playerList[p].addPiece(kingPiece);
                            kingList[p] = kingPiece;
                        } else {
                            System.out.println("There is already a king for this player");
                        }
                        break;
                    case "Queen":
                        Position queenPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                        Piece queenPiece = new Queen(playerList[p],null);
                        board.setPiece(queenPiece, queenPosition);
                        playerList[p].addPiece(queenPiece);
                        break;
                    case "Bishop":
                        Position bishopPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                        Piece bishopPiece = new Bishop(playerList[p],null);
                        board.setPiece(bishopPiece, bishopPosition);
                        playerList[p].addPiece(bishopPiece);
                        break;
                    case "Knight":
                        Position knightPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                        Piece knightPiece = new Knight(playerList[p],null);
                        board.setPiece(knightPiece,knightPosition );
                        playerList[p].addPiece(knightPiece);
                        break;
                    case "Rook":
                        Position rookPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                        Piece rookPiece = new Rook(playerList[p],null);
                        board.setPiece(rookPiece,rookPosition);
                        playerList[p].addPiece(rookPiece);
                        break;
                    case "Done":
                        if (kingList[p]==null) {
                            System.out.println("No King is in play for this player");
                            userChoice="";
                        }
                        break;
                }
            }
        }
        
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
        moveHistory = new LinkedList<Move>();
        
        for (int p=0;p<playerList.length ;p++) {    //This is standard board generation
            for (int i=0;i<8;i++) {
                Position pawnPosition = board.getPosition(((8+ 2*playerList[p].getOrientation()) %9), i);
                Piece pawnPiece = new Pawn(playerList[p],null);
                board.setPiece(pawnPiece, pawnPosition);
                playerList[p].addPiece(pawnPiece);
            }

            Position kingPosition = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9),3);
            Piece kingPiece = new King(playerList[p],null);
            board.setPiece(kingPiece, kingPosition);
            playerList[p].addPiece(kingPiece);
            kingList[p] = kingPiece;
            
            Position queenPosition = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), 4);
            Piece queenPiece = new Queen(playerList[p],null);
            board.setPiece(queenPiece, queenPosition);
            playerList[p].addPiece(queenPiece);
            
            Position bishopPosition1 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), 5);
            Position bishopPosition2 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), 2);
            Piece bishopPiece1 = new Bishop(playerList[p],null);
            Piece bishopPiece2 = new Bishop(playerList[p],null);
            board.setPiece(bishopPiece1, bishopPosition1);
            board.setPiece(bishopPiece2, bishopPosition2);
            playerList[p].addPiece(bishopPiece1);
            playerList[p].addPiece(bishopPiece2);
            
            Position knightPosition1 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), 1);
            Position knightPosition2 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), 6);
            Piece knightPiece1 = new Knight(playerList[p],null);
            Piece knightPiece2 = new Knight(playerList[p],null);
            board.setPiece(knightPiece1,knightPosition1 );
            board.setPiece(knightPiece2,knightPosition2 );
            playerList[p].addPiece(knightPiece1);
            playerList[p].addPiece(knightPiece2);
            
            Position rookPosition1 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), 0);
            Position rookPosition2 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), 7);
            Piece rookPiece1 = new Rook(playerList[p],null);
            Piece rookPiece2 = new Rook(playerList[p],null);
            board.setPiece(rookPiece1,rookPosition1 );
            board.setPiece(rookPiece2,rookPosition2 );
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
                    for (int j=0;j<playerList.length;j++) {
                        if (playerList[j].getPlayerState()==1) {
                            return currentTurn;
                        }
                        if (playerList[j].getPlayerState()==2) {
                            return null;
                        }
                    }                        

                }
                System.out.println(currentTurn.getName()+"'s turn");
                UserInterface.displayASCII(board);
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
        for (int i=0;i<playerList.length;i++) {
            if (playerList[i].getPlayerState()!=0) {
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
        moveHistory.addLast(new Move(move));
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

        
        if (move.getSpecial()!=null) {
            switch (move.getSpecial()) {
                case "PawnPromotion":
                    String[] validPieces = new String[]{"Pawn","King","Queen","Bishop","Knight","Rook"};
                    String pieceChoice = UserInterface.getStringInput("Pawn is being promoted to what piece?", validPieces);
                    switch (pieceChoice) {
                        case "Queen":
                            Piece queenPiece = new Queen(end.getCurrentPiece().getOwner(),end);   
                            end.getCurrentPiece().getOwner().addPiece(queenPiece);
                            end.getCurrentPiece().getOwner().removePiece(end.getCurrentPiece());    
                            board.setPiece(queenPiece, end);
                            break;
                        case "Bishop":
                            Piece bishopPiece = new Bishop(end.getCurrentPiece().getOwner(),end);   
                            end.getCurrentPiece().getOwner().addPiece(bishopPiece);
                            end.getCurrentPiece().getOwner().removePiece(end.getCurrentPiece());    
                            board.setPiece(bishopPiece, end);
                            break;
                        case "Knight":
                            Piece knightPiece = new Queen(end.getCurrentPiece().getOwner(),end);   
                            end.getCurrentPiece().getOwner().addPiece(knightPiece);
                            end.getCurrentPiece().getOwner().removePiece(end.getCurrentPiece());    
                            board.setPiece(knightPiece, end);
                            break;
                        case "Rook":
                            Piece rookPiece = new Queen(end.getCurrentPiece().getOwner(),end);   
                            end.getCurrentPiece().getOwner().addPiece(rookPiece);
                            end.getCurrentPiece().getOwner().removePiece(end.getCurrentPiece());    
                            board.setPiece(rookPiece, end);
                            break;
                    }
                    break;
            }
        }
        
        
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
        int moveNumber = -1;

        if (this.inCheck(currentTurn)){
            System.out.println(currentTurn.getName()+" is in check...");
            checked = true;
        }

        if (moveOptions.size()==0) {
            if (checked) {
                System.out.println(currentTurn.getName()+" has been checkmated!");
                currentTurn.setPlayerState(1);
            } else {
                System.out.println(currentTurn.getName()+" has been stalemated!");
                currentTurn.setPlayerState(2);
            }
            
        } else {
            do {
                newMove.setStartPosition(UserInterface.getPositionFromUser(board, "From what position do you want to move a piece?"));
                newMove.setEndPosition(UserInterface.getPositionFromUser(board, "Where do you want to move to?"));
                moveNumber= this.isValidMove(newMove,moveOptions);
            } while (moveNumber==-1);
            
            movePiece(moveOptions.get(moveNumber));
            
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
    private int isValidMove(Move move,ArrayList<Move> validMoves) {
        for (int i=0;i<validMoves.size();i++) {
            if (move.equalsStartEnd(validMoves.get(i))) {
                return i;
            }
        }
        return -1;
    }

}