package com.ba.controller;

import static java.lang.System.getenv;

public class Utils {
	private static final String CUSTOMER_ENDPOINT_KEY = "CUSTOMER_ENDPOINT";
	
	public static String getCustomerEndpoint(String resourcePath) {
		String baseUrl = "";
		
		System.out.println("Host" + getenv("CUSTOMER_SERVICE_HOST"));
		System.out.println("Port" + getenv("CUSTOMER_SERVICE_PORT"));
		
		if (!isEmpty(getenv("CUSTOMER_SERVICE_HOST")) // check kubernetes service 
				&& !isEmpty(getenv("CUSTOMER_SERVICE_PORT")))
			baseUrl = "http://" + getenv("CUSTOMER_SERVICE_HOST") + ":" + System.getenv("CUSTOMER_SERVICE_PORT"); 
		
		if (isEmpty(baseUrl)) { // check system properties
			baseUrl = System.getProperty(CUSTOMER_ENDPOINT_KEY);
		}
		
		if (isEmpty(baseUrl)) { // check environment variables
			baseUrl = System.getenv(CUSTOMER_ENDPOINT_KEY);
		}
		
		if (isEmpty(baseUrl)) { // default value
			baseUrl = "http://localhost:8080";
		} 
		System.out.println("Final Url " + baseUrl + resourcePath);
		return baseUrl + resourcePath;
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
}
