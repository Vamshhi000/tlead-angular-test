package com.trustfinity.Tlead.jwt.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.trustfinity.Tlead.ServiceImpl.CustomUserDetailsService;
import com.trustfinity.Tlead.jwt.helper.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //get jwt
        //Bearer
        //validate
        String requestTokenHeader = request.getHeader("Authorization");
        String username=null;
        String jwtToken=null;
        

        //null and format
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer "))
        {
            jwtToken=requestTokenHeader.substring(7);

            try{

                username = this.jwtUtil.getUsernameFromToken(jwtToken);


            }catch (Exception e)
            {
//                e.printStackTrace();
            	System.out.println(e);
            }

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {

            	
            		  UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
                      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                      usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            	
            	

              
              





            }else
            {
                System.out.println("Token is not validated..");
            }




        }
        
//        *******************************************************************************************************************************
        
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Adminn "))
        {
            jwtToken=requestTokenHeader.substring(7);

            try{

                username = this.jwtUtil.getUsernameFromToken(jwtToken);


            }catch (Exception e)
            {
//                e.printStackTrace();
            	System.out.println(e);
            }

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {

            	
      		  UserDetails userDetails = this.customUserDetailsService.admin(username);
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

              usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

              SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            	
            	

              
              





            }else
            {
                System.out.println("Token is not validated..");
            }




        }
        
        
        


        filterChain.doFilter(request,response);






    }
}
