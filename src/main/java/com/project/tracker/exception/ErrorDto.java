package com.project.tracker.exception;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
	int errorCode;
	String errorMessage;
	Long errorTimeStamp;
}
