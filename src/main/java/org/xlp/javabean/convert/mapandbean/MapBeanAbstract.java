package org.xlp.javabean.convert.mapandbean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xlp.javabean.JavaBeanPropertiesDescriptor;
import org.xlp.javabean.MethodException;
import org.xlp.javabean.PropertyDescriptor;
import org.xlp.javabean.annotation.Bean;
import org.xlp.javabean.annotation.Formatter;
import org.xlp.javabean.config.DateFormatConfig;
import org.xlp.javabean.processer.ValueProcesser;
import org.xlp.utils.XLPDateUtil;
import org.xlp.utils.XLPFormatterUtil;
import org.xlp.utils.XLPOutputInfoUtil;
import org.xlp.utils.XLPPackingTypeUtil;
import org.xlp.utils.XLPStringUtil;
import org.xlp.utils.collection.XLPCollectionUtil;

/**
 * map与bean相互转换抽象类
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public abstract class MapBeanAbstract<T> implements MapBean<T> {
	// 标志map中是否存储bean字段值为空的条目
	private boolean isContainNull = false;
	// map值处理器
	private ValueProcesser processer = new MapValueProcesser();

	public MapBeanAbstract() {
		this(XLPDateUtil.DATE_FORMAT);
	}

	/**
	 * @param format
	 *            字符串日期相互转换格式
	 */
	public MapBeanAbstract(String format) {
		this(format, false);
	}

	/**
	 * 标志map中是否存储bean字段值为空的条目
	 * 
	 * @param isContainNull
	 */
	public MapBeanAbstract(boolean isContainNull) {
		this(XLPDateUtil.DATE_FORMAT, isContainNull);
	}

	/**
	 * @param format
	 *            字符串日期相互转换格式
	 * @param isContainNull
	 *            标志map中是否存储bean字段值为空的条目
	 */
	public MapBeanAbstract(String format, boolean isContainNull) {
		this.processer = new MapValueProcesser(format);
		this.isContainNull = isContainNull;
	}

	/**
	 * @param processer
	 *            值处理器
	 * @param isContainNull
	 *            标志map中是否存储bean字段值为空的条目
	 */
	public MapBeanAbstract(ValueProcesser processer, boolean isContainNull) {
		if (processer != null) {
			this.processer = processer;
		}
		this.isContainNull = isContainNull;
	}

	/**
	 * @param processer
	 *            值处理器
	 */
	public MapBeanAbstract(ValueProcesser processer) {
		this(processer, false);
	}
	
	/**
	 * 创建一个新的bean对象
	 */
	public T newInstance(Class<T> cs) {
		if (cs != null) {
			try {
				return cs.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	public boolean isContainNull() {
		return isContainNull;
	}

	/**
	 * 标志map中是否存储bean字段值为空的条目
	 * 
	 * @param isContainNull
	 */
	public void setContainNull(boolean isContainNull) {
		this.isContainNull = isContainNull;
	}

	/**
	 * 用map创建一个指定类型的bean对象
	 * 
	 * @param map
	 *            key存储的字段名，value存储的是值
	 * @param cs
	 *            bean类型
	 * @return
	 */
	@Override
	public T mapToBean(Map<String, ?> map, Class<T> cs) {
		if (map == null || cs == null) {
			return null;
		}

		PropertyDescriptor<T>[] pds = new JavaBeanPropertiesDescriptor<T>(cs)
				.getPds();

		return createBean(map, cs, pds);
	}

	/**
	 * 用给定的map->bean
	 * 
	 * @param map
	 * @param cs
	 * @param pds
	 */
	private T createBean(Map<String, ?> map, Class<T> cs,
			PropertyDescriptor<T>[] pds) {
		if (canUseBeanAnnotation() && cs.getAnnotation(Bean.class) == null) { 
			throw new RuntimeException("要实例化的类没有@Bean注解，创建对象失败！");
		}

		T bean = newInstance(cs);

		for (Entry<String, ?> entry : map.entrySet()) {
			String key;
			if (entry != null && !XLPStringUtil.isEmpty(key = entry.getKey())) { 
				try {
					_createBean(key, bean, pds, entry.getValue());
				} catch (Exception e) {
					XLPOutputInfoUtil.println("值设置异常，" + e.getMessage());
				}
			}
		}

		return bean;
	}

	/**
	 * 深度创建bean
	 * 
	 * @param fieldName
	 * @param bean
	 * @param pds
	 * @param value
	 * @throws MethodException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void _createBean(String fieldName, Object bean, PropertyDescriptor<?>[] pds, Object value) throws Exception {
		int dotIndex = fieldName.indexOf(".");
		PropertyDescriptor<Object> pd = null;
		for (int i = 0; i < pds.length; i++) {
			if (fieldName.equalsIgnoreCase(virtualWriteFieldName(pds[i]))) {
				pd = (PropertyDescriptor<Object>) pds[i];
				break;
			}
		}
		
		if (pd != null) {
			_callSetter(value, bean, (PropertyDescriptor<Object>) pd);
		} else if (dotIndex >= 0) {
			String prefixName = fieldName.substring(0, dotIndex); 
			for (int i = 0; i < pds.length; i++) {
				if (prefixName.equalsIgnoreCase(virtualWriteFieldName(pds[i]))) {
					pd = (PropertyDescriptor<Object>) pds[i];
					break;
				}
			}
			if (pd != null) {
				Object _bean = pd.executeReadMethod(bean);
				Class<?> _beanClass = pd.getFiledClassType();
				//判断是否需要创建bean
				if (canUseBeanAnnotation() && _beanClass.getAnnotation(Bean.class) == null) {
					return;
				}
				if (_bean == null ) {
					_bean = _beanClass.newInstance();
					pd.executeWriteMethod(bean, _bean); 
				}
				pds = new JavaBeanPropertiesDescriptor(_beanClass).getPds();
				_createBean(fieldName.substring(dotIndex + 1), _bean, pds, value);
			}
		} 
	}

	/**
	 * 用指定的属性描述器获取其对应的字段名(bean->map是字段名映射)
	 * 
	 * @param pd
	 * @return
	 */
	protected abstract String virtualReadFieldName(PropertyDescriptor<?> pd);
	
	/**
	 * 用指定的属性描述器获取其对应的字段名(map->bean是字段名映射)
	 * 
	 * @param pd
	 * @return
	 */
	protected abstract String virtualWriteFieldName(PropertyDescriptor<?> pd);
	
	/**
	 * 标记是否用{@link Bean}注解判断是否是JavaBean
	 * 
	 * @return
	 */
	protected abstract boolean canUseBeanAnnotation();

	/**
	 * 调用bean的某个属性的写方法
	 * 
	 * @param value
	 * @param bean
	 * @param pd
	 *            属性描述
	 */
	@SuppressWarnings("unchecked")
	public void callSetter(Object value, T bean, PropertyDescriptor<T> pd) {
		try {
			_callSetter(value, bean, (PropertyDescriptor<Object>) pd);
		} catch (MethodException e) {
			XLPOutputInfoUtil.println(pd.getFieldName() + "的值设置异常，" + e.getMessage());
		} 
	}
	
	/**
	 * 调用bean的某个属性的写方法
	 * 
	 * @param value
	 * @param bean
	 * @param pd
	 *            属性描述
	 * @throws MethodException 
	 */
	private void _callSetter(Object value, Object bean, PropertyDescriptor<Object> pd) throws MethodException {
		Class<?> fieldType = pd.getFiledClassType();
		if (value != null){
			// 对给定的值处理成适合bean字段的属性值
			Formatter formatter = pd.getFieldAnnotation(Formatter.class);
			//当字段类型不是数组类型时而value的值是数组时，处理成适合的值
			if (!fieldType.isArray() && value.getClass().isArray()) {
				int length = Array.getLength(value);//用反射获取数组的长度
				value = length > 0 ? Array.get(value, 0) : null;//用反射获取数组中的元素;
			}
			String format;
			try{
				if (value != null && formatter != null
						&& !XLPStringUtil.isEmpty(format = formatter.formatter())) {
					if ((XLPPackingTypeUtil.isNumberType(fieldType) || fieldType.isAssignableFrom(Number.class))
							&& CharSequence.class.isAssignableFrom(value.getClass())){
						value = XLPFormatterUtil.parse(format, (CharSequence) value, fieldType);
					} else {
						DateFormatConfig dateFormatConfig = new DateFormatConfig(format, format, format);
						ValueProcesser processer = new MapValueProcesser(dateFormatConfig);
						value = processer.processValue(fieldType, value);
					}
				} else {
					value = processer.processValue(fieldType, value);
				}
			} catch (Exception e) {
				XLPOutputInfoUtil.println(pd.getFieldName() + "的值转换异常，" + e.getMessage());
			}
		}
		if (fieldType != null && value == null && fieldType.isPrimitive()) {
			value = ValueProcesser.PRIMITIVE_DEFAULTS.get(fieldType);
		}

		pd.executeWriteMethod(bean, value);
	}

	/**
	 * 把bean转换成Map
	 * 
	 * @param bean
	 * @return 假如参数为null，返回大小为0的hashmap集合
	 */
	@Override
	public Map<String, Object> beanToMap(T bean) {
		if (bean == null) {
			return new HashMap<String, Object>();
		}

		@SuppressWarnings("unchecked")
		JavaBeanPropertiesDescriptor<T> jbd = new JavaBeanPropertiesDescriptor<T>(
				(Class<T>) bean.getClass());

		PropertyDescriptor<T>[] pds = jbd.getPds();

		return createMap(pds, bean);
	}

	/**
	 * 创建一个新的Map集合
	 * 
	 * @param pds
	 * @param bean
	 * @return
	 */
	private Map<String, Object> createMap(final PropertyDescriptor<T>[] pds,
			T bean) {
		Map<String, Object> map = new HashMap<String, Object>();

		for (int i = 0, len = pds.length; i < len; i++) {
			Object value = callGetter(pds[i], bean);
			if (!isContainNull && value == null) {
				continue;
			}

			String vfn = virtualReadFieldName(pds[i]);
			if (vfn != null)
				map.put(vfn, value);
		}
		return map;
	}

	/**
	 * 调用读方法
	 * 
	 * @param bean
	 * @return
	 */
	public Object callGetter(PropertyDescriptor<T> propertyDescriptor, T bean) {
		try {
			return propertyDescriptor.executeReadMethod(bean);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 用List<Map<String, Object>>创建一个指定类型的List<T>对象
	 * 
	 * @param mapList
	 *            key存储的字段名，value存储的是值
	 * @param cs
	 *            bean类型
	 * @return
	 */
	@Override
	public List<T> mapListToBeanList(List<Map<String, ?>> mapList,
			Class<T> cs) {
		List<T> beanList = new ArrayList<T>();
		if (cs == null || XLPCollectionUtil.isEmpty(mapList)) {
			return beanList;
		}

		PropertyDescriptor<T>[] pds = new JavaBeanPropertiesDescriptor<T>(cs)
				.getPds();

		for (Map<String, ?> map : mapList) {
			if (map != null) {
				beanList.add(this.createBean(map, cs, pds));
			}
		}
		return beanList;
	}

	/**
	 * 把List<T> beaList转换成List<Map<String, Object>> Maps
	 * 
	 * @param beanList
	 * @return
	 */
	@Override
	public List<Map<String, ?>> beanListToMapList(List<T> beanList) {
		List<Map<String, ?>> mapList = new ArrayList<Map<String, ?>>();
		if (XLPCollectionUtil.isEmpty(beanList)) {
			return mapList;
		}

		@SuppressWarnings("unchecked")
		JavaBeanPropertiesDescriptor<T> jbd = new JavaBeanPropertiesDescriptor<T>(
				(Class<T>) beanList.get(0).getClass());

		PropertyDescriptor<T>[] pds = jbd.getPds();

		for (T bean : beanList) {
			mapList.add(createMap(pds, bean));
		}
		return mapList;
	}

}
