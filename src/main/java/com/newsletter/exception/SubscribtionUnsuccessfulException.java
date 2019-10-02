package com.newsletter.exception;

public class SubscribtionUnsuccessfulException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubscribtionUnsuccessfulException(String subscriptionId) {
		super("Error occured while trying to subscribe " + subscriptionId + "\n"
				+ "This could be because of:" + "\n"
				+ "\t" + "1. The subscriptionId/emailId is missing or wrong in the request. Please validate the request." + "\n"
				+ "\t" + "2. The user is already subscribed. You may use /issubscribed/{id} API to find out.");
	}
}