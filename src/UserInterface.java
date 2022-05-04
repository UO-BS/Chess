import java.util.Scanner;
public class UserInterface{

    static Scanner sc = new Scanner(System.in);

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

    public static String getStringInput(String prompt) {
        String userInput = "";
        System.out.println(prompt);
        userInput = sc.next();
        return userInput;

    }

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