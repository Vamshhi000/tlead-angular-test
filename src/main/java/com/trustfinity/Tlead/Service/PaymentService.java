package com.trustfinity.Tlead.Service;

import com.razorpay.Order;
import com.trustfinity.Tlead.models.Payments;

public interface PaymentService {
	
	public void savePayments(Payments payments);
	

	
	public void saveOrder(String email,Order order);
	
	public void updateOrder(Payments payments);
	
	public String getOrderId(String email);

}
