package com.nms.util.beans;

import java.lang.reflect.Method;

/**
 * This is the utility class for printing the string representation of the bean
 * 
 * @author nshaikh
 * 
 */
public class ToString {

	/**
	 * Returns the string representation of the bean with
	 * name and value.
	 * @param bean
	 * @return
	 */
	public static String toString(Object bean) {
		StringBuffer buffer = new StringBuffer();
		@SuppressWarnings("rawtypes")
		Class objClass = bean.getClass();
		Method[] methods = objClass.getMethods();
		buffer.append(bean.getClass().getName() + "[");
		for (Method method : methods) {
			if (method.getName().startsWith("get")
					&& method.getParameterTypes().length == 0) {
				String name = method.getName();
				buffer.append(name);
				buffer.append("=");
				try {
					buffer.append(method.invoke(bean));
				} catch (Exception exp) {
					exp.printStackTrace();
				}
				buffer.append(" ");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
}
