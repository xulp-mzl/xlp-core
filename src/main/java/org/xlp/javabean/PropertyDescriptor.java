package org.xlp.javabean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.xlp.javabean.utils.MethodNameUtil;

/**
 * JavaBean单个字段属性描述类
 * 
 * @author 徐龙平
 *         <p>
 *         2017-5-22
 *         </p>
 * @version 2.0
 * 
 * 
 */
public class PropertyDescriptor<T> extends FieldDescriptor{

	volatile private Class<T> cs;
	
	/**
	 * 
	 * @param field bean字段
	 * @param cs bean类型
	 */
	public PropertyDescriptor(Field field, Class<T> cs) {
		super(field);
		this.cs = cs;
	}
	
	/**
	 * 得到写方法
	 * 
	 * @return
	 * @throws MethodException 假如获取写方法出错时，抛出该异常
	 * @throws NullPointerException 
	 */
	public Method getWriteMethod() throws MethodException{
		String fieldName = this.getFieldName();
		if (fieldName == null) {
			throw new MethodException("给定字段(" + fieldName + ")的写方法不存在");
		}
		
		Method method;
		Class<?> type = this.getFiledClassType();
		boolean typeIsBoolean = Boolean.TYPE == type || Boolean.class == type;
		String setterName = MethodNameUtil.createSetterMethodName(fieldName, typeIsBoolean);
		try {
			method =  cs.getMethod(setterName, new Class<?>[]{type});
			return method;
		} catch (NoSuchMethodException e) {
			if (typeIsBoolean) {
				setterName = MethodNameUtil.createSetterMethodName(fieldName);
				try {
					method =  cs.getMethod(setterName, new Class<?>[]{type});
					return method;
				} catch (NoSuchMethodException e1) {
					throw new MethodException("该方法" + setterName + "(...)不存在");
				} catch (SecurityException e1) {
					throw new MethodException("该方法" + setterName + "(...)不可访问");
				}
			}
			throw new MethodException("该方法" + setterName + "(...)不存在");
		} catch (SecurityException e) {
			throw new MethodException("该方法" + setterName + "(...)不可访问");
		}
	}
	
	/**
	 * 得到读方法
	 * 
	 * @return
	 * @throws MethodException 假如获取读方法出错时，抛出该异常
	 * @throws NullPointerException 
	 */
	public Method getReadMethod() throws MethodException{
		String fieldName = this.getFieldName();
		if (fieldName == null) {
			throw new MethodException("给定字段(" + fieldName + ")的读方法不存在");
		}
		
		Class<?> type = this.getFiledClassType();
		boolean typeIsBoolean = Boolean.TYPE == type || Boolean.class == type;
		String getterName;
		if (typeIsBoolean) {
			getterName = MethodNameUtil.createIsMethodName(fieldName);
		}else {
			getterName = MethodNameUtil.createGetterMethodName(fieldName);
		}
		Method method;
		try {
			method =  cs.getMethod(getterName, new Class<?>[]{});
			return method;
		} catch (NoSuchMethodException e) {
			if (typeIsBoolean) { 
				getterName = MethodNameUtil.createGetterMethodName(fieldName);
				try {
					method =  cs.getMethod(getterName, new Class<?>[]{});
					return method;
				} catch (NoSuchMethodException e1) {
					throw new MethodException("该方法" + getterName + "()不存在");
				} catch (SecurityException e1) {
					throw new MethodException("该方法" + getterName + "()不可访问");
				}
			}
			throw new MethodException("该方法" + getterName + "()不存在");
		} catch (SecurityException e) {
			throw new MethodException("该方法" + getterName + "()不可访问");
		}
	}
	
	/**
	 * 执行读方法并返回方法结果
	 * 
	 * @param obj 指定执行此对象
	 * @return
	 * @throws MethodException 假如获执行读方法出错时，抛出该异常
	 * @throws NullPointerException 假如参数为null，抛出该异常
	 */
	public Object executeReadMethod(T obj) throws MethodException{
		if (cs == null || obj == null) {
			throw new NullPointerException("参数不能为空");
		}
		
		Method method = this.getReadMethod();
		
		try {
			if (method == null) {
				return null;
			}
			return method.invoke(obj, new Object[]{});
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 执行写方法
	 * 
	 * @param obj 指定执行此对象的方法
	 * @param param 方法参数值
	 * @return
	 * @throws MethodException 假如获执行写方法出错时，抛出该异常
	 * @throws NullPointerException 假如参数为null，抛出该异常
	 */
	public void executeWriteMethod(T obj, Object param) throws MethodException{
		if (cs == null || obj == null) {
			throw new NullPointerException();
		}
		
		Method method = this.getWriteMethod();
		
		try {
			if (method != null) {
				method.invoke(obj, new Object[]{param});
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
