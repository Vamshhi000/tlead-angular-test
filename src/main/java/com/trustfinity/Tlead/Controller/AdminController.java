package com.trustfinity.Tlead.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trustfinity.Tlead.Service.AdminService;
import com.trustfinity.Tlead.ServiceImpl.CustomUserDetailsService;
import com.trustfinity.Tlead.jwt.helper.JwtUtil;
import com.trustfinity.Tlead.models.EditUsers;
import com.trustfinity.Tlead.models.ResponceDto;
import com.trustfinity.Tlead.models.Users;

import globalExceptionHandler.EmailNotFound;
import globalExceptionHandler.PasswordNotMatch;

@Controller
@RequestMapping(value="/Admin")
@CrossOrigin(origins = "*")
public class AdminController {
	 @Autowired
	  private JwtUtil jwtUtil;
	 
	 @Autowired
	 private AdminService adminService;
	
	@Autowired
	private String getAdmin; 
	@Autowired
	private String getCred; 
	
	 @Autowired
	 private CustomUserDetailsService customUserDetailsService;
	@PostMapping("login")
	public ResponseEntity<?> adminLogin(@RequestBody Users users) throws PasswordNotMatch,EmailNotFound{
		ResponceDto responceDto= new ResponceDto();
		try {
			if(users.getEmail().equals(getAdmin)) {
		  		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				boolean matched = bCryptPasswordEncoder.matches(users.getPassword(),getCred);
				
				if(matched) {
					   UserDetails userDetails = this.customUserDetailsService.admin(users.getEmail());
				    String token = this.jwtUtil.generateToken(userDetails);
	                responceDto.setToken(token);
	                responceDto.setMsg("Logined Sucussfully");

	                return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
				}
				else {
		 			throw new PasswordNotMatch("Invalid Password");
				}
				
			}
			else {
				throw new EmailNotFound("Admin not found");
			}
		} catch (PasswordNotMatch e) {
           
            responceDto.setMsg("Invalid Password");
            responceDto.setAuthenticate(false);
           
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
        }
        catch (EmailNotFound e) {
           
            responceDto.setMsg("Admin Not Registered");
            responceDto.setAuthenticate(false);
           
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            responceDto.setMsg("Internal Server Error");
            responceDto.setAuthenticate(false);
           
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
				
		}

		
		
	}

	
	@GetMapping("getAllResults/{pageNo}")
	private ResponseEntity<?> getAllResults(@PathVariable Integer pageNo){
		Integer pageSize=2;
	try {
		List<ResponceDto> userss=adminService.getAllUsers(pageNo,pageSize);
	 	return new ResponseEntity<List<ResponceDto>>(userss,HttpStatus.OK);
	} catch (Exception e) {
		ResponceDto responcedto= new ResponceDto();
		responcedto.setMsg("error in getting usess");
		
		return new ResponseEntity<ResponceDto>(responcedto,HttpStatus.BAD_REQUEST);
	
	}
	}
	
	
	
	@PutMapping("editUsers")
	private ResponseEntity<ResponceDto> editUsers(@RequestBody EditUsers editUsers){
		ResponceDto responceDto= new ResponceDto();
		try {
			
			
			adminService.editUsers(editUsers);
            responceDto.setMsg("Edited Sucussfully");

            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
			
			
			
			
		} catch (Exception e) {
            responceDto.setMsg("Internal Server Error");
            responceDto.setAuthenticate(false);
           
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		
		}
		
		
		
		
	}
	@PutMapping("deleteUsers")
	public ResponseEntity<?> deleteUsers(@RequestBody EditUsers editusers){
		ResponceDto responceDto= new ResponceDto();
		try {
			adminService.deleteUsers(editusers);
            responceDto.setMsg("Edited Sucussfully");

            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.OK);
		} catch (Exception e) {
            responceDto.setMsg("Internal Server Error");
            responceDto.setAuthenticate(false);
           
            return new ResponseEntity<ResponceDto>(responceDto,HttpStatus.BAD_REQUEST);
		}
	}
}
