/**
 * Class for the main menu.
 * @author UO-BS
 */
public class Menu {


    public static void main(String[] args) {
        Game currentGame;

        String gameType = UserInterface.getStringInput("Would you like to play a Standard game or Custom game?:",new String[]{"Standard","Custom"});

        if (gameType.equals("Standard")) {
            Player[] playerList = new Player[2];
            for (int i =0; i<playerList.length;i++) {
                String playerName = UserInterface.getStringInput( "What is the name of player "+(i+1)+": ");
                playerList[i] = new Player(playerName,1+(-2)*i);
            }
            currentGame = new Game(playerList);

        } else {

            int boardHeight = UserInterface.getIntInput("Enter the height of the board (2-20): ", 2, 20);
            int boardWidth = UserInterface.getIntInput("Enter the width of the board (2-20): ", 2, 20);
            int playerNum = UserInterface.getIntInput("Enter the number of players (2-4): ", 2, 4);

            Player[] playerList = new Player[playerNum];
            for (int i =0; i<playerList.length;i++) {
                String playerName = UserInterface.getStringInput("What is the name of player "+(i+1)+": ");
                playerList[i] = new Player(playerName);
            }

            currentGame = new Game(playerList,boardHeight,boardWidth);
        }

        Player winner = currentGame.runGame();
        System.out.println(winner.getName()+" Wins!");

    }

    

}