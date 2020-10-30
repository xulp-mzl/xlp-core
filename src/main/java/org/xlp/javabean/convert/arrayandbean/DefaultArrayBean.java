package org.xlp.javabean.convert.arrayandbean;

import java.util.ArrayList;
import java.util.List;

import org.xlp.javabean.JavaBeanPropertiesDescriptor;
import org.xlp.javabean.PropertyDescriptor;
import org.xlp.javabean.convert.mapandbean.DefaultMapBean;
import org.xlp.javabean.convert.mapandbean.MapBeanAbstract;
import org.xlp.javabean.processer.ValueProcesser;
import org.xlp.utils.XLPDateUtil;
import org.xlp.utils.collection.XLPCollectionUtil;

/**
 * 数组与bean相互转换实现类
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public class DefaultArrayBean<T> implements ArrayBean<T>{
	private final MapBeanAbstract<T> mapBean;

	public DefaultArrayBean() {
		this(XLPDateUtil.DATE_FORMAT);
	}

	/**
	 * @param format
	 *            字符串日期相互转换格式
	 */
	public DefaultArrayBean(String format) {
		mapBean = new DefaultMapBean<T>(format);
	}

	/**
	 * @param processer
	 *            值处理器
	 */
	public DefaultArrayBean(ValueProcesser processer) {
		mapBean = new DefaultMapBean<T>(processer);
	}
	
	@Override
	public T arrayToBean(Object[] array, Class<T> cs) {
		if (array == null || cs == null) {
			return null;
		}

		PropertyDescriptor<T>[] pds = new JavaBeanPropertiesDescriptor<T>(cs)
				.getPds();
		
		return createBean(array, cs, pds);
	}

	/**
	 * 
	 * @param array
	 * @param cs
	 * @param pds
	 * @return
	 */
	private T createBean(Object[] array, Class<T> cs,
			PropertyDescriptor<T>[] pds) {
		T bean = this.mapBean.newInstance(cs);
		for (int i = 0, len = Math.min(array.length, pds.length); 
				i < len; i++) {
			this.mapBean.callSetter(array[i], bean, pds[i]);
		}
		
		return bean;
	}

	@Override
	public Object[] beanToArray(T bean) {
		if (bean == null) {
			return new Object[]{};
		}
		
		@SuppressWarnings("unchecked")
		PropertyDescriptor<T>[] pds = new JavaBeanPropertiesDescriptor<T>(
				(Class<T>) bean.getClass()).getPds();
		
		return createArray(pds, bean);
	}

	/**
	 * @param pds
	 * @param bean
	 * @return
	 */
	private Object[] createArray(PropertyDescriptor<T>[] pds, T bean) {
		int len = pds.length;
		Object[] values = new Object[len];
		
		for (int i = 0; i < len; i++) {
			values[i] = callGetter(pds[i], bean);
		}
		return values;
	}

	/**
	 * 
	 * @param propertyDescriptor
	 * @param bean
	 * @return
	 */
	private Object callGetter(PropertyDescriptor<T> propertyDescriptor, T bean) {
		return mapBean.callGetter(propertyDescriptor, bean);
	}

	@Override
	public List<T> arrayListToBeanList(List<Object[]> arrays, Class<T> cs) {
		List<T> beanList = new ArrayList<T>();
		if (cs == null || XLPCollectionUtil.isEmpty(arrays)) {
			return beanList;
		}
		
		PropertyDescriptor<T>[] pds = new JavaBeanPropertiesDescriptor<T>(cs)
				.getPds();
		for (Object[] array : arrays) {
			if (array != null) {
				beanList.add(createBean(array, cs, pds));
			}
		}
		
		return beanList;
	}

	@Override
	public List<Object[]> beanListToArrayList(List<T> beaList) {
		List<Object[]> arrayList = new ArrayList<Object[]>();
		if (XLPCollectionUtil.isEmpty(beaList)) {
			return arrayList;
		}
		
		@SuppressWarnings("unchecked")
		PropertyDescriptor<T>[] pds = new JavaBeanPropertiesDescriptor<T>(
				(Class<T>) beaList.get(0).getClass()).getPds();
		
		for (T bean : beaList) {
			arrayList.add(createArray(pds, bean));
		}
		return arrayList;
	}
}
