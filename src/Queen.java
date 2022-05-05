import java.lang.Math;
public class Queen extends Piece{

    public Queen(Player player) {
        super(player);
    }

    public boolean canMove(Board board,Position initial, Position end) {
        int xDistance = initial.getXDistance(end);
        int yDistance = initial.getYDistance(end);


        if ((Math.abs(yDistance) == Math.abs(xDistance)) && (yDistance!=0)) {     //Bishop's movement
            if (xDistance >0 && yDistance >0) {//Positive x, Positive y direction
                for (int i=0;i<Math.abs(yDistance);i++) {
                    if (board.getPosition(initial.getX()+i, initial.getY()+i).getCurrentPiece()!=null){ 
                        return false;
                    }
                }
            }
            if (xDistance >0 && yDistance <0) {//Positive x, Negative y direction
                for (int i=0;i<Math.abs(yDistance);i++) {
                    if (board.getPosition(initial.getX()-i, initial.getY()+i).getCurrentPiece()!=null){ 
                        return false;
                    }
                }
            }
            if (xDistance <0 && yDistance >0) {//Negative x, Positive y direction
                for (int i=0;i<Math.abs(yDistance);i++) {
                    if (board.getPosition(initial.getX()+i, initial.getY()-i).getCurrentPiece()!=null){ 
                        return false;
                    }
                }
            }
            if (xDistance <0 && yDistance <0) {//Negative x, Negative y direction
                for (int i=0;i<Math.abs(yDistance);i++) {
                    if (board.getPosition(initial.getX()-i, initial.getY()-i).getCurrentPiece()!=null){ 
                        return false;
                    }
                }
            }
            
            return true;
        }
        
        //----------------------------------------------------------------------------------------

        if (!(xDistance==0 && yDistance==0) && (xDistance==0 || yDistance==0)) {    //Rook's movement
            if (xDistance!=0) {
                if (xDistance>0) {
                    for (int i = initial.getX();i<initial.getX()+xDistance;i++) {
                        if (board.getPosition(i,initial.getY()).getCurrentPiece()!=null){
                            return false;
                        }
                    }
                } else { //xDistance < 0
                    for (int i = initial.getX();i>initial.getX()+xDistance;i--) {
                        if (board.getPosition(i,initial.getY()).getCurrentPiece()!=null){
                            return false;
                        }
                    }
                }
                
            } else { //yDistance != 0
                    
                if (yDistance>0) {
                    for (int i = initial.getY();i<initial.getY()+yDistance;i++) {
                        if (board.getPosition(initial.getX(),i).getCurrentPiece()!=null){
                            return false;
                        }
                    }
                } else { //yDistance < 0
                    for (int i = initial.getY();i>initial.getY()+yDistance;i--) {
                        if (board.getPosition(initial.getX(),i).getCurrentPiece()!=null){
                            return false;
                        }
                    }
                }
            }
            
            return true;
        }

        return false; //returns false if move is neither Rook nor Bishop
        
    }

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"Q");
    }
    

}