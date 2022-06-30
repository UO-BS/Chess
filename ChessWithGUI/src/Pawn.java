import java.lang.Math;
/**
 * Class representing a Pawn chess piece.
 * @author UO-BS
 */
public class Pawn extends Piece{
    
    

    public Pawn(Player player, Position position) {
        super(player, position);
        if (this.getOwner().getOrientation()==1) { //This is a white piece
            this.setImageFilepath("pieceTexture/Chess_plt60.png");
        } else { //This is a black piece
            this.setImageFilepath("pieceTexture/Chess_pdt60.png");
        }
    }

    

    public PieceType getPieceType(){
        return PieceType.PAWN;
    }

    public Piece copyPiece(){
        Piece newPiece = new Pawn(this.getOwner(),this.getPosition());
        newPiece.setHasMoved(this.getHasMoved());
        newPiece.setState(this.getState());
        return newPiece;
    }

    public boolean canMove(Board board, Move newMove) {
        Position initial = newMove.getStartPosition();
        Position end = newMove.getEndPosition();
        
        if (!this.getHasMoved()
                && initial.getYDistance(end)==2*this.getOwner().getOrientation() 
                && initial.getXDistance(end)==0) {
            for (int i=1;i<=2;i++) {
                if (board.getPosition( initial.getY()+i*this.getOwner().getOrientation(),initial.getX()).getCurrentPiece()!=null) {
                    return false;
                }
            }
            return true;
        }
        //Non capture movement
        if (initial.getYDistance(end)==1*this.getOwner().getOrientation() 
                && initial.getXDistance(end)==0   
                && end.getCurrentPiece()==null) {
            return true;
        }
        //Capture movement
        if (initial.getYDistance(end)==1*this.getOwner().getOrientation() 
                && Math.abs(initial.getXDistance(end))==1 
                && end.getCurrentPiece()!=null) {
            return true;
        }
        //EnPassant movement
        if (board.getVulnerableToEnPassant()!=null){
            if (initial.getYDistance(end)==1*this.getOwner().getOrientation() 
                    && Math.abs(initial.getXDistance(end))==1 
                    && board.getVulnerableToEnPassant().equalsXY(end)) {
                return true;
            }
        }
        return false;
    }

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"P");
    }

}