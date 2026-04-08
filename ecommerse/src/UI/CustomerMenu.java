package UI;

import Session.session;
import Utils.ConsolePrinter;
import Utils.InputHelper;

public class CustomerMenu {

    public void show() {
        int choice;

        do {
            ConsolePrinter.clear();
            ConsolePrinter.printHeader("CUSTOMER DASHBOARD");
            System.out.println("  Welcome, " + session.getUser().getUsername());
            ConsolePrinter.printDivider();
            System.out.println("  1. Browse Products");
            System.out.println("  2. View Cart");
            System.out.println("  3. My Orders");
            System.out.println("  4. Logout");
            ConsolePrinter.printDivider();

            choice = InputHelper.readInt("Enter choice: ");

            switch (choice) {
                case 1 -> new ProductMenu().show();
                case 2 -> new CartMenu().show();
                case 3 -> new OrderMenu().show();
                case 4 -> {
                    session.logout();
                    ConsolePrinter.printSuccess("Logged out.");
                    return;
                }
                default -> {
                    ConsolePrinter.printError("Invalid choice.");
                    ConsolePrinter.pause();
                }
            }

        } while (true);
    }
}
