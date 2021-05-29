package com.trustfinity.Tlead.Controller;





import java.security.SignatureException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.trustfinity.Tlead.Service.PaymentService;
import com.trustfinity.Tlead.ServiceImpl.PaymentServiceImpl;
import com.trustfinity.Tlead.ServiceImpl.UserServiceImpl;
import com.trustfinity.Tlead.models.Payments;
import com.trustfinity.Tlead.models.ResponceDto;
import com.trustfinity.Tlead.models.Users;

@RestController
@RequestMapping(value="/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private PaymentService paymentService;
	

	
	@PostMapping
	public void savePayments(Payments payments) {
		
		paymentService.savePayments(payments);
		
	}
	
	
	@PostMapping(value="/createOrder")
	public String createOrder(@RequestBody Users usesr){
		
		
		
		try {
			
			RazorpayClient razorpayClient=new RazorpayClient("rzp_test_onuC0CoGHQvLsf","BIV33xgVcLjllWDsaKeIo1va");
			
			JSONObject options = new JSONObject();
			options.put("amount", 5000);
			options.put("currency", "INR");
			options.put("receipt", "txn_123456");
			Order order = razorpayClient.Orders.create(options);
			System.out.println(order.toString());
			paymentService.saveOrder(usesr.getEmail(), order);
	
			logger.info("saved the order id");
			return order.toString();

		} catch (Exception e) {
			System.out.println(e);
			logger.error("error while saving the order",e);
			logger.info("error while saving the order");
			return "Exception";
		}
		
		
		
		
		
		
		
		
	}

	@PutMapping(value="/updateOrder")
	public ResponseEntity<ResponceDto> updateOrder(@RequestBody Payments payments) {
		ResponceDto responceDto = new ResponceDto();
	try {
		paymentService.updateOrder(payments);
	
		logger.info("updated the order with payment id");

		responceDto.setMsg("Successfully updated the payment");
		return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
	} catch (Exception e) {
		logger.error("error while updating the order",e);
		logger.info("error while updating the order");

		responceDto.setMsg("Error in while updating the Payment");
		return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
	}
		
	}
	@PutMapping(value="/signarture")
	public String verifySignature(@RequestBody String payments) throws SignatureException {
		
		
		try {
			return PaymentServiceImpl.calculateRFC2104HMAC(payments, "BIV33xgVcLjllWDsaKeIo1va");
		} catch (Exception e) {
			
			logger.error("error while updating the order",e);
			logger.info("error while updating the order");
			return "Error in genarating the signature";
		}
		
		
	}
	
	@PutMapping(value="/orderId")
	public String getOrderId(@RequestBody String email) {
		
		try {
			logger.info("getting the orderId");
			return paymentService.getOrderId(email);
		} catch (Exception e) {
			logger.error("error while getting the orderId",e);
			logger.info("error while getting the orderId");
		return "error in getting order id";
		}
		
		
	}
    
    
}
	


