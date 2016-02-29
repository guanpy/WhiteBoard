package com.example.gpy.whiteboard.utils;

import android.os.Environment;


import java.io.File;

/**
 * Created by Bryce on 2014/10/20.
 */
public class SdCardStatus {
    private static String CACHE_FOLDER_NAME;
    private static String NONE_SD_CARD_PROMPT = "您的手机中sd卡不存在";

    public static void init(String cacheFolderName) {
        CACHE_FOLDER_NAME = cacheFolderName;
        hasSdCard();
    }

    public static boolean hasSdCard() {
        String sdCardPath = null;
        sdCardPath = getSDPath();
        if (null == sdCardPath) {
//            Ln.e(NONE_SD_CARD_PROMPT);
            return false;
        }
        return true;
    }

    private static boolean isSDCardReady(){
        String STATE = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(STATE) ;
    }

    public static String getDownloadDir(){
        String downloadDir = null;
        if(isSDCardReady()){
            downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .getAbsolutePath();
        }else{
            downloadDir = Environment.getExternalStorageDirectory().getPath()+ File.separator+ Environment.DIRECTORY_DOWNLOADS;
        }
        return downloadDir;
    }

    public static String getDefaulstCacheDirInSdCard() throws IllegalStateException {
        String sdCardPath = null;
        sdCardPath = getSDPath();
        if (null == sdCardPath) {
            throw new IllegalStateException(NONE_SD_CARD_PROMPT);
        }
        return sdCardPath + File.separator + CACHE_FOLDER_NAME;
    }

    /**
     * when not exist sd card,return null.
     *
     * @return
     */
    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            DevMountInfo dev = DevMountInfo.getInstance();
            DevInfo info = dev.getExternalInfo();
            if (null == info) {
                return null;
            }
            String sd2Path = info.getPath(); // SD 卡路径
            if (sd2Path != null && sd2Path.length() > 0) {
                return sd2Path;
            } else {
                return null;
            }
        }
    }
}
