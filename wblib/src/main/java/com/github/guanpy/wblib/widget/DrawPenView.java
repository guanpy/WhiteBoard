package com.github.guanpy.wblib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.guanpy.library.EventBus;
import com.github.guanpy.wblib.R;
import com.github.guanpy.wblib.bean.DrawPenPoint;
import com.github.guanpy.wblib.bean.DrawPenStr;
import com.github.guanpy.wblib.bean.DrawPoint;
import com.github.guanpy.wblib.bean.Point;
import com.github.guanpy.wblib.utils.Events;
import com.github.guanpy.wblib.utils.OperationUtils;

import java.util.Iterator;


public class DrawPenView extends View {
    /**
     * 用于画线
     */
    private Paint mPaint = null;
    /**
     * 用于画图
     */
    private Paint mBitmapPaint = null;
    /**
     * 画图路径
     */
    private Path mPath = null;
    /**
     * 步骤存储bean
     */
    private DrawPoint mDrawPath = null;
    /**
     * 绘画存储-画笔路径(字符串格式，方便储存)
     */
    private DrawPenStr mDrawPenStr = null;
    /**
     * 画图
     */
    private Bitmap mBottomBitmap = null;
    private int mBottomBitmapDrawHeight = 0;
    float posX, posY;
    private final float TOUCH_TOLERANCE = 4;
    private Context mContext;
    private Canvas mCanvas = null;

    public DrawPenView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        initView(context);
    }

    public DrawPenView(Context context, AttributeSet attr) {
        super(context, attr);
        initView(context);
    }

    public DrawPenView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        initPaint();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    }

    public void init(Paint paint) {
        if (paint == null) {
            initPaint();
        } else {
            mPaint = paint;
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//是否使用抗锯齿功能,会消耗较大资源，绘制图形速度会变慢
        mPaint.setDither(true);// 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        mPaint.setColor(OperationUtils.getInstance().mCurrentColor);//设置绘制的颜色
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔的样式
        mPaint.setStrokeJoin(Paint.Join.ROUND);//设置绘制时各图形的结合方式，如平滑效果等
        mPaint.setStrokeCap(Paint.Cap.ROUND);//当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式    Cap.ROUND,或方形样式Cap.SQUARE
        mPaint.setStrokeWidth(2 * OperationUtils.getInstance().mCurrentPenSize);//当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的粗细度
    }

    /**
     * 初始化画笔
     */
    public void changeEraser() {
        mPaint.setColor(mContext.getResources().getColor(R.color.transparent));
        mPaint.setStrokeWidth(2 * OperationUtils.getInstance().mCurrentEraserSize);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//擦除模式
        postInvalidate();
    }
    /**
     * 修改橡皮擦尺寸
     */
    public void setEraserSize() {
        mPaint.setStrokeWidth(2 * OperationUtils.getInstance().mCurrentEraserSize);//size为圆半径
        postInvalidate();
    }
    /**
     * 修改画笔尺寸
     */
    public void setPenSize() {
        mPaint.setStrokeWidth(2 * OperationUtils.getInstance().mCurrentPenSize);//size为圆半径
        postInvalidate();
    }
    /**
     * 修改画笔颜色
     */
    public void setPenColor() {
        mPaint.setColor(OperationUtils.getInstance().mCurrentColor);
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w>0&&h>0){
            mBottomBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBottomBitmap);
        }

    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawColor(mContext.getResources().getColor(R.color.transparent));
        int nCanvasHeight = canvas.getHeight();
        int nBitmapHeight = mBottomBitmap.getHeight();
        mBottomBitmapDrawHeight = (nCanvasHeight - nBitmapHeight) / 2;
        canvas.drawBitmap(mBottomBitmap, 0, mBottomBitmapDrawHeight, mBitmapPaint);
        if (mPath != null) {
            canvas.drawPath(mPath, mPaint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (OperationUtils.getInstance().DISABLE && (OperationUtils.getInstance().mCurrentDrawType == OperationUtils.DRAW_PEN || OperationUtils.getInstance().mCurrentDrawType == OperationUtils.DRAW_ERASER)) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPath = new Path();
                    mDrawPath = new DrawPoint();
                    mPath.moveTo(x, y);
                    DrawPenPoint dpp = new DrawPenPoint();
                    dpp.setPaint(new Paint(mPaint));
                    dpp.setPath(mPath);
                    //绘画时存储字符形式
                    mDrawPenStr = new DrawPenStr();
                    mDrawPenStr.setColor(mPaint.getColor());
                    mDrawPenStr.setStrokeWidth(mPaint.getStrokeWidth());
                    mDrawPenStr.setMoveTo(new Point(x,y));
                    if(OperationUtils.getInstance().mCurrentDrawType == OperationUtils.DRAW_ERASER){
                        mDrawPenStr.setIsEraser(true);
                    }else{
                        mDrawPenStr.setIsEraser(false);
                    }
                    mDrawPath.setDrawPen(dpp);
                    mDrawPath.setType(OperationUtils.DRAW_PEN);
                    posX = x;
                    posY = y;
                    postInvalidate();

                    break;
                case MotionEvent.ACTION_MOVE:
                    float dx = Math.abs(x - posX);
                    float dy = Math.abs(y - posY);
                    if (dx >= TOUCH_TOLERANCE || dy > TOUCH_TOLERANCE) {
                        mDrawPenStr.getQuadToA().add(new Point(posX,posY));
                        mDrawPenStr.getQuadToB().add(new Point((x + posX) / 2,(y + posY) / 2));
                        mPath.quadTo(posX, posY, (x + posX) / 2, (y + posY) / 2);
                        posX = x;
                        posY = y;
                    }
                    postInvalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    mDrawPenStr.setLineTo(new Point(posX, posY));
                    mPath.lineTo(posX, posY);
                    mDrawPenStr.setOffset(new Point(0, -mBottomBitmapDrawHeight));
                    mPath.offset(0, -mBottomBitmapDrawHeight);
                    mCanvas.drawPath(mPath, mPaint);
                    mDrawPath.setDrawPenStr(mDrawPenStr);
                    OperationUtils.getInstance().getSavePoints().add(mDrawPath);
                    OperationUtils.getInstance().getDeletePoints().clear();
                    EventBus.postEvent(Events.WHITE_BOARD_UNDO_REDO);
                    mPath = null;
                    postInvalidate();
                    break;
            }
        }
        return true;
    }

    public void setPaint(Paint paint) {
        if (paint == null) {
            initPaint();
            postInvalidate();
            return;
        }
        mPaint = paint;
        postInvalidate();

    }


    public void clearImage() {
        OperationUtils.getInstance().getSavePoints().clear();
        OperationUtils.getInstance().getDeletePoints().clear();
        int width = mCanvas.getWidth();
        int height = mCanvas.getHeight();
        mBottomBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBottomBitmap);
        postInvalidate();

    }

    public void undo() {
        showPoints();

    }


    public void redo() {
        showPoints();
    }

    /**
     * 重绘一遍路径
     */
    public void showPoints() {
        int width = mCanvas.getWidth();
        int height = mCanvas.getHeight();
        mBottomBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBottomBitmap);

        Iterator<DrawPoint> iter = OperationUtils.getInstance().getSavePoints().iterator();
        DrawPoint temp;
        while (iter.hasNext()) {
            temp = iter.next();
            if (temp.getType() == OperationUtils.DRAW_PEN) {
                mCanvas.drawPath(temp.getDrawPen().getPath(), temp.getDrawPen().getPaint());
            }

        }
        postInvalidate();
    }
}
