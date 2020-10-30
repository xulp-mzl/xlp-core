package org.xlp.utils.exception;

/**
 * @version 1.0
 * @author 徐龙平
 *         <p>
 *         2017-4-29
 *         </p>
 */
public class XLPParamFormatException extends IllegalArgumentException {
	private static final long serialVersionUID = 6340496568181340552L;

	public XLPParamFormatException() {
		super();
	}

	public XLPParamFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public XLPParamFormatException(String s) {
		super(s);
	}

}
