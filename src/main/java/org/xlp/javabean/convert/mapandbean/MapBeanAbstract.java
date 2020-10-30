package org.xlp.javabean.convert.mapandbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xlp.javabean.JavaBeanPropertiesDescriptor;
import org.xlp.javabean.PropertyDescriptor;
import org.xlp.javabean.processer.ValueProcesser;
import org.xlp.utils.XLPDateUtil;
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
	private final ValueProcesser processer;

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
		this.processer = processer;
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
	 * @param bean
	 * @param pds
	 */
	private T createBean(Map<String, ?> map, Class<T> cs,
			PropertyDescriptor<T>[] pds) {

		T bean = newInstance(cs);

		for (Entry<String, ?> entry : map.entrySet()) {
			if (entry != null) {
				String key = entry.getKey();

				for (int i = 0; i < pds.length; i++) {
					if (key != null
							&& key.equalsIgnoreCase(virtualWriteFieldName(pds[i]))) {
						callSetter(entry.getValue(), bean, pds[i]);
						break;
					}
				}
			}
		}

		return bean;
	}

	/**
	 * 用指定的属性描述器获取其对应的字段名(bean->map是字段名映射)
	 * 
	 * @param pd
	 * @return
	 */
	protected abstract String virtualReadFieldName(PropertyDescriptor<T> pd);
	
	/**
	 * 用指定的属性描述器获取其对应的字段名(map->bean是字段名映射)
	 * 
	 * @param pd
	 * @return
	 */
	protected abstract String virtualWriteFieldName(PropertyDescriptor<T> pd);

	/**
	 * 调用bean的某个属性的写方法
	 * 
	 * @param value
	 * @param bean
	 * @param pd
	 *            属性描述
	 */
	public void callSetter(Object value, T bean, PropertyDescriptor<T> pd) {
		Class<?> fieldType = pd.getFiledClassType();
		// 对给定的值处理成适合bean字段的属性值
		value = processer.processValue(fieldType, value);

		if (fieldType != null && value == null && fieldType.isPrimitive()) {
			value = ValueProcesser.PRIMITIVE_DEFAULTS.get(fieldType);
		}

		try {
			pd.executeWriteMethod(bean, value);
		} catch (Exception e) {

		}
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
	 * @param beaList
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
