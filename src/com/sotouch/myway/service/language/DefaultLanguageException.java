package com.sotouch.myway.service.language;

public class DefaultLanguageException extends Exception {

	/**
	 * Constructor
	 */
	public DefaultLanguageException() {
		super();
	}

	/**
	 * Constructor
	 * @param Exception cause
	 */
	public DefaultLanguageException(Exception exception) {
		super(exception);
	}

	/**
	 * Constructor
	 * @param message
	 */
	public DefaultLanguageException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message
	 * @param Exception cause
	 */
	public DefaultLanguageException(String message, Exception exception) {
		super(message, exception);
	}
}
