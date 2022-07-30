package com.project.tracker.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<ErrorDto> handleBillNotFound(ResourceNotFoundException ex) {
		return new ResponseEntity<ErrorDto>(new ErrorDto(HttpStatus.NOT_FOUND.value(),ex.getMessage(),System.currentTimeMillis()), HttpStatus.NOT_FOUND);
	}
	
	//handles Generic exceptions
	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorDto> handleGeneralExceptions(Exception ex){
		return new ResponseEntity<ErrorDto>(new ErrorDto(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage(),
				System.currentTimeMillis()),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({UserException.class})
	public ResponseEntity<ErrorDto> handleUserRelatedExceptions(UserException ex){
		return new ResponseEntity<ErrorDto>(new ErrorDto(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage(),
				System.currentTimeMillis()
				),HttpStatus.CONFLICT);
	}
	 
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<ErrorDto> errorList = new ArrayList<ErrorDto>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorList.add(new ErrorDto(HttpStatus.BAD_REQUEST.value(),error.getDefaultMessage(),System.currentTimeMillis()));
		});
		
		return new ResponseEntity<Object>(errorList, status);
	}
}
