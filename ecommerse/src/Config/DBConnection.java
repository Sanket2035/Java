package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection getConnection() {
		Connection connection = null;
		String Url = "jdbc:mysql://localhost:3306/e_commerse";
		String User = "root";
		String Password ="Sanket05";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(Url,User,Password);
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load databse drivers!");
		} catch (SQLException e) {
			System.out.println("Failed to stablish database connection!");
		}
		return connection;
	}
	
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Failed to close database connection");
		}
	}
}
