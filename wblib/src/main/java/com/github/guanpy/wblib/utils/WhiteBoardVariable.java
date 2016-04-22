package com.github.guanpy.wblib.utils;

import com.github.guanpy.wblib.R;

/**
 * 白板属性值汇总
 * Created by gpy on 2015/8/17.
 */
public final class WhiteBoardVariable {
    /**白板工具栏操作*/
    public interface Operation{
        /**画笔-正常*/
        int PEN_NORMAL = 1 ;
        /**画笔-点击*/
        int PEN_CLICK = 2 ;
        /**画笔-展开*/
        int PEN_EXPAND = 3 ;

        /**颜色-正常*/
        int COLOR_NORMAL = 4 ;
        /**颜色-点击*/
        int COLOR_CLICK = 5 ;
        /**颜色-展开*/
        int COLOR_EXPAND = 6 ;
        /**文字-正常*/
        int TEXT_NORMAL = 7 ;
        /**文字-点击*/
        int TEXT_CLICK = 8 ;
        /**文字-展开*/
        int TEXT_EXPAND =9 ;
        /**橡皮擦-正常*/
        int ERASER_NORMAL = 10 ;
        /**橡皮擦-点击*/
        int ERASER_CLICK = 11 ;
        /**橡皮擦-展开*/
        int ERASER_EXPAND = 12 ;
        /**点击外围*/
        int OUTSIDE_CLICK = 13 ;

    }


    /**颜色*/
    public interface  Color{
        /**黑色*/
        int BLACK =  AppContextUtil.getContext().getResources().getColor(R.color.grey_3e);
        /**橙色*/
        int ORANGE =  AppContextUtil.getContext().getResources().getColor(R.color.orange);
        /**粉色*/
        int PINK =  AppContextUtil.getContext().getResources().getColor(R.color.light_red);
        /**紫色*/
        int PURPLE =  AppContextUtil.getContext().getResources().getColor(R.color.primary_purple);
        /**绿色*/
        int GREEN =  AppContextUtil.getContext().getResources().getColor(R.color.green);
    }
    /**画笔大小*/
    public interface  PenSize{
        /**大*/
        int LARRGE = AppContextUtil.dip2px(9);
        /**中*/
        int MIDDLE = AppContextUtil.dip2px(6);
        /**小*/
        int MINI = AppContextUtil.dip2px(4);
    }
    /**橡皮擦大小*/
    public interface  EraserSize{
        /**大*/
        int LARRGE = AppContextUtil.dip2px(16);
        /**中*/
        int MIDDLE = AppContextUtil.dip2px(9);
        /**小*/
        int MINI = AppContextUtil.dip2px(4);
    }
    /**圆环宽度大小*/
    public interface  RingSize{
        /**大*/
        int LARRGE = AppContextUtil.dip2px(3);
        /**小*/
        int MINI = AppContextUtil.dip2px(1);
    }
    /**文字风格*/
    public interface  TextStyle{
        /**设置下划线*/
        int CHANGE_UNDERLINE = 1;
        /**设置斜体*/
         int CHANGE_ITALICS =2;
        /**设置粗体*/
        int CHANGE_BOLD = 3;
    }
}
