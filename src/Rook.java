public class Rook extends Piece{

    public Rook(Player player, Position position) {
        super(player, position);
    }

    public boolean canMove(Board board,Position initial, Position end) {
        //To be added: Castling once player move history is added
        int xDistance = initial.getXDistance(end);
        int yDistance = initial.getYDistance(end);

        if ((xDistance==0 && yDistance==0) || (xDistance!=0 && yDistance!=0)) {
            return false;
        }

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

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"R");
    }

}