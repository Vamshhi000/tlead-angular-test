package com.trustfinity.Tlead.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class Admin {

	
    @Value("${tlead.name}")
    private String admin;
    
    @Value("${tlead.cred}")
    private String cred;
    
    @Bean
    public String getAdmin() {
        return admin;
    }
    
    @Bean
    public String getCred() {
        return cred;
    }
    
}
