package Models;

import Enums.PaymentMethod;
import Enums.PaymentStatus;

public class Payments {
	private int id;
	private int orderId;
	private double amount;
	private PaymentMethod method;
	private PaymentStatus status;
	
	public Payments(int id, int orderId, double amount, PaymentMethod method, PaymentStatus status) {
		this.id = id;
		this.orderId = orderId;
		this.amount = amount;
		this.method = method;
		this.status = status;
	}
	public Payments(int orderId, double amount, PaymentMethod method, PaymentStatus status) {
		this.orderId = orderId;
		this.amount = amount;
		this.method = method;
		this.status = status;
	}
	public Payments() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public PaymentMethod getMethod() {
		return method;
	}
	public void setMethod(PaymentMethod method) {
		this.method = method;
	}
	public PaymentStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
}
