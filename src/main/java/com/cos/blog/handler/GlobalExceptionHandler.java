
package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cos.blog.dto.ResponseDto;

@RestControllerAdvice

@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IllegalStateException.class)
	public Object illegalStateEx(Exception e) {
		String ErrorMessage = "에러 메세지: "+e.getMessage();
		return ErrorMessage;
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
}
