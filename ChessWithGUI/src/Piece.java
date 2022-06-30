import java.util.ArrayList;
/**
 * Class representing a chess piece.
 * @author UO-BS
 */
public abstract class Piece {
    
    private Player owner;
    private boolean inPlay;
    private boolean hasMoved;
    private Position currentPosition;
    private String imageFilepath;

    /**
     * Generates a piece in the game.
     * 
     * @param player The owner of this piece.
     * @param position The Position of this piece on the board.
     */
    public Piece(Player player, Position position) {
        owner = player;
        inPlay = true;
        hasMoved=false;
        currentPosition = position;
    }

    public String getImageFilepath(){
        return imageFilepath;
    }

    public void setImageFilepath(String path){
        imageFilepath= path;
    }

    public abstract Piece copyPiece();

    public abstract PieceType getPieceType();

    public Position getPosition() {
        return currentPosition;
    }

    public void setPosition(Position position) {
        currentPosition = position;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean getState() {
        return inPlay;
    }

    public void setState(boolean newState) {
        this.inPlay = newState;
    }

    public void setHasMoved(boolean moved) {
        this.hasMoved = moved;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    /**
     * Generates an ArrayList containing all possible moves that a piece can make from its current position
     * 
     * @param board A board containing all the Positions
     * @return An ArrayList containing all possible moves that a piece can piece
     */
    public ArrayList<Move> getPossibleMoves(Board board){
        ArrayList<Move> possibleMoves = new ArrayList<Move>();
        if (inPlay) { //Cannot have any moves if the piece has been removed
            for (int i=0;i<board.getRows();i++) {
                for (int j=0;j<board.getColumns();j++) {
                    Move testMove = new Move(currentPosition,board.getPosition(i, j));

                    if (this.canMove(board, testMove)) {

                        //Special Moves:
                        //Vulnerable to EnPassant
                        if (testMove.getStartPosition().getCurrentPiece().getPieceType()==PieceType.PAWN 
                                && testMove.getStartPosition().getYDistance(testMove.getEndPosition())==2*this.getOwner().getOrientation() 
                                && testMove.getStartPosition().getXDistance(testMove.getEndPosition())==0) {
                            testMove.setMoveType(MoveType.VULNERABLETOENPASSANT);
                        }
                        //EnPassant
                        if (board.getVulnerableToEnPassant()!=null){
                            if (testMove.getStartPosition().getCurrentPiece().getPieceType()==PieceType.PAWN
                                    && testMove.getStartPosition().getYDistance(testMove.getEndPosition())==1*this.getOwner().getOrientation() 
                                    && Math.abs(testMove.getStartPosition().getXDistance(testMove.getEndPosition()))==1 
                                    && board.getVulnerableToEnPassant().equalsXY(testMove.getEndPosition())) {
                            testMove.setMoveType(MoveType.ENPASSANT);
                            }
                        }
                        //Pawn promotion
                        if (testMove.getStartPosition().getCurrentPiece().getPieceType()==PieceType.PAWN 
                                && testMove.getEndPosition().getY()==(board.getRows()-1*this.getOwner().getOrientation())%(board.getRows()+1)) {
                            testMove.setMoveType(MoveType.PAWNPROMOTION);
                        }
                        //Castling
                        if (testMove.getStartPosition().getCurrentPiece().getPieceType()==PieceType.KING
                                && Math.abs(testMove.getStartPosition().getXDistance(testMove.getEndPosition()))==2) {
                            testMove.setMoveType(MoveType.CASTLING);
                        }

                        possibleMoves.add(testMove);
                    }
                }
            }
        }
        
        return possibleMoves;
    }

    public boolean equals(Piece other) {
        if (other == null) {
            return false;
        }
        if (this.owner==other.getOwner() && this.currentPosition.equalsXY(other.getPosition()) && this.inPlay==other.getState()){
            return true;
        }
        return false;
    }

    /**
     * Determines if a piece of a class can make a move
     * 
     * @param newMove A move from any Position to another Position
     * @return Boolean representing if a piece of a class can complete the move
     */
    public abstract boolean canMove(Board board, Move newMove);

}