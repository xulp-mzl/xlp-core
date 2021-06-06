package org.xlp.javabean.convert.mapandbean;

import org.xlp.javabean.PropertyDescriptor;
import org.xlp.javabean.annotation.FieldName;
import org.xlp.javabean.processer.ValueProcesser;
import org.xlp.utils.XLPStringUtil;

/**
 * 用MapKey注解进行map与bean相互转换
 * <p>MapKey注解可以使bean的字段名与map的键名称不同
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public class MapBeanConverter<T> extends MapBeanAbstract<T>{
	public MapBeanConverter() {
		super();
	}

	/**
	 * 标志map中是否存储bean字段值为空的条目
	 * @param isContainNull
	 */
	public MapBeanConverter(boolean isContainNull) {
		super(isContainNull);
	}

	/**
	 * @param format
	 * 				字符串日期相互转换格式
	 * @param isContainNull
	 * 				标志map中是否存储bean字段值为空的条目
	 */
	public MapBeanConverter(String format, boolean isContainNull) {
		super(format, isContainNull);
	}

	/**
	 * @param format
	 *            字符串日期相互转换格式
	 */
	public MapBeanConverter(String format) {
		super(format);
	}
	
	/**
	 * @param processer
	 *            值处理器
	 * @param isContainNull
	 *            标志map中是否存储bean字段值为空的条目
	 */
	public MapBeanConverter(ValueProcesser processer, boolean isContainNull) {
		super(processer, isContainNull);
	}

	/**
	 * @param processer
	 *            值处理器
	 */
	public MapBeanConverter(ValueProcesser processer) {
		super(processer);
	}
	
	private String virtualFieldName(PropertyDescriptor<T> pd) {
		String virtualFieldName = null;
		FieldName mapKeyName = pd.getFieldAnnotation(FieldName.class);
		if(mapKeyName != null) {
			String fieldName = XLPStringUtil.emptyTrim(mapKeyName.name()); 
			if (!XLPStringUtil.isEmpty(fieldName)) {
				virtualFieldName = fieldName; 
			}else {
				virtualFieldName = pd.getFieldName(); 
			}
		}
		return virtualFieldName;
	}

	@Override
	protected String virtualReadFieldName(PropertyDescriptor<T> pd) {
		return virtualFieldName(pd);
	}

	@Override
	protected String virtualWriteFieldName(PropertyDescriptor<T> pd) {
		return virtualFieldName(pd);
	}

}
