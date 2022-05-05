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

    public abstract boolean canMove(Board board, Position initial, Position end);

}