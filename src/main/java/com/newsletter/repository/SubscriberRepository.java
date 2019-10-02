package com.newsletter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsletter.entity.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
	public Subscriber findBySubscriptionID(String subscriptionID);

}