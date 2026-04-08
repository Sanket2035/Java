package Services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Config.DBConnection;
import DAO.CartDAO;
import DAO.OrderDAO;
import DAO.OrderItemsDAO;
import DAO.PaymentDAO;
import DAO.ProductsDAO;
import Enums.OrderStatus;
import Enums.PaymentMethod;
import Models.CartItems;
import Models.OrderItems;
import Models.Orders;
import Models.Payments;
import Models.Products;
import Utils.ConsolePrinter;

public class OrderService {
	
	//	// placeOrder(userId)  — THE CORE METHOD:
	//  Step 1: fetch cart items for user
	//  Step 2: validate all items still in stock
	//  Step 3: calculate total
	//  Step 4: BEGIN TRANSACTION (setAutoCommit false)
	//  Step 5: INSERT into orders // get order id
	//  Step 6: INSERT all order items
	//  Step 7: UPDATE stock for each product
	//  Step 8: CLEAR cart
	//  Step 9: COMMIT
	//  If anything fails // ROLLBACK

    public static void placeOrder(int userId, String paymentMethod) {

        List<CartItems> cartItems = CartDAO.getCartItems(userId);
        if (cartItems.isEmpty()) {
            ConsolePrinter.printError("Cart is empty. Cannot place order.");
            return;
        }

        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            double total = 0;
            for (CartItems item : cartItems) {
                Products product =ProductsDAO.findById(item.getProductId());
                if (product == null) {
                    System.out.println("Product not found: ");
                }
                if (product.getStock() < item.getQuantity()) {
                	System.out.println("Insufficient stock for: " + product.getProduct()+ ". Available: " + product.getStock()+ ", Requested: " + item.getQuantity()
                    );
                }
                total += item.getunitPrice() * item.getQuantity();
            }

            Orders order = new Orders();
            order.setUserId(userId);
            order.setTotalBill(total);
            order.setOrderStatus("PENDING");
            OrderDAO.save(order);
            int orderId = OrderDAO.getMaxId();
            ConsolePrinter.printInfo("Order record created. ID: " + orderId);
            List<OrderItems> orderItems = new ArrayList<OrderItems>();
            for(CartItems cartItem: cartItems) {
            	orderItems.add(new OrderItems(orderId,cartItem.getProductId(),cartItem.getQuantity(),cartItem.getunitPrice()));
            }
            OrderItemsDAO.saveAll(orderItems, orderId);
            ConsolePrinter.printInfo("Order items saved.");

            for (CartItems item : cartItems) {
                Products product = ProductsDAO.findById(item.getProductId());
                int newStock = product.getStock() - item.getQuantity();
                ProductsDAO.updateStock(item.getProductId(), newStock);
            }
            ConsolePrinter.printInfo("Stock updated.");

            Payments payment = new Payments();
            payment.setOrderId(orderId);
            payment.setAmount(total);
            payment.setMethod(PaymentMethod.valueOf(paymentMethod));

            PaymentDAO.save(payment);
            ConsolePrinter.printInfo("Payment recorded.");

            CartDAO.clearCart(userId);
            ConsolePrinter.printInfo("Cart cleared.");

            conn.commit();

            ConsolePrinter.printSuccess(
                "Order placed successfully! Order ID: #" + orderId
                + " | Total: Rs. " + String.format("%.2f", total)
                + " | Payment: " + paymentMethod
            );

        } catch (SQLException e) {
            try {
                conn.rollback();
                ConsolePrinter.printError("Order failed. All changes rolled back!");
                ConsolePrinter.printError("Reason: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                ConsolePrinter.printError("Rollback also failed: " + rollbackEx.getMessage());
            }
        } finally {
        	try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // getOrderHistory(userId)
    public static List<Orders> getOrderHistory(int userId) {
        return OrderDAO.findByUser(userId);
    }

    // getOrderDetails(orderId)
    public static List<OrderItems> getOrderDetails(int orderId) {
        return OrderItemsDAO.findByOrderId(orderId);
    }

    
    public static List<Orders> getAllOrders() {
        return OrderDAO.findAll();
    }

    // cancelOrder(orderId)  only if PENDING
    public static boolean cancelOrder(int orderId) {
        Orders order = OrderDAO.findById(orderId);
        if (order == null) {
            ConsolePrinter.printError("Order not found!");
            return false;
        }

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            ConsolePrinter.printError(
                "Only PENDING orders can be cancelled. "
                + "Current status: " + order.getOrderStatus()
            );
            return false;
        }
        OrderDAO.updateOrderStatus(orderId, OrderStatus.CANCELLED);
        ConsolePrinter.printSuccess("Order #" + orderId + " cancelled.");
        return true;
    }

    // updateOrderStatus(orderId, status)   For admin use only
    public static boolean updateOrderStatus(int orderId, OrderStatus status) {
        Orders order = OrderDAO.findById(orderId);
        if (order == null) {
            ConsolePrinter.printError("Order not found.");
            return false;
        }

        return OrderDAO.updateOrderStatus(orderId, status);
        
    }
}