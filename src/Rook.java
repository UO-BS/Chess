/**
 * Class representing a Rook chess piece.
 * @author UO-BS
 */
public class Rook extends Piece{

    public Rook(Player player, Position position) {
        super(player, position);
    }

    public Piece copyPiece(){
        Piece newPiece = new Rook(this.getOwner(),this.getPosition());
        newPiece.setHasMoved(this.getHasMoved());
        newPiece.setState(this.getState());
        return newPiece;
    }

    public boolean canMove(Board board, Move newMove) {
        Position initial = newMove.getStartPosition();
        Position end = newMove.getEndPosition();
        
        //To be added: Castling once player move history is added
        
        int xDistance = initial.getXDistance(end);
        int yDistance = initial.getYDistance(end);

        if ((xDistance==0 && yDistance==0) || (xDistance!=0 && yDistance!=0)) {
            return false;
        }

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

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"R");
    }

}