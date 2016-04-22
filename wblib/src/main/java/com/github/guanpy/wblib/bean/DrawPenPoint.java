package com.github.guanpy.wblib.bean;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * 绘画存储-画笔路径
 * Created 2015-7-13 10:36:1
 *
 * @author gpy
 */
public class DrawPenPoint {

    /**
     * 绘画路径
     */
    private Path mPath;
    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 获得绘画路径
     * Created 2015-7-13 10:36:1
     *
     * @return 绘画路径
     * @author gpy
     */
    public Path getPath() {
        return mPath;
    }

    /**
     * 设置绘画路径
     * Created 2015-7-13 10:36:1
     *
     * @param path 绘画路径
     * @author gpy
     */
    public void setPath(Path path) {
        this.mPath = path;
    }

    /**
     * 获得画笔
     * Created 2015-7-13 10:36:1
     *
     * @return 画笔
     * @author gpy
     */
    public Paint getPaint() {
        return mPaint;
    }

    /**
     * 设置画笔
     * Created 2015-7-13 10:36:1
     *
     * @param paint 画笔
     * @author gpy
     */
    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

}
