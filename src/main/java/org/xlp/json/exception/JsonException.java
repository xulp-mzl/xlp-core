package org.xlp.json.exception;

public class JsonException extends RuntimeException{
	private static final long serialVersionUID = -2415758537693552514L;

	public JsonException() {
		super();
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonException(String message) {
		super(message);
	}
}
