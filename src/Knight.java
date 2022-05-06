import java.lang.Math;
public class Knight extends Piece{

    public Knight(Player player, Position position) {
        super(player, position);
    }

    public boolean canMove(Board board, Move newMove) {
        Position initial = newMove.getStartPosition();
        Position end = newMove.getEndPosition();
        
        int xDistance = initial.getXDistance(end);
        int yDistance = initial.getYDistance(end);
        
        if ((Math.abs(xDistance)==2 && Math.abs(yDistance)==1)
                || (Math.abs(yDistance)==2 && Math.abs(xDistance)==1)) {
            return true;
        }
        return false;
    }

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"N");
    }

}