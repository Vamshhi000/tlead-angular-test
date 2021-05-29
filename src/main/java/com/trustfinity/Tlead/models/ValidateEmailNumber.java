package com.trustfinity.Tlead.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otp_reminder")
public class ValidateEmailNumber {
	@Id
	private String email;
	private String number;
	private String otp;
	private long otpTime;

	public ValidateEmailNumber() {
		
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getOtpTime() {
		return otpTime;
	}

	public void setOtpTime(long otpTime) {
		this.otpTime = otpTime;
	}
	
	
}
