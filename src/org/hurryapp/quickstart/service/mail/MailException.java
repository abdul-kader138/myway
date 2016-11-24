package org.hurryapp.quickstart.service.mail;

/**
 * Cette exception survient lors d'une erreur d'envoi de mail
 */
public class MailException extends Exception {

	/**
	 * Constructor
	 */
	public MailException() {
		super();
	}

	/**
	 * Constructor
	 * @param Exception cause
	 */
	public MailException(Exception exception) {
		super(exception);
	}

	/**
	 * Constructor
	 * @param message
	 */
	public MailException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message
	 * @param Exception cause
	 */
	public MailException(String message, Exception exception) {
		super(message, exception);
	}
}
