package ecommerse;


import UI.MainMenu;

public class Main {
    public static void main(String[] args) {
        new MainMenu().show();
    }
}


/*
UI Flow:

Main.java
   └── MainMenu.show()
         ├── Login  → AuthService → Session.login()
         │     ├── role = ADMIN     → AdminMenu.show()
         │     │     ├── Manage Products  → ProductService
         │     │     ├── Manage Categories → CategoryService
         │     │     ├── View Orders      → OrderService
         │     │     └── Update Status    → OrderService
         │     │
         │     └── role = CUSTOMER  → CustomerMenu.show()
         │           ├── Browse Products  → ProductMenu.show()
         │           │     └── Add to Cart → CartService
         │           ├── View Cart        → CartMenu.show()
         │           │     └── Checkout   → OrderService
         │           └── My Orders        → OrderMenu.show()
         │
         └── Register → AuthService.register()

*/