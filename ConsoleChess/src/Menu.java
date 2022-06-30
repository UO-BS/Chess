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
            UserInterface.displayText("Nobody Wins!");
        } else {
            UserInterface.displayText(winner.getName()+" Wins!");
        }

        //Prints the move history to the console
        UserInterface.displayMoveHistory(currentGame.getMoveHistory());
        

    }

}