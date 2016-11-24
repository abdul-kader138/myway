package org.hurryapp.fwk.util;

/**
 * Cette exception survient lors de la modification ou la lecture d'un document XML
 * 
 * @author Lipfi
 */
public class ResizeException extends Exception {

	/**
	 * Constructor
	 */
	public ResizeException() {
		super();
	}

	/**
	 * Constructor
	 * @param Exception cause
	 */
	public ResizeException(Exception exception) {
		super(exception);
	}

	/**
	 * Constructor
	 * @param message
	 */
	public ResizeException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message
	 * @param Exception cause
	 */
	public ResizeException(String message, Exception exception) {
		super(message, exception);
	}
}
