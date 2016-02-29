package com.example.gpy.whiteboard.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * SharedPrefCache
 * User: Twinkle
 * Date: 13-4-7
 */
public class SharedPrefCache<K, V> implements ICache<K, V> {

    private static final String TAG = "SharedPrefCache";
    private SharedPreferences  mSharedPref;
    private Class<? extends V> clazz;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final        Object       sync          = new Object();

    public SharedPrefCache(Context context, String identifier, Class<? extends V> clazz) {
        mSharedPref = context.getSharedPreferences(identifier, 0);
        this.clazz = clazz;
    }

    @Override
    public V put(K key, V value) {
        synchronized (sync) {
            SharedPreferences.Editor editor = getEditor();
            try {
                String content = value == null ? null : OBJECT_MAPPER.writeValueAsString(value);
                editor.putString(key.toString(), content);
                editor.commit();
            } catch (JsonProcessingException e) {
                Log.e(TAG, "put error", e);
            }
            return value;
        }
    }

    @Override
    public V get(K key) {
        synchronized (sync) {
            String value = mSharedPref.getString(key.toString(), null);
            if (value != null) {
                try {
                    return OBJECT_MAPPER.readValue(value, clazz);
                } catch (IOException e) {
                    Log.e(TAG, "readValue error", e);
                }
            }
            return null;
        }
    }

    @Override
    public V remove(K key) {
        synchronized (sync) {
            V value = get(key);
            SharedPreferences.Editor editor = getEditor();
            editor.remove(key.toString());
            editor.commit();
            return value;
        }
    }

    @Override
    public void clear() {
        synchronized (sync) {
            SharedPreferences.Editor editor = getEditor();
            editor.clear();
            editor.commit();
        }
    }

    @Override
    public boolean isOutOfDate(K key, long interval) {
        return false;
    }

    private SharedPreferences.Editor getEditor() {
        return mSharedPref.edit();
    }

}
