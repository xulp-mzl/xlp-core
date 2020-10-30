package org.xlp.javabean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.xlp.utils.XLPListArrayUtil;

/**
 * JavaBean所有属性描述
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 */
public class JavaBeanPropertiesDescriptor<T> {
	// 属性描述数组
	private PropertyDescriptor<T>[] pds;
	
	//所有字段
	private Field[] fields;

	// javabean类型
	private Class<T> cs;
	
	/**
	 * 标志是否深度查找给定class的字段对象， true：是，false：否
	 */
	private boolean isDepth = true;

	/**
	 * @param cs
	 *            javabean对象类型
	 * @throws NullPointerException
	 *             假如参数为null，抛出该异常
	 */
	public JavaBeanPropertiesDescriptor(Class<T> cs) {
		this(cs, true);
	}
	
	/**
	 * @param cs
	 *            javabean对象类型
	 * @param isDepth
	 * 			   标志是否深度查找给定class的字段对象， true：是，false：否
	 * @throws NullPointerException
	 *             假如参数为null，抛出该异常
	 */
	public JavaBeanPropertiesDescriptor(Class<T> cs, boolean isDepth) {
		if (cs == null) {
			throw new NullPointerException("javabean类型为空");
		}
		this.cs = cs;
		this.isDepth = isDepth;
		initFields();
		initPds();
	}

	private void initFields() {
		List<Field[]> fieldList = new ArrayList<Field[]>();
		if (isDepth) {
			Class<?> supperClass = cs;
			while(supperClass != Object.class){
				fieldList.add(supperClass.getDeclaredFields());
				supperClass = supperClass.getSuperclass();
			}
		}else {
			fieldList.add(cs.getDeclaredFields());
		}

		int fieldCount = 0;
		for (Field[] fields : fieldList) {
			fieldCount += fields.length;
		}
		int i = 0;
		this.fields = new Field[fieldCount];
		for (Field[] fields : fieldList) {
			for (Field field : fields) {
				this.fields[i] = field;
				i++;
			}
		}
	}
	
	/**
	 * 获取指定类的所有字段，包括父类的
	 * 
	 * @return
	 */
	public Field[] getFields(){
		return fields;
	}

	/**
	 * 获取JavaBean属性描述数组
	 * 
	 * @return
	 */
	public PropertyDescriptor<T>[] getPds() {
		return pds;
	}

	/**
	 * 初始化属性描述数组
	 */
	@SuppressWarnings("unchecked")
	private void initPds() {
		Field[] fields = getFields();
		int len = fields.length;
		this.pds = new PropertyDescriptor[len];
		for (int i = 0; i < len; i++) {
			pds[i] = new PropertyDescriptor<T>(fields[i], cs);
		}
	}

	/**
	 * 得到所有字段名称
	 * 
	 * @return
	 */
	public String[] getAllFieldNames() {
		Field[] fields = getFields();
		int len = fields.length;
		String[] fieldNames = new String[len];

		for (int i = 0; i < len; i++) {
			fieldNames[i] = fields[i].getName();
		}

		return fieldNames;
	}

	/**
	 * 得到带有指定注解字段的所有描述
	 * 
	 * @param annotationClass
	 * @return 从不返回null，假如传入的参数为null，则返回大小为零的数组
	 */
	@SuppressWarnings({ "all" })
	public <A extends Annotation> PropertyDescriptor<T>[] getPdsWithAnnotation(
			Class<A> annotationClass) {
		List<PropertyDescriptor> propertyDescriptorList = new ArrayList<PropertyDescriptor>();
		if (annotationClass != null) {
			PropertyDescriptor<T>[] pds = getPds();
			int len = pds.length;
			for (int i = 0; i < len; i++) {
				A anno = pds[i].getFieldAnnotation(annotationClass);
				if (anno != null) {
					propertyDescriptorList.add(pds[i]);
				}
			}
		}

		return XLPListArrayUtil.listToArray(propertyDescriptorList,
				PropertyDescriptor.class);
	}

	/**
	 * 得到指定字段名的属性描述器
	 * 
	 * @param fieldName
	 *            字段名
	 * @return 假如未找到，返回null
	 */
	public PropertyDescriptor<T> getDescriptor(String fieldName) {
		for (PropertyDescriptor<T> pd : pds) {
			if (pd.getFieldName().equals(fieldName))
				return pd;
		}
		return null;
	}
}
