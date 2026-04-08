package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Config.DBConnection;
import Models.CartItems;

public class CartDAO {
	// addItem(userId, productId, quantity)
	public static boolean addItem(int userId, int ProductId, int quantity) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into cart(user_id, product_id, quantity) values(?,?,?)");
			ps.setInt(1, userId);
			ps.setInt(2, ProductId);
			ps.setInt(3, quantity);
			int result = ps.executeUpdate();
			if(result>0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on adding Product to Cart!");
		}
		return false;
	}
	// getCartItems(userId)       returns: List<CartItem>
	public static List<CartItems> getCartItems(int userId){
		List<CartItems> cartItems = new ArrayList<CartItems>();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select p.id, p.product, p.price, c.quantity from products as p join cart as c on p.id = c.product_id join users as u on u.id = c.user_id where u.id = ?");
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				cartItems.add(new CartItems(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getInt(4)));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on fetching Cart Items!");
		}
		return cartItems;
	}
    
	// updateQuantity(userId, productId, quantity)
	public static boolean updateQuantity(int userId, int productId, int quantity) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("update cart set quantity = ? where user_id = ? and product_id = ?");
			ps.setInt(1, quantity);
			ps.setInt(2, userId);
			ps.setInt(3, productId);
			int rs = ps.executeUpdate();
			if(rs>0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on updating product quantity!");
		}
		return false;
	}
    
	// removeItem(userId, productId)
	public static boolean removeItem(int userId, int productId) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("delete from cart where user_id = ? and product_id = ?");
			ps.setInt(1, userId);
			ps.setInt(2, productId);
			int rs = ps.executeUpdate();
			if(rs>0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on removing product from cart!");
		}
		return false;
	}
    
	// clearCart(userId)
	public static boolean clearCart(int userId) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("delete from cart where user_id = ?");
			ps.setInt(1, userId);
			int rs = ps.executeUpdate();
			if(rs>0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on removing product from cart!");
		}
		return false;
	}
}
