package com.trustfinity.Tlead.ServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trustfinity.Tlead.Repository.UserRepository;
import com.trustfinity.Tlead.models.CustomUserDetails;
import com.trustfinity.Tlead.models.Users;

@Service
public class CustomUserDetailsService implements UserDetailsService {


	
	@Autowired
	private String getAdmin; 
	@Autowired
	private String getCred; 
	
	   @Autowired
	    private UserRepository userRepository;

	    @Override
	    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

	        final Users user = this.userRepository.findByEmail(userName);

	        if (user == null) {
	            throw new UsernameNotFoundException("User not found !");
	        } else {
	        	
	            return new CustomUserDetails(user);
	        }

	    }
	    public UserDetails admin(String users) throws UsernameNotFoundException {
	    	Users usr= new Users();
	    	
	    	usr.setEmail(getAdmin);
	    	usr.setPassword(getCred);
	        final Users user = usr;

	        return new CustomUserDetails(user);

	    }
	    
}



//@Autowired
//private UsersRepository userRepository;
//
//@Override
//public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//
//  final User user = this.userRepository.findByUsername(userName);
//
//  if (user == null) {
//      throw new UsernameNotFoundException("User not found !!");
//  } else {
//      return new CustomUserDetails(user);
//  }
//
//
//
//
//}


