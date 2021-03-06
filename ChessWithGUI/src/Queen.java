import java.lang.Math;
/**
 * Class representing a Queen chess piece.
 * @author UO-BS
 */
public class Queen extends Piece{

    public Queen(Player player, Position position) {
        super(player, position);
        if (this.getOwner().getOrientation()==1) { //This is a white piece
            this.setImageFilepath("pieceTexture/Chess_qlt60.png");
        } else { //This is a black piece
            this.setImageFilepath("pieceTexture/Chess_qdt60.png");
        }
    }

    public PieceType getPieceType(){
        return PieceType.QUEEN;
    }

    public Piece copyPiece(){
        Piece newPiece = new Queen(this.getOwner(),this.getPosition());
        newPiece.setHasMoved(this.getHasMoved());
        newPiece.setState(this.getState());
        return newPiece;
    }

    public boolean canMove(Board board, Move newMove) {
        Position initial = newMove.getStartPosition();
        Position end = newMove.getEndPosition();

        int xDistance = initial.getXDistance(end);
        int yDistance = initial.getYDistance(end);


        if ((Math.abs(yDistance) == Math.abs(xDistance)) && (yDistance!=0)) {     //Bishop's movement
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
        
        //----------------------------------------------------------------------------------------

        if (!(xDistance==0 && yDistance==0) && (xDistance==0 || yDistance==0)) {    //Rook's movement
            if (xDistance!=0) {
                if (xDistance>0) {
                    for (int i = 1;i<xDistance;i++) {
                        if (board.getPosition(initial.getY(),initial.getX()+i).getCurrentPiece()!=null){
                            return false;
                            
                        }
                    }
                } else { //xDistance < 0
                    for (int i = -1;i>xDistance;i--) {
                        if (board.getPosition(initial.getY(),initial.getX()+i).getCurrentPiece()!=null){
                            return false;
                        }
                    }
                }
                
            } else { //yDistance != 0
                    
                if (yDistance>0) {

                    for (int i = 1;i<yDistance;i++) {
                        if (board.getPosition(initial.getY()+i,initial.getX()).getCurrentPiece()!=null){
                            return false;
                        }
                    }

                } else { //yDistance < 0
                    for (int i = -1;i>yDistance;i--) {
                        if (board.getPosition(initial.getY()+i,initial.getX()).getCurrentPiece()!=null){
                            return false;
                        }
                    }
                }
            }
            
            return true;
        }

        return false; 
        
    }

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"Q");
    }
    

}