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
}