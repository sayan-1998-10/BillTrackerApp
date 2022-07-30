package com.project.tracker.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import lombok.Data;

@Data
public class UserDto {

	@NotNull(message = "Username cannot be empty.")
	@NotBlank(message = "Need to provide user name.")
	private String username;
	
	@NotNull(message = "Email cannot be empty.")
	@NotBlank(message = "Need to provide user email address.")
	@javax.validation.constraints.Email
	private String emailAddress;
	
	@NotNull(message = "Password field cannot be empty")
	@NotBlank(message = "Need to provide a strong password")
	private String password;
	
	
	private String phoneNumber;
}
