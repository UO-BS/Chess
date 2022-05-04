public class Bishop extends Piece{

    public Bishop(Player player) {
        super(player);
    }

    public boolean canMove(Board board,Position initial, Position end) {

        int xDistance = inital.getXDistance(end);
        int yDistance = inital.getYDistance(end);

        if ((abs(yDistance) != abs(xDistance)) || (yDistance==0)) {
            return false;
        }

        if (xDistance >0 && yDistance >0) {//Positive x, Positive y direction
            for (int i=0;i<abs(yDistance);i++) {
                if (board.getPosition(initial.getX()+i, initial.getY()+i).getCurrentPiece()!=null){ 
                    return false;
                }
            }
        }
        if (xDistance >0 && yDistance <0) {//Positive x, Negative y direction
            for (int i=0;i<abs(yDistance);i++) {
                if (board.getPosition(initial.getX()-i, initial.getY()+i).getCurrentPiece()!=null){ 
                    return false;
                }
            }
        }
        if (xDistance <0 && yDistance >0) {//Negative x, Positive y direction
            for (int i=0;i<abs(yDistance);i++) {
                if (board.getPosition(initial.getX()+i, initial.getY()-i).getCurrentPiece()!=null){ 
                    return false;
                }
            }
        }
        if (xDistance <0 && yDistance <0) {//Negative x, Negative y direction
            for (int i=0;i<abs(yDistance);i++) {
                if (board.getPosition(initial.getX()-i, initial.getY()-i).getCurrentPiece()!=null){ 
                    return false;
                }
            }
        }
        
        return true;
    }

    public String toString(){
        return ((this.getOwner().getOrientation()==1)?"W":"B"+"B");
    }

}