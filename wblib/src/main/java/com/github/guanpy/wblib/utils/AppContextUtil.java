package com.github.guanpy.wblib.utils;

import android.content.Context;

/**
 * AppContextUtil
 *
 * @author yangz
 * @version 2014/11/4
 */
public class AppContextUtil {

    private static Context sContext;

    public static void init(Context ctx) {
        sContext = ctx.getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

    public static int getColor(int resId) {
        if (sContext == null) {
            return -1;
        }
        return sContext.getResources().getColor(resId);
    }

    public static String getString(int resId) {
        if (sContext == null) {
            return null;
        }
        return sContext.getResources().getString(resId);
    }

    public static String getString(int resId, Object... objs) {
        if (sContext == null) {
            return null;
        }
        return sContext.getResources().getString(resId, objs);
    }

    /**
     * dip转px
     */
    public static int dip2px(float dpValue) {
        final float scale = AppContextUtil.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
