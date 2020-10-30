package org.xlp.javabean.convert;

import java.util.Map;

import org.xlp.javabean.convert.mapandbean.DefaultMapBean;
import org.xlp.utils.XLPDateUtil;

/**
 * bean->map或map->bean的工具类，主要是简化用户的操作
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 3.0
 * 
 */
public class XLPMapBeanUtil {
	/**
	 * 把javabean转换成map
	 * 
	 * @param bean
	 * @return map集合 假如参数为null则返回大小为0的map，此函数不返回null 假如某个字段的值为空，则map中不包含这个字断
	 */
	public static <T> Map<String, ?> beanToMap(T bean) {
		return beanToMap(bean, XLPDateUtil.DATE_FORMAT);
	}

	/**
	 * 把javabean转换成map
	 * 
	 * @param bean
	 * @param dateType
	 *            时间显示格式
	 * @return map集合 假如参数为null则返回大小为0的map，此函数不返回null 假如某个字段的值为空，则map中不包含这个字断
	 */
	public static <T> Map<String, ?> beanToMap(T bean, String dateType) {
		return new DefaultMapBean<T>(dateType).beanToMap(bean);
	}

	/**
	 * 把map转换成javabean
	 * 
	 * @param beanClass
	 * @param map
	 * @return 假如参数有一个参数为null或该函数内部产生异常时，则返回null
	 */
	public static <T> T mapToBean(Class<T> beanClass, Map<String, ?> map) {
		return mapToBean(beanClass, map, XLPDateUtil.DATE_FORMAT);
	}

	/**
	 * 把map转换成javabean
	 * 
	 * @param beanClass
	 * @param map
	 * @param dateType
	 *            以该格式字符串转换成date
	 * @return 假如参数有一个参数为null或该函数内部产生异常时，则返回null
	 */
	public static <T> T mapToBean(Class<T> beanClass, Map<String, ?> map,
			String dateType) {
		return new DefaultMapBean<T>(dateType).mapToBean(map, beanClass);
	}
}
