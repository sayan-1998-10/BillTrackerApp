package com.project.tracker.services.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.tracker.entity.Bill;
import com.project.tracker.entity.UserEntity;
import com.project.tracker.exception.UserException;
import com.project.tracker.repository.UserRepository;
import com.project.tracker.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public void registerUser(UserEntity user) {
		if(!userExists(user,null)) {
			//hash the password before storing
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			this.userRepo.save(user);
			System.out.println("user saved to db");
		}
		else {
			throw new UserException("User with this email address already exists");
		}
		
	}

	@Override
	public boolean userExists(UserEntity user, Long userId) {
		boolean userExists = false;
		if(userId != null) {
			userExists = this.userRepo.findById(userId).isPresent();
		}
		else {
			//search by email address if userId not present
			userExists =  this.userRepo.findByEmailAddress(user.getEmailAddress()).isPresent();
		}
		return userExists;
		
		
	}

	@Override
	public void deleteUserAccount(Long userId) {
		if(!userExists(null,userId)) {
			throw new UserException("User does not exist");
		}
		else {
			this.userRepo.deleteById(userId);
		}
	}
	
	@Override
	public UserEntity fetchUserDetails(Long userId) {
		if(!userExists(null, userId)) {
			throw new UserException("User does not exist");
		}
		return this.userRepo.findById(userId).get();
	} 
	
	@Override
	public void updateUserAccount(UserEntity newUserDtls, Long userId) {
		UserEntity userEntity = this.fetchUserDetails(userId);
		userEntity.setUsername( !userEntity.getUsername().equals(newUserDtls.getUsername()) ?  newUserDtls.getUsername(): userEntity.getUsername()); 
		userEntity.setPassword( !userEntity.getPassword().equals(newUserDtls.getPassword()) ?  newUserDtls.getPassword(): userEntity.getPassword()); 
		userEntity.setEmailAddress( !userEntity.getEmailAddress().equals(newUserDtls.getEmailAddress()) ?  newUserDtls.getEmailAddress(): userEntity.getEmailAddress()); 
		userEntity.setPhoneNumber( !userEntity.getPhoneNumber().equals(newUserDtls.getPhoneNumber()) ?  newUserDtls.getPhoneNumber(): userEntity.getPhoneNumber());
		
		this.userRepo.save(userEntity);
	}
	
	@Override
	public List<Bill> fetchBillsOfUser(Long userId) {
		UserEntity userDetails = this.fetchUserDetails(userId);
		return userDetails.getBills();
	}

}
