package Services;

import java.util.List;

import DAO.CartDAO;
import Models.CartItems;

public class CartService {
	// addToCart(userId, productId, quantity)
	//     └── check stock availability first
	public static boolean addToCart(int userId, int productId, int quantity) {
		if(productId<=0) {
			System.out.println("Product id cannot be 0 or negative number!");
			return false;
		}
		if(quantity<=0) {
			System.out.println("Quantity cannot be 0 or negative number!");
			return false;
		}
		if(ProductService.isInStock(productId, quantity)) {
			return CartDAO.addItem(userId, productId, quantity);
		} else {
			System.out.println("Product is out of stocks!");
			return false;
		}
	}
	
	// viewCart(userId)
	public static List<CartItems> viewCart(int userId){
		return CartDAO.getCartItems(userId);
	}
	
	// updateCartItem(userId, productId, quantity)
	public static boolean updateCartItems(int userId, int productId, int quantity) {
		if(productId<=0) {
			System.out.println("Product id cannot be 0 or negative number!");
			return false;
		}
		if(quantity<=0) {
			System.out.println("Quantity cannot be 0 or negative number!");
			return false;
		}
		if(ProductService.isInStock(productId, quantity)) {
			return CartDAO.updateQuantity(userId, productId, quantity);
		} else {
			System.out.println("There are no more products in stock!");
			return false;
		}
	}
	
	// removeFromCart(userId, productId)
	public static boolean removeFromCart(int userId, int productId) {
		if(productId<=0) {
			System.out.println("Product id cannot be 0 or negative number!");
			return false;
		}
		return CartDAO.removeItem(userId, productId);
	}
	
	// calculateTotal(userId)     returns: double
	public static double calculateToatal(int userId) {
		double total = 0;
		List<CartItems> cartItems = CartDAO.getCartItems(userId);
		for(CartItems item:cartItems) {
			total+= item.getQuantity() * item.getunitPrice();
		}
		
		return total;
	}
}
