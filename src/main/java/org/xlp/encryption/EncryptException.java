package org.xlp.encryption;

public class EncryptException extends Exception {
	private static final long serialVersionUID = -3286003868997760407L;

	public EncryptException(String message) {
		super(message);
	}

	public EncryptException(Throwable cause) {
		super(cause);
	}

	public EncryptException(String message, Throwable cause) {
		super(message, cause);
	}
}
