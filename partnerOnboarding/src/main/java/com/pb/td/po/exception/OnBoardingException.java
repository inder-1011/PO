/**
 * 
 */
package com.pb.td.po.exception;

/**
 * @author indrajeet.verma@pb.com
 *
 * Apr 7, 2016
 */
public class OnBoardingException extends Exception {

	private String message;
	private String errorCode;
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public OnBoardingException(String message, String errorCode) {
		super();
		this.message = message;
		this.errorCode = errorCode;
	}
	
}
