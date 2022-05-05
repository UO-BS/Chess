import java.lang.Math;
public class Pawn extends Piece{
    

    public Pawn(Player player) {
        super(player);
    }

    public boolean canMove(Board board, Position initial, Position end) {
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

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"P");
    }

}