package com.example.gpy.whiteboard;

import android.os.Bundle;

import com.example.gpy.whiteboard.view.WhiteBoardActivity;
import com.example.gpy.whiteboard.view.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
                    navi2Page(WhiteBoardActivity.class);
    }
}
