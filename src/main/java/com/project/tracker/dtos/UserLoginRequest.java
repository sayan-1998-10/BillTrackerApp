package com.project.tracker.dtos;

import lombok.Data;

@Data
public class UserLoginRequest {

	private String emailAddress;
	private String password;
}
