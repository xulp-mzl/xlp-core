package org.xlp.assertion;

public class IllegalObjectException extends RuntimeException {
	private static final long serialVersionUID = -5974975722940429965L;

	public IllegalObjectException(String message) {
		super(message);
	}

	public IllegalObjectException(Throwable cause) {
		super(cause);
	}

	public IllegalObjectException(String message, Throwable cause) {
		super(message, cause);
	}
}
