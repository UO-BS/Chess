import java.lang.Math;
public class Bishop extends Piece{

    public Bishop(Player player, Position position) {
        super(player, position);
    }
    
    public boolean canMove(Board board,Position initial, Position end) {

        int xDistance = initial.getXDistance(end);
        int yDistance = initial.getYDistance(end);
        System.out.println(xDistance);
        System.out.println(yDistance);

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
                    System.out.println(initial.getY());
                    System.out.println(initial.getX());
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