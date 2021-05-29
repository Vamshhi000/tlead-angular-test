package com.trustfinity.Tlead.Controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trustfinity.Tlead.Repository.PaymentRepository;
import com.trustfinity.Tlead.Repository.UserRepository;
import com.trustfinity.Tlead.Service.ChatService;
import com.trustfinity.Tlead.Service.UserService;
import com.trustfinity.Tlead.ServiceImpl.CustomUserDetailsService;
import com.trustfinity.Tlead.jwt.helper.JwtUtil;
import com.trustfinity.Tlead.models.Payments;
import com.trustfinity.Tlead.models.ResponceDto;
import com.trustfinity.Tlead.models.Users;
import com.trustfinity.Tlead.models.ValidateEmailNumber;

import globalExceptionHandler.EmailFound;
import globalExceptionHandler.EmailNotFound;
import globalExceptionHandler.NumberFound;
import globalExceptionHandler.OtpExpired;
import globalExceptionHandler.OtpInvalid;
import globalExceptionHandler.PasswordNotMatch;
import globalExceptionHandler.TokenNotFound;
import net.bytebuddy.utility.RandomString;
@RestController
//@RequestMapping(value="/register")
@CrossOrigin(origins = "*")


public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
	
	
	
	
	
	
	
	
	public static String getRandomNumberString() {
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
	}
	@PostMapping(value="/emailVerification")
	public ResponseEntity<ResponceDto> emailVerification(@RequestBody Users users) throws UnsupportedEncodingException, MessagingException,EmailFound {
		ResponceDto responceDto= new ResponceDto();
	try {
		String token=UserController.getRandomNumberString();
		String email=users.getEmail();
			userService.sendOTPEmail(token, users);
			responceDto.setMsg("OTP send Sucussfully");
			logger.info("email send Sucussfully");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
			
	}
	catch (EmailFound e) {
		responceDto.setMsg("Email already registered");
		logger.error("Email already registered",e);
		return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
	}catch (Exception e) {
		logger.error("chcked exception",e);
		responceDto.setMsg("Unable to send Otp");
		return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
	}
			

		
		}
	
	@PostMapping(value="/emialOtpSubmit")
	public ResponseEntity<ResponceDto> emailOtpSubmit(@RequestBody ValidateEmailNumber validateEmailNumber) throws OtpExpired,OtpInvalid{
		
		ResponceDto responceDto= new ResponceDto();
		try {
			
		
			
			userService.otpSubmit(validateEmailNumber);
			responceDto.setMsg("OTP Verified");
			responceDto.setAuthenticate(true);
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
		}catch (OtpInvalid e) {
			responceDto.setMsg("OTP invalid");
			responceDto.setAuthenticate(false);
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}
		catch (OtpExpired e) {
			responceDto.setMsg("OTP expired");
			responceDto.setAuthenticate(false);
			System.out.println(e);
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		} 
		catch (Exception e) {
			responceDto.setMsg("unable to verify");
			responceDto.setAuthenticate(false);
			System.out.println(e);
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}
		
		
	}
	@PostMapping("/sendSms")
	public ResponseEntity<ResponceDto> sendSms(@RequestBody Users users) throws NumberFound{
		ResponceDto responceDto= new ResponceDto();
		try {
			String token=UserController.getRandomNumberString();
			responceDto.setMsg("OTP send Sucussfully");
			logger.info("OTP send Sucussfully to mobile");
			userService.sendSms(users,token);
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
		}
		catch (NumberFound e) {
			responceDto.setMsg("Number already registered");
			logger.error("Number already registered",e);
		
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			logger.error("chcked exception",e);
			responceDto.setMsg("Unable to send Otp");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@PostMapping("/registerUser")
	public ResponseEntity<ResponceDto> registerUser(@RequestBody Users users) throws EmailFound{
		ResponceDto responceDto= new ResponceDto();
		Users userrsemail=userRepository.findByEmail(users.getEmail());
		Users userrsnumber=userRepository.findByPhoneNumberr(users.getPhoneNumber());
		try {
			if(userrsemail==null) {
				if(userrsnumber==null) {
					userService.registerUser(users);
					responceDto.setMsg("Sucussfully Registered! please login");
					logger.info("registered Sucussfully");
					return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
				}else {
					throw new NumberFound("number already registered");
				}
			}else {
				throw new EmailFound("User Not Registered");
			}
		} catch (EmailFound e) {
			responceDto.setMsg("Already registered with this Email");
//			logger.error("chcked exception",e);
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		} catch (NumberFound e) {
			responceDto.setMsg("Already registered with this Number");
//			logger.error("chcked exception",e);
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}
		
		
		catch (Exception e) {
			logger.error("chcked exception",e);
			responceDto.setMsg("Unable to register");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}
		
	}
		

	
	
	@PostMapping("/loginUser")
	public ResponseEntity<?> loginUser(@RequestBody Users users) throws UsernameNotFoundException,BadCredentialsException{
		ResponceDto responceDto= new ResponceDto();
		
		Users userrs=userRepository.findByEmail(users.getEmail());
        try {
        	if(userrs!=null) {
        		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    			boolean matched = bCryptPasswordEncoder.matches(users.getPassword(),userrs.getPassword());
    	
        		if(matched) {
//                    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getEmail(), users.getPassword()));
        			
        			
                    UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(users.getEmail());
                   
                    
                    Payments payments = paymentRepository.findByUserEmail(users.getEmail());
                    
                    if(payments==null) {
                        String token = this.jwtUtil.generateToken(userDetails);
                     
                        responceDto.setToken(token);
                        responceDto.setMsg("Logined Sucussfully");
                        responceDto.setEmail(users.getEmail());
                        responceDto.setAuthenticate(true);
                        responceDto.setPhoneNumber(userrs.getPhoneNumber());
                        responceDto.setStatus("unPaid");
                        responceDto.setUserName(userrs.getFirstName()+userrs.getLastName());
                        return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
                    	
                    }else {
                    	
                    	
                        String token = this.jwtUtil.generateToken(userDetails);
                      
                        responceDto.setToken(token);
                        responceDto.setMsg("Logined Sucussfully");
                        responceDto.setEmail(users.getEmail());
                        responceDto.setAuthenticate(true);
                        responceDto.setPhoneNumber(userrs.getPhoneNumber());
                        responceDto.setStatus(payments.getStatus());
                        responceDto.setUserName(userrs.getFirstName()+userrs.getLastName());
                        return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
                    	
                    }
                    
        		}else {
        			
        			throw new PasswordNotMatch("Invalid Password");
        			
        		}
        		
        	}else {
        		
        		throw new EmailNotFound("User Not Registered");
        	}
         }catch (PasswordNotMatch e) {
           
            responceDto.setMsg("Invalid Password");
            responceDto.setAuthenticate(false);
           
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
        }
        catch (EmailNotFound e) {
           
            responceDto.setMsg("User Not Registered");
            responceDto.setAuthenticate(false);
           
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
        }
        
        
        catch (UsernameNotFoundException e) {
           
            responceDto.setMsg("User not found");
            responceDto.setAuthenticate(false);
            e.printStackTrace();
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
        }catch (BadCredentialsException e)
        {   
        responceDto.setMsg("Invalid Email/Password");
        responceDto.setAuthenticate(false);
        
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
    
            responceDto.setMsg("unable to login");
            responceDto.setAuthenticate(false);
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}

}
	
	@PostMapping(value="/sendEmailToReset")
	public ResponseEntity<ResponceDto> sendResetPassowrd(@RequestBody Users users) throws EmailNotFound{
		String token =RandomString.make(45);
		String email=users.getEmail();
		String link="http://localhost:4200"+"/resetPassword?token="+ token;
		ResponceDto responceDto= new ResponceDto();
		Users userrs=userRepository.findByEmail(users.getEmail());
		try {
	
			if(userrs!=null) {
				userService.updateToken(token, email);
				logger.info("updated the token");
				userService.sendEmailToReset(link, email);
				logger.info("email sent");

				responceDto.setEmail(email);
				responceDto.setMsg("Successfully send email! please check");
				return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
			}else {
			throw new EmailNotFound("user not Registered");
				
			}
		
		}catch (EmailNotFound e) {
			logger.error("Not a registered email",e);
			responceDto.setEmail(email);
			responceDto.setMsg("Plese !! Enter registered Email");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
			
		}
		
		catch (Exception e) {
			logger.error("Internal server error",e);
			responceDto.setEmail(email);
			responceDto.setMsg("Unable to send reset Link");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
			
		}
		
		
		
		
		}
	
	
	@PostMapping(value="/resetPassword")
	public ResponseEntity<ResponceDto> reSettingPassword(@RequestParam("token") String token,@RequestBody Users users){
	
		
		ResponceDto responceDto= new ResponceDto();
		
		try {
			userService.reSettingPassword(token, users);
			responceDto.setMsg("Succuessfully reset the password");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
		}catch (TokenNotFound e) {
			responceDto.setMsg("Invalid Request");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}catch (OtpExpired e) {
			responceDto.setMsg("Link Expired");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}
		
		
		catch (Exception e) {
			responceDto.setMsg("Bad request");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}
	
		
	}
	
	@GetMapping(value="/{email}")
	public ResponseEntity<ResponceDto> getRefId(@PathVariable String email){
		ResponceDto responceDto= new ResponceDto();
		try {
			Users userrs=userRepository.findByEmail(email);
			responceDto.setRefId(userrs.getMyRefId());
			responceDto.setMsg("Succuessfully reset the password");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
		} catch (Exception e) {
			
			responceDto.setMsg("Bad request");
			return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);

		}

		
		
		
	}

	
	
	@GetMapping("viewReferals/{email}")
	public Map<String, List<ResponceDto>> getAllReferals(@PathVariable String email){
		Map<String, List<ResponceDto>> eeee=userService.getAllReferals(email);
		
		return eeee;
		
		
		
	}
	

	
}
