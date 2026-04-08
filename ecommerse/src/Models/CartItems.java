package Models;

public class CartItems {
	private int  productId;
	private String product;
	private int quantity;
	private double unitPrice;
	
	public 	CartItems(int productId, String product, int quantity, double unitPrice) {
		this.productId = productId;
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	public 	CartItems(int productId, String product, double unitPrice, int quantity) {
		this.productId = productId;
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getunitPrice() {
		return unitPrice;
	}
	public void setunitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}	
}
