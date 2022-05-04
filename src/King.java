public class King extends Piece{

    public King(Player player) {
        super(player);
    }

    public boolean canMove(Board board, Position initial, Position end) {
        //To be added: Check if spot is dangerous
        int xDistance = inital.getXDistance(end);
        int yDistance = inital.getYDistance(end);
        
        if (abs(xDistance)>1 || abs(yDistance)>1 || (xDistance==0 && yDistance==0)) {
            return false;
        }
        return true;
    }

    public String toString(){
        return ((this.getOwner().getOrientation()==1)?"W":"B"+"K");
    }

}