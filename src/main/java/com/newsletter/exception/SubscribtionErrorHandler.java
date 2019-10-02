package com.newsletter.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class SubscribtionErrorHandler {
	
	@ResponseBody
	@ExceptionHandler(SubscribtionUnsuccessfulException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(SubscribtionUnsuccessfulException e) {
	  return e.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(UserNotSubscribedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(UserNotSubscribedException e) {
	  return e.getMessage();
	}

}