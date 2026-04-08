package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Config.DBConnection;
import Models.OrderItems;

public class OrderItemsDAO {
	// saveAll(List<OrderItem>, int orderId)
	public static boolean saveAll(List<OrderItems> orderItems, int orderId) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into order_items(order_id,product_id, quantity, unit_price) values(?,?,?,?);");
			for(OrderItems orderItem:orderItems) {
				ps.setInt(1, orderItem.getOrderId());
				ps.setInt(2, orderItem.getProductId());
				ps.setInt(3, orderItem.getQuantity());
				ps.setDouble(4, orderItem.getUnitPrice());
				ps.addBatch();
			}
			int result = ps.executeUpdate();
			if(result>0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on saving a Order Items!");
		}
		return false;
	}
	
	// findByOrderId(orderId)
	public static List<OrderItems> findByOrderId(int orderId){
		List<OrderItems> orderItems = new ArrayList<OrderItems>();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from order_items where order_id = ?;");
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				orderItems.add(new OrderItems(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDouble(5)));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on saving a Order Items!");
		}
		return orderItems;
	}
}
