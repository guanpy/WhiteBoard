package com.github.guanpy.wblib.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.github.guanpy.library.EventBus;
import com.github.guanpy.wblib.R;
import com.github.guanpy.wblib.bean.DrawPoint;
import com.github.guanpy.wblib.bean.DrawTextPoint;
import com.github.guanpy.wblib.utils.Events;
import com.github.guanpy.wblib.utils.OperationUtils;
import com.github.guanpy.wblib.utils.WhiteBoardVariable;

/**
 * 白板--文字层
 * @author  gpy
 */
public class DrawTextLayout extends FrameLayout {


    private Context mContext;


    private static final int MARGIN_RIGHT = 100;
    private static final int MARGIN_BOTTOM = 75;

    public DrawTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public DrawTextLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;

    }

    public void init(Activity activity) {
        LayoutParams params = (LayoutParams) this.getLayoutParams();
        if (activity.getWindowManager() != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            params.width = display.getWidth();
            params.height = display.getWidth();
        }
        this.setLayoutParams(params);
        this.setBackgroundColor(getResources().getColor(R.color.transparent));
        showPoints();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (OperationUtils.getInstance().mCurrentDrawType == OperationUtils.DRAW_TEXT&&OperationUtils.getInstance().DISABLE) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    float moveX =  event.getX();
                    float moveY =  event.getY();
                    if(getHeight()-moveY<dip2px(MARGIN_BOTTOM)){
                        moveY = moveY - dip2px(MARGIN_BOTTOM);
                    }
                    if(getWidth()-moveX<dip2px(MARGIN_RIGHT)){
                        moveX = moveX - dip2px(MARGIN_RIGHT);
                    }
                    Log.d("添加文字", "-" + moveX + "," + moveY);
                    DrawTextPoint ip = new DrawTextPoint();
                    ip.setX(moveX);
                    ip.setY(moveY);
                    ip.setColor(OperationUtils.getInstance().mCurrentColor);
                    ip.setStatus(DrawTextView.TEXT_EDIT);
                    ip.setIsVisible(true);
                    ip.setId(OperationUtils.getInstance().getNewMarkId());

                    DrawPoint drawPoint = new DrawPoint();
                    drawPoint.setType(OperationUtils.DRAW_TEXT);
                    drawPoint.setDrawText(ip);
//                    OperationUtils.getInstance().getSavePoints().add(drawPoint);
                    showPoints();
                    showNewPoint(drawPoint);
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    public void showPoints() {
        int size = OperationUtils.getInstance().getSavePoints().size();
        Log.d("gpy", "显示文字列表--"+size);
        this.removeAllViews();
        if(size==0){
            return;
        }
        for(int i = 0;i<size;i++){
            DrawPoint dp =  OperationUtils.getInstance().getSavePoints().get(i);
            if(dp.getType() == OperationUtils.DRAW_TEXT&&dp.getDrawText().getIsVisible()&&dp.getDrawText().getStatus()!=DrawTextView.TEXT_DELETE){
                final DrawTextView dw = new DrawTextView(mContext,
                        dp, mCallBackListener);
                dw.setTag(i);
                this.addView(dw);
//                }
            }
        }
    }
    /**
     * 文字编辑之后
     */
    public  void afterEdit(boolean isSave){
        ((DrawTextView)getChildAt(getChildCount()-1)).afterEdit(isSave);
    }

    private void showNewPoint(DrawPoint dp) {
        Log.d("gpy", "显示新建文字");
            if(dp.getType() == OperationUtils.DRAW_TEXT&&dp.getDrawText().getIsVisible()&&dp.getDrawText().getStatus()!=DrawTextView.TEXT_DELETE){
                final DrawTextView dw = new DrawTextView(mContext,
                        dp, mCallBackListener);
                this.addView(dw);
            }
    }

    private DrawTextView.CallBackListener mCallBackListener = new  DrawTextView.CallBackListener() {

        @Override
        public void onUpdate(DrawPoint drawPoint) {
            updatePoint(drawPoint);
            showPoints();
        }

    };

    private void updatePoint(DrawPoint drawPoint) {
        Log.d("修改标注", "-" + drawPoint.getDrawText().getX() + "," + drawPoint.getDrawText().getY());
        int size = OperationUtils.getInstance().getSavePoints().size();
        for (int i = size - 1; i >= 0; i--) {
            DrawPoint temp = OperationUtils.getInstance().getSavePoints().get(i);
            if (temp.getType()== OperationUtils.DRAW_TEXT&&temp.getDrawText().getId() == drawPoint.getDrawText().getId()) {//如果文字组件是之前已存在，则隐藏之前的
                OperationUtils.getInstance().getSavePoints().get(i).getDrawText().setIsVisible(false);
                break;
            }
        }
        if(!TextUtils.isEmpty(drawPoint.getDrawText().getStr())){
            OperationUtils.getInstance().getSavePoints().add(drawPoint);
            EventBus.postEvent(Events.WHITE_BOARD_UNDO_REDO);
        }
        OperationUtils.getInstance().getDeletePoints().clear();


    }
    /**设置文字风格：下划线、粗体、斜体等*/
    public void setTextStyle(int changeStyle){
        int size = OperationUtils.getInstance().getSavePoints().size();
        if(size==0){
            return;
        }
        DrawPoint dp = OperationUtils.getInstance().getSavePoints().get(size - 1);
        if(dp.getType()== OperationUtils.DRAW_TEXT){
            DrawPoint temp = DrawPoint.copyDrawPoint(dp);
            switch (changeStyle){
                case WhiteBoardVariable.TextStyle.CHANGE_UNDERLINE:
                    temp.getDrawText().setIsUnderline(!temp.getDrawText().getIsUnderline());
                    break;
                case  WhiteBoardVariable.TextStyle.CHANGE_ITALICS:
                    temp.getDrawText().setIsItalics(!temp.getDrawText().getIsItalics());
                    break;
                case  WhiteBoardVariable.TextStyle.CHANGE_BOLD:
                    temp.getDrawText().setIsBold(!temp.getDrawText().getIsBold());
                    break;
            }

            updatePoint(temp);
            showPoints();
        }
    }
    /**设置文字颜色*/
    public void setTextColor(){
        int size = OperationUtils.getInstance().getSavePoints().size();
        if(size==0){
            return;
        }
        DrawPoint dp = OperationUtils.getInstance().getSavePoints().get(size-1);
        if(dp.getType()== OperationUtils.DRAW_TEXT&&dp.getDrawText().getStatus()==DrawTextView.TEXT_DETAIL){
            DrawPoint temp = DrawPoint.copyDrawPoint(dp);
            temp.getDrawText().setColor(OperationUtils.getInstance().mCurrentColor);
            updatePoint(temp);
            showPoints();
        }
    }
    /**撤销*/
    public void undo(){
        DrawPoint drawPoint = OperationUtils.getInstance().getDeletePoints().get(OperationUtils.getInstance().getDeletePoints().size()-1);
        int size = OperationUtils.getInstance().getSavePoints().size();
        if(size>0) {
            for (int i = size - 1; i >= 0; i--) {
                DrawPoint temp = OperationUtils.getInstance().getSavePoints().get(i);
                if (temp.getType() == OperationUtils.DRAW_TEXT && temp.getDrawText().getId() == drawPoint.getDrawText().getId()) {//如果文字组件是之前已存在，则显示最近的
                    OperationUtils.getInstance().getSavePoints().get(i).getDrawText().setIsVisible(true);
                    break;
                }
            }
        }
        showPoints();
    }
    /**重做*/
    public void redo(){
        int size = OperationUtils.getInstance().getSavePoints().size();
        DrawPoint drawPoint = OperationUtils.getInstance().getSavePoints().get(size - 1);
        if(size>1) {
            for (int i = size - 2; i >= 0; i--) {
                DrawPoint temp = OperationUtils.getInstance().getSavePoints().get(i);
                if (temp.getType() == OperationUtils.DRAW_TEXT && temp.getDrawText().getId() == drawPoint.getDrawText().getId()) {//如果文字组件是之前已存在，则隐藏之前的
                    OperationUtils.getInstance().getSavePoints().get(i).getDrawText().setIsVisible(false);
                    break;
                }
            }
        }
        showPoints();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
