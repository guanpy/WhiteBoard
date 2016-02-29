package com.example.gpy.whiteboard.utils;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextUtils;
import android.util.Log;

import com.example.gpy.whiteboard.bean.DrawPenPoint;
import com.example.gpy.whiteboard.bean.DrawPenStr;
import com.example.gpy.whiteboard.bean.DrawPoint;
import com.example.gpy.whiteboard.bean.Point;
import com.example.gpy.whiteboard.bean.WhiteBoardPoint;
import com.example.gpy.whiteboard.bean.WhiteBoardPoints;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 白板操作公共类
 *
 * @author gpy
 */

public class OperationUtils {
    /**
     * 操作类型-画笔
     */
    public static final int DRAW_PEN = 1;
    /**
     * 操作类型-文字
     */
    public static final int DRAW_TEXT = 2;
    /**
     * 操作类型-橡皮擦
     */
    public static final int DRAW_ERASER = 3;
    /**
     * 单例
     */
    private static OperationUtils mOperationStack;
    /**
     * 当前是否禁止白板操作
     */
    public boolean DISABLE = true;
    /**
     * 当前所在白板位置
     */
    public int mCurrentIndex = 0;
    /**
     * 当前按钮点击
     */
    public int mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_CLICK;
    /**
     * 当前按钮点击
     */
    public int mCurrentOPerationColor = WhiteBoardVariable.Operation.COLOR_NORMAL;
    /**
     * 当前按钮点击
     */
    public int mCurrentOPerationText = WhiteBoardVariable.Operation.TEXT_NORMAL;
    /**
     * 当前按钮点击
     */
    public int mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_NORMAL;
    /**
     * 当前绘画类型：笔或者文字等
     */
    public int mCurrentDrawType = DRAW_PEN;
    /**
     * 每次操作的唯一标识
     */
    private long markId = 0;

    /**
     * 当前颜色
     */
    public int mCurrentColor = WhiteBoardVariable.Color.ORANGE;
    /**
     * 当前画笔大小
     */
    public int mCurrentPenSize = WhiteBoardVariable.PenSize.MIDDLE;
    /**
     * 当前橡皮擦大小
     */
    public int mCurrentEraserSize = WhiteBoardVariable.EraserSize.MIDDLE;
    /**
     * 白板操作集
     */
    private WhiteBoardPoints mWhiteBoardPoints;

    /**
     * 单例
     */
    public static OperationUtils getInstance() {
        if (mOperationStack == null) {
            mOperationStack = new OperationUtils();
        }
        return mOperationStack;
    }

    /**
     * 私有实例化
     */
    private OperationUtils() {
    }

    /**
     * 初始化操作集
     *
     * @param id 此处为讨论id
     */
    public void init(String id) {
        DISABLE = true;
        mCurrentIndex = 0;
        mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_NORMAL;
        mCurrentOPerationColor = WhiteBoardVariable.Operation.COLOR_NORMAL;
        mCurrentOPerationText = WhiteBoardVariable.Operation.TEXT_NORMAL;
        mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_NORMAL;
        mCurrentDrawType = DRAW_PEN;
        markId = 0;
        mCurrentColor = WhiteBoardVariable.Color.ORANGE;
        mCurrentPenSize = WhiteBoardVariable.PenSize.MIDDLE;
        mCurrentEraserSize = WhiteBoardVariable.EraserSize.MIDDLE;
        OperationUtils.getInstance().initDrawPointList();
        OperationUtils.getInstance().getWhiteBoardPoints(id);
    }

    /**
     * 返回指定白板的操作集
     */
    public WhiteBoardPoint getDrawPointList(int i) {
        if (null != mWhiteBoardPoints) {
            if (mWhiteBoardPoints.getWhiteBoardPoints().size() <= i) {
                WhiteBoardPoint drawPointList = new WhiteBoardPoint();
                drawPointList.setId(i);
                mWhiteBoardPoints.getWhiteBoardPoints().add(drawPointList);
                return getDrawPointList(i);
            } else {
                return mWhiteBoardPoints.getWhiteBoardPoints().get(i);
            }
        } else {
            mWhiteBoardPoints = new WhiteBoardPoints();
            mWhiteBoardPoints.setWhiteBoardPoints(new ArrayList<WhiteBoardPoint>());
            return getDrawPointList(i);
        }
    }

    /**
     * 返回指定白板的操作集
     */
    public int getDrawPointSize() {
        if (mWhiteBoardPoints != null && null != mWhiteBoardPoints.getWhiteBoardPoints()) {
            return mWhiteBoardPoints.getWhiteBoardPoints().size();
        } else {
            getDrawPointList(mCurrentIndex);
            return getDrawPointSize();
        }
    }

    /**
     * 初始化白板
     */
    public void initDrawPointList() {
        if (mWhiteBoardPoints != null && mWhiteBoardPoints.getWhiteBoardPoints() != null) {
            mWhiteBoardPoints.getWhiteBoardPoints().clear();
        } else {
            getDrawPointList(mCurrentIndex);
        }

    }

    /**
     * 新建白板
     */
    public void newPage() {
        mCurrentIndex = getDrawPointSize();
        getDrawPointList(mCurrentIndex);
    }


    /**
     * 获取每次操作的唯一标识
     */
    public long getNewMarkId() {
        return markId++;
    }

