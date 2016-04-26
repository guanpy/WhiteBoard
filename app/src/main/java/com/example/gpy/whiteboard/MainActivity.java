package com.example.gpy.whiteboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gpy.whiteboard.utils.FileUtil;
import com.example.gpy.whiteboard.utils.StoreUtil;
import com.example.gpy.whiteboard.utils.ToastUtils;
import com.example.gpy.whiteboard.view.WhiteBoardActivity;
import com.example.gpy.whiteboard.view.base.BaseActivity;
import com.github.guanpy.wblib.utils.OperationUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.lv_wb)
    ListView mLv;
    @InjectView(R.id.iv_wb_add)
    ImageView mIvAdd;


    private WbAdapter mWbAdapter;
    ArrayList<String> filenames;
    ArrayList<String> filepaths;
    private long mBackPressedTime;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        loadData();
        initView();
    }

    private void loadData() {
        File folder = new File(StoreUtil.getWbPath());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        final File[] files = folder.listFiles();
        if (files.length > 0) {
            filenames = new ArrayList<String>();
            filepaths = new ArrayList<String>();
            for (File f : files) {
                filenames.add(FileUtil.getFileName(f));
                filepaths.add(f.getAbsolutePath());
            }
        }
//        mWbAdapter.notifyDataSetChanged();

    }

    private void initView() {
        mWbAdapter = new WbAdapter();
        mLv.setAdapter(mWbAdapter);
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperationUtils.getInstance().initDrawPointList();
                navi2Page(WhiteBoardActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        final long mCurrentTime = System.currentTimeMillis();
        if (mCurrentTime - this.mBackPressedTime > 1000) {
            ToastUtils.showToast(this, R.string.app_logout);
            this.mBackPressedTime = mCurrentTime;
            return;
        }
        super.onBackPressed();
        System.exit(0);
    }

    private class WbAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return filenames != null ? filenames.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            WbViewHolder holder = null;
            if (convertView != null) {
                holder = (WbViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.wb_item, null);
                if (convertView != null) {
                    convertView.setTag(
                            holder = new WbViewHolder(convertView)
                    );
                }
                if (holder != null) {
                    holder.nWbName.setText(filenames.get(position));
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            StoreUtil.readWhiteBoardPoints(filepaths.get(position));
                            navi2Page(WhiteBoardActivity.class);
                        }
                    });
                }
            }
            return convertView;
        }
    }

    private final class WbViewHolder {
        final TextView nWbName;

        public WbViewHolder(final View view) {
            this.nWbName = (TextView) view.findViewById(R.id.tv_wb_name);
        }
    }
}
