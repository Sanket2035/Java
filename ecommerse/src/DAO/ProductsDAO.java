package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Config.DBConnection;
import Models.Products;

public class ProductsDAO {
	// save(Product)
	public static boolean save(Products product) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into products(product,descriptions,price,stock,category_id) values(?,?,?,?,?)");
			ps.setString(1, product.getProduct());
			ps.setString(2, product.getDescription());
			ps.setDouble(3, product.getPrice());
			ps.setInt(4, product.getStock());
			ps.setInt(5, product.getcategoryId());
			int result = ps.executeUpdate();
			if(result>0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on saving a Product!");
		}
		return false;
	}
	
    // findAll()		Returns: List<Product>
	public static List<Products> findAll(){
		List<Products> products = new ArrayList<Products>();
		Connection con = DBConnection.getConnection();
		try {
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery("select * from products where stock > 0;");
			while(rs.next()) {
				products.add(new Products(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getInt(5),rs.getInt(6)));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on saving a Product!");
		}
		return products;
	}
	
    // findById(int)
	public static Products findById(int id) {
		Products product = null;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from products where id = ?;");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				product = new Products(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getInt(5),rs.getInt(6));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on saving a Product!");
		}
		return product;
	}
	
    // findByCategory(int categoryId)
	public static List<Products> findByCategory(int categoryId) {
		List<Products> products = new ArrayList<Products>();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from products where category_id = ?;");
			ps.setInt(1, categoryId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				products.add(new Products(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getInt(5),rs.getInt(6)));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on filtering Products!");
		}
		return products;
	}
	
    // searchByName(String keyword)	SQL LIKE query
	public static List<Products> searchByName(String keyword){
		List<Products> products = new ArrayList<Products>();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from products where product like "+"%?%"+";");
			ps.setString(1, keyword);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				products.add(new Products(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getInt(5),rs.getInt(6)));
			}
		} catch (SQLException e) {
			System.out.println("Database Query failed on searching Products!");
		}
		return products;
	}
	
    // update(Product)
    public static boolean update(Products product) {
    	Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("update products set product = ?, descriptions = ?, price = ?, stock = ? where id = ?;");
			ps.setString(1, product.getProduct());
			ps.setString(2, product.getDescription());
			ps.setDouble(3, product.getPrice());
			ps.setInt(4, product.getStock());
			ps.setInt(5, product.getId());
			int result = ps.executeUpdate();
			if(result>0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on updating a Product!");
		}
		return false;
    }
	
	// updateStock(int productId, int newQty)
    public static boolean updateStock(int productId,int newQty) {
    	Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("update products set stock = ? where id = ?");
			ps.setInt(1, newQty);
			ps.setInt(2, productId);
			int result = ps.executeUpdate();
			if(result>0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on updating a Product Stock!");
		}
		return false;
    }
    
	// delete(int)
    public static boolean delete(int id) {
    	Connection con = DBConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("delete from products where id = ?");
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			if(result>0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Database Query failed on updating a Product!");
		}
		return false;
    }
}
