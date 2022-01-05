package org.xlp.javabean.convert.mapandbean;

import org.xlp.javabean.PropertyDescriptor;
import org.xlp.javabean.processer.ValueProcesser;


/**
 * map与bean相互转换
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public class DefaultMapBean<T> extends MapBeanAbstract<T>{

	public DefaultMapBean() {
		super();
	}

	/**
	 * 标志map中是否存储bean字段值为空的条目
	 * @param isContainNull
	 */
	public DefaultMapBean(boolean isContainNull) {
		super(isContainNull);
	}

	/**
	 * @param format
	 * 				字符串日期相互转换格式
	 * @param isContainNull
	 * 				标志map中是否存储bean字段值为空的条目
	 */
	public DefaultMapBean(String format, boolean isContainNull) {
		super(format, isContainNull);
	}

	/**
	 * @param format
	 *            字符串日期相互转换格式
	 */
	public DefaultMapBean(String format) {
		super(format);
	}

	/**
	 * @param processer
	 *            值处理器
	 * @param isContainNull
	 *            标志map中是否存储bean字段值为空的条目
	 */
	public DefaultMapBean(ValueProcesser processer, boolean isContainNull) {
		super(processer, isContainNull);
	}

	/**
	 * @param processer
	 *            值处理器
	 */
	public DefaultMapBean(ValueProcesser processer) {
		super(processer);
	}
	
	@Override
	protected String virtualReadFieldName(PropertyDescriptor<?> pd) {
		return pd.getFieldName();
	}

	@Override
	protected String virtualWriteFieldName(PropertyDescriptor<?> pd) {
		return pd.getFieldName();
	}

	@Override
	protected boolean canUseBeanAnnotation() {
		return false;
	}

}
