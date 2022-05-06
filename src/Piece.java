public abstract class Piece {
    
    private Player owner;
    private boolean inPlay;
    private boolean hasMoved;
    private Position currentPosition;

    public Piece(Player player, Position position) {
        owner = player;
        inPlay = true;
        hasMoved=false;
        currentPosition = position;
    }

    public Position getPosition() {
        return currentPosition;
    }

    public void setPosition(Position position) {
        currentPosition = position;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean getState() {
        return inPlay;
    }

    public void setState(boolean newState) {
        this.inPlay = newState;
    }

    public void setHasMoved(boolean moved) {
        this.hasMoved = moved;
    }

    public boolean getHasMoved() {
        return this.hasMoved;
    }

    public ArrayList<Move> getPossibleMoves(Board board){
        ArrayList<Move> possibleMoves = new ArrayList();
        for (int i=0;i<board.getRows;i++) {
            for (int j=0;j<board.getColumns();j++) {
                Move testMove = new Move(currentPosition,board.getPosition(i, j));
                if (this.canMove(board, testMove)) {
                    possibleMoves.add(testMove);
                }
            }
        }
        return possibleMoves;
    }

    public abstract boolean canMove(Board board, Move newMove);

}