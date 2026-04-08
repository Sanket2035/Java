package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Config.DBConnection;
import Models.Users;

public class UserDAO {
	public static boolean save(Users user) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into users(user_name,email,pass,role) values(?,?,?,?)");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPass());
			ps.setString(4, user.getRole().name());
			return (ps.executeUpdate()>0)?true:false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on creating a new user!");
		}
		return false;
	}
	
	public static boolean isUserNameTaken(String username) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select user_name from users where user_name = ?;");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
					return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on finding user!");
		}
		return false;
	}
	
	public static boolean isEmailRegistered(String email){
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from users where email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on finding user!");
		}
		return false;
	}
	
	public static Users findByEmail(String email){
		Users user = new Users();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from users where email = ?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user = new Users(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString("role"));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on finding user!");
		}
		return user;
	}
	
	public static Users findById(int id){
		Users user = new Users();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from users where id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setPass(rs.getString(4));
				user.setRole(rs.getString("role"));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on finding user!");
		}
		return user;
	}
}
