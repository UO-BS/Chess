import java.util.Scanner;
public class Menu {


    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        Game currentGame;
        String gameType = "";

        System.out.println("Would you like to play a Standard game or Custom game?: ");
        gameType = reader.next();
        while (!gameType.equals("Standard") || !gameType.equals("Standard")) {
            System.out.println("Invalid Input");
            gameType = reader.next();
        }

        if (gameType.equals("Standard")) {
            Player[] playerList = new Player[2];
            for (int i =0; i<playerList.length;i++) {
                System.out.println("What is the name of player "+(i+1)+": ");
                playerList[i] = new Player(reader.next());
            }
            currentGame = new Game(playerList);

        } else {
            System.out.println("Enter the height of the board: ");
            int boardHeight = reader.nextInt();
            System.out.println("Enter the width of the board: ");
            int boardWidth = reader.nextInt();
            System.out.println("Enter the number of players: ");
            int playerNum = reader.nextInt();
            Player[] playerList = new Player[playerNum];
            for (int i =0; i<playerList.length;i++) {
                System.out.println("What is the name of player "+(i+1)+": ");
                playerList[i] = new Player(reader.next());
            }

            currentGame = new Game(playerList,boardHeight,boardWidth);
        }

        Player winner = currentGame.runGame();
        System.out.println(winner.getName()+" Wins!");

        reader.close();
    }

}