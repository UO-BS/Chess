import java.util.Scanner;
/**
 * Class to get user input from console
 * @author UO-BS
 */
public class UserInterface{

    static Scanner sc = new Scanner(System.in);

    /**
     * Gets a valid string input from the user.
     * 
     * @param prompt The prompt given to the user in console.
     * @param validInputs Array of all valid inputs
     * @return A string from the list of validInputs chosen by the user.
     */
    public static String getStringInput(String prompt, String[] validInputs) {
        String userInput = "";
        while (true) {
            System.out.println(prompt);
            userInput = sc.next();
            for (int i=0;i<validInputs.length;i++){
                if (userInput.equals(validInputs[i])) {
                    return userInput;
                }
            }
            System.out.println("Invalid Input");
        }

    }

    /**
     * Gets any string input from the user.
     * 
     * @param prompt The prompt given to the user in console.
     * @return A string given by the user
     */
    public static String getStringInput(String prompt) {
        String userInput = "";
        System.out.println(prompt);
        userInput = sc.next();
        return userInput;

    }

    /**
     * Gets a valid int input from the user.
     * 
     * @param prompt The prompt given to the user in console.
     * @param minValue The minimum int value.
     * @param maxValue The maximum int value.
     * @return An int between minValue and maxValue
     */
    public static int getIntInput(String prompt,int minValue, int maxValue) {
        int userInput;
        
        do {
            System.out.println(prompt);
            while (!sc.hasNextInt()) {
                System.out.println("Invalid Integer");
                sc.next();
            }
            userInput = sc.nextInt();

        } while (userInput>maxValue || userInput<minValue);
        return userInput;

    }

    /**
     * Displays an ASCII board to the console.
     */
    public static void displayASCII(Board chessBoard) {
        int totalRow = chessBoard.getRows();
        int totalColumn = chessBoard.getColumns();
        
        for (int i=totalRow;i>=0;i--) {
            for (int j=0;j<totalColumn;j++){
                System.out.print("----");
            }
            System.out.println("");
            
            if (i==0){
                
                System.out.print("* |");
                for(int j=0;j<totalColumn;j++) {
                    System.out.print(Character.toString((char) j+65 )+"  |");
                }
                System.out.println("");

            } else {
            
                System.out.print(i +" |");
                for(int j=0;j<totalColumn;j++) {
                    if (chessBoard.getPosition(i-1,j).getCurrentPiece()!=null) {
                        System.out.print(chessBoard.getPosition(i-1,j).getCurrentPiece().toString());
                        System.out.print(" |");
                    } else {
                        System.out.print("   |");
                    }
                }
                System.out.println("");
            }
        }

    }

    /**
     * Asks the user for a Position code (A1 for example) in response to a prompt.
     * 
     * @param chessBoard The chess board from which the user can choose a position.
     * @param prompt The prompt given to the user.
     * @return A position chosen by the user.
     */
    public static Position getPositionFromUser(Board chessBoard, String prompt) {
        String userInput ="";
        Position newPosition;
        int x = -1;
        int y = -1;

        while (userInput.length()!=2 || !chessBoard.insideBoard(y,x)) {
            userInput = UserInterface.getStringInput(prompt);
            if (userInput.length()!=2) {
                System.out.println("Not a Position");
            } else {
                x = Position.stringToXPosition(userInput);
                y = Position.stringToYPosition(userInput);
                if (!chessBoard.insideBoard(y,x)) {
                    System.out.println("Position is ouside of the board");
                }
            }
        }
        newPosition = chessBoard.getPosition(y,x); //NOTE: y comes first here since our array is board[rows][columns], but y=row and x=column
        return newPosition;
    }
}