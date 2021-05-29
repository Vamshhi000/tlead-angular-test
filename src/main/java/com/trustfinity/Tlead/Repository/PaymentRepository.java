package com.trustfinity.Tlead.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trustfinity.Tlead.models.Payments;
@Repository
public interface PaymentRepository extends JpaRepository<Payments, Long>{
	
	
	public Payments findByUserEmail(String email);
	
	public Payments findByOrderId(String orderId);

}
