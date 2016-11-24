package com.sotouch.myway.service.activation;

public class ActivationException extends Exception {

	/**
	 * Constructor
	 */
	public ActivationException() {
		super();
	}

	/**
	 * Constructor
	 * @param Exception cause
	 */
	public ActivationException(Exception exception) {
		super(exception);
	}

	/**
	 * Constructor
	 * @param message
	 */
	public ActivationException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message
	 * @param Exception cause
	 */
	public ActivationException(String message, Exception exception) {
		super(message, exception);
	}
}
