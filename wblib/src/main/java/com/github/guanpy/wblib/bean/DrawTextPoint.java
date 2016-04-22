package com.github.guanpy.wblib.bean;

/**
 * 绘画存储-文字
 * Created 2015-7-13 10:36:1
 *
 * @author gpy
 */
public class DrawTextPoint {

    /**
     * 唯一性标识
     */
    private long mId;
    /**
     * 文字x坐标
     */
    private float mX;
    /**
     * 文字y坐标
     */
    private float mY;
    /**
     * 文字
     */
    private String mStr;
    /**
     * 是否有下划线
     */
    private boolean mIsUnderline;
    /**
     * 是否斜体
     */
    private boolean mIsItalics;
    /**
     * 是否粗体
     */
    private boolean mIsBold;

    /**
     * 文字颜色
     */
    private int mColor;
    /**
     * 当前文字状态
     */
    private int mStatus;

    /**
     * 是否显示
     */
    private boolean mIsVisible;

    /**
     * 获得唯一性标识
     * Created 2015-7-13 10:42:14
     *
     * @return 唯一性标识
     * @author gpy
     */
    public long getId() {
        return mId;
    }

    /**
     * 设置唯一性标识
     * Created 2015-7-13 10:42:14
     *
     * @param id 唯一性标识
     * @author gpy
     */
    public void setId(long id) {
        this.mId = id;
    }

    /**
     * 获得文字x坐标
     * Created 2015-7-13 10:34:39
     *
     * @return 文字x坐标
     * @author gpy
     */
    public float getX() {
        return mX;
    }

    /**
     * 设置文字x坐标
     * Created 2015-7-13 10:34:39
     *
     * @param x 文字x坐标
     * @author gpy
     */
    public void setX(float x) {
        this.mX = x;
    }

    /**
     * 获得文字y坐标
     * Created 2015-7-13 10:34:39
     *
     * @return 文字y坐标
     * @author gpy
     */
    public float getY() {
        return mY;
    }

    /**
     * 设置文字y坐标
     * Created 2015-7-13 10:34:39
     *
     * @param y 文字y坐标
     * @author gpy
     */
    public void setY(float y) {
        this.mY = y;
    }

    /**
     * 获得文字
     * Created 2015-7-13 10:34:39
     *
     * @return 文字
     * @author gpy
     */
    public String getStr() {
        return mStr;
    }

    /**
     * 设置文字
     * Created 2015-7-13 10:34:39
     *
     * @param str 文字
     * @author gpy
     */
    public void setStr(String str) {
        this.mStr = str;
    }

    /**
     * 获得是否有下划线
     * Created 2015-7-17 14:57:13
     *
     * @return 是否有下划线
     * @author gpy
     */
    public boolean getIsUnderline() {
        return mIsUnderline;
    }

    /**
     * 设置是否有下划线
     * Created 2015-7-17 14:57:13
     *
     * @param isUnderline 是否有下划线
     * @author gpy
     */
    public void setIsUnderline(boolean isUnderline) {
        this.mIsUnderline = isUnderline;
    }

    /**
     * 获得是否斜体
     * Created 2015-7-17 14:57:13
     *
     * @return 是否斜体
     * @author gpy
     */
    public boolean getIsItalics() {
        return mIsItalics;
    }

    /**
     * 设置是否斜体
     * Created 2015-7-17 14:57:13
     *
     * @param isItalics 是否斜体
     * @author gpy
     */
    public void setIsItalics(boolean isItalics) {
        this.mIsItalics = isItalics;
    }

    /**
     * 获得是否粗体
     * Created 2015-7-17 14:57:13
     *
     * @return 是否粗体
     * @author gpy
     */
    public boolean getIsBold() {
        return mIsBold;
    }

    /**
     * 设置是否粗体
     * Created 2015-7-17 14:57:13
     *
     * @param isBold 是否粗体
     * @author gpy
     */
    public void setIsBold(boolean isBold) {
        this.mIsBold = isBold;
    }

    /**
     * 获得文字颜色
     * Created 2015-7-17 15:40:0
     *
     * @return 文字颜色
     * @author gpy
     */
    public int getColor() {
        return mColor;
    }

    /**
     * 设置文字颜色
     * Created 2015-7-17 15:40:0
     *
     * @param color 文字颜色
     * @author gpy
     */
    public void setColor(int color) {
        this.mColor = color;
    }

    /**
     * 获得当前文字状态
     * Created 2015-7-13 10:34:39
     *
     * @return 当前文字状态
     * @author gpy
     */
    public int getStatus() {
        return mStatus;
    }

    /**
     * 设置当前文字状态
     * Created 2015-7-13 10:34:39
     *
     * @param status 当前文字状态
     * @author gpy
     */
    public void setStatus(int status) {
        this.mStatus = status;
    }

    /**
     * 获得是否显示
     * Created 2015-7-13 11:31:52
     *
     * @return 是否显示
     * @author gpy
     */
    public boolean getIsVisible() {
        return mIsVisible;
    }

    /**
     * 设置是否显示
     * Created 2015-7-13 11:31:52
     *
     * @param isVisible 是否显示
     * @author gpy
     */
    public void setIsVisible(boolean isVisible) {
        this.mIsVisible = isVisible;
    }
}