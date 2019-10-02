package com.newsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableJpaRepositories
public class NewsletterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsletterApplication.class, args);
	}
}