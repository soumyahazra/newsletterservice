package com.newsletter.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.newsletter.entity.Subscriber;
import com.newsletter.service.SubscriberService;

@RestController
class SubscriberControler {

	@Autowired
	private SubscriberService subscriberService;
	
	@GetMapping("/")
	public String defaultMessage() {
		return subscriberService.getDefaultMessage();
	}
	
	@GetMapping("/subscribers")
	public List<Subscriber> findAll() {
		return subscriberService.findAllSubscribers();
	}
	
	@PostMapping("/subscribe")
	@ResponseStatus(HttpStatus.CREATED)
	public Subscriber subscribe(@RequestBody Subscriber subscriber) {
		return subscriberService.subscribe(subscriber);
	}
		
	@DeleteMapping("/unsubscribe/{id}")
	public String removeSubscriber(@PathVariable String id) {
		return subscriberService.unsubscribe(id);
	}
	
	@GetMapping("/issubscribed/{id}")
	public boolean isSubscribed(@PathVariable String id) {
		return subscriberService.isSubscribed(id);
	}
	
	@GetMapping("/subscribers/{operator}/{date}")
	public List<Subscriber> findAllBasedOnDate(@PathVariable String operator, @PathVariable String date) throws Exception {
		return subscriberService.findAllBasedOnDate(operator, new SimpleDateFormat("dd-MM-yyyy").parse(date));
	}
}
