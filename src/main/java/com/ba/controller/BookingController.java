package com.ba.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ba.domain.booking.Booking;
import com.ba.domain.booking.Product;
import com.ba.domain.customer.Customer;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	
	private static final String CUST_ENDPOINT_KEY = "CUSTOMER_SERVICE_EP";
	
	private static List<Booking> bookings = new ArrayList<Booking>();
	
	private static List<Product> firstBookingProducts  = new ArrayList<Product>();
	
	private static List<Product> secondBookingProducts = new ArrayList<Product>();
	
	private RestTemplate restTemplate;
	
	static {
		
		firstBookingProducts.add(new Product("PNRREFA","LHR-BCN",130.0));
		firstBookingProducts.add(new Product("HOTBKNGA","Marriott Barcelona",150.0));
		
		secondBookingProducts.add(new Product("PNRREFB","LHR-PAR",90.0));
		secondBookingProducts.add(new Product("HOTBKNGB","Hotel Hilton Paris",150.0));		
		secondBookingProducts.add(new Product("CARBKNGB","Car Hire Paris",50.0));
		
		bookings.add(new Booking("1","Booking 1",firstBookingProducts, 280.0) );
		bookings.add(new Booking("2","Booking 2",secondBookingProducts, 290.0) );		
	}
	
	public BookingController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@RequestMapping( method = RequestMethod.GET)
	@ResponseBody
	public List<Booking> retrieveAllBookings() {
		
		getCustomer();
		
		getCustomerService();
		
		return bookings;
	}
	
	@SuppressWarnings("unchecked")
	private List<Customer> getCustomer() {
		
		List<Customer> cust = new ArrayList<Customer>();
		
		cust = restTemplate.getForObject(
				Utils.getCustomerEndpoint("/customers"), ArrayList.class);
		
		System.out.println("Environment variable based lookup response" + cust.toString());
		
		return cust;
	}
	
	@SuppressWarnings("unchecked")
	private List<Customer> getCustomerService() {
		
		List<Customer> cust = new ArrayList<Customer>();
		
		System.out.println("Env Variable is " + System.getenv(CUST_ENDPOINT_KEY));
		cust = restTemplate.getForObject(
				"http://" + System.getenv(CUST_ENDPOINT_KEY) + "/customers", ArrayList.class);
		
		System.out.println("Namespace based Lookup response" + cust.toString());

		return cust;
	}

}
