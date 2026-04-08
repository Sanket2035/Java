package Services;

import DAO.PaymentDAO;
import Enums.PaymentMethod;
import Enums.PaymentStatus;
import Models.Payments;

public class PaymentService {
	// processPayment(orderId, amount, method)
	//		create Payment object
	//  	set status SUCCESS
	//  	save to DB
	public static int processPayment(int orderId, double amount, PaymentMethod method) {
		Payments payment = new Payments(orderId,amount,method,PaymentStatus.PENDING);
		payment.setStatus(PaymentStatus.SUCCESS);
		return PaymentDAO.save(payment);
	}
}
