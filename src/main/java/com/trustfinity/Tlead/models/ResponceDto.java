package com.trustfinity.Tlead.models;

public class ResponceDto {
	
	
	private String msg;
	private boolean authenticate;
	String token;
	private String email;
	private String status;
	private String phoneNumber;
	private String userName;
	private String refId;
	private String orderId;
	private String paymentId;
	private String myRefId;
	private String Paidstatus;
	public ResponceDto() {
		
		
		
	}


	


	public String getOrderId() {
		return orderId;
	}





	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}





	public String getPaymentId() {
		return paymentId;
	}





	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}





	public String getMyRefId() {
		return myRefId;
	}





	public void setMyRefId(String myRefId) {
		this.myRefId = myRefId;
	}





	public String getPaidstatus() {
		return Paidstatus;
	}





	public void setPaidstatus(String paidstatus) {
		Paidstatus = paidstatus;
	}





	public String getRefId() {
		return refId;
	}





	public void setRefId(String refId) {
		this.refId = refId;
	}





	public String getUserName() {
		return userName;
	}





	public void setUserName(String userName) {
		this.userName = userName;
	}





	public String getPhoneNumber() {
		return phoneNumber;
	}





	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}





	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public boolean isAuthenticate() {
		return authenticate;
	}


	public void setAuthenticate(boolean authenticate) {
		this.authenticate = authenticate;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
