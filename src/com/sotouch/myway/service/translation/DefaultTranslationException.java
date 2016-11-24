package com.sotouch.myway.service.translation;

public class DefaultTranslationException extends Exception {

	/**
	 * Constructor
	 */
	public DefaultTranslationException() {
		super();
	}

	/**
	 * Constructor
	 * @param Exception cause
	 */
	public DefaultTranslationException(Exception exception) {
		super(exception);
	}

	/**
	 * Constructor
	 * @param message
	 */
	public DefaultTranslationException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message
	 * @param Exception cause
	 */
	public DefaultTranslationException(String message, Exception exception) {
		super(message, exception);
	}
}
