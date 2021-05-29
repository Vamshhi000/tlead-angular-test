package com.trustfinity.Tlead.ServiceImpl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.trustfinity.Tlead.Repository.UserRepository;
import com.trustfinity.Tlead.Repository.ValidateOtpRepository;
import com.trustfinity.Tlead.Service.UserService;
import com.trustfinity.Tlead.models.ResponceDto;
import com.trustfinity.Tlead.models.Users;
import com.trustfinity.Tlead.models.ValidateEmailNumber;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import globalExceptionHandler.EmailFound;
import globalExceptionHandler.EmailNotFound;
import globalExceptionHandler.NumberFound;
import globalExceptionHandler.OtpExpired;
import globalExceptionHandler.OtpInvalid;
import globalExceptionHandler.TokenNotFound;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ValidateOtpRepository validateOtpRepository;
	 private static final long OTP_VALID_DURATION = 5 * 60 * 1000;
	
	@Override
	public void sendOTPEmail(String otp, Users users) throws UnsupportedEncodingException, MessagingException,EmailFound {
	Optional<Users> user=userRepository.findById(users.getEmail());
		
		if(user.isPresent()) {
			throw new EmailFound("Already registered");
			
			
			
		}else {
			otpReminder(otp, users);
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		   

		    helper.setTo(users.getEmail());
		     
		    String subject = "VERIFY YOUR EMAIL!T-LEAD";
		     
		    String content = "<p>Hello, " + "</p>"
		            + "<p>Thanks for using T-LEAD! Please confirm your email address by entering the below otp and proceed the registration process.</p>"
		            + "<p>One Time Password to Verification!</p>"
		            + "<p><b>" + otp + "</b></p>"
		            + "<br>"
		            + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
		     
		    helper.setSubject(subject);
		     
		    helper.setText(content, true);
		     
		    javaMailSender.send(msg); 
		    
		}
	}
	   public boolean isExpired(long validateTime) {

		   long currentTimeInMillis = System.currentTimeMillis();
	        if (validateTime + OTP_VALID_DURATION < currentTimeInMillis) {
	            // OTP expires
	            return false;
	        }
	         
	        return true;
	    }
	public void otpReminder(String otp,Users users) {
		ValidateEmailNumber ValidateEmailNumber= new ValidateEmailNumber();
		 long currentTimeInMillis = System.currentTimeMillis();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encodedPassword=bCryptPasswordEncoder.encode(otp);
		ValidateEmailNumber.setEmail(users.getEmail());
		ValidateEmailNumber.setOtp(encodedPassword);
		ValidateEmailNumber.setOtpTime(currentTimeInMillis);
		ValidateEmailNumber.setNumber(users.getPhoneNumber());
		validateOtpRepository.save(ValidateEmailNumber);
	}
	@Override
	public boolean otpSubmit(ValidateEmailNumber validateEmailNumber) throws OtpExpired,OtpInvalid{
		ValidateEmailNumber validateEmailNumberr=validateOtpRepository.findByEmail(validateEmailNumber.getEmail());
		if(isExpired(validateEmailNumberr.getOtpTime())) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			
			boolean matched = bCryptPasswordEncoder.matches(validateEmailNumber.getOtp(), validateEmailNumberr.getOtp());
			if(matched) {
				return true;
			}else {
				
				throw new OtpInvalid("otp invalid");
			}
		}else {
			
			throw new OtpExpired("otp Expired");
			
		}
		
		
	}
	

	@Override
	public void sendSms(Users users,String otp) throws NumberFound{
		String bodyy="one time password is"+" "+otp;
		
		Optional<Users> user=userRepository.findByPhoneNumber(users.getPhoneNumber().substring(3, 13));
		if(user.isPresent()) {
			throw new NumberFound("Already registered");
		}else {
		
		
		
		try {
			
			
			
			otpReminder(otp, users);
			 Twilio.init("ACddc102c136dea0ed04411a3567120acf","5a58ca1c1141a2daee99642997e11105");
		        Message message = Message.creator( new PhoneNumber(users.getPhoneNumber()), new PhoneNumber("+18434737959"),bodyy)           
		                    
		            .create();

		        System.out.println(message);
		        System.out.println(message.getSid());

			}catch(Exception e) {
				
				e.printStackTrace();
				
			}
		}
		
	}
	@Override
	public void registerUser(Users users) throws EmailFound {
		
		Optional<Users> user=userRepository.findById(users.getEmail());
		
		if(user.isPresent()) {
			throw new EmailFound("Already registered");
			
			
			
		}else {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encodedPassword=bCryptPasswordEncoder.encode(users.getPassword());
			users.setPassword(encodedPassword);
			users.setReenterPassword(encodedPassword);
			userRepository.save(users);
			
			
		}
		
		
	}
	@Override
	public void updateToken(String token, String email) throws EmailNotFound{
		Users users = userRepository.findByEmail(email);
		
	 if(users!=null) {
		 long currentTimeInMillis = System.currentTimeMillis();
		 users.setPasstoken(token);
		 users.setLinkTime(currentTimeInMillis);
		 userRepository.save(users);
	 }else {
		 throw new EmailNotFound("email does't exits");
	 }
		
	}
	@Override
	public void sendEmailToReset(String link, String email) throws MessagingException {

		
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//	   <div class="card"

	    helper.setTo(email);
	     
	    String subject = "Reset Password!TLEAD";
	     
	    String content = "<p>Hello, " + "</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>Click the link below to change your password:</p>"
	            + "<p><b>" + link + "</b></p>"
	            + "<br>"
	            + "<p>Note: this Link is set to expire in 5 minutes.</p>"
	            +"<p>Ignore this if you remembered your password</p>";
	           
	     
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    javaMailSender.send(msg); 

		
	}
	@Override
	public void reSettingPassword(String token, Users users)  throws TokenNotFound,OtpExpired{

		Users passtokenobj = userRepository.findByPasstoken(token);
		if(passtokenobj!=null) {
			if(isExpired(passtokenobj.getLinkTime())) {	
		
			
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encodedPassword=bCryptPasswordEncoder.encode(users.getPassword());
			passtokenobj.setPassword(encodedPassword);
			passtokenobj.setReenterPassword(encodedPassword);
			userRepository.save(passtokenobj);
			}
			else {
				
				throw new OtpExpired("Link Expired");
				
			}
		}else {
			throw new TokenNotFound("token not found");
		}
		}
	@Override
	public Map<String, List<ResponceDto>> getAllReferals(String email) {
		
//		List<Map<String, List<ResponceDto>>> refaralList = new ArrayList<Map<String, List<ResponceDto>>>();
		Map<String, List<ResponceDto>> referalMap= new HashMap<String, List<ResponceDto>>();
	
		
		
		Users users = userRepository.findByEmail(email);
		List<Users> refList1=userRepository.findAllByReferalId(users.getMyRefId());
		
		for(Users rlist :refList1) {
			List<ResponceDto> referals= new ArrayList<ResponceDto>();
			
			String keyy=rlist.getMyRefId()+"@"+rlist.getEmail();
			List<Users> refList2=userRepository.findAllByReferalId(rlist.getMyRefId());
	
			for(Users x:refList2) {
			
				ResponceDto responceDto = new ResponceDto();
				responceDto.setEmail(x.getEmail());
				
				responceDto.setRefId(x.getMyRefId());
				referals.add(responceDto);
			}
			
			referalMap.put(keyy, referals);
			}
			
			
			
		
		
		return referalMap;
	}
	

	
	}

	
	

