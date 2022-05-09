/**
 * Class representing a Move from one Position on the board to another.
 * @author UO-BS
 */
public class Move {

    private Position start;
    private Position end;
    private String specialMove; //Includes: enPassant, castling, pawnPromotion

    /**
     * Generates a move for a piece.
     * 
     * @param startPosition The starting position of the move.
     * @param endPosition The position where the piece will land after moving.
     */
    public Move(Position startPosition, Position endPosition){
        start = startPosition;
        end = endPosition;
    }

    public Move(Position startPosition, Position endPosition, String specialIdentifier){
        start = startPosition;
        end = endPosition;
        specialMove = specialIdentifier;
    }

    public String getSpecial(){
        return specialMove;
    }

    public void setSpecial(String specialIdentifier){
        specialMove = specialIdentifier;
    }

    public Position getStartPosition(){
        return start;
    }

    public void setStartPosition(Position startPosition){
        start=startPosition;
    }

    public Position getEndPosition(){
        return end;
    }

    public void setEndPosition(Position endPosition){
        end=endPosition;
    }

    public Move reverseMove(){
        return new Move(end,start);
    }

    public boolean equalsStartEnd(Move other){
        if (this.start.equals(other.getStartPosition()) && this.end.equals(other.getEndPosition())) {
            return true;
        }
        return false;
    }

    public boolean equals(Move other){
        if (this.start.equals(other.getStartPosition()) && this.end.equals(other.getEndPosition()) && specialMove==other.getSpecial()) {
            return true;
        }
        return false;
    }

    public String toString() {
        String result = start.toString() + " to " +end.toString();
        return result;
    }

}