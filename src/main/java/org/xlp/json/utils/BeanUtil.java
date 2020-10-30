package org.xlp.json.utils;

import org.xlp.javabean.PropertyDescriptor;


/**
 * 简化bean操作工具类
 * 
 * @author 徐龙平
 * 
 * @version 1.0
 */
public class BeanUtil {
	/**
	 * 快速调用写方法
	 * 
	 * @param bean
	 * @param pd
	 * @param value
	 * @throws NullPointerException
	 *             假如参数为null,抛出该异常
	 */
	public static <T> void callSetter(T bean, PropertyDescriptor<T> pd,
			Object value) {
		try {
			pd.executeWriteMethod(bean, value);
		} catch (Exception e) {
			System.out.println("----------调用[" + pd.getFieldName() + "]该字段的写方法失败---------");
		}
	}

	/**
	 * 快速调用读方法
	 * 
	 * @param bean
	 * @param pd
	 * @return
	 * @throws NullPointerException
	 *             假如参数为null,抛出该异常
	 */
	public static <T> Object callGetter(T bean, PropertyDescriptor<T> pd) {
		try {
			return pd.executeReadMethod(bean);
		} catch (Exception e) {
			System.out.println("-----------调用[" + pd.getFieldName() + "]该字段的读方法失败---------");
		}
		return null;
	}

	/**
	 * 用beanClass得到bean对象
	 * 
	 * @param beanClass
	 * @return
	 * @throws NullPointerException
	 *             假如参数为null,抛出该异常
	 * @throws RuntimeException
	 *             假如指定类实例化失败，抛出该异常
	 */
	public static <T> T newInstance(Class<T> beanClass) {
		try {
			return beanClass.newInstance();
		} catch (InstantiationException e) {
			System.out.println("---------[" + beanClass.getName() + "]该类对象实例化失败------------");
			throw new RuntimeException("bean对象实例化失败", e);
		} catch (IllegalAccessException e) {
			System.out.println("---------[" + beanClass.getName() + "]该类对象实例化失败-------------");
			throw new RuntimeException("bean对象实例化失败", e);
		}
	}
}
