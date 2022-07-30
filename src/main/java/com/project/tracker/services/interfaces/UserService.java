package com.project.tracker.services.interfaces;

import java.util.List;

import com.project.tracker.entity.Bill;
import com.project.tracker.entity.UserEntity;

public interface UserService {

	public void registerUser(UserEntity user);
	public boolean userExists(UserEntity user,Long userId);
	public void updateUserAccount(UserEntity user,Long userId);
	public void deleteUserAccount(Long userId);
	public UserEntity fetchUserDetails(Long userId);
	public List<Bill> fetchBillsOfUser(Long userId);

}
