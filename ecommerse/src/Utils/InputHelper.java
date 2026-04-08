package Utils;

import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine() {
    	return scanner.nextLine().trim();
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
        	try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                ConsolePrinter.printError("Please enter a valid number.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
        	 try {
                 System.out.print(prompt);
                 double value = Double.parseDouble(scanner.nextLine().trim());
                 return value;
             } catch (NumberFormatException e) {
                 ConsolePrinter.printError("Please enter a valid decimal number.");
             }
        }
    }
}