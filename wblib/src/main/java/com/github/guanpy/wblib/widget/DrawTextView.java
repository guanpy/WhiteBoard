package com.github.guanpy.wblib.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.guanpy.library.EventBus;
import com.github.guanpy.wblib.R;
import com.github.guanpy.wblib.bean.DrawPoint;
import com.github.guanpy.wblib.utils.Events;
import com.github.guanpy.wblib.utils.OperationUtils;


public class DrawTextView extends RelativeLayout implements
        View.OnClickListener {

    /**
     * 显示状态
     */
    public static final int TEXT_VIEW = 1;
    /**
     * 编辑（文字编辑）状态
     */
    public static final int TEXT_EDIT = 2;
    /**
     * 详情（显示删除、编辑按钮）状态
     */
    public static final int TEXT_DETAIL = 3;
    /**
     * 被删除状态
     */
    public static final int TEXT_DELETE = 4;

    /** */
    private View mVOutside;
    /** */
    private RelativeLayout mRlContent;
    /** */
    private RelativeLayout mRlText;
    /** */
    private EditText mEtTextEdit;
    /** */
    private TextView mTvTextEdit;
    /** */
    private Button mBtTextDelete;
    /** */
    private Button mBtTextEdit;

    private Context mContext;

    private CallBackListener mCallBackListener;

    private DrawPoint mDrawPoint;

    private int mWidth;
    /**
     * 特殊字符所需
     */
    private Spannable mSpannable;

    public DrawTextView(Context context
            , DrawPoint drawPoint,
                        CallBackListener callBackListener) {
        super(context);
        init(context, drawPoint, callBackListener);
    }

    private void init(Context context
            , DrawPoint drawPoint,
                      CallBackListener callBackListener) {
        mContext = context;
        mDrawPoint = DrawPoint.copyDrawPoint(drawPoint);
        mCallBackListener = callBackListener;
        Display display = ((Activity) mContext).getWindowManager()
                .getDefaultDisplay();
        mWidth = display.getWidth();
        initUI();
        initEvent();
        switchView(mDrawPoint.getDrawText().getStatus());

    }


    /**
     * 初始化界面控件 <br>
     * Created 2015-8-10 16:55:49
     *
     * @author : gpy
     */
    private void initUI() {
        LayoutInflater.from(mContext).inflate(R.layout.draw_text, this, true);
        mVOutside = (View) findViewById(R.id.v_outside);
        mRlContent = (RelativeLayout) findViewById(R.id.rl_content);
        mRlText = (RelativeLayout) findViewById(R.id.rl_text);
        mEtTextEdit = (EditText) findViewById(R.id.et_text_edit);
        mTvTextEdit = (TextView) findViewById(R.id.tv_text_edit);
        mBtTextDelete = (Button) findViewById(R.id.bt_text_delete);
        mBtTextEdit = (Button) findViewById(R.id.bt_text_edit);
        if (null != mDrawPoint) {
            setText(mDrawPoint.getDrawText().getStr());
        }
        setLayoutParams();

    }

    /**
     * 初始化监听 <br>
     * Created 2015-8-10 16:55:49
     *
     * @author : gpy
     */
    private void initEvent() {
        mVOutside.setOnClickListener(this);
        mRlText.setOnClickListener(this);
        mEtTextEdit.setOnClickListener(this);
        mBtTextDelete.setOnClickListener(this);
        mBtTextEdit.setOnClickListener(this);
        mTvTextEdit.setOnClickListener(this);
        mTvTextEdit.setOnTouchListener(new OnTouchListener() {
            int lastX, lastY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (mDrawPoint.getDrawText().getStatus() == TEXT_DETAIL&& OperationUtils.getInstance().DISABLE) {
                    int ea = event.getAction();
                    switch (ea) {
                        case MotionEvent.ACTION_DOWN:
                            // 获取触摸事件触摸位置的原始X坐标
                            lastX = (int) event.getRawX();
                            lastY = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int dx = (int) event.getRawX() - lastX;
                            int dy = (int) event.getRawY() - lastY;

                            int left = mRlContent.getLeft() + dx;
                            int top = mRlContent.getTop() + dy;
                            int right = mRlContent.getRight() + dx;
                            int bottom = mRlContent.getBottom() + dy;
                            if (left < 0) {
                                left = 0;
                                right = left + mRlContent.getWidth();
                            }
                            if (right > getWidth()) {
                                right = getWidth();
                                left = right - mRlContent.getWidth();
                            }
                            if (top < 0) {
                                top = 0;
                                bottom = top + mRlContent.getHeight();
                            }
                            if (bottom > getHeight()) {
                                bottom = getHeight();
                                top = bottom - mRlContent.getHeight();
                            }
                            mDrawPoint.getDrawText().setX(left);
                            mDrawPoint.getDrawText().setY(top);
                            Log.e("移动", "-" + left + "," + top);
                            mRlContent.layout(left, top, right, bottom);
                            lastX = (int) event.getRawX();
                            lastY = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            if (null != mCallBackListener) {
                                mCallBackListener.onUpdate(mDrawPoint);
                            }
                            break;
                    }
                }

                return false;
            }
        });
    }


    private void setText(String strText) {
        if (!TextUtils.isEmpty(strText)) {
            mEtTextEdit.setText(strText);
            mTvTextEdit.setText(strText);
        }
        mEtTextEdit.setTextColor(mDrawPoint.getDrawText().getColor());
        mTvTextEdit.setTextColor(mDrawPoint.getDrawText().getColor());
        if (mDrawPoint.getDrawText().getIsUnderline()) {
            mTvTextEdit.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            mEtTextEdit.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
        if (mDrawPoint.getDrawText().getIsBold()) {
            mTvTextEdit.getPaint().setFakeBoldText(true);
            mEtTextEdit.getPaint().setFakeBoldText(true);
        }

    }

    private void setLayoutParams() {
        LayoutParams layParamsTxt = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layParamsTxt.leftMargin = (int) mDrawPoint.getDrawText().getX();
        layParamsTxt.topMargin = (int) mDrawPoint.getDrawText().getY();
        mRlContent.setLayoutParams(layParamsTxt);
    }

    public void switchView(int currentStatus) {
        switch (currentStatus) {
            case TEXT_VIEW:
                mVOutside.setVisibility(View.GONE);
                mEtTextEdit.setVisibility(View.GONE);
                mTvTextEdit.setVisibility(View.VISIBLE);
                mRlText.setBackgroundResource(
                        R.color.transparent);
                mBtTextEdit.setVisibility(View.GONE);
                mBtTextDelete.setVisibility(View.GONE);
                break;
            case TEXT_EDIT:
                mVOutside.setBackgroundResource(R.color.white);
                mVOutside.setVisibility(View.VISIBLE);
                mEtTextEdit.setVisibility(View.VISIBLE);
                mTvTextEdit.setVisibility(View.GONE);
                mRlText.setBackgroundResource(R.drawable.draw_text_border);
                mBtTextEdit.setVisibility(View.GONE);
                mEtTextEdit.setSelection(mEtTextEdit.getText().length());
                mBtTextDelete.setVisibility(View.GONE);
                EventBus.postEvent(Events.WHITE_BOARD_TEXT_EDIT);
                showSoftKeyBoard(mEtTextEdit);
                break;
            case TEXT_DETAIL:
                mVOutside.setBackgroundResource(R.color.transparent);
                mVOutside.setVisibility(View.VISIBLE);
                mEtTextEdit.setVisibility(View.GONE);
                mTvTextEdit.setVisibility(View.VISIBLE);
                mRlText.setBackgroundResource(R.drawable.draw_text_border);
                mBtTextEdit.setVisibility(View.VISIBLE);
                mBtTextDelete.setVisibility(View.VISIBLE);
                break;
            case TEXT_DELETE:

                break;
            default:
                break;
        }
        Log.d("gpy","文字宽："+mRlText.getHeight());
        if (mDrawPoint.getDrawText().getStatus() != currentStatus) {
            mDrawPoint.getDrawText().setStatus(currentStatus);
            if (null != mCallBackListener && currentStatus != TEXT_EDIT) {
                mCallBackListener.onUpdate(mDrawPoint);
            }
        }

    }

    /**
     * 文字编辑完成
     *
     * @param isSave 是否保存
     */
    public void afterEdit(boolean isSave) {
        Log.d("gpy", "要保存的文字：" + mEtTextEdit.getText().toString());
        if (isSave) {
            mDrawPoint.getDrawText().setStr(mEtTextEdit.getText().toString());
        }
        switchView(TEXT_VIEW);
        hideSoftInput();
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.v_outside) {
            if (mDrawPoint.getDrawText().getStatus() == TEXT_DETAIL && OperationUtils.getInstance().DISABLE) {
                switchView(TEXT_VIEW);
            }
            hideSoftInput();
        } else if (vId == R.id.tv_text_edit) {
            if (OperationUtils.getInstance().DISABLE) {
                switchView(TEXT_DETAIL);
            }
        } else if (vId == R.id.bt_text_delete) {
            if (OperationUtils.getInstance().DISABLE) {
                switchView(TEXT_DELETE);
            }
        } else if (vId == R.id.bt_text_edit) {
            if (OperationUtils.getInstance().DISABLE) {
                switchView(TEXT_EDIT);
            }
        }
    }

    public interface CallBackListener {
        /**
         * 更新文字属性
         */
        void onUpdate(DrawPoint drawPoint);
    }

    private void showSoftKeyBoard(final EditText et) {
        et.requestFocus();
        et.post(new Runnable() {
            @Override
            public void run() {
                // 弹出输入法
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });
    }

    private void hideSoftInput() {
        if (this == null || mContext == null || mEtTextEdit == null) {
            return;
        }
        // 隐藏输入法
        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mEtTextEdit.getWindowToken(), 0);
    }
}
