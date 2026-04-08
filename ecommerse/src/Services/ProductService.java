package Services;

import java.util.List;

import DAO.ProductsDAO;
import Models.Products;

public class ProductService {
	// addProduct(...)
	public static boolean addProduct(String name, String description, double price, int stock, int categoryId) {
		Products product = new Products(name, description, price, stock, categoryId);
		if(price<=0) {
			System.out.println("Product price cannot be 0 or Negative!");
			return false;
		}
		if(stock<=0) {
			System.out.println("Product stock cannot be 0 or Negative!");
			return false;
		}
		if(categoryId<=0) {
			System.out.println("Product Category Id cannot be 0 or Negative!");
			return false;
		}
		return ProductsDAO.save(product);
	}
	
    // getAllProducts()
	public static List<Products> getAllProducts(){
		return ProductsDAO.findAll();
	}
	
    // getProductsByCategory(categoryId)
	public static List<Products> getProductsByCategory(int categoryId){
		if(categoryId<=0) {
			System.out.println("Product Category Id cannot be 0 or Negative!");
			return null;
		}
		return ProductsDAO.findByCategory(categoryId);
	}
    
	// searchProducts(keyword)
	public static List<Products> searchProducts(String keyword){
		if(keyword.isEmpty()) {
			System.out.println("Keyword cannot be empty for searching!");
			return null;
		}
		return ProductsDAO.searchByName(keyword);
	}
    
	// updateProduct(...)
	public static boolean updateProduct(int id, String name, String description, double price, int stock) {
		Products product = new Products(id, name, description, price, stock);
		if(price<=0) {
			System.out.println("Product price cannot be 0 or Negative!");
			return false;
		}
		if(stock<=0) {
			System.out.println("Product stock cannot be 0 or Negative!");
			return false;
		}
		
		return ProductsDAO.update(product);
	}
    
	// deleteProduct(id)
	public static boolean deleteProduct(int id) {
		if(id<=0) {
			System.out.println("Product Id cannot be 0 or Negative!");
			return false;
		}
		return ProductsDAO.delete(id);
	}
    
	// isInStock(productId, requiredQty)
	public static boolean isInStock(int productId, int requiredQty) {
		if(productId<=0) {
			System.out.println("Product Id cannot be 0 or Negative!");
			return false;
		}
		if(requiredQty<=0) {
			System.out.println("Quantity cannot be 0 or Negative!");
			return false;
		}
		Products product = ProductsDAO.findById(productId);
		if(product.getStock()<requiredQty) {
			return false;
		} else {
			return true;
		}
	}
}
