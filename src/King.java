import java.lang.Math;
/**
 * Class representing a King chess piece.
 * @author UO-BS
 */
public class King extends Piece{

    public King(Player player, Position position) {
        super(player, position);
    }

    public Piece copyPiece(){
        Piece newPiece = new King(this.getOwner(),this.getPosition());
        newPiece.setHasMoved(this.getHasMoved());
        newPiece.setState(this.getState());
        return newPiece;
    }
    
    public boolean canMove(Board board, Move newMove) {
        Position initial = newMove.getStartPosition();
        Position end = newMove.getEndPosition();
        //To be added: Check if spot is dangerous
        int xDistance = initial.getXDistance(end);
        int yDistance = initial.getYDistance(end);
        
        if (Math.abs(xDistance)>1 || Math.abs(yDistance)>1 || (xDistance==0 && yDistance==0)) {
            return false;
        }
        return true;
    }

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"K");
    }

}