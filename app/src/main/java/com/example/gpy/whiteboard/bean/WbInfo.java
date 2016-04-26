package com.example.gpy.whiteboard.bean;

import java.io.File;

/**
 * Created by gpy on 2016/4/22.
 */
public class WbInfo {

    /**
     * 文件名
     */
    private String mName;
    /**
     * 文件
     */
    private File mFile;

    /**
     * 获得文件名
     * Created 2016-3-22 18:30:34
     *
     * @return 文件名
     * @author gpy
     */
    public String getName() {
        return mName;
    }

    /**
     * 设置文件名
     * Created 2016-3-22 18:30:34
     *
     * @param name 文件名
     * @author gpy
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * 获得文件
     * Created 2016-3-22 18:30:34
     *
     * @return 文件
     * @author gpy
     */
    public File getFile() {
        return mFile;
    }

    /**
     * 设置文件
     * Created 2016-3-22 18:30:34
     *
     * @param file 文件
     * @author gpy
     */
    public void setFile(File file) {
        this.mFile = file;
    }


}
