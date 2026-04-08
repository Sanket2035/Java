package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Config.DBConnection;
import Models.Categories;

public class CategoriesDAO {
	// save(Category)
	public static boolean save(Categories category) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into categories(category,descriptions) values(?,?)");
			ps.setString(1, category.getCategory());
			ps.setString(2, category.getDescription());
			int result = ps.executeUpdate();
			if(result>0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on creating a new Category!");
		}
		return false;
	}
	
    // findAll()		Returns List<Category>
	public static List<Categories> findAll(){
		List<Categories> categories = new ArrayList<Categories>();
		Connection con = DBConnection.getConnection();
		try {
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery("select * from categories;");
			while(rs.next()) {
				categories.add(new Categories(rs.getInt(1),rs.getString(2),rs.getString(3)));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on fetching Categories!");
		}
		return categories;
	}
	
    // findById(int)
	public static Categories findById(int id) {
		Categories category = null;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from categories where id = ?;");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				category = new Categories(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on finding Category!");
		}
		return category;
	}
	
    // update(Category)
	public static boolean update(Categories category) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("update categories set category = ? , description = ? where id = ?");
			ps.setString(1, category.getCategory());
			ps.setString(2, category.getDescription());
			ps.setInt(3,category.getId());
			int result = ps.executeUpdate();
			if(result>0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on updating a Category!");
		}
		return false;
	}
	
    // delete(int)
	public static boolean delete(int id) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("delete from categories where id = ?");
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			if(result>0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on deleting a Category!");
		}
		return false;
	}
}
