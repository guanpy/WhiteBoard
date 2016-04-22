package com.github.guanpy.wblib.bean;

import java.util.List;

/**
 * 画板合集
 * Created by gpy on 2015/8/21.
 */
public class WhiteBoardPoints {

    /**唯一性id*/
    private String mId;
    /**画板合集*/
    private List<WhiteBoardPoint> mWhiteBoardPoints;

    /**
     *获得唯一性id
     *Created 2015-7-21 15:19:17
     *@return 唯一性id
     *@author gpy
     */
    public String getId() {
        return mId;
    }

    /**
     *设置唯一性id
     *Created 2015-7-21 15:19:17
     *@param id 唯一性id
     *@author gpy
     */
    public void setId(String id) {
        this.mId = id;
    }

    /**
     *获得画板合集
     *Created 2015-7-21 15:19:17
     *@return 画板合集
     *@author gpy
     */
    public List<WhiteBoardPoint> getWhiteBoardPoints() {
        return mWhiteBoardPoints;
    }

    /**
     *设置画板合集
     *Created 2015-7-21 15:19:17
     *@param whiteBoardPoints 画板合集
     *@author gpy
     */
    public void setWhiteBoardPoints(List<WhiteBoardPoint> whiteBoardPoints) {
        this.mWhiteBoardPoints = whiteBoardPoints;
    }


}
