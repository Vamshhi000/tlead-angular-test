package globalExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionhandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionhandler.class);
	
	
	
	@ExceptionHandler(value=Exception.class)
	public ResponseEntity<ExceptionModel> catchException(Exception e){
		logger.error("chcked exception",e);
		ExceptionModel exceptionModel = new ExceptionModel();
		
		exceptionModel.setE(e);
		
		exceptionModel.setMessage("chcked exception");
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	@ExceptionHandler(value=RuntimeException.class)
	public ResponseEntity<ExceptionModel> catchRuntimeException(Exception e){
		logger.error("runtime exception",e);
		ExceptionModel exceptionModel = new ExceptionModel();
		
		exceptionModel.setE(e);
		
		exceptionModel.setMessage("runtime exception");
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}

	@ExceptionHandler(value=EmailNotFound.class)
	public ResponseEntity<ExceptionModel> CatchEmailNotFound(EmailNotFound emailNotFound){
		logger.error("Email is not found",emailNotFound);
		
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setE(emailNotFound);
		exceptionModel.setMessage("This email address does't exists");
		
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(value=EmailFound.class)
	public ResponseEntity<ExceptionModel> CatchEmailFound(EmailNotFound emailNotFound){
		logger.error("Email is founded",emailNotFound);
		
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setE(emailNotFound);
		exceptionModel.setMessage("This email address already exists");
		
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(value=PasswordNotMatch.class)
	public ResponseEntity<ExceptionModel> catchPasswordNotMatch(PasswordNotMatch passwordNotMatch){
		
		logger.error("password not Match");
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setE(passwordNotMatch);
		exceptionModel.setMessage("password not Match");
		
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(value=OtpExpired.class)
	public ResponseEntity<ExceptionModel> catchOtpExpired(OtpExpired otpExpired){
		
		logger.error("otp Expired");
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setE(otpExpired);
		exceptionModel.setMessage("OTP expired");
		
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value=OtpInvalid.class)
	public ResponseEntity<ExceptionModel> catchOtpInvalid(OtpInvalid otpInvalid){
		
		logger.error("otp is invalid");
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setE(otpInvalid);
		exceptionModel.setMessage("otp is invalid");
		
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value=NumberFound.class)
	public ResponseEntity<ExceptionModel> CatchNumberFound(NumberFound numberFound){
		logger.error("Number found",numberFound);
		
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setE(numberFound);
		exceptionModel.setMessage("Already number registered");
		
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value=TokenNotFound.class)
	public ResponseEntity<ExceptionModel> CatchTokenNotfound(TokenNotFound tokenNotFound){
		logger.error("Number found",tokenNotFound);
		
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setE(tokenNotFound);
		exceptionModel.setMessage("Already number registered");
		
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
