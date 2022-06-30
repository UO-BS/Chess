import java.util.ArrayList;
/**
 * Class representing a player for a game of chess.
 * @author UO-BS
 */

public class Player{

    private String playerName;
    private int orientation;
    private ArrayList<Piece> playablePieces;
    private int playerState; //PlayerStates: (0)Playing, (1)Checkmated, (2)Stalemated

    /**
     * Generates a player of the game.
     * 
     * @param playerName The custom name of a player.
     * @param orientation The direction the player moves. 1 goes up on the board and -1 goes down.
     */
    public Player(String playerName, int orientation) {
        this.playerName = playerName;
        this.orientation = orientation;
        playablePieces = new ArrayList<Piece>();
        playerState =0;
    }

    /**
     * Generates a player of the game with default orientation of going up on the board.
     * 
     * @param playerName The custom name of a player.
     */
    public Player(String playerName) {
        this.playerName = playerName;
        this.orientation = 1;
        playablePieces = new ArrayList<Piece>();
        playerState =0;
    }

    public void setPlayerState(int checkmate){
        playerState=checkmate;
    }

    public int getPlayerState(){
        return playerState;
    }

    public String getName(){
        return this.playerName;
    }

    public int getOrientation(){
        return this.orientation;
    }

    public void addPiece(Piece e) {
        playablePieces.add(e);
    }

    public void removePiece(Piece e) {
        playablePieces.remove(e);
    }

    public ArrayList<Piece> getPieceList(){
        return playablePieces;
    }
    
    public String toString(){
        return playerName;
    }

}