package org.xlp.utils.exception;

/**
 * 值转换一常
 * 
 * @author 徐龙平
 *         <p>
 *         2017-6-8
 *         </p>
 * @version 1.0
 */
public class ValueCastException extends RuntimeException{

	private static final long serialVersionUID = -9043029220171659302L;

	public ValueCastException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValueCastException(String message) {
		super(message);
	}
}
