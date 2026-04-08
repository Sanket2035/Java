package Models;

import java.time.LocalDateTime;

import Enums.OrderStatus;
import Enums.PaymentStatus;

public class Orders {
	private int id;
	private int userId;
	private Double totalBill;
	private OrderStatus orderStatus;
	private PaymentStatus paymentStatus;
	private LocalDateTime createdAt;
	
	public Orders() {
	}
	
	public Orders(int id, int userId, Double totalBill, OrderStatus orderStatus, PaymentStatus paymentStatus, LocalDateTime createdAt) {
		this.id = id;
		this.userId = userId;
		this.totalBill = totalBill;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.createdAt = createdAt;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Double getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(Double totalBill) {
		this.totalBill = totalBill;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = PaymentStatus.valueOf(paymentStatus);
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = OrderStatus.valueOf(orderStatus);
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
