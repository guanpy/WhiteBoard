package com.github.guanpy.wblib.bean;



import java.util.ArrayList;
import java.util.List;

/**
 * 画板绘画路径存储
 *
 * @author gpy
 */
public class WhiteBoardPoint {




    /**
     * 画板id
     */
    private int mId;
    /**
     * 保存路径
     */
    private List<DrawPoint> mSavePoints;
    /**
     * 撤销路径
     */
    private List<DrawPoint> mDeletePoints;

    /**
     * 获得画板id Created 2015-7-12 10:6:10
     *
     * @return 画板id
     * @author gpy
     */
    public int getId() {
        return mId;
    }

    /**
     * 设置画板id Created 2015-7-12 10:6:10
     *
     * @param id 画板id
     * @author gpy
     */
    public void setId(int id) {
        this.mId = id;
    }

    /**
     * 获得保存路径 Created 2015-7-12 10:6:10
     *
     * @return 保存路径
     * @author gpy
     */
    public List<DrawPoint> getSavePoints() {
        if (mSavePoints == null) {
            mSavePoints = new ArrayList<DrawPoint>();
        }
        return mSavePoints;
    }

    /**
     * 获得撤销路径 Created 2015-7-12 10:6:10
     *
     * @return 撤销路径
     * @author gpy
     */
    public List<DrawPoint> getDeletePoints() {
        if (mDeletePoints == null) {
            mDeletePoints = new ArrayList<DrawPoint>();
        }
        return mDeletePoints;
    }

}
