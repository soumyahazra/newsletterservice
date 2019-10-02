package com.newsletter.service;

import java.util.Date;
import java.util.List;

import com.newsletter.entity.Subscriber;

public interface SubscriberService {
	
	public List<Subscriber> findAllSubscribers();
	
	public String unsubscribe(String id);
	
	public boolean isSubscribed(String id);
	
	public List<Subscriber> findAllBasedOnDate(String operator, Date date);
	
	public Subscriber subscribe(Subscriber subscriber);
	
	public String getDefaultMessage();
}