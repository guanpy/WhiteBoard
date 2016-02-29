package com.example.gpy.whiteboard.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bryce on 2014/10/20.
 */
public class DevMountInfo implements IDev {
    /**
     * ***
     */
    public final String HEAD        = "dev_mount";
    public final String LABEL       = "<label>";
    public final String MOUNT_POINT = "<mount_point>";
    public final String PATH        = "<part>";
    public final String SYSFS_PATH  = "<sysfs_path1...>";

    /**
     * Label for the volume
     */
    private final int NLABEL       = 1;
    /**
     * Partition
     */
    private final int NPATH        = 2;
    /**
     * Where the volume will be mounted
     */
    private final int NMOUNT_POINT = 3;
    private final int NSYSFS_PATH  = 4;

    private final int DEV_INTERNAL = 0;
    private final int DEV_EXTERNAL = 1;

    private ArrayList<String> cache = new ArrayList<String>();

    private static DevMountInfo dev;
    private        DevInfo       info;

    private final File VOLD_FSTAB = new File(Environment.getRootDirectory()
            .getAbsoluteFile()
            + File.separator
            + "etc"
            + File.separator
            + "vold.fstab");

    public static DevMountInfo getInstance() {
        if (null == dev)
            dev = new DevMountInfo();
        return dev;
    }

    private DevInfo getInfo(final int device) {
        // for(String str:cache)
        // System.out.println(str);

        if (null == info)
            info = new DevInfo();

        try {
            initVoldFstabToCache();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (device >= cache.size())
            return null;
        String[] sinfo = cache.get(device).split(" ");

        info.setLabel(sinfo[NLABEL]);
        info.setMount_point(sinfo[NMOUNT_POINT]);
        info.setPath(sinfo[NPATH]);
        info.setSysfs_path(sinfo[NSYSFS_PATH]);

        return info;
    }

    /**
     * init the words into the cache array
     *
     * @throws IOException
     */
    private void initVoldFstabToCache() throws IOException {
        cache.clear();
        BufferedReader br = new BufferedReader(new FileReader(VOLD_FSTAB));
        String tmp = null;
        while ((tmp = br.readLine()) != null) {
            // the words startsWith "dev_mount" are the SD info
            if (tmp.startsWith(HEAD)) {
                cache.add(tmp);
            }
        }
        br.close();
        cache.trimToSize();
    }


    @Override
    public DevInfo getInternalInfo() {
        return getInfo(DEV_INTERNAL);
    }

    @Override
    public DevInfo getExternalInfo() {
        return getInfo(DEV_EXTERNAL);
    }

}
