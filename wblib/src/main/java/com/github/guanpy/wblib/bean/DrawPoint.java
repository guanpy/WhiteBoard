package com.github.guanpy.wblib.bean;


import com.github.guanpy.wblib.utils.BeanUtil;

public class DrawPoint {

    /**绘画类型*/
    private int mType;

    /**画笔路径*/
    private DrawPenPoint mDrawPen;
    /**文字*/
    private DrawTextPoint mDrawText;
    /**画笔路径（字符形式）*/
    private DrawPenStr mDrawPenStr;

    /**
     *获得画笔路径（字符形式）
     *Created 2015-7-21 17:9:7
     *@return 画笔路径（字符形式）
     *@author gpy
     */
    public DrawPenStr getDrawPenStr() {
        return mDrawPenStr;
    }

    /**
     *设置画笔路径（字符形式）
     *Created 2015-7-21 17:9:7
     *@param DrawPenStr 画笔路径（字符形式）
     *@author gpy
     */
    public void setDrawPenStr(DrawPenStr DrawPenStr) {
        this.mDrawPenStr = DrawPenStr;
    }


    /**
     *获得绘画类型
     *Created 2015-7-13 10:42:14
     *@return 绘画类型
     *@author gpy
     */
    public int getType() {
        return mType;
    }

    /**
     *设置绘画类型
     *Created 2015-7-13 10:42:14
     *@param type 绘画类型
     *@author gpy
     */
    public void setType(int type) {
        this.mType = type;
    }



    /**
     *获得画笔路径
     *Created 2015-7-13 10:42:14
     *@return 画笔路径
     *@author gpy
     */
    public DrawPenPoint getDrawPen() {
        return mDrawPen;
    }

    /**
     *设置画笔路径
     *Created 2015-7-13 10:42:14
     *@param drawPen 画笔路径
     *@author gpy
     */
    public void setDrawPen(DrawPenPoint drawPen) {
        this.mDrawPen = drawPen;
    }

    /**
     *获得文字
     *Created 2015-7-13 10:42:14
     *@return 文字
     *@author gpy
     */
    public DrawTextPoint getDrawText() {
        return mDrawText;
//        if(null==mDrawTexts||mDrawTexts.size()==0){
//          return null;
//        }
//        return mDrawTexts.get(0);
    }


    /**
     *设置文字
     *Created 2015-7-13 10:42:14
     *@param drawText 文字
     *@author gpy
     */
    public void setDrawText(DrawTextPoint drawText) {
        mDrawText = drawText;
//        if(null==drawText){
//            return;
//        }
//        if(null==mDrawTexts){
//            mDrawTexts = new ArrayList<DrawTextPoint>();
//        }
//        mDrawTexts.add(0,drawText);
    }
    /**
     * 拷贝数据，防止引用传递 <br>
     * 只在文字情况下使用
     * Created 2015-8-10 16:55:49
     *
     * @author : gpy
     */
    public static DrawPoint copyDrawPoint(DrawPoint drawPoint) {
        DrawPoint dp = new DrawPoint();
        try {
            dp.setType(drawPoint.getType());
            DrawTextPoint dtp = new DrawTextPoint();
            dtp = (DrawTextPoint) BeanUtil.CopyBeanToBean(drawPoint.getDrawText(), dtp);
            dp.setDrawText(dtp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dp;
    }
}
