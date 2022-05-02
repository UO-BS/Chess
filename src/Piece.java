public abstract class Piece {
    
    private Player owner;
    private boolean inPlay;

    public Piece(Player player) {
        owner = player;
        alive = true;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean getState() {
        return inPlay;
    }

    public abstract validMove(Position initial, Position end);

}