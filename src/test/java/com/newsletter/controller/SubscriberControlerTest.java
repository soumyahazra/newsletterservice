/**
 * 
 */
package com.newsletter.controller;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.newsletter.entity.Subscriber;
import com.newsletter.exception.SubscribtionUnsuccessfulException;
import com.newsletter.exception.UserNotSubscribedException;
import com.newsletter.repository.SubscriberRepository;
import com.newsletter.service.SubscriberService;

/**
 * @author hazra
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = SubscriberControler.class)
public class SubscriberControlerTest {

	@MockBean
	private SubscriberRepository subscriberRepository;

	@MockBean
	private SubscriberService subscriberService;

	@Autowired
	private MockMvc mockMvc;

	private String newSubscriber = "{\r\n" + "\"subscriptionID\": \"test.subscriber.email@gmail.com\",\r\n"
			+ "\"subscriptionDate\": \"2019-03-31\"\r\n" + "}";

	private String newUnSubscribeResponse = "{\r\n" + "    \"isUnsubscribed\": true,\r\n"
			+ "    \"subscriptionID\": soumya.hazz@gmail.com\r\n" + "}";
	
	private String expectedResultAfterDate = "[{\"id\":0,\"subscriptionID\":\"soumya.hazz@outlook.com\",\"subscriptionDate\":\"2019-01-31T18:30:00.000+0000\"}]";
	
	private String expectedResultBeforeDate = "[{\"id\":0,\"subscriptionID\":\"soumya.hazz@gmail.com\",\"subscriptionDate\":\"2018-12-31T18:30:00.000+0000\"}]";
	
	private String expectedResult = "[{\"id\":0,\"subscriptionID\":\"soumya.hazz@gmail.com\",\"subscriptionDate\":\"2018-12-31T18:30:00.000+0000\"},{\"id\":0,\"subscriptionID\":\"soumya.hazz@outlook.com\",\"subscriptionDate\":\"2019-01-31T18:30:00.000+0000\"}]";


	@Test
	public void testFindAll() throws Exception {

		List<Subscriber> subscriberList = new ArrayList<>();
		subscriberList
				.add(new Subscriber("soumya.hazz@gmail.com", parseDate("01-01-2019")));
		subscriberList
				.add(new Subscriber("soumya.hazz@outlook.com", parseDate("01-02-2019")));
		Mockito.when(subscriberService.findAllSubscribers()).thenReturn(subscriberList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/subscribers");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		JSONAssert.assertEquals(expectedResult, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testSubscribe() throws Exception {

		Subscriber mockSeubscriber = new Subscriber("soumya.bikash.hazra@gmail.com",
				parseDate("10-01-2019"));
		Mockito.when(subscriberService.subscribe(Mockito.any(Subscriber.class))).thenReturn(mockSeubscriber);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscribe").accept(MediaType.APPLICATION_JSON)
				.content(newSubscriber).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

	}

	@Test
	public void testSubscribe_Unsuccessful() throws Exception {

		Mockito.when(subscriberService.subscribe(Mockito.any(Subscriber.class)))
				.thenThrow(new SubscribtionUnsuccessfulException(Mockito.anyString()));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscribe").accept(MediaType.APPLICATION_JSON)
				.content(newSubscriber).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

	}

	@Test
	public void testRemoveSubscriber() throws Exception {

		Mockito.when(subscriberService.unsubscribe(Mockito.anyString())).thenReturn(newUnSubscribeResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/unsubscribe/soumya.hazz@gmail.com");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	public void testRemoveSubscriber_Unsuccessful() throws Exception {

		Mockito.when(subscriberService.unsubscribe(Mockito.anyString())).thenThrow(new UserNotSubscribedException());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/unsubscribe/soumya.hazz@gmail.com");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}

	@Test
	public void testIsSubscribed() throws Exception {

		Mockito.when(subscriberService.isSubscribed(Mockito.anyString())).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/issubscribed/soumya.hazz@gmail.com");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals("true", result.getResponse().getContentAsString());
	}

	@Test
	public void testIsSubscribed_Negative() throws Exception {

		Mockito.when(subscriberService.isSubscribed(Mockito.anyString())).thenReturn(false);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/issubscribed/soumya.hazz@gmail.com");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals("false", result.getResponse().getContentAsString());
	}

	@Test
	public void testFindAllBeforeDate() throws Exception {

		List<Subscriber> subscriberList = new ArrayList<>();
		subscriberList
				.add(new Subscriber("soumya.hazz@gmail.com", parseDate("01-01-2019")));
		Mockito.when(
				subscriberService.findAllBasedOnDate("before", parseDate("10-01-2019")))
				.thenReturn(subscriberList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/subscribers/before/10-01-2019");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("Before Date : "+ result.getResponse().getContentAsString());

		JSONAssert.assertEquals(expectedResultBeforeDate, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testFindAllAfterDate() throws Exception {

		List<Subscriber> subscriberList = new ArrayList<>();
		subscriberList
				.add(new Subscriber("soumya.hazz@outlook.com", new SimpleDateFormat("dd-MM-yyyy").parse("01-02-2019")));
		Mockito.when(
				subscriberService.findAllBasedOnDate("after", new SimpleDateFormat("dd-MM-yyyy").parse("10-01-2019")))
				.thenReturn(subscriberList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/subscribers/after/10-01-2019");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		JSONAssert.assertEquals(expectedResultAfterDate, result.getResponse().getContentAsString(), false);
	}
	
	private Date parseDate(String date) throws ParseException {
		return new SimpleDateFormat("dd-MM-yyyy").parse(date);
	}

}
