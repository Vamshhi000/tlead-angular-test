package com.trustfinity.Tlead.models;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class Payments {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	
	 
	 private int ammount;
	 
	 private String receipt;
	 
	 private String status;
	 
	 private String paymentId;
	 
	 private String orderId;
	 
	 @OneToOne(fetch = FetchType.LAZY, optional = false)
	 @JoinColumn(name = "user_id", nullable = false)
	 private Users user;
	 
	 public Payments() {
		 
	 }

	 
	 
	public Users getUser() {
		return user;
	}



	public void setUser(Users user) {
		this.user = user;
	}



	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public int getAmmount() {
		return ammount;
	}



	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}



	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Users getUsers() {
		return user;
	}

	public void setUsers(Users users) {
		this.user = users;
	}
	 
	 
}
