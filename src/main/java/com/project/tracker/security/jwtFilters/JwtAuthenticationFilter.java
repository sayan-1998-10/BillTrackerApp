package com.project.tracker.security.jwtFilters;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tracker.dtos.UserLoginRequest;
import com.project.tracker.security.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private JwtConfig jwtConfig;
	
	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager,JwtConfig jwtConfig) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken token = null;
		
		try {
			UserLoginRequest loginRequest = new ObjectMapper()
					.readValue(request.getInputStream(), UserLoginRequest.class);
			
			token = new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(),
															loginRequest.getPassword());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return this.authenticationManager.authenticate(token);
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String key = this.jwtConfig.getSecretKey();
		System.out.println("Key is "+key);
		String jwt = Jwts
			.builder()
			.claim("subject", authResult.getName())
			.claim("authorities", authResult.getAuthorities())
			.setIssuedAt(new Date())
			.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
			.signWith(SignatureAlgorithm.HS256,new SecretKeySpec(key.getBytes(),"HS256"))
			.compact();
		
		
		response.addHeader("Authorization", this.jwtConfig.getAuthorizationType() + " " + jwt);

	}
	
	
}
