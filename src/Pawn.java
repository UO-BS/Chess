import java.lang.Math;
import java.util.ArrayList;
/**
 * Class representing a Pawn chess piece.
 * @author UO-BS
 */
public class Pawn extends Piece{
    

    public Pawn(Player player, Position position) {
        super(player, position);
    }

    public boolean canMove(Board board, Move newMove) {
        Position initial = newMove.getStartPosition();
        Position end = newMove.getEndPosition();
        
        if (!this.getHasMoved()
                && initial.getYDistance(end)==2*this.getOwner().getOrientation() 
                && initial.getXDistance(end)==0) {
            for (int i=1;i<=2;i++) {
                if (board.getPosition( initial.getY()+i*this.getOwner().getOrientation(),initial.getX()).getCurrentPiece()!=null) {
                    return false;
                }
            }
            return true;
        }
        if (initial.getYDistance(end)==1*this.getOwner().getOrientation() 
                && initial.getXDistance(end)==0   
                && end.getCurrentPiece()==null) {
            return true;
        }
        if (initial.getYDistance(end)==1*this.getOwner().getOrientation() 
                && Math.abs(initial.getXDistance(end))==1 
                && end.getCurrentPiece()!=null) {
            return true;
        }
        return false;
    }

    /**
     * Generates an ArrayList containing all possible moves that a piece can make from its current position
     * <p>
     * This is an override of the same method in Piece. The only difference here is the check for a "Special" move.
     * This same change can be achieved in the canMove method by modifying the parameter newMove, 
     * though it might be considered bad practice to do so as it would be hard to notice this change to the newMove parameter.
     * </p>
     * 
     * @param board A board containing all the Positions
     * @return An ArrayList containing all possible moves that a piece can piece
     */
    public ArrayList<Move> getPossibleMoves(Board board){
        ArrayList<Move> possibleMoves = new ArrayList();
        if (this.getState()) { //Cannot have any moves if the piece has been removed
            for (int i=0;i<board.getRows();i++) {
                for (int j=0;j<board.getColumns();j++) {
                    
                    Move testMove = new Move(this.getPosition(),board.getPosition(i, j));
                    if (testMove.getEndPosition().getY()==(board.getRows()-1*this.getOwner().getOrientation())%(board.getRows()+1)) {
                        testMove.setSpecial("PawnPromotion");
                        System.out.println("pawnpromote1");
                    }

                    if (this.canMove(board, testMove)) {
                        possibleMoves.add(testMove);
                    }
                }
            }
        }
        
        return possibleMoves;
    }

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"P");
    }

}