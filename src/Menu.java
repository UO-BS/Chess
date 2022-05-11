import java.util.LinkedList;
import java.util.ListIterator;
/**
 * Class for the main menu.
 * @author UO-BS
 */
public class Menu {


    public static void main(String[] args) {
        Game currentGame;
        String gameType = UserInterface.getStringInput("Would you like to play a Standard, Custom or Chess960 game?:",new String[]{"Standard","Custom","Chess960"});

        //Generating the game
        Player[] playerList = new Player[2];
        for (int i =0; i<playerList.length;i++) {
            String playerName = UserInterface.getStringInput( "What is the name of player "+(i+1)+": ");
            playerList[i] = new Player(playerName,1+(-2)*i);
        }
        currentGame = new Game(playerList,gameType);

        //Running the game and finding the winner
        Player winner = currentGame.runGame();
        if (winner == null) {
            System.out.println("Nobody Wins!");
        } else {
            System.out.println(winner.getName()+" Wins!");
        }

        //Prints the move history to the console
        LinkedList<Move> moveHistory = currentGame.getMoveHistory();
        ListIterator<Move> moveHistoryIterator = moveHistory.listIterator();
        System.out.println("Move History:");
        while (moveHistoryIterator.hasNext()) {
            System.out.print(moveHistoryIterator.next()+" | ");
            if (moveHistoryIterator.hasNext()) {
                System.out.print(moveHistoryIterator.next());
            }
            System.out.println("");
        }

    }

}