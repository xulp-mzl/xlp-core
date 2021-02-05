package org.xlp.csv;
/**
 * <p>创建时间：2021年1月20日 下午11:27:11</p>
 * @author xlp
 * @version 1.0 
 * @Description 操作csv文件出错时，所需异常类
*/
public class CSVException extends RuntimeException{
	private static final long serialVersionUID = -4617516891618576393L;

	public CSVException(String message, Throwable cause) {
		super(message, cause);
	}

	public CSVException(String message) {
		super(message);
	}

	public CSVException(Throwable cause) {
		super(cause);
	}
}
