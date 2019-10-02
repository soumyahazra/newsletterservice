package com.newsletter.repository;

import java.text.SimpleDateFormat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.newsletter.entity.Subscriber;

@Configuration
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(SubscriberRepository repo) {
		return args -> {
			repo.save(new Subscriber("soumya.hazz@gmail.com", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2019")));
			repo.save(new Subscriber("soumya.hazz@outlook.com", new SimpleDateFormat("dd-MM-yyyy").parse("01-02-2019")));
		};
	}
}
