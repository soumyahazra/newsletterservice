package com.newsletter;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsletterApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NewsletterApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String initialSubscriberList = "[{id=1, subscriptionID=soumya.hazz@gmail.com, subscriptionDate=2018-12-31T18:30:00.000+0000}, {id=2, subscriptionID=soumya.hazz@outlook.com, subscriptionDate=2019-01-31T18:30:00.000+0000}]";
	
	private String defaultMessage = "<h1>Welcome to Newsletter subscription service</h1>";

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testDefaultMessage() {

		assertEquals(this.restTemplate.getForEntity("http://localhost:" + port + "/", String.class)
				.getBody().toString(), defaultMessage);
	}

	@Test
	public void testFindAll() {

		assertEquals(this.restTemplate.getForEntity("http://localhost:" + port + "/subscribers", Collection.class)
				.getBody().toString(), initialSubscriberList);
	}

	@Test
	public void testFindAllBeforeDate() {

		assertEquals(this.restTemplate
				.getForEntity("http://localhost:" + port + "/subscribers/before/10-01-2019", Collection.class)
				.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testFindAllAfterDate() {

		assertEquals(this.restTemplate
				.getForEntity("http://localhost:" + port + "/subscribers/after/10-01-2019", Collection.class)
				.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testSubscribe() throws Exception {

		String requestJson = "{\"subscriptionID\": \"soumya.bikash.hazra@gmail.com\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		assertEquals(this.restTemplate.postForEntity("http://localhost:" + port + "/subscribe", entity, String.class).getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	public void testSubscribe_Unsuccessful() throws Exception {

		String requestJson = "{\"subscriptionID\": \"soumya.hazz@gmail.com\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		assertEquals(this.restTemplate.postForEntity("http://localhost:" + port + "/subscribe", entity, String.class).getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testSubscribe_InvalidEmail() throws Exception {

		String requestJson = "{\"subscriptionID\": \"soumya.hazz\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		assertEquals(this.restTemplate.postForEntity("http://localhost:" + port + "/subscribe", entity, String.class).getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testSubscribe_EmptyEmail() throws Exception {

		String requestJson = "{\"subscriptionID\": \"\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		assertEquals(this.restTemplate.postForEntity("http://localhost:" + port + "/subscribe", entity, String.class).getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testRemoveSubscriber() {

		this.restTemplate.delete("http://localhost:" + port + "/unsubscribe/soumya.hazz@outlook.com");
		assertEquals(this.restTemplate
				.getForEntity("http://localhost:" + port + "/issubscribed/soumya.hazz@outlook.com", String.class)
				.getBody().toString(), "The user is not subscribed.");
	}

	@Test
	public void testIsSubscribed() {

		assertEquals(this.restTemplate
				.getForEntity("http://localhost:" + port + "/issubscribed/soumya.hazz@gmail.com", String.class)
				.getBody().toString(), "true");
	}

	@Test
	public void testIsSubscribed_Unsuccessful() {

		assertEquals(this.restTemplate
				.getForEntity("http://localhost:" + port + "/issubscribed/some_junk_email@gmail.com", String.class)
				.getBody().toString(), "The user is not subscribed.");
	}

}
