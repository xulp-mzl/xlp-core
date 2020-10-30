package org.xlp.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 把异常堆栈内容转换为字符串工具类
 * 
 * @author xlp
 * @date 2020-05-16
 */
public class XLPStackTraceUtil {
	/**
	 * 获取异常堆栈内容
	 * 
	 * @param exception
	 * @return
	 */
	public static String getStackTrace(Throwable exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		pw.close();
		return sw.toString();
	}
}
