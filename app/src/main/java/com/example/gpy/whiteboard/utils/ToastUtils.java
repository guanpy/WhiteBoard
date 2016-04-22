package com.example.gpy.whiteboard.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by gpy on 2016/4/11.
 */
public class ToastUtils {

    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int intStr) {
        Toast.makeText(context, intStr, Toast.LENGTH_SHORT).show();
    }
}
