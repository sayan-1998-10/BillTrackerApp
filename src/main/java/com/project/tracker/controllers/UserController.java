package com.project.tracker.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tracker.dtos.BillDto;
import com.project.tracker.dtos.UserDto;
import com.project.tracker.entity.Bill;
import com.project.tracker.entity.UserEntity;
import com.project.tracker.services.interfaces.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("register")
	public ResponseEntity<String> registerUser(@RequestBody @Valid UserDto user) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		userService.registerUser(userEntity);
		return new ResponseEntity<String>("New user has been registered",HttpStatus.CREATED);
	}
	
	@DeleteMapping("delete/{userId}")
	public ResponseEntity<String> deleteUserById(@PathVariable("userId") Long userId) {
		this.userService.deleteUserAccount(userId);
		return new ResponseEntity<String>("User has been deleted",HttpStatus.GONE);
	}
	
	@PutMapping("update/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable("userId") Long userId,@RequestBody @Valid UserDto user) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		this.userService.updateUserAccount(userEntity,userId);
		return new ResponseEntity<String>("User has been updated",HttpStatus.GONE);
	}
	
	//fetch bills of a user
	@GetMapping("{userId}/bills")
	public ResponseEntity<List<BillDto>> fetchBillsOfUser(@PathVariable("userId") Long userId){
		List<Bill> bills = this.userService.fetchBillsOfUser(userId);
		List<BillDto> response = new ArrayList<BillDto>();
		bills.forEach(b ->{
			BillDto bill = new BillDto();
			bill.setName(b.getName());
			bill.setTotalExpense(b.getTotalExpense());
			bill.setDate(b.getDate());
			response.add(bill);
		});
		return new ResponseEntity<List<BillDto>>(response, HttpStatus.OK);
	}
	
	@GetMapping("{userId}")
	public ResponseEntity<UserDto> fetchUserDetails(@PathVariable("userId") Long userId){
		UserEntity userDtls = this.userService.fetchUserDetails(userId);
		UserDto userObj = new UserDto();
		BeanUtils.copyProperties(userDtls, userObj);
		return new ResponseEntity<UserDto>(userObj, HttpStatus.OK);
	}
}
