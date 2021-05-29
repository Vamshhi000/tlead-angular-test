package com.trustfinity.Tlead.ServiceImpl;

import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.trustfinity.Tlead.Repository.PaymentRepository;
import com.trustfinity.Tlead.Repository.UserRepository;
import com.trustfinity.Tlead.Service.PaymentService;
import com.trustfinity.Tlead.models.Payments;
import com.trustfinity.Tlead.models.Users;
@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void savePayments(Payments payments) {
		
		paymentRepository.save(payments);
	}



	@Override
	public void saveOrder(String email,Order order) {
		Payments payments = new Payments();
		Payments paymentss=paymentRepository.findByUserEmail(email);
		if(paymentss!=null) {
			
			paymentss.setOrderId(order.get("id"));
			paymentss.setPaymentId(null);
			paymentss.setReceipt(order.get("receipt"));
			paymentss.setAmmount(order.get("amount"+""));
			paymentss.setStatus(order.get("status"));
			savePayments(paymentss);
			
		
			
			
		}else {
			
			payments.setOrderId(order.get("id"));
			payments.setPaymentId(null);
			payments.setUser(userRepository.findByEmail(email));
			payments.setReceipt(order.get("receipt"));
			payments.setAmmount(order.get("amount"+""));
			payments.setStatus(order.get("status"));
			savePayments(payments);
			
		
		}
		
		
	}



	@Override
	public void updateOrder(Payments payments) {
		Payments paymentss=paymentRepository.findByOrderId(payments.getOrderId());
		paymentss.setStatus(payments.getStatus());
		paymentss.setPaymentId(payments.getPaymentId());
		generateRefaralId(3,paymentss.getUser().getEmail());
		
		savePayments(paymentss);
	}
private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    
    public static String calculateRFC2104HMAC(String data, String secret)
    throws java.security.SignatureException
    {
        String result;
        try {

    
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA256_ALGORITHM);


            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);


            byte[] rawHmac = mac.doFinal(data.getBytes());

    
            result = DatatypeConverter.printHexBinary(rawHmac).toLowerCase();

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }



	@Override
	public String getOrderId(String email) {
	Payments payments= paymentRepository.findByUserEmail(email);
		return payments.getOrderId();
	}
	
	
	public void generateRefaralId(int codeLength,String email) {
		
		Users users = userRepository.findByEmail(email);
		if(users!=null) {
		     char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		        StringBuilder sb = new StringBuilder();
		        Random random = new SecureRandom();
		        for (int i = 0; i < codeLength; i++) {
		            char c = chars[random.nextInt(chars.length)];
		            sb.append(c);
		        }
		        String output = sb.toString();
		        String Referral=users.getFirstName().substring(0, 3)+output+users.getPhoneNumber().substring(6, 10);
	
		        users.setMyRefId(Referral.toUpperCase());
		        userRepository.save(users);
		       
			}}
		}

	


