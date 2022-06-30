import java.lang.Math;
/**
 * Class representing a Bishop chess piece.
 * @author UO-BS
 */
public class Bishop extends Piece{

    public Bishop(Player player, Position position) {
        super(player, position);
    }

    public PieceType getPieceType(){
        return PieceType.BISHOP;
    }

    public Piece copyPiece(){
        Piece newPiece = new Bishop(this.getOwner(),this.getPosition());
        newPiece.setHasMoved(this.getHasMoved());
        newPiece.setState(this.getState());
        return newPiece;
    }
    
    public boolean canMove(Board board, Move newMove) {
        Position initial = newMove.getStartPosition();
        Position end = newMove.getEndPosition();

        int xDistance = initial.getXDistance(end);
        int yDistance = initial.getYDistance(end);

        if ((Math.abs(yDistance) != Math.abs(xDistance)) || (yDistance==0)) {
            return false;
        }

        if (xDistance >0 && yDistance >0) {//Positive x, Positive y direction
            for (int i=1;i<Math.abs(yDistance);i++) {
                if (board.getPosition(initial.getY()+i, initial.getX()+i).getCurrentPiece()!=null){ 
                    return false;
                }
            }
        }
        if (xDistance >0 && yDistance <0) {//Positive x, Negative y direction
            for (int i=1;i<Math.abs(yDistance);i++) {
                if (board.getPosition(initial.getY()-i,initial.getX()+i).getCurrentPiece()!=null){ 
                    return false;
                }
            }
        }
        if (xDistance <0 && yDistance >0) {//Negative x, Positive y direction
            for (int i=1;i<Math.abs(yDistance);i++) {
                if (board.getPosition(initial.getY()+i, initial.getX()-i).getCurrentPiece()!=null){ 
                    return false;
                }
            }
        }
        if (xDistance <0 && yDistance <0) {//Negative x, Negative y direction
            for (int i=1;i<Math.abs(yDistance);i++) {
                if (board.getPosition( initial.getY()-i,initial.getX()-i).getCurrentPiece()!=null){ 
                    return false;
                }
            }
        }
        
        return true;
    }

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"B");
    }

}