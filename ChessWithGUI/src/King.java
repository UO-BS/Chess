import java.lang.Math;
/**
 * Class representing a King chess piece.
 * @author UO-BS
 */
public class King extends Piece{

    public King(Player player, Position position) {
        super(player, position);
        if (this.getOwner().getOrientation()==1) { //This is a white piece
            this.setImageFilepath("pieceTexture/Chess_klt60.png");
        } else { //This is a black piece
            this.setImageFilepath("pieceTexture/Chess_kdt60.png");
        }
    }

    public PieceType getPieceType(){
        return PieceType.KING;
    }

    public Piece copyPiece(){
        Piece newPiece = new King(this.getOwner(),this.getPosition());
        newPiece.setHasMoved(this.getHasMoved());
        newPiece.setState(this.getState());
        return newPiece;
    }
    
    public boolean canMove(Board board, Move newMove) {
        Position initial = newMove.getStartPosition();
        Position end = newMove.getEndPosition();

        int xDistance = initial.getXDistance(end);
        int yDistance = initial.getYDistance(end);

        if (Math.abs(xDistance)<=1 && Math.abs(yDistance)<=1 && (xDistance!=0 || yDistance!=0)) {
            return true;
        }
        
        //castling: go through all moves and search for end position being on important squares!!!!!!!!!!!!!!!!!!!!!!!
        //Castling move
        if (!this.getHasMoved() && Math.abs(xDistance)==2 && yDistance==0){
            int castlingRookDistance = getCastlingRookDistance(board, newMove);
            if (castlingRookDistance!=0) {
                if (!castlingBlocked(board, castlingRookDistance) && !castlingChecked(board, castlingRookDistance)) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getCastlingRookDistance(Board board, Move newMove){
        Position initial = newMove.getStartPosition();
        Position end = newMove.getEndPosition();

        int xDistance = initial.getXDistance(end);

        //Castling toward the right side of the board
        if (xDistance==2) {
            if (board.insideBoard(initial.getY(), initial.getX()+3)) {
                if (board.getPosition(initial.getY(), initial.getX()+3).getCurrentPiece()!=null) {
                    if (board.getPosition(initial.getY(), initial.getX()+3).getCurrentPiece().getPieceType()==PieceType.ROOK
                            && !board.getPosition(initial.getY(), initial.getX()+3).getCurrentPiece().getHasMoved()) {
                        return 3;
                        
                    }
                }
            }
            if (board.insideBoard(initial.getY(), initial.getX()+4)) {
                if (board.getPosition(initial.getY(), initial.getX()+4).getCurrentPiece()!=null) {
                    if (board.getPosition(initial.getY(), initial.getX()+4).getCurrentPiece().getPieceType()==PieceType.ROOK
                            && !board.getPosition(initial.getY(), initial.getX()+4).getCurrentPiece().getHasMoved()) { 
                        return 4;
                    }
                }
            }
        }
    
        //Castling toward the left side of the board
        if (xDistance==-2) {
            if (board.insideBoard(initial.getY(), initial.getX()-3)) {
                if (board.getPosition(initial.getY(), initial.getX()-3).getCurrentPiece()!=null) {
                    if (board.getPosition(initial.getY(), initial.getX()-3).getCurrentPiece().getPieceType()==PieceType.ROOK
                            && !board.getPosition(initial.getY(), initial.getX()-3).getCurrentPiece().getHasMoved()) {
                        return -3;
                    }
                }
            }
            if (board.insideBoard(initial.getY(), initial.getX()-4)) {
                if (board.getPosition(initial.getY(), initial.getX()-4).getCurrentPiece()!=null) {
                    if (board.getPosition(initial.getY(), initial.getX()-4).getCurrentPiece().getPieceType()==PieceType.ROOK
                            && !board.getPosition(initial.getY(), initial.getX()-4).getCurrentPiece().getHasMoved()) {
                        
                        return -4;
                                
                    }
                }
            }
        }
        
        return 0; //If there is no rook that can castle
        
    }

    private boolean castlingBlocked(Board board, int rookDistance){
        
        for (int distance=1;distance<Math.abs(rookDistance);distance++) {
            
            if (rookDistance>0) {
                if (board.getPosition(this.getPosition().getY(), this.getPosition().getX()+distance).getCurrentPiece()!=null) {
                    return true;
                }
            } else { //rookDistance<0
                if (board.getPosition(this.getPosition().getY(), this.getPosition().getX()-distance).getCurrentPiece()!=null) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean castlingChecked(Board board, int rookDistance){
        
        for (int distance=0;distance<2;distance++) {
            
            //Castling to the right
            if (rookDistance>0) {

                for (int attackerY=0;attackerY<board.getRows();attackerY++) {
                    for (int attackerX=0;attackerX<board.getColumns();attackerX++) {
                        if (board.getPosition(attackerY, attackerX).getCurrentPiece()!=null) {
                            if (board.getPosition(attackerY, attackerX).getCurrentPiece().getOwner()!=this.getOwner()) {

                                //Making sure no piece can put the king in check as it performs the castling move
                                Position castlingPosition = board.getPosition(this.getPosition().getY(), this.getPosition().getX()+distance);
                                Move attackingMove = new Move(board.getPosition(attackerY, attackerX), castlingPosition);
                                if (board.getPosition(attackerY, attackerX).getCurrentPiece().canMove(board, attackingMove)) {
                                    return true;
                                }

                            }
                        }
                    }
                }
                
            //Castling to the left
            } else { //rookDistance<0
                
                for (int attackerY=0;attackerY<board.getRows();attackerY++) {
                    for (int attackerX=0;attackerX<board.getColumns();attackerX++) {
                        if (board.getPosition(attackerY, attackerX).getCurrentPiece()!=null) {
                            if (board.getPosition(attackerY, attackerX).getCurrentPiece().getOwner()!=this.getOwner()) {

                                //Making sure no piece can put the king in check as it performs the castling move
                                Position castlingPosition = board.getPosition(this.getPosition().getY(), this.getPosition().getX()-distance);
                                Move attackingMove = new Move(board.getPosition(attackerY, attackerX), castlingPosition);
                                if (board.getPosition(attackerY, attackerX).getCurrentPiece().canMove(board, attackingMove)) {
                                    return true;
                                }

                            }
                        }
                    }
                }
                
            }
        }
        return false;
    }

    public String toString(){
        String color = (this.getOwner().getOrientation()==1)?"W":"B";
        return (color+"K");
    }

}