package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import Config.DBConnection;
import Enums.PaymentMethod;
import Enums.PaymentStatus;
import Models.Payments;

public class PaymentDAO {
	// save(Payment)
	public static int save(Payments payment) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into payments(order_id, ammount, method, payment_status, paid_at) values(?,?,?,?,?);",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, payment.getOrderId());
			ps.setDouble(2, payment.getAmount());
			ps.setString(3, payment.getMethod().name());
			ps.setObject(4, LocalDateTime.now());
			ps.executeUpdate();
			ResultSet keys = ps.getGeneratedKeys();
			if(keys.next()) {
				return keys.getInt(1);
			}
		} catch(SQLException e) {
			System.out.println("Database Query failed on creating Payment record!");
		}
		return -1;
	}
	
    // findByOrderId(int orderId)
	public static Payments findByOrderId(int orderId) {
		Payments payment = null;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from payments where order_id = ?;");
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				payment = new Payments(rs.getInt(1),rs.getInt(2),rs.getDouble(3),PaymentMethod.valueOf(rs.getString(4)),PaymentStatus.valueOf(rs.getString(5)));
			}
		} catch(SQLException e) {
			System.out.println("Database Query failed on creating Payment record!");
		}
		return payment;
	}
}
