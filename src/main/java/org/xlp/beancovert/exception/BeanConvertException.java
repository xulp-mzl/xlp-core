package org.xlp.beancovert.exception;

/**
 * <p>创建时间：2022年12月2日 下午10:42:44</p>
 * @author xlp
 * @version 1.0 
 * @Description bean转换异常类
*/
public class BeanConvertException extends Exception{
	private static final long serialVersionUID = -32289251214943999L;
	
	/**
	 * 记录导致该异常的目标异常
	 */
	private Throwable target;

	public BeanConvertException(String message, Throwable cause) {
		super(message, cause);
		target = cause;
	}

	public BeanConvertException(String message) {
		super(message);
	}

	/**
	 * 获取导致该异常的目标异常
	 * @return the target
	 */
	public Throwable getTarget() {
		return target;
	}

}
