package org.xlp.text;
/**
 * <p>创建时间：2021年2月19日 下午11:08:53</p>
 * @author xlp
 * @version 1.0 
 * @Description 文本文件行处理器
*/
@FunctionalInterface
public interface LineHandler<T> {
	T handle(LineData lineData);
}
