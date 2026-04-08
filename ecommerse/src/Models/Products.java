package Models;

public class Products {
	private int id;
	private String product;
	private String description;
	private double price;
	private int stock;
	private int categoryId;
	
	public Products() {
	}
	public Products(int id, String product, String description, double price, int stock, int categoryId) {
		this.id = id;
		this.product = product;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.categoryId = categoryId;
	}
	public Products(String product, String description, double price, int stock, int categoryId) {
		this.product = product;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.categoryId = categoryId;
	}
	public Products(int id, String product, String description, double price, int stock) {
		this.id = id;
		this.product = product;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getcategoryId() {
		return categoryId;
	}
	public void setcategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
}
