
enum MoveType{
    ENPASSANT,
    VULNERABLETOENPASSANT,
    PAWNPROMOTION,
    CASTLING,
    STANDARD
}

/**
 * Class representing a Move from one Position on the board to another.
 * @author UO-BS
 */
public class Move {


    private Position start;
    private Position end;
    private MoveType moveType;

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

    /**
     * Generates a copy of a move for a piece.
     * 
     * @param original The original Move being copied
     */
    public Move(Move original){
        start = new Position(original.start);
        end = new Position(original.end);
        moveType = original.moveType;
    }

    public Move(Position startPosition, Position endPosition, MoveType specialIdentifier){
        start = startPosition;
        end = endPosition;
        moveType = specialIdentifier;
    }

    public MoveType getMoveType(){
        return moveType;
    }

    public void setMoveType(MoveType specialIdentifier){
        moveType = specialIdentifier;
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

    public boolean equalsStartEnd(Move other){
        if (this.start.equals(other.getStartPosition()) && this.end.equals(other.getEndPosition())) {
            return true;
        }
        return false;
    }

    public boolean equals(Move other){
        if (this.start.equals(other.getStartPosition()) && this.end.equals(other.getEndPosition()) && moveType==other.getMoveType()) {
            return true;
        }
        return false;
    }

    public String toString() {
        String result = start.toString() + " to " +end.toString();
        return result;
    }

}