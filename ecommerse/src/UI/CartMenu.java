package UI;

import Models.CartItems;
import Services.CartService;
import Services.OrderService;
import Session.session;
import Utils.ConsolePrinter;
import Utils.InputHelper;

import java.util.List;

public class CartMenu {
    public void show() {
        int choice;

        do {
            ConsolePrinter.clear();
            ConsolePrinter.printHeader("MY CART");
            int userId = session.getUser().getId();
            List<CartItems> items = CartService.viewCart(userId);
            if (items.isEmpty()) {
                ConsolePrinter.printInfo("Your cart is empty.");
            } else {
                printCartItems(items);
                System.out.printf("%nTotal: Rs. %.2f%n",
                    CartService.calculateToatal(userId));
            }

            ConsolePrinter.printDivider();
            System.out.println("  1. Update Item Quantity");
            System.out.println("  2. Remove Item");
            System.out.println("  3. Checkout");
            System.out.println("  4. Back");
            ConsolePrinter.printDivider();

            choice = InputHelper.readInt("Enter choice: ");

            switch (choice) {
                case 1 -> updateItem(userId);
                case 2 -> removeItem(userId);
                case 3 -> checkout(userId, items);
                case 4 -> { return; }
                default -> {
                    ConsolePrinter.printError("Invalid choice.");
                    ConsolePrinter.pause();
                }
            }

        } while (true);
    }

    private void printCartItems(List<CartItems> items) {
        System.out.printf("%-5s %-20s %-8s %-10s%n",
            "ID", "Product", "Qty", "Subtotal");
        ConsolePrinter.printDivider();
        for (CartItems item : items) {
            System.out.printf("%-5d %-20s %-8d %-10.2f%n",
                item.getProductId(),
                item.getProduct(),
                item.getQuantity(),
                item.getunitPrice() * item.getQuantity());
        }
    }

    private void updateItem(int userId) {
        int productId = InputHelper.readInt("Enter Product ID : ");
        int quantity  = InputHelper.readInt("New Quantity     : ");
        CartService.updateCartItems(userId, productId, quantity);
        ConsolePrinter.pause();
    }

    private void removeItem(int userId) {
        int productId = InputHelper.readInt("Enter Product ID to remove: ");
        CartService.removeFromCart(userId, productId);
        ConsolePrinter.pause();
    }

    private void checkout(int userId, List<CartItems> items) {
        if (items.isEmpty()) {
            ConsolePrinter.printError("Cannot checkout with empty cart.");
            ConsolePrinter.pause();
            return;
        }

        ConsolePrinter.clear();
        ConsolePrinter.printHeader("CHECKOUT");

        printCartItems(items);
        System.out.printf("%nTotal: Rs. %.2f%n", CartService.calculateToatal(userId));
        ConsolePrinter.printDivider();

        System.out.println("  Payment Method:");
        System.out.println("  1. CASH");
        System.out.println("  2. CARD");
        System.out.println("  3. UPI");

        int payChoice = InputHelper.readInt("Select: ");
        String method = switch (payChoice) {
            case 1 -> "CASH";
            case 2 -> "CARD";
            case 3 -> "UPI";
            default -> null;
        };

        if (method == null) {
            ConsolePrinter.printError("Invalid payment method.");
            ConsolePrinter.pause();
            return;
        }

        String confirm = InputHelper.readString("Confirm order? (yes/no): ");

        if (confirm.equalsIgnoreCase("yes")) {
            OrderService.placeOrder(userId, method);
        } else {
            ConsolePrinter.printInfo("Order cancelled.");
        }

        ConsolePrinter.pause();
    }
}