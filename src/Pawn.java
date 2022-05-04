public class Pawn extends Piece{

    public Pawn(Player player) {
        super(player);
    }

    public boolean canMove(Position initial, Position end) {
        //To be added: Pawns can move 2 spaces forward if they have not been moved yet. 
        //This will be added by searching the player's move history

        if (initial.getYDistance(end)==1*this.getOwner().getOrientation() 
                && end.getCurrentPiece()==null) {
            return true;
        }
        if (initial.getYDistance(end)==1*this.getOwner().getOrientation() 
                && initial.getXDistance(end)==1 
                && end.getCurrentPiece()!=null) {
            return true;
        }
        return false;
    }

}