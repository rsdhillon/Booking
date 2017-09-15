package com.ba.controller;

import static java.lang.System.getenv;

public class Utils {
	private static final String CUSTOMER_ENDPOINT_KEY = "SB_REST_ENDPOINT";
	
	public static String getCustomerEndpoint(String resourcePath) {
		String baseUrl = "";
		
		System.out.println("Host" + getenv("SB_REST_SERVICE_HOST"));
		System.out.println("Port" + getenv("SB_REST_SERVICE_PORT"));
		
		if (!isEmpty(getenv("SB_REST_SERVICE_HOST")) // check kubernetes service 
				&& !isEmpty(getenv("SB_REST_SERVICE_PORT")))
			baseUrl = "http://" + getenv("SB_REST_SERVICE_HOST") + ":" + System.getenv("SB_REST_SERVICE_PORT"); 
		
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
