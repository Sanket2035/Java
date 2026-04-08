package UI;

import Enums.UserRole;
import Models.Users;
import Services.AuthService;
import Session.session;
import Utils.ConsolePrinter;
import Utils.InputHelper;

public class MainMenu {

    public void show() {
        int choice;

        do {
            ConsolePrinter.clear();
            ConsolePrinter.printHeader("MINI E-COMMERCE APP");
            System.out.println("  1. Login");
            System.out.println("  2. Register");
            System.out.println("  3. Exit");
            ConsolePrinter.printDivider();

            choice = InputHelper.readInt("Enter choice: ");

            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleRegister();
                case 3 -> {
                    ConsolePrinter.printInfo("Goodbye!");
                    System.exit(0);
                }
                default -> {
                    ConsolePrinter.printError("Invalid choice.");
                    ConsolePrinter.pause();
                }
            }

        } while (true);
    }

    private void handleLogin() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("LOGIN");

        String email    = InputHelper.readString("Email    : ");
        String password = InputHelper.readString("Password : ");

        boolean success = AuthService.login(email, password);

        if (success) {
            ConsolePrinter.pause();
            redirectByRole();         // go to respective dashboard
        } else {
            ConsolePrinter.pause();
        }
    }

    private void handleRegister() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("REGISTER");

        String name     = InputHelper.readString("Name     : ");
        String email    = InputHelper.readString("Email    : ");
        String password = InputHelper.readString("Password : ");
        String role		= InputHelper.readString("Role	   :");

        if(AuthService.register(name, email, password, role)) {
        	ConsolePrinter.printSuccess("User Registered Successfuly!");
        } else {
        	ConsolePrinter.printSuccess("User Registration Failed!");
        }
        ConsolePrinter.pause();
    }

    private void redirectByRole() {
        Users user = session.getUser();

        if (user.getRole() == UserRole.ADMIN) {
            new AdminMenu().show();
        } else {
            new CustomerMenu().show();
        }
    }
}