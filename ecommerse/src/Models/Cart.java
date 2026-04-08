package Models;

import java.util.List;

public class Cart {
	private int userId;
	private List<CartItems> cartItems;
	
	public Cart(int userId, List<CartItems> cartItems) {
		this.userId = userId;
		this.cartItems = cartItems;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<CartItems> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}
}
