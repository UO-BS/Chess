import java.lang.Math;
public class Pawn extends Piece{

    public Pawn(Player player) {
        super(player);
    }

    public boolean canMove(Board board, Position initial, Position end) {
        //To be added: Pawns can move 2 spaces forward if they have not been moved yet. 
        //This will be added by searching the player's move history
        //To be added: different rules around forward movement (cannot move forward no matter what piece is in front)

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