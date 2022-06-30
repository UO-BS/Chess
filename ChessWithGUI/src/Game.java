import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
/**
 * Class for a single game of chess.
 * @author UO-BS
 */
public class Game {
    
    private Board board;
    private Player[] playerList;
    private Player currentTurn;
    private Piece[] kingList;
    private LinkedList<Move> moveHistory;

    /**
     * Generates a game of Chess.
     * 
     * @param playerList The list containing the 2 opponents in this game of Chess.
     */
    public Game(Player[] playerList, String gameType) {
        this.playerList = playerList;
        this.kingList = new Piece[playerList.length];
        moveHistory = new LinkedList<Move>();
        currentTurn = playerList[0];
        
        switch(gameType){
            case "Standard":
                generateStandardBoard();
                break;
            case "Custom":
                generateCustomBoard();
                break;
            case "Chess960": 
                generateChess960Board();
                break;
        }
        
    }

    /**
     * Method that checks if there is only 1 player left in the game. 
     * 
     * @return Boolean for when the game is over.
     */
    public boolean checkWin(){
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
     * This method will return the last non-eliminated player.
     * 
     * @return The winner of the chess game. Returns null if the game is not over
     */
    public Player getWinner(){
        if (checkWin()) {
            for (int i=0;i<playerList.length;i++) {
                if (playerList[i].getPlayerState()==0) {
                    return playerList[i];
                }
            }
        }
        return null;
    }

    /**
     * Moves a piece and cleans up any "dead" pieces created from this move.
     * 
     * @param move The move that is being made.
     */
    public void movePiece(Move move) {
        moveHistory.addLast(new Move(move));
        Position initial = move.getStartPosition();
        Position end = move.getEndPosition();
        
        initial.getCurrentPiece().setHasMoved(true); 
        if (end.getCurrentPiece()!=null) {
            end.getCurrentPiece().setState(false);
        }
        end.setCurrentPiece(initial.getCurrentPiece());
        end.getCurrentPiece().setPosition(end);
        initial.setCurrentPiece(null);

        //This section deals with special moves
        board.setVulnerableToEnPassant(null);
        if (move.getMoveType()!=null) {
            switch (move.getMoveType()) {
                
                case PAWNPROMOTION: 
                    board.setPawnPromotionPosition(move.getEndPosition()); //Sets a position on the board to place the new chosen piece
                    //The new piece's type is chosen by the user so it is handled in the UserInterface class
                    break;
                
                
                case VULNERABLETOENPASSANT:
                    board.setVulnerableToEnPassant(board.getPosition((move.getStartPosition().getY()+move.getEndPosition().getY())/2, move.getStartPosition().getX()));
                    break;
                
                case ENPASSANT:
                    Position passingPiecePosition = board.getPosition(move.getEndPosition().getY()-(move.getStartPosition().getYDistance(move.getEndPosition())),move.getEndPosition().getX());
                    passingPiecePosition.getCurrentPiece().setState(false);
                    board.setPiece(null, passingPiecePosition);
                    break;

                case CASTLING:
                    int xDistance = move.getStartPosition().getXDistance(move.getEndPosition());
                    if (xDistance>0) { //Castled to the right
                        for (int i=3;i<=4;i++) {
                            if (board.insideBoard(move.getStartPosition().getY(), move.getStartPosition().getX()+i)) {
                                Position castledRookPosition = board.getPosition(move.getStartPosition().getY(), move.getStartPosition().getX()+i);
                                if (castledRookPosition.getCurrentPiece()!=null) {
                                    if (castledRookPosition.getCurrentPiece().getPieceType()==PieceType.ROOK && !castledRookPosition.getCurrentPiece().getHasMoved()) {
    
                                        board.setPiece(castledRookPosition.getCurrentPiece(), board.getPosition(move.getStartPosition().getY(), move.getStartPosition().getX()+1));
                                        castledRookPosition.getCurrentPiece().setHasMoved(true); 
                                        board.setPiece(null, castledRookPosition);
        
                                    }
                                }
                            }
                        }
                    } else { //Castled to the left
                        for (int i=3;i<=4;i++) {
                            if (board.insideBoard(move.getStartPosition().getY(), move.getStartPosition().getX()-i)) {
                                Position castledRookPosition = board.getPosition(move.getStartPosition().getY(), move.getStartPosition().getX()-i);
                                if (castledRookPosition.getCurrentPiece()!=null) {
                                    if (castledRookPosition.getCurrentPiece().getPieceType()==PieceType.ROOK && !castledRookPosition.getCurrentPiece().getHasMoved()) {
      
                                        board.setPiece(castledRookPosition.getCurrentPiece(), board.getPosition(move.getStartPosition().getY(), move.getStartPosition().getX()-1));
                                        castledRookPosition.getCurrentPiece().setHasMoved(true); 
                                        board.setPiece(null, castledRookPosition);
        
                                    }
                                }
                            } 
                        }
                    }
                    break;
                case STANDARD:
                    break;
            }
        }
        
    }

    /**
     * Determines if a player is in check.
     * 
     * @param checkedPlayer The player that is being determined.
     * @return Boolean representing wether the player is in check.
     */
    public boolean inCheck(Player checkedPlayer){
        Piece checkedKing = null;
        for (int i=0;i<kingList.length;i++) {
            if (kingList[i].getOwner()==checkedPlayer) {
                checkedKing = kingList[i];
            }
        }
        
        for (int i =0;i<playerList.length;i++) {
            if (playerList[i]!=checkedPlayer) {
                if (isUnderAttack(playerList[i], board, checkedKing.getPosition())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if a given position can be attacked by a given player
     * 
     * @param attackingPlayer The player that could attack the square.
     * @param board The chess board.
     * @param position The potentially dangerous position.
     * @return Boolean representing wether the given position can be attacked by a given player
     */
    public static boolean isUnderAttack(Player attackingPlayer, Board board, Position position){
        ArrayList<Move> possibleDangers = Game.allPossibleMoves(board, attackingPlayer);
        for (Move attack:possibleDangers){
            if (attack.getEndPosition().equalsXY(position)) {
                return true;
            }
        }
        return false;
        
    }

    /**
     * Generates a single ArrayList that contains every possible move that a player's pieces can make.
     * 
     * @param board The chess board.
     * @param player The player that the moves are being generated for.
     * @return ArrayList of every possible move that a player's pieces can make.
     */
    public static ArrayList<Move> allPossibleMoves(Board board, Player player){
        ArrayList<Move> fullList = new ArrayList<>();
        for (Piece piece:player.getPieceList()) {
            fullList.addAll(piece.getPossibleMoves(board));
        }
        return fullList;
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
     * Generates a single ArrayList that contains every valid move that a player's pieces can make.
     * <p>
     * A valid move cannot end on a piece owned by the same player, and cannot put the moving player in check.
     * </p>
     * 
     * @param player The player that the moves are being generated for
     * @param possibleMoves An ArrayList of all possible moves that the pieces can make
     * @return ArrayList of every valid move that a player's pieces can make.
     */
    public ArrayList<Move> allValidMoves(Player player, ArrayList<Move> possibleMoves){
        ArrayList<Move> fullValidList = new ArrayList<>();
        
        for (Move aMove:possibleMoves) {
            
            if (aMove.getEndPosition().getCurrentPiece()!=null) {    
                if (player != aMove.getEndPosition().getCurrentPiece().getOwner()) {
                    if (testMoveCheck(aMove)) { //Looking for "check" is costly, I put it in a new if{} so that it isnt called unless necessary
                        fullValidList.add(aMove);
                    }
                }
            } else {
                if (testMoveCheck(aMove)) {
                    fullValidList.add(aMove);
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
    public int isValidMove(Move move,ArrayList<Move> validMoves) {
        for (int i=0;i<validMoves.size();i++) {
            if (move.equalsStartEnd(validMoves.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private void generateStandardBoard(){ //Generates a standard chess board
        board = new Board(8,8);
        for (int p=0;p<playerList.length ;p++) {    //This is standard board generation
            for (int i=0;i<8;i++) {
                Position pawnPosition = board.getPosition(((8+ 2*playerList[p].getOrientation()) %9), i);
                Piece pawnPiece = new Pawn(playerList[p],null);
                board.setPiece(pawnPiece, pawnPosition);
                playerList[p].addPiece(pawnPiece);
            }

            Position kingPosition = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9),4);
            Piece kingPiece = new King(playerList[p],null);
            board.setPiece(kingPiece, kingPosition);
            playerList[p].addPiece(kingPiece);
            kingList[p] = kingPiece;
            
            Position queenPosition = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), 3);
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

    private void generateCustomBoard(){ 
        //Custom board generation requires user input, it is within the UserInterface class
    }

    private void generateChess960Board(){ //Generates a Chess960 board
        boolean[] filledColumns = new boolean[]{false,false,false,false,false,false,false,false};
        Random rand = new Random();

        //Generating random column numbers for pieces following chess960 placement rules
        int kingColumnNumber = rand.nextInt(6)+1;
        filledColumns[kingColumnNumber] =true;
        int rookColumnNumber1 = rand.nextInt(kingColumnNumber); //Rook to the left of the king
        filledColumns[rookColumnNumber1] =true;
        int rookColumnNumber2 = rand.nextInt(7-kingColumnNumber)+kingColumnNumber+1; //Rook to the right of the king
        filledColumns[rookColumnNumber2] =true;

        int bishopColumnNumber1=-1;
        do {
            bishopColumnNumber1 = rand.nextInt(8);
        } while (filledColumns[bishopColumnNumber1]);
        filledColumns[bishopColumnNumber1]=true;

        int bishopColumnNumber2=-1;
        if (bishopColumnNumber1%2==0) { //Bishop1's column number is even, so Bishop2 must be odd
            do {
                bishopColumnNumber2 = rand.nextInt(8);
            } while (filledColumns[bishopColumnNumber2] && bishopColumnNumber2%2!=1);
        } else {                        //Bishop1's column number is odd so Bishop2 must be even
            do {
                bishopColumnNumber2 = rand.nextInt(8);
            } while (filledColumns[bishopColumnNumber2] && bishopColumnNumber2%2!=0);
        }
        filledColumns[bishopColumnNumber2]=true;

        int knightColumnNumber1=-1;
        do {
            knightColumnNumber1 = rand.nextInt(8);
        } while (filledColumns[knightColumnNumber1]);
        filledColumns[knightColumnNumber1]=true;
        int knightColumnNumber2=-1;
        do {
            knightColumnNumber2 = rand.nextInt(8);
        } while (filledColumns[knightColumnNumber2]);
        filledColumns[knightColumnNumber2]=true;

        int queenColumnNumber=-1;
        for (int i=0;i<filledColumns.length;i++) {
            if (!filledColumns[i]){
                queenColumnNumber = i;
            }
        }
        filledColumns[queenColumnNumber]=true;

        
        
        //Placing the pieces on the board
        board = new Board(8,8);
        for (int p=0;p<playerList.length ;p++) {
            for (int i=0;i<8;i++) {
                Position pawnPosition = board.getPosition(((8+ 2*playerList[p].getOrientation()) %9), i);
                Piece pawnPiece = new Pawn(playerList[p],null);
                board.setPiece(pawnPiece, pawnPosition);
                playerList[p].addPiece(pawnPiece);
            }

            Position kingPosition = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9),kingColumnNumber);
            Piece kingPiece = new King(playerList[p],null);
            board.setPiece(kingPiece, kingPosition);
            playerList[p].addPiece(kingPiece);
            kingList[p] = kingPiece;
            
            Position queenPosition = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), queenColumnNumber);
            Piece queenPiece = new Queen(playerList[p],null);
            board.setPiece(queenPiece, queenPosition);
            playerList[p].addPiece(queenPiece);

            Position bishopPosition1 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), bishopColumnNumber1);
            Position bishopPosition2 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), bishopColumnNumber2);
            Piece bishopPiece1 = new Bishop(playerList[p],null);
            Piece bishopPiece2 = new Bishop(playerList[p],null);
            board.setPiece(bishopPiece1, bishopPosition1);
            board.setPiece(bishopPiece2, bishopPosition2);
            playerList[p].addPiece(bishopPiece1);
            playerList[p].addPiece(bishopPiece2);
            
            Position knightPosition1 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), knightColumnNumber1);
            Position knightPosition2 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), knightColumnNumber2);
            Piece knightPiece1 = new Knight(playerList[p],null);
            Piece knightPiece2 = new Knight(playerList[p],null);
            board.setPiece(knightPiece1,knightPosition1 );
            board.setPiece(knightPiece2,knightPosition2 );
            playerList[p].addPiece(knightPiece1);
            playerList[p].addPiece(knightPiece2);
            
            Position rookPosition1 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), rookColumnNumber1);
            Position rookPosition2 = board.getPosition(((8+ 1*playerList[p].getOrientation()) %9), rookColumnNumber2);
            Piece rookPiece1 = new Rook(playerList[p],null);
            Piece rookPiece2 = new Rook(playerList[p],null);
            board.setPiece(rookPiece1,rookPosition1 );
            board.setPiece(rookPiece2,rookPosition2 );
            playerList[p].addPiece(rookPiece1);
            playerList[p].addPiece(rookPiece2);
    
        }
        
    }

    public LinkedList<Move> getMoveHistory(){
        return moveHistory;
    }

    public Board getBoard(){
        return board;
    }

    public void setBoard(Board newBoard){
        this.board = newBoard;
    }

    public Player getCurrentTurn(){
        return currentTurn;
    }

    /**
     * Calling this method will change currentTurn to the next player in the list
     */
    public void nextTurn(){ 
        int nextIndex = -1;
        for (int i=0;i<playerList.length;i++) {
            if (currentTurn==playerList[i]) {
                nextIndex = i+1;
            }
        }
        if (nextIndex>=playerList.length) currentTurn=playerList[0] ;
        else currentTurn=playerList[nextIndex] ;
    }

    /**
     * Replaces the pawn at this board's pawnPromotionPosition with the new promoted piece
     * 
     * @param replacingType The pieceType of the replacing piece
     */
    public void promotePawn(PieceType replacingType){ 
        Piece replacingPiece=null;
        Position pawnPromotionPosition = board.getPawnPromotionPosition();
        switch (replacingType){
            case KING: //This case will never happen
                break;
            case QUEEN:
                replacingPiece = new Queen(pawnPromotionPosition.getCurrentPiece().getOwner(), pawnPromotionPosition);
                break;
            case ROOK:
                replacingPiece = new Rook(pawnPromotionPosition.getCurrentPiece().getOwner(), pawnPromotionPosition);
                break;
            case BISHOP:
                replacingPiece = new Bishop(pawnPromotionPosition.getCurrentPiece().getOwner(), pawnPromotionPosition);
                break;
            case KNIGHT:
                replacingPiece = new Knight(pawnPromotionPosition.getCurrentPiece().getOwner(), pawnPromotionPosition);
                break;
            case PAWN:  //This case will never happen
                break;
        }
        pawnPromotionPosition.getCurrentPiece().getOwner().addPiece(replacingPiece);
        pawnPromotionPosition.getCurrentPiece().getOwner().removePiece(pawnPromotionPosition.getCurrentPiece());    
        board.setPiece(replacingPiece, pawnPromotionPosition);
    }

    /**
     * Places a piece at a position, this will remove any piece already in that position
     * 
     * @param replacingType The pieceType of the replacing piece
     * @param placingPlayer The owner of the new piece being placed
     * @param placingPosition The position that the piece will be added to
     */
    public void placeCustomPiece(Player placingPlayer, PieceType replacingType, Position placingPosition){
        Piece replacingPiece=null;
        switch (replacingType){
            case KING: 
                replacingPiece = new King(placingPlayer, placingPosition);
                break;
            case QUEEN:
                replacingPiece = new Queen(placingPlayer, placingPosition);
                break;
            case ROOK:
                replacingPiece = new Rook(placingPlayer, placingPosition);
                break;
            case BISHOP:
                replacingPiece = new Bishop(placingPlayer, placingPosition);
                break;
            case KNIGHT:
                replacingPiece = new Knight(placingPlayer, placingPosition);
                break;
            case PAWN:  
                replacingPiece = new Pawn(placingPlayer, placingPosition);
                break;
        }
        placingPlayer.addPiece(replacingPiece);
        if (placingPosition.getCurrentPiece()!=null) {
            placingPosition.getCurrentPiece().getOwner().removePiece(placingPosition.getCurrentPiece());
        }
        board.setPiece(replacingPiece, placingPosition);
    }

    public Piece[] getKingList(){
        return kingList;
    }

}