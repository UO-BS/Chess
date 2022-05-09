/**
 * Class representing a Move from one Position on the board to another.
 * @author UO-BS
 */
public class Move {

    private Position start;
    private Position end;

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

    public boolean equals(Move other){
        if (this.start.equals(other.getStartPosition()) && this.end.equals(other.getEndPosition())) {
            return true;
        }
        return false;
    }

    public String toString() {
        String result = start.toString() + " to " +end.toString();
        return result;
    }

}