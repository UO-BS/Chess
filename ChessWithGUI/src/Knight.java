import java.lang.Math;
/**
 * Class representing a Knight chess piece.
 * @author UO-BS
 */
public class Knight extends Piece{

    public Knight(Player player, Position position) {
        super(player, position);
        if (this.getOwner().getOrientation()==1) { //This is a white piece
            this.setImageFilepath("pieceTexture/Chess_nlt60.png");
        } else { //This is a black piece
            this.setImageFilepath("pieceTexture/Chess_ndt60.png");
        }
    }

    public PieceType getPieceType(){
        return PieceType.KNIGHT;
    }

    public Piece copyPiece(){
        Piece newPiece = new Knight(this.getOwner(),this.getPosition());
        newPiece.setHasMoved(this.getHasMoved());
        newPiece.setState(this.getState());
        return newPiece;
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