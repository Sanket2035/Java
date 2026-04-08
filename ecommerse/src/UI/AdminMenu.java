package UI;


import Services.CategoriesService;
import Services.OrderService;
import Services.ProductService;
import Session.session;
import Utils.ConsolePrinter;
import Utils.InputHelper;
import Enums.OrderStatus;
import Models.Orders;
import Models.Products;
import Models.Categories;

import java.util.List;

public class AdminMenu {

	
    public void show() {
        int choice;

        do {
            ConsolePrinter.clear();
            ConsolePrinter.printHeader("ADMIN DASHBOARD");
            System.out.println("  Welcome, " + session.getUser().getUsername());
            ConsolePrinter.printDivider();
            System.out.println("  1. Manage Products");
            System.out.println("  2. Manage Categories");
            System.out.println("  3. View All Orders");
            System.out.println("  4. Update Order Status");
            System.out.println("  5. Logout");
            ConsolePrinter.printDivider();

            choice = InputHelper.readInt("Enter choice: ");

            switch (choice) {
                case 1 -> manageProducts();
                case 2 -> manageCategories();
                case 3 -> viewAllOrders();
                case 4 -> updateOrderStatus();
                case 5 -> {
                    session.logout();
                    ConsolePrinter.printSuccess("Logged out.");
                    return;           // returns to MainMenu loop
                }
                default -> {
                    ConsolePrinter.printError("Invalid choice.");
                    ConsolePrinter.pause();
                }
            }

        } while (true);
    }

    private void manageProducts() {
        int choice;
        do {
            ConsolePrinter.clear();
            ConsolePrinter.printHeader("MANAGE PRODUCTS");
            System.out.println("  1. View All Products");
            System.out.println("  2. Add Product");
            System.out.println("  3. Update Product");
            System.out.println("  4. Delete Product");
            System.out.println("  5. Back");
            ConsolePrinter.printDivider();

            choice = InputHelper.readInt("Enter choice: ");

            switch (choice) {
                case 1 -> viewAllProducts();
                case 2 -> addProduct();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> { return; }
                default -> {
                    ConsolePrinter.printError("Invalid choice.");
                    ConsolePrinter.pause();
                }
            }
        } while (true);
    }

    private void viewAllProducts() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("ALL PRODUCTS");

        List<Products> products = ProductService.getAllProducts();

        if (products.isEmpty()) {
            ConsolePrinter.printInfo("No products found.");
        } else {
            System.out.printf("%-5s %-20s %-10s %-8s%n",
                "ID", "Name", "Price", "Stock");
            ConsolePrinter.printDivider();
            for (Products p : products) {
                System.out.printf("%-5d %-20s %-10.2f %-8d%n",
                    p.getId(),
                    p.getProduct(),
                    p.getPrice(),
                    p.getStock());
            }
        }
        ConsolePrinter.pause();
    }

    private void addProduct() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("ADD PRODUCT");

        String name        = InputHelper.readString("Name        : ");
        String description = InputHelper.readString("Description : ");
        double price       = InputHelper.readDouble("Price       : ");
        int    stock       = InputHelper.readInt   ("Stock       : ");
        int    categoryId  = InputHelper.readInt   ("Category ID : ");

        ProductService.addProduct(name, description, price, stock, categoryId);
        ConsolePrinter.pause();
    }

    private void updateProduct() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("UPDATE PRODUCT");

        int id = InputHelper.readInt("Enter Product ID to update: ");

        String name        = InputHelper.readString("New Name        : ");
        String description = InputHelper.readString("New Description : ");
        double price       = InputHelper.readDouble("New Price       : ");
        int    stock       = InputHelper.readInt   ("New Stock       : ");

        ProductService.updateProduct(id, name, description, price, stock);
        ConsolePrinter.pause();
    }

    private void deleteProduct() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("DELETE PRODUCT");

        int id = InputHelper.readInt("Enter Product ID to delete: ");
        ProductService.deleteProduct(id);
        ConsolePrinter.pause();
    }

    private void manageCategories() {
        int choice;
        do {
            ConsolePrinter.clear();
            ConsolePrinter.printHeader("MANAGE CATEGORIES");
            System.out.println("  1. View All Categories");
            System.out.println("  2. Add Category");
            System.out.println("  3. Delete Category");
            System.out.println("  4. Back");
            ConsolePrinter.printDivider();

            choice = InputHelper.readInt("Enter choice: ");

            switch (choice) {
                case 1 -> {
                    List<Categories> categories = CategoriesService.getAllCategories();
                    ConsolePrinter.clear();
                    ConsolePrinter.printHeader("ALL CATEGORIES");
                    for (Categories c : categories) {
                        System.out.println("  [" + c.getId() + "] " + c.getCategory());
                    }
                    ConsolePrinter.pause();
                }
                case 2 -> {
                    ConsolePrinter.clear();
                    String name = InputHelper.readString("Category Name        : ");
                    String desc = InputHelper.readString("Category Description : ");
                    CategoriesService.addCategory(name, desc);
                    ConsolePrinter.pause();
                }
                case 3 -> {
                    int id = InputHelper.readInt("Category ID to delete: ");
                    CategoriesService.deleteCategory(id);
                    ConsolePrinter.pause();
                }
                case 4 -> { return; }
                default -> {
                    ConsolePrinter.printError("Invalid choice.");
                    ConsolePrinter.pause();
                }
            }
        } while (true);
    }

    private void viewAllOrders() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("ALL ORDERS");

        List<Orders> orders = OrderService.getAllOrders();

        if (orders.isEmpty()) {
            ConsolePrinter.printInfo("No orders found.");
        } else {
            System.out.printf("%-6s %-10s %-12s %-12s%n",
                "ID", "User ID", "Total", "Status");
            ConsolePrinter.printDivider();
            for (Orders o : orders) {
                System.out.printf("%-6d %-10d %-12.2f %-12s%n",
                    o.getId(),
                    o.getUserId(),
                    o.getTotalBill(),
                    o.getOrderStatus());
            }
        }
        ConsolePrinter.pause();
    }

    private void updateOrderStatus() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("UPDATE ORDER STATUS");

        int orderId = InputHelper.readInt("Enter Order ID: ");

        System.out.println("  1. CONFIRMED");
        System.out.println("  2. SHIPPED");
        System.out.println("  3. DELIVERED");
        System.out.println("  4. CANCELLED");

        int choice = InputHelper.readInt("New Status: ");

        OrderStatus status = switch (choice) {
            case 1 -> OrderStatus.CONFIRMED;
            case 2 -> OrderStatus.DELIVERED;
            case 3 -> OrderStatus.CANCELLED;
            default -> null;
        };

        if (status == null) {
            ConsolePrinter.printError("Invalid status choice.");
        } else {
            OrderService.updateOrderStatus(orderId, status);
        }
        ConsolePrinter.pause();
    }
}