package org.xlp.text;
/**
 * <p>创建时间：2021年2月19日 下午11:12:55</p>
 * @author xlp
 * @version 1.0 
 * @Description 文本文件行过滤器
*/
@FunctionalInterface
public interface LineFilter {
	boolean accept(LineData lineData);
}
