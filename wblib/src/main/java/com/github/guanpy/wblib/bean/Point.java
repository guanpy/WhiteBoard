package com.github.guanpy.wblib.bean;

/**
 * Created by gpy on 2015/8/21.
 */
public class Point {

    /**
     * X坐标
     */
    private float mX;
    /**
     * Y坐标
     */
    private float mY;
    public Point(){
    }
    public Point(float x,float y){
        mX = x;
        mY = y;
    }
    /**
     * 获得X坐标
     * Created 2015-7-21 18:6:32
     *
     * @return X坐标
     * @author gpy
     */
    public float getX() {
        return mX;
    }

    /**
     * 设置X坐标
     * Created 2015-7-21 18:6:32
     *
     * @param x X坐标
     * @author gpy
     */
    public void setX(float x) {
        this.mX = x;
    }

    /**
     * 获得Y坐标
     * Created 2015-7-21 18:6:32
     *
     * @return Y坐标
     * @author gpy
     */
    public float getY() {
        return mY;
    }

    /**
     * 设置Y坐标
     * Created 2015-7-21 18:6:32
     *
     * @param y Y坐标
     * @author gpy
     */
    public void setY(float y) {
        this.mY = y;
    }


}
