import java.util.ArrayList;
public class Player{

    private String playerName;
    private int orientation;
    private ArrayList<Piece> playablePieces;
    private boolean checkmated;

    public Player(String playerName, int orientation) {
        this.playerName = playerName;
        this.orientation = orientation;
        playablePieces = new ArrayList<Piece>();
        checkmated =false;
    }

    public Player(String playerName) {
        this.playerName = playerName;
        this.orientation = 1;
        playablePieces = new ArrayList<Piece>();
        checkmated= false;
    }

    public void setCheckmated(boolean checkmate){
        checkmated=checkmate;
    }

    public boolean getCheckmated(){
        return checkmated;
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
    
}