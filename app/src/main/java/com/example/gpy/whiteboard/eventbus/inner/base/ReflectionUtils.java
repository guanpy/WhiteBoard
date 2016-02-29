package com.example.gpy.whiteboard.eventbus.inner.base;

import java.util.ArrayList;


public final class ReflectionUtils {

    public static ArrayList<Class<?>> buildClassHierarchy(Class<?> cls) {
        ArrayList<Class<?>> hierarchy = new ArrayList<Class<?>>();
        boolean enteredDroidParts = false;
        do {
            hierarchy.add(0, cls);
            boolean inCorePaths = cls.getName().startsWith("com.nd.up91.core");
            if (enteredDroidParts && !inCorePaths) {
                break;
            } else {
                enteredDroidParts = inCorePaths;
                cls = cls.getSuperclass();
            }
        } while (cls != null);
        return hierarchy;
    }

}
