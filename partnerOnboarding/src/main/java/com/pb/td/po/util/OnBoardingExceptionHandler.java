package com.pb.td.po.util;

import javax.ws.rs.core.Response;

public class OnBoardingExceptionHandler extends Exception {

	private String message = null;

	public OnBoardingExceptionHandler(String message) {
		super();
		this.message = message;
	}
	
	public OnBoardingExceptionHandler(Throwable cause){
		super(cause);
	}
	
	
	
}
