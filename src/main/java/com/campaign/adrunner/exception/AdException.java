package com.campaign.adrunner.exception;

public class AdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7016038885744013566L;
	
	private String errorMessage;
	 
	public String getErrorMessage() {
		return errorMessage;
	}
	public AdException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	public AdException() {
		super();
	}

}
