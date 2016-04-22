package com.example.gpy.whiteboard.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.github.guanpy.library.EventBus;

import butterknife.ButterKnife;

/**
 * Created by gpy on 2016/2/17.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        ButterKnife.inject(this);
        this.afterCreate(savedInstanceState);

    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    protected void showMessage(CharSequence msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.registerAnnotatedReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.unregisterAnnotatedReceiver(this);
    }

    public void navi2Page(final Class<?> page) {
        this.navi2Page(page, false);
    }

    public void navi2Page(final Class<?> page, final boolean shut) {
        this.navi2Page(page, null, shut);
    }

    public void navi2Page(final Class<?> page, final Bundle data, final boolean shut) {
        final Intent intent = new Intent(this, page);
        if (null != data) {
            intent.putExtras(data);
        }
        this.startActivity(intent);
        if (shut) {
            this.finish();
        }
    }
}
