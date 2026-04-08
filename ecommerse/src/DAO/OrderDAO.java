package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Config.DBConnection;
import Enums.OrderStatus;
import Enums.PaymentStatus;
import Models.Orders;
import Services.CartService;
import Utils.ConsolePrinter;

public class OrderDAO {
	// save(Order)				returns: generated order id
	public static boolean save(Orders order) {
		Connection con = DBConnection.getConnection();
		try {
			order.setTotalBill( CartService.calculateToatal(order.getUserId()));
			PreparedStatement ps = con.prepareStatement("insert into orders(user_id, total_bill, order_status, payment_status, created_at) values(?,?,?,?,?);");
			ps.setInt(1, order.getUserId());
			ps.setDouble(2, order.getTotalBill());
			ps.setString(3, order.getOrderStatus().name());
			ps.setString(4, order.getOrderStatus().name());
			ps.setObject(5, LocalDateTime.now());
			
			int keys = ps.executeUpdate();;
			if(keys>0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on saving a Order!");
		}
		return false;
	}
	
    // findByUser(int userId)	returns: List<Order>
	public static List<Orders> findAll(){
		List<Orders> orders = new ArrayList<Orders>();
		Connection con = DBConnection.getConnection();
		try {
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery("select * from orders;");
			while(rs.next()) {
				orders.add(new Orders(rs.getInt(1),rs.getInt(2),rs.getDouble(3),OrderStatus.valueOf(rs.getString(4)), PaymentStatus.valueOf(rs.getString(5)),(LocalDateTime)rs.getObject(6)));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on fetching User Orders!");
		}
		return orders;
	}
	
	// findByUser(int userId)	returns: List<Order>
	public static List<Orders> findByUser(int userId){
		List<Orders> orders = new ArrayList<Orders>();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from orders where user_id = ?");
			ps.setInt(1, userId);
			ps.setObject(4, LocalDateTime.now());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				orders.add(new Orders(rs.getInt(1),rs.getInt(2),rs.getDouble(3),OrderStatus.valueOf(rs.getString(4)), PaymentStatus.valueOf(rs.getString(5)),(LocalDateTime)rs.getObject(6)));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on fetching User Orders!");
		}
		return orders;
	}	
    
	// findById(int orderId)
	public static Orders findById(int orderId){
		Orders order =null;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from orders where order_id = ?");
			ps.setInt(1, orderId);
			ps.setObject(4, LocalDateTime.now());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				order = new Orders(rs.getInt(1),rs.getInt(2),rs.getDouble(3),OrderStatus.valueOf(rs.getString(4)), PaymentStatus.valueOf(rs.getString(5)),(LocalDateTime)rs.getObject(6));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on searching Order!");
		}
		return order;
	}
	
	// getMaxId()
	public static int getMaxId() {
		int orderId=-1;
		Connection con = DBConnection.getConnection();
		try {
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery("select max(id) from orders;");
			if(rs.next()) {
				orderId = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on updating Order Status!");
		}
		return orderId;
	}
	    
	// updateOrderStatus(int orderId, OrderStatus)
	public static boolean updateOrderStatus(int orderId, OrderStatus orderStatus) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("update orders set order_status = ? where order_id = ?");
			ps.setString(1, orderStatus.name());
			ps.setInt(2, orderId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ConsolePrinter.printSuccess("Order Status Updated Successfuly!");
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on updating Order Status!");
		}
		return false;
	}
	
	// updatePaymentStatus(int orderId, OrderStatus)
	public static boolean updatePaymentStatus(int orderId, PaymentStatus paymentStatus) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("update orders set payment_status = ? where order_id = ?");
			ps.setString(1, paymentStatus.name());
			ps.setInt(2, orderId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on updating Payment Status!");
		}
		return false;
	}
}
