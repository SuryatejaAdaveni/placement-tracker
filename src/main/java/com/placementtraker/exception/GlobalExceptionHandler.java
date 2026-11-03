package com.placementtraker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleExceptionGlobally(Exception ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(JobNotFoundException.class)
	public ResponseEntity<String> handleExceptionLocally(JobNotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
}
