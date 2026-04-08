package UI;

import Models.Orders;
import Models.Products;
import Models.OrderItems;
import Services.OrderService;
import Services.ProductService;
import Session.session;
import Utils.ConsolePrinter;
import Utils.InputHelper;

import java.util.List;

public class OrderMenu {

    public void show() {
        int choice;

        do {
            ConsolePrinter.clear();
            ConsolePrinter.printHeader("MY ORDERS");

            int userId = session.getUser().getId();
            List<Orders> orders = OrderService.getOrderHistory(userId);

            if (orders.isEmpty()) {
                ConsolePrinter.printInfo("No orders placed yet.");
            } else {
                System.out.printf("%-6s %-12s %-12s%n",
                    "ID", "Total", "Status");
                ConsolePrinter.printDivider();
                for (Orders o : orders) {
                    System.out.printf("%-6d %-12.2f %-12s%n",
                        o.getId(),
                        o.getTotalBill(),
                        o.getOrderStatus());
                }
            }

            ConsolePrinter.printDivider();
            System.out.println("  1. View Order Details");
            System.out.println("  2. Cancel an Order");
            System.out.println("  3. Back");
            ConsolePrinter.printDivider();

            choice = InputHelper.readInt("Enter choice: ");

            switch (choice) {
                case 1 -> viewOrderDetails();
                case 2 -> cancelOrder();
                case 3 -> { return; }
                default -> {
                    ConsolePrinter.printError("Invalid choice.");
                    ConsolePrinter.pause();
                }
            }

        } while (true);
    }

    private void viewOrderDetails() {
        int orderId = InputHelper.readInt("Enter Order ID: ");

        ConsolePrinter.clear();
        ConsolePrinter.printHeader("ORDER DETAILS — #" + orderId);

        List<OrderItems> items = OrderService.getOrderDetails(orderId);
        List<Products> products = ProductService.getAllProducts();
        if (items.isEmpty()) {
            ConsolePrinter.printError("Order not found or no items.");
        } else {
            System.out.printf("%-5s %-20s %-8s %-10s%n",
                "ID", "Product", "Qty", "Price");
            ConsolePrinter.printDivider();
            double total = 0;
            for (OrderItems item : items) {
            	String productName="ProductId"+item.getProductId();
            	for(Products product: products) {
            		if(product.getId() == item.getProductId()) {
            			productName = product.getProduct();
            		}
            	}
                System.out.printf("%-5d %-20s %-8d %-10.2f%n",
                    item.getProductId(),
                    productName,
                    item.getQuantity(),
                    item.getUnitPrice());
                total += item.getUnitPrice() * item.getQuantity();
            }
            ConsolePrinter.printDivider();
            System.out.printf("Total: Rs. %.2f%n", total);
        }
        ConsolePrinter.pause();
    }

    private void cancelOrder() {
        int orderId = InputHelper.readInt("Enter Order ID to cancel: ");
        OrderService.cancelOrder(orderId);
        ConsolePrinter.pause();
    }
}