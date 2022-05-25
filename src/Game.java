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
    private boolean gameOver;
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
        gameOver = false;
        moveHistory = new LinkedList<Move>();
        
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
                UserInterface.displayText(currentTurn.getName()+"'s turn");
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
            UserInterface.displayText(end.getCurrentPiece()+" has been removed"); 
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
                            Piece knightPiece = new Knight(end.getCurrentPiece().getOwner(),end);   
                            end.getCurrentPiece().getOwner().addPiece(knightPiece);
                            end.getCurrentPiece().getOwner().removePiece(end.getCurrentPiece());    
                            board.setPiece(knightPiece, end);
                            break;
                        case "Rook":
                            Piece rookPiece = new Rook(end.getCurrentPiece().getOwner(),end);   
                            end.getCurrentPiece().getOwner().addPiece(rookPiece);
                            end.getCurrentPiece().getOwner().removePiece(end.getCurrentPiece());    
                            board.setPiece(rookPiece, end);
                            break;
                    }
                    break;
                
                case VULNERABLETOENPASSANT:
                    board.setVulnerableToEnPassant(board.getPosition((move.getStartPosition().getY()+move.getEndPosition().getY())/2, move.getStartPosition().getX()));
                    break;
                
                case ENPASSANT:
                    Position passingPiecePosition = board.getPosition(move.getEndPosition().getY()-(move.getStartPosition().getYDistance(move.getEndPosition())),move.getEndPosition().getX());
                    UserInterface.displayText(passingPiecePosition.getCurrentPiece()+" has been removed"); 
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
        ArrayList<Move> moveOptions = this.allValidMoves(currentTurn, Game.allPossibleMoves(board, currentTurn));
        boolean checked = false; //Variable so that we do not have to look for check more than once per turn
        int moveNumber = -1;

        if (this.inCheck(currentTurn)){
            UserInterface.displayText(currentTurn.getName()+" is in check...");
            checked = true;
        }

        if (moveOptions.size()==0) {
            if (checked) {
                UserInterface.displayText(currentTurn.getName()+" has been checkmated!");
                currentTurn.setPlayerState(1);
            } else {
                UserInterface.displayText(currentTurn.getName()+" has been stalemated!");
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
    private static ArrayList<Move> allPossibleMoves(Board board, Player player){
        ArrayList<Move> fullList = new ArrayList<>();
        for (Piece piece:player.getPieceList()) {
            fullList.addAll(piece.getPossibleMoves(board));
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
    private int isValidMove(Move move,ArrayList<Move> validMoves) {
        for (int i=0;i<validMoves.size();i++) {
            if (move.equalsStartEnd(validMoves.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private void generateStandardBoard(){
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
        int boardHeight = UserInterface.getIntInput("Enter the height of the board (2-20): ", 2, 20);
        int boardWidth = UserInterface.getIntInput("Enter the width of the board (2-20): ", 2, 20);
        board = new Board(boardHeight,boardWidth);

        String[] validOptions = new String[]{"Pawn","King","Queen","Bishop","Knight","Rook","Done","Switch"};
        String userChoice="Switch";
        int placingPlayerNumber = 0;
        UserInterface.displayText("Placing "+playerList[placingPlayerNumber].getName()+"'s pieces");

        while (!userChoice.equals("Done")) {
            UserInterface.displayASCII(board);
            userChoice = UserInterface.getStringInput("What type of piece would you like to place? (Write Switch to switch players, and Done when you've finished)", validOptions);
            switch(userChoice) {
                case "Switch":
                    placingPlayerNumber++;
                    if (placingPlayerNumber==playerList.length) {
                        placingPlayerNumber=0;
                    }
                    UserInterface.displayText("Placing "+playerList[placingPlayerNumber].getName()+"'s pieces");

                    break;
                case "Done":
                    for (int i=0;i<kingList.length;i++) {
                        if (kingList[i]==null) {
                            UserInterface.displayText("A player does not have a king");
                            userChoice="";
                        }
                    }
                    break;
                case "Pawn":
                    Position pawnPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                    Piece pawnPiece = new Pawn(playerList[placingPlayerNumber],null);
                    if (pawnPosition.getCurrentPiece()!=null) {
                        playerList[placingPlayerNumber].getPieceList().remove(pawnPosition.getCurrentPiece());
                        if (pawnPosition.getCurrentPiece().getPieceType()==PieceType.KING) {
                            for (int kingNumber=0;kingNumber<kingList.length;kingNumber++) {
                                kingList[kingNumber]=null;
                            }
                        }
                    }
                    board.setPiece(pawnPiece, pawnPosition);
                    playerList[placingPlayerNumber].addPiece(pawnPiece);
                    break;
                case "King":
                    if (kingList[placingPlayerNumber]==null) {
                        Position kingPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                        Piece kingPiece = new King(playerList[placingPlayerNumber],null);
                        if (kingPosition.getCurrentPiece()!=null) {
                            playerList[placingPlayerNumber].getPieceList().remove(kingPosition.getCurrentPiece());
                            if (kingPosition.getCurrentPiece().getPieceType()==PieceType.KING) {
                                for (int kingNumber=0;kingNumber<kingList.length;kingNumber++) {
                                kingList[kingNumber]=null;
                            }
                            }
                        }
                        board.setPiece(kingPiece, kingPosition);
                        playerList[placingPlayerNumber].addPiece(kingPiece);
                        kingList[placingPlayerNumber] = kingPiece;
                    } else {
                        UserInterface.displayText("There is already a king for this player");
                    }
                    break;
                case "Queen":
                    Position queenPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                    Piece queenPiece = new Queen(playerList[placingPlayerNumber],null);
                    if (queenPosition.getCurrentPiece()!=null) {
                        playerList[placingPlayerNumber].getPieceList().remove(queenPosition.getCurrentPiece());
                        if (queenPosition.getCurrentPiece().getPieceType()==PieceType.KING) {
                            for (int kingNumber=0;kingNumber<kingList.length;kingNumber++) {
                                kingList[kingNumber]=null;
                            }
                        }
                    }
                    board.setPiece(queenPiece, queenPosition);
                    playerList[placingPlayerNumber].addPiece(queenPiece);
                    break;
                case "Bishop":
                    Position bishopPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                    Piece bishopPiece = new Bishop(playerList[placingPlayerNumber],null);
                    if (bishopPosition.getCurrentPiece()!=null) {
                        playerList[placingPlayerNumber].getPieceList().remove(bishopPosition.getCurrentPiece());
                        if (bishopPosition.getCurrentPiece().getPieceType()==PieceType.KING) {
                            for (int kingNumber=0;kingNumber<kingList.length;kingNumber++) {
                                kingList[kingNumber]=null;
                            }
                        }
                    }
                    board.setPiece(bishopPiece, bishopPosition);
                    playerList[placingPlayerNumber].addPiece(bishopPiece);
                    break;
                case "Knight":
                    Position knightPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                    Piece knightPiece = new Knight(playerList[placingPlayerNumber],null);
                    if (knightPosition.getCurrentPiece()!=null) {
                        playerList[placingPlayerNumber].getPieceList().remove(knightPosition.getCurrentPiece());
                        if (knightPosition.getCurrentPiece().getPieceType()==PieceType.KING) {
                            for (int kingNumber=0;kingNumber<kingList.length;kingNumber++) {
                                kingList[kingNumber]=null;
                            }
                        }
                    }
                    board.setPiece(knightPiece,knightPosition );
                    playerList[placingPlayerNumber].addPiece(knightPiece);
                    break;
                case "Rook":
                    Position rookPosition = UserInterface.getPositionFromUser(board, "Where would you like to place it?");
                    Piece rookPiece = new Rook(playerList[placingPlayerNumber],null);
                    if (rookPosition.getCurrentPiece()!=null) {
                        playerList[placingPlayerNumber].getPieceList().remove(rookPosition.getCurrentPiece());
                        if (rookPosition.getCurrentPiece().getPieceType()==PieceType.KING) {
                            for (int kingNumber=0;kingNumber<kingList.length;kingNumber++) {
                                kingList[kingNumber]=null;
                            }
                        }
                    }
                    board.setPiece(rookPiece,rookPosition);
                    playerList[placingPlayerNumber].addPiece(rookPiece);
                    break;
            }
        }
    }

    private void generateChess960Board(){
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
}