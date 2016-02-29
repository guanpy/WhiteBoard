package com.example.gpy.whiteboard.eventbus.inner;

import com.example.gpy.whiteboard.eventbus.inner.base.MethodSpec;
import com.example.gpy.whiteboard.eventbus.inner.base.ReflectionUtils;
import com.example.gpy.whiteboard.eventbus.inner.bus.ReceiveEventsAnn;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public final class ClassSpecRegistry {

    // Bus

    @SuppressWarnings("unchecked")
    public static MethodSpec<ReceiveEventsAnn>[] getReceiveEventsSpecs(
            Class<?> cls) {
        MethodSpec<ReceiveEventsAnn>[] specs = RECEIVE_EVENTS_SPECS.get(cls);
        if (specs == null) {
            ArrayList<MethodSpec<ReceiveEventsAnn>> list = new ArrayList<MethodSpec<ReceiveEventsAnn>>();
            for (Class<?> cl : ReflectionUtils.buildClassHierarchy(cls)) {
                for (Method method : cl.getDeclaredMethods()) {
                    ReceiveEventsAnn ann = AnnBuilder.getReceiveEventsAnn(method);
                    if (ann != null) {
                        list.add(new MethodSpec<ReceiveEventsAnn>(method, ann));
                    }
                }
            }
            specs = list.toArray(new MethodSpec[list.size()]);
            RECEIVE_EVENTS_SPECS.put(cls, specs);
        }
        return specs;
    }

    private static final ConcurrentHashMap<Class<?>, MethodSpec<ReceiveEventsAnn>[]> RECEIVE_EVENTS_SPECS = new ConcurrentHashMap<Class<?>, MethodSpec<ReceiveEventsAnn>[]>();

}
