package com.newsletter.exception;

public class UserNotSubscribedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotSubscribedException() {
		super("The user is not subscribed.");
	}
}