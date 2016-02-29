package com.example.gpy.whiteboard.eventbus.inner;

import com.example.gpy.whiteboard.eventbus.ann.ReceiveEvents;
import com.example.gpy.whiteboard.eventbus.inner.bus.ReceiveEventsAnn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class AnnBuilder {

    static ReceiveEventsAnn getReceiveEventsAnn(Method m) {
        for (Annotation a : m.getDeclaredAnnotations()) {
            Class<?> at = a.annotationType();
            if (ReceiveEvents.class == at) {
                return new ReceiveEventsAnn((ReceiveEvents) a);
            }
        }
        return null;
    }
}