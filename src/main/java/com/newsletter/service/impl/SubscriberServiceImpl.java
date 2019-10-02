package com.newsletter.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.newsletter.entity.Subscriber;
import com.newsletter.exception.SubscribtionUnsuccessfulException;
import com.newsletter.exception.UserNotSubscribedException;
import com.newsletter.repository.SubscriberRepository;
import com.newsletter.service.SubscriberService;

@Service
public class SubscriberServiceImpl implements SubscriberService {

	@Autowired
	private SubscriberRepository subscriberRepo;

	private final String DEFAULT_MESSAGE = "<h1>Welcome to Newsletter subscription service</h1>";

	@Override
	public List<Subscriber> findAllSubscribers() {
		return subscriberRepo.findAll();
	}

	@Override
	public Subscriber subscribe(Subscriber subscriber) {
		String subscriptionID = subscriber.getSubscriptionID();
		if (!StringUtils.isEmpty(subscriptionID) && isValidEmail(subscriptionID)
				&& subscriberRepo.findBySubscriptionID(subscriptionID) == null) {
			subscriber.setSubscriptionDate(new Date());
			return subscriberRepo.save(subscriber);
		} else
			throw new SubscribtionUnsuccessfulException(subscriptionID);
	}

	@Override
	public String unsubscribe(String id) {
		Subscriber subscriber = subscriberRepo.findBySubscriptionID(id);
		if (null != subscriber) {
			subscriberRepo.delete(subscriber);
			return "{\r\n" + "\"isUnsubscribed\": true,\r\n" + "\"subscriptionID\": " + id + "\r\n" + "}";
		} else
			throw new UserNotSubscribedException();
	}

	@Override
	public boolean isSubscribed(String id) {
		Subscriber subscriber = subscriberRepo.findBySubscriptionID(id);
		if (null != subscriber)
			return true;
		else
			throw new UserNotSubscribedException();
	}

	@Override
	public List<Subscriber> findAllBasedOnDate(String operator, Date date) {
		List<Subscriber> subscribers = new ArrayList<>();
		if (operator.equalsIgnoreCase("before")) {
			subscribers = subscriberRepo.findAll().stream()
					.filter(subscriber -> subscriber.getSubscriptionDate().before(date)).collect(Collectors.toList());
		} else if (operator.equalsIgnoreCase("after")) {
			subscribers = subscriberRepo.findAll().stream()
					.filter(subscriber -> subscriber.getSubscriptionDate().after(date)).collect(Collectors.toList());
		}
		return subscribers;
	}

	@Override
	public String getDefaultMessage() {
		return DEFAULT_MESSAGE;
	}

	private static boolean isValidEmail(String email) {
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
		return email.matches(regex);
	}
}
