package org.xlp.utils;
/**
 * <p>创建时间：2021年5月28日 下午10:48:41</p>
 * @author xlp
 * @version 1.0 
 * @Description 信息输出工具了，用来包装System.out.println()日志打印
*/
public class XLPOutputInfoUtil {
	/**
	 * 打印信息
	 * 
	 * @param msg
	 */
	public static void println(String msg){
		System.out.println(msg == null ? "null" : msg);
	}
	
	/**
	 * 打印信息和输出错误信息
	 * 
	 * @param msg
	 * @param throwable
	 */
	public static void println(String msg, Throwable throwable){
		if (!XLPStringUtil.isEmpty(msg)) {
			System.out.println(msg);
		}
		if (throwable != null) {
			throwable.printStackTrace();
		}
	}
	
	/**
	 * 输出错误信息
	 * 
	 * @param msg
	 * @param throwable
	 */
	public static void println(Throwable throwable){
		if (throwable != null) {
			throwable.printStackTrace();
		}
	}
	
	 /**
     * 打印给定的对象信息
     *
     * @param object
     */
    public static void println(Object object) {
    	System.out.println(object == null ? "null" : object.toString());
    }
    
    /**
     * 打印给定的对象信息
     *
     * @param object
     */
    public static void println(String msg, Object... values) {
    	System.out.println(msg == null ? "null" : String.format(msg, values)); 
    }
}
