package com.pb.td.po.util;

public class OnBoardingErrorResponse {

	private String error;
	private String message;
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getError() {
		return error;
	}
	
	public String getMessage() {
		return message;
	}

	public void setError(String error) {
		this.error = error;
		
	}

	public void setMessage(String message) {
		this.message = message;
		
	}

}