    /**
     * 获取当前白板操作集
     */
    public List<DrawPoint> getSavePoints() {
        return OperationUtils.getInstance().getDrawPointList(OperationUtils.getInstance().mCurrentIndex).getSavePoints();
    }

    /**
     * 获取当前白板删除操作集
     */
    public List<DrawPoint> getDeletePoints() {
        return OperationUtils.getInstance().getDrawPointList(OperationUtils.getInstance().mCurrentIndex).getDeletePoints();
    }

    /**
     * dip转px
     */
    public static int dip2px(float dpValue) {
        final float scale = AppContextUtil.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取保存路径
     */
    public static String getSavePath() {
        return SdCardStatus.getDefaulstCacheDirInSdCard() + "/photos/" + UUID.randomUUID().toString() + ".png";
    }

    /**
     * 白板存储本地
     */
    private final SharedPrefCache<String, String> WhiteBoardPointsCache = new SharedPrefCache<String, String>(
            AppContextUtil.getContext(), "WhiteBoardPointsCache", String.class);

    /**
     * 根据讨论id存储白板集
     */
    public void putWhiteBoardPoints(String discussId) {
        //        String strId = UserRecord.LAST.getUid() + discussId;
        String strId =  discussId;
        if (mWhiteBoardPoints == null || mWhiteBoardPoints.getWhiteBoardPoints() == null || mWhiteBoardPoints.getWhiteBoardPoints().isEmpty()) {
            return;
        }

        mWhiteBoardPoints.setId(strId);
        for (WhiteBoardPoint whiteBoardPoint : mWhiteBoardPoints.getWhiteBoardPoints()) {//清除绘画路径，保留字符串形式就行
            for (DrawPoint drawPoint : whiteBoardPoint.getSavePoints()) {
                if (drawPoint.getType() == OperationUtils.DRAW_PEN) {
                    drawPoint.setDrawPen(null);

                }
            }
        }
        String strJson = GsonUtils.convertString(mWhiteBoardPoints);
        Log.d("gpy", "存储长度：" + strJson.length() + "字符：" + strJson);
        WhiteBoardPointsCache.put(strId, strJson);
        convertWhiteBoardPoints(mWhiteBoardPoints);
//        SuperToast.create(AppContextUtil.getContext(), AppContextUtil.getContext().getString(R.string.white_board_save_sucess), SuperToast.Duration.SHORT).show();
    }

    /**
     * 根据讨论id读取白板集
     */
    public void getWhiteBoardPoints(String discussId) {
//        String strId = UserRecord.LAST.getUid() + discussId;
        String strId =  discussId;
        String strJson = WhiteBoardPointsCache.get(strId);
        if (!TextUtils.isEmpty(strJson)) {
            Log.d("gpy", "存储长度：" + strJson.length() + " 字符：" + strJson);
            mWhiteBoardPoints = GsonUtils.convertObject(strJson, WhiteBoardPoints.class);
            convertWhiteBoardPoints(mWhiteBoardPoints);
        }

    }

    /**
     * 从json字符中将Path、Paint系统类转换出来
     */
    public void convertWhiteBoardPoints(WhiteBoardPoints whiteBoardPoints) {
        for (WhiteBoardPoint whiteBoardPoint : whiteBoardPoints.getWhiteBoardPoints()) {
            whiteBoardPoint.getDeletePoints().clear();
            for (DrawPoint drawPoint : whiteBoardPoint.getSavePoints()) {
                if (drawPoint.getType() == OperationUtils.DRAW_PEN) {
                    DrawPenStr drawPenStr = drawPoint.getDrawPenStr();
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);//是否使用抗锯齿功能,会消耗较大资源，绘制图形速度会变慢
                    paint.setDither(true);// 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
                    paint.setColor(drawPenStr.getColor());//设置绘制的颜色
                    paint.setStyle(Paint.Style.STROKE);//设置画笔的样式
                    paint.setStrokeJoin(Paint.Join.ROUND);//设置绘制时各图形的结合方式，如平滑效果等
                    paint.setStrokeCap(Paint.Cap.ROUND);//当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式    Cap.ROUND,或方形样式Cap.SQUARE
                    paint.setStrokeWidth(drawPenStr.getStrokeWidth());//当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的粗细度
                    if (drawPenStr.getIsEraser()) {
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//擦除模式
                    }
                    Path path = new Path();
                    path.moveTo(drawPenStr.getMoveTo().getX(), drawPenStr.getMoveTo().getY());
                    for (int i = 0; i < drawPenStr.getQuadToA().size(); i++) {
                        Point pointA = drawPenStr.getQuadToA().get(i);
                        Point pointB = drawPenStr.getQuadToB().get(i);
                        path.quadTo(pointA.getX(), pointA.getY(), pointB.getX(), pointB.getY());
                    }
                    path.lineTo(drawPenStr.getLineTo().getX(), drawPenStr.getLineTo().getY());
                    path.offset(drawPenStr.getOffset().getX(), drawPenStr.getOffset().getY());

                    DrawPenPoint drawPenPoint = new DrawPenPoint();
                    drawPenPoint.setPaint(paint);
                    drawPenPoint.setPath(path);
                    drawPoint.setDrawPen(drawPenPoint);
                }

            }
        }
    }
}
