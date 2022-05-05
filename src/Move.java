public class Move {

    private Position start;
    private Position end;

    public Move(Position startPosition, Position endPosition){
        start = startPosition;
        end = endPosition;
    }

    public Position getStartPosition(){
        return start;
    }

    public Position getEndPosition(){
        return end;
    }

    public Move reverseMove(){
        return new Move(end,start);
    }

}