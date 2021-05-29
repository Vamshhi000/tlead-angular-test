package com.trustfinity.Tlead.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import globalExceptionHandler.EmailNotFound;

public interface UserDetailsService {
	
	public UserDetails loadUserByUsername(String userName) throws UserPrincipalNotFoundException;
	


}
