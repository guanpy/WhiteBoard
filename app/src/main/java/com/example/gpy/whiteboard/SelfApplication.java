package com.example.gpy.whiteboard;

import android.app.Application;

import com.example.gpy.whiteboard.utils.Config;
import com.example.gpy.whiteboard.utils.SdCardStatus;
import com.github.guanpy.wblib.utils.AppContextUtil;

/**
 * Created by gpy on 2015/8/17.
 */
public class SelfApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtil.init(this);
        SdCardStatus.init(Config.CACHE_DIR);
    }
}
