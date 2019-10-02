package com.newsletter.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Subscriber {
	
	public Subscriber() {
	}
		
	@Id
	@GeneratedValue
	private long id;
	private String subscriptionID;
	private Date subscriptionDate;

	public Subscriber(String subscriptionID, Date subscriptionDate) {
		this.subscriptionID = subscriptionID;
		this.subscriptionDate = subscriptionDate;
	}
	
	public Subscriber(String subscriptionID) {
		this.subscriptionID = subscriptionID;
		this.subscriptionDate = new Date();
	}
	
	public long getId() {
		return id;
	}
	
	public String getSubscriptionID() {
		return subscriptionID;
	}
	
	public Date getSubscriptionDate() {
		return subscriptionDate;
	}
	
	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}
}
