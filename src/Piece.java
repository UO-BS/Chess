public abstract class Piece {
    
    private Player owner;
    private boolean inPlay;
    private boolean hasMoved;

    public Piece(Player player) {
        owner = player;
        inPlay = true;
        hasMoved=false;
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