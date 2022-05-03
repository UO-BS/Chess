public abstract class Piece {
    
    private Player owner;
    private boolean inPlay;

    public Piece(Player player) {
        owner = player;
        inPlay = true;
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

    public abstract canMove(Position initial, Position end);

}