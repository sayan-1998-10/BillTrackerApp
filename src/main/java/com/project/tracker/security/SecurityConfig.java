package com.project.tracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.project.tracker.security.jwtFilters.JwtAuthenticationFilter;
import com.project.tracker.security.jwtFilters.JwtValidationFilter;
import com.project.tracker.security.services.ApplicationUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//disable session since we are using Jwt.
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//add custom filters for jwt authentication and validation
		http
			.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilterAfter(new JwtValidationFilter(), JwtAuthenticationFilter.class);
		
		http
			.authorizeRequests()
			.antMatchers("/user/register").permitAll()
			.anyRequest()
			.authenticated();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(getUserDetailsService());
	}
	
	@Bean
	UserDetailsService getUserDetailsService() {
		return new ApplicationUserDetailsService();
	}
}
