package com.user.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.user.service.responsedto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<ApiResponse> handelFileUploadException(FileUploadException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(exception.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handelResourceNotFoundException(ResourceNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(exception.getMessage(), false, HttpStatus.BAD_REQUEST));
	}
	
}
