package com.example.gpy.whiteboard.eventbus.inner.reader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class AnnotationElementsReader {

	private static Field elementsField;
	private static Field nameField;
	private static Method validateValueMethod;

	public static HashMap<String, Object> getElements(Annotation annotation)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		InvocationHandler handler = Proxy.getInvocationHandler(annotation);
		if (elementsField == null) {
			elementsField = handler.getClass().getDeclaredField("elements");
			elementsField.setAccessible(true);
		}
		Object[] annotationMembers = (Object[]) elementsField.get(handler);
		for (Object annotationMember : annotationMembers) {
			if (nameField == null) {
				Class<?> cl = annotationMember.getClass();
				nameField = cl.getDeclaredField("name");
				nameField.setAccessible(true);
				validateValueMethod = cl.getDeclaredMethod("validateValue");
				validateValueMethod.setAccessible(true);
			}
			String name = (String) nameField.get(annotationMember);
			Object val = validateValueMethod.invoke(annotationMember);
			map.put(name, val);
		}
		return map;
	}

}
