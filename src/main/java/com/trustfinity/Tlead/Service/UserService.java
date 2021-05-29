package com.trustfinity.Tlead.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.trustfinity.Tlead.models.ResponceDto;
import com.trustfinity.Tlead.models.Users;
import com.trustfinity.Tlead.models.ValidateEmailNumber;

import globalExceptionHandler.EmailFound;
import globalExceptionHandler.EmailNotFound;
import globalExceptionHandler.NumberFound;
import globalExceptionHandler.OtpExpired;
import globalExceptionHandler.OtpInvalid;
import globalExceptionHandler.TokenNotFound;

public interface UserService {
	
	
	public void sendOTPEmail(String otp,Users users) throws UnsupportedEncodingException, MessagingException,EmailFound;
	public boolean otpSubmit(ValidateEmailNumber validateEmailNumber) throws OtpExpired,OtpInvalid;
	
	public void sendSms(Users users,String otp) throws NumberFound;
	
	public void registerUser(Users users) throws EmailFound;
	public void updateToken(String token,String email) throws EmailNotFound;
	public void sendEmailToReset(String link,String email) throws MessagingException;
	public void reSettingPassword(String token,Users users) throws TokenNotFound,OtpExpired;
	public Map<String, List<ResponceDto>> getAllReferals(String email);

}
