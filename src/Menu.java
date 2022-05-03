import java.util.Scanner;
public class Menu {


    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        Game currentGame;

        String gameType = Menu.getStringInput(reader, "Would you like to play a Standard game or Custom game?:",new String[]{"Standard","Custom"});

        if (gameType.equals("Standard")) {
            Player[] playerList = new Player[2];
            for (int i =0; i<playerList.length;i++) {
                String playerName = Menu.getStringInput(reader, "What is the name of player "+(i+1)+": ");
                playerList[i] = new Player(playerName);
            }
            currentGame = new Game(playerList);

        } else {

            int boardHeight = Menu.getIntInput(reader, "Enter the height of the board (2-20): ", 2, 20);
            int boardWidth = Menu.getIntInput(reader, "Enter the width of the board (2-20): ", 2, 20);
            int playerNum = Menu.getIntInput(reader, "Enter the number of players (2-4): ", 2, 4);

            Player[] playerList = new Player[playerNum];
            for (int i =0; i<playerList.length;i++) {
                String playerName = Menu.getStringInput(reader, "What is the name of player "+(i+1)+": ");
                playerList[i] = new Player(playerName);
            }

            currentGame = new Game(playerList,boardHeight,boardWidth);
        }

        Player winner = currentGame.runGame();
        System.out.println(winner.getName()+" Wins!");

        reader.close();
    }

    public static String getStringInput(Scanner reader, String prompt, String[] validInputs) {
        String userInput = "";
        while (true) {
            System.out.println(prompt);
            userInput = reader.next();
            for (int i=0;i<validInputs.length;i++){
                if (userInput.equals(validInputs[i])) {
                    return userInput;
                }
            }
            System.out.println("Invalid Input");
        }

    }

    public static String getStringInput(Scanner reader,String prompt) {
        String userInput = "";
        System.out.println(prompt);
        userInput = reader.next();
        return userInput;

    }

    public static int getIntInput(Scanner reader,String prompt,int minValue, int maxValue) {
        int userInput;
        
        do {
            System.out.println(prompt);
            while (!reader.hasNextInt()) {
                System.out.println("Invalid Integer");
                reader.next();
            }
            userInput = reader.nextInt();

        } while (userInput>maxValue || userInput<minValue);
        return userInput;

    }

}