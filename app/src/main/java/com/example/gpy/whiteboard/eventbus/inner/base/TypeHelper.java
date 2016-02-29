package com.example.gpy.whiteboard.eventbus.inner.base;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.Preference;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public final class TypeHelper {

	public static boolean isBoolean(Class<?> cls, boolean orWrapper) {
		return (cls == boolean.class) || (orWrapper && (cls == Boolean.class));
	}

	public static boolean isInteger(Class<?> cls, boolean orWrapper) {
		return (cls == int.class) || (orWrapper && (cls == Integer.class));
	}

	public static boolean isLong(Class<?> cls, boolean orWrapper) {
		return (cls == long.class) || (orWrapper && (cls == Long.class));
	}

	public static boolean isFloat(Class<?> cls, boolean orWrapper) {
		return (cls == float.class) || (orWrapper && (cls == Float.class));
	}

	public static boolean isDouble(Class<?> cls, boolean orWrapper) {
		return (cls == double.class) || (orWrapper && (cls == Double.class));
	}

	public static boolean isByte(Class<?> cls, boolean orWrapper) {
		return (cls == byte.class) || (orWrapper && (cls == Byte.class));
	}

	public static boolean isShort(Class<?> cls, boolean orWrapper) {
		return (cls == short.class) || (orWrapper && (cls == Short.class));
	}

	public static boolean isCharacter(Class<?> cls, boolean orWrapper) {
		return (cls == char.class) || (orWrapper && (cls == Character.class));
	}

	//

	public static boolean isString(Class<?> cls) {
		return cls == String.class;
	}

	public static boolean isEnum(Class<?> cls) {
		return cls.isEnum();
	}

	public static boolean isUUID(Class<?> cls) {
		return UUID.class.isAssignableFrom(cls);
	}

	public static boolean isUri(Class<?> cls) {
		return Uri.class.isAssignableFrom(cls);
	}

	public static boolean isDate(Class<?> cls) {
		return Date.class.isAssignableFrom(cls);
	}

	//

	public static boolean isByteArray(Class<?> cls) {
		return cls == byte[].class;
	}

	public static boolean isArray(Class<?> cls) {
		return cls.isArray();
	}

	public static boolean isCollection(Class<?> cls) {
		return Collection.class.isAssignableFrom(cls);
	}

	//

	public static boolean isBitmap(Class<?> cls) {
		return Bitmap.class.isAssignableFrom(cls);
	}

	public static boolean isDrawable(Class<?> cls) {
		return Drawable.class.isAssignableFrom(cls);
	}

	//

	public static boolean isJSONObject(Class<?> cls) {
		return JSONObject.class.isAssignableFrom(cls);
	}

	public static boolean isJSONArray(Class<?> cls) {
		return JSONArray.class.isAssignableFrom(cls);
	}

	//

	public static boolean isView(Class<?> cls) {
		return View.class.isAssignableFrom(cls);

	}

	public static boolean isPreference(Class<?> cls) {
		return Preference.class.isAssignableFrom(cls);
	}

	protected TypeHelper() {
	}
}
