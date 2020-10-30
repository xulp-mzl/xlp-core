package org.xlp.json.exception;

public class JsonParseException extends JsonException{
	private static final long serialVersionUID = -4116518513533525897L;

	public JsonParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonParseException(String message) {
		super(message);
	}
}
