package com.project.tracker.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.tracker.entity.UserEntity;
import com.project.tracker.repository.UserRepository;

public class ApplicationUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
		UserEntity userEntity = this.userRepo.findByEmailAddress(emailAddress).get();
		/*
		 * 1. Default ROLE passed for every user
		 * 2. All other properties like isAccExpired... taking default values
		 */
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
		
		return User
			.builder()
				.username(userEntity.getEmailAddress())
				.password(userEntity.getPassword())
				.authorities(grantedAuthorities) 
				.build();
	}

}
