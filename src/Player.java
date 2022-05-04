public class Player{

    private String playerName;
    private int orientation;

    public Player(String playerName, int orientation) {
        this.playerName = playerName;
        this.orientation = orientation;
    }

    public Player(String playerName) {
        this.playerName = playerName;
        this.orientation = 1;
    }

    public String getName(){
        return this.playerName;
    }

    public int getOrientation(){
        return this.orientation;
    }
    
}