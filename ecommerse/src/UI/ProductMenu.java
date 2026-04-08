package UI;

import Models.Categories;
import Models.Products;
import Services.CartService;
import Services.CategoriesService;
import Services.ProductService;
import Session.session;
import Utils.ConsolePrinter;
import Utils.InputHelper;

import java.util.List;

public class ProductMenu {

    public void show() {
        int choice;

        do {
            ConsolePrinter.clear();
            ConsolePrinter.printHeader("BROWSE PRODUCTS");
            System.out.println("  1. View All Products");
            System.out.println("  2. Browse by Category");
            System.out.println("  3. Search by Name");
            System.out.println("  4. Back");
            ConsolePrinter.printDivider();

            choice = InputHelper.readInt("Enter choice: ");

            switch (choice) {
                case 1 -> viewAllProducts();
                case 2 -> browseByCategory();
                case 3 -> searchProducts();
                case 4 -> { return; }
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
        printProductTable(products);

        System.out.println("\n  A. Add a product to Cart");
        System.out.println("  B. Back");
        String action = InputHelper.readString("Choice: ").toUpperCase();

        if (action.equals("A")) {
            addToCart();
        }
    }

    private void browseByCategory() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("BROWSE BY CATEGORY");

        List<Categories> categories = CategoriesService.getAllCategories();

        if (categories.isEmpty()) {
            ConsolePrinter.printInfo("No categories found.");
            ConsolePrinter.pause();
            return;
        }

        for (Categories c : categories) {
            System.out.println("  [" + c.getId() + "] " + c.getCategory());
        }

        int categoryId = InputHelper.readInt("\nEnter Category ID: ");
        List<Products> products = ProductService.getProductsByCategory(categoryId);

        ConsolePrinter.clear();
        printProductTable(products);

        System.out.println("\n  A. Add a product to Cart");
        System.out.println("  B. Back");
        String action = InputHelper.readString("Choice: ").toUpperCase();

        if (action.equals("A")) {
            addToCart();
        }
    }

    private void searchProducts() {
        ConsolePrinter.clear();
        ConsolePrinter.printHeader("SEARCH PRODUCTS");

        String keyword = InputHelper.readString("Enter product name: ");
        List<Products> results = ProductService.searchProducts(keyword);

        if (results.isEmpty()) {
            ConsolePrinter.printInfo("No products found for: " + keyword);
            ConsolePrinter.pause();
            return;
        }

        printProductTable(results);

        System.out.println("\n  A. Add a product to Cart");
        System.out.println("  B. Back");
        String action = InputHelper.readString("Choice: ").toUpperCase();

        if (action.equals("A")) {
            addToCart();
        }
    }

    private void addToCart() {
        int productId = InputHelper.readInt("Enter Product ID : ");
        int quantity  = InputHelper.readInt("Enter Quantity   : ");

        int userId = session.getUser().getId();
        CartService.addToCart(userId, productId, quantity);
        ConsolePrinter.pause();
    }

    private void printProductTable(List<Products> products) {
        if (products.isEmpty()) {
            ConsolePrinter.printInfo("No products available.");
            ConsolePrinter.pause();
            return;
        }

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
}