package com.github.guanpy.wblib.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 绘画存储-画笔路径(字符串格式，方便储存)
 * Created 2015-7-13 10:36:1
 *
 * @author gpy
 */
public class DrawPenStr {


    /**画笔颜色*/
    private int mColor;
    /**画笔粗细*/
    private float mStrokeWidth;
    /**是否橡皮擦*/
    private boolean mIsEraser;
    /**移动到初始点坐标*/
    private Point mMoveTo;
    /**移动中A集*/
    private List<Point> mQuadToA;
    /**移动中B集*/
    private List<Point> mQuadToB;
    /**移动到终点坐标*/
    private Point mLineTo;
    /**所在界面高距坐标*/
    private Point mOffset;

    /**
     *获得画笔颜色
     *Created 2015-7-21 18:11:48
     *@return 画笔颜色
     *@author gpy
     */
    public int getColor() {
        return mColor;
    }

    /**
     *设置画笔颜色
     *Created 2015-7-21 18:11:48
     *@param color 画笔颜色
     *@author gpy
     */
    public void setColor(int color) {
        this.mColor = color;
    }

    /**
     *获得画笔粗细
     *Created 2015-7-21 18:11:48
     *@return 画笔粗细
     *@author gpy
     */
    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    /**
     *设置画笔粗细
     *Created 2015-7-21 18:11:48
     *@param strokeWidth 画笔粗细
     *@author gpy
     */
    public void setStrokeWidth(float strokeWidth) {
        this.mStrokeWidth = strokeWidth;
    }

    /**
     *获得是否橡皮擦
     *Created 2015-7-21 18:11:48
     *@return 是否橡皮擦
     *@author gpy
     */
    public boolean getIsEraser() {
        return mIsEraser;
    }

    /**
     *设置是否橡皮擦
     *Created 2015-7-21 18:11:48
     *@param isEraser 是否橡皮擦
     *@author gpy
     */
    public void setIsEraser(boolean isEraser) {
        this.mIsEraser = isEraser;
    }

    /**
     *获得移动到初始点坐标
     *Created 2015-7-21 18:11:48
     *@return 移动到初始点坐标
     *@author gpy
     */
    public Point getMoveTo() {
        return mMoveTo;
    }

    /**
     *设置移动到初始点坐标
     *Created 2015-7-21 18:11:48
     *@param moveTo 移动到初始点坐标
     *@author gpy
     */
    public void setMoveTo(Point moveTo) {
        this.mMoveTo = moveTo;
    }

    /**
     *获得移动中A集
     *Created 2015-7-21 18:11:48
     *@return 移动中A集
     *@author gpy
     */
    public List<Point> getQuadToA() {
        if(null==mQuadToA){
            mQuadToA = new ArrayList<Point>();
        }
        return mQuadToA;
    }

    /**
     *设置移动中A集
     *Created 2015-7-21 18:11:48
     *@param quadToA 移动中A集
     *@author gpy
     */
    public void setQuadToA(List<Point> quadToA) {
        this.mQuadToA = quadToA;
    }

    /**
     *获得移动中B集
     *Created 2015-7-21 18:11:48
     *@return 移动中B集
     *@author gpy
     */
    public List<Point> getQuadToB() {
        if(null==mQuadToB){
            mQuadToB = new ArrayList<Point>();
        }
        return mQuadToB;
    }

    /**
     *设置移动中B集
     *Created 2015-7-21 18:11:48
     *@param quadToB 移动中B集
     *@author gpy
     */
    public void setQuadToB(List<Point> quadToB) {
        this.mQuadToB = quadToB;
    }

    /**
     *获得移动到终点坐标
     *Created 2015-7-21 18:11:48
     *@return 移动到终点坐标
     *@author gpy
     */
    public Point getLineTo() {
        return mLineTo;
    }

    /**
     *设置移动到终点坐标
     *Created 2015-7-21 18:11:48
     *@param lineTo 移动到终点坐标
     *@author gpy
     */
    public void setLineTo(Point lineTo) {
        this.mLineTo = lineTo;
    }

    /**
     *获得所在界面高距坐标
     *Created 2015-7-21 18:11:48
     *@return 所在界面高距坐标
     *@author gpy
     */
    public Point getOffset() {
        return mOffset;
    }

    /**
     *设置所在界面高距坐标
     *Created 2015-7-21 18:11:48
     *@param offset 所在界面高距坐标
     *@author gpy
     */
    public void setOffset(Point offset) {
        this.mOffset = offset;
    }


}
