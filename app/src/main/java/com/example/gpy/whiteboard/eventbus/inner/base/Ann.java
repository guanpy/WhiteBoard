package com.example.gpy.whiteboard.eventbus.inner.base;

import android.util.Log;

import com.example.gpy.whiteboard.eventbus.inner.reader.AnnotationElementsReader;

import java.lang.annotation.Annotation;
import java.util.HashMap;

public abstract class Ann<T extends Annotation> {

    private static final   String TAG  = "Ann";
    protected static final String NAME = "name";

    private static boolean hackSuccess = true;

    private HashMap<String, Object> elements;

    public Ann(T annotation) {
        if (hackSuccess) {
            try {
                elements = AnnotationElementsReader.getElements(annotation);
            } catch (Exception e) {
                Log.wtf(TAG, e);
                hackSuccess = false;
            }
        }
    }

    protected final boolean hackSuccess() {
        return hackSuccess;
    }

    protected final Object getElement(String name) {
        return elements.get(name);
    }

    protected final void cleanup() {
        elements = null;
    }

}