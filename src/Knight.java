public class Knight extends Piece{

    public Knight(Player player) {
        super(player);
    }

    public boolean canMove(Board board, Position initial, Position end) {
        int xDistance = inital.getXDistance(end);
        int yDistance = inital.getYDistance(end);
        
        if ((abs(xDistance)==2 && abs(yDistance)==1)
                || (abs(yDistance)==2 && abs(xDistance)==1)) {
            return true;
        }
        return false;
    }

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"N");
    }

}