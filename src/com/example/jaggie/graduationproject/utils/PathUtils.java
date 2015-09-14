package com.example.jaggie.graduationproject.utils;

import android.os.Environment;

import java.io.UnsupportedEncodingException;

/**
 * Created by jaggie on 2015/2/4.
 */
public class PathUtils {

    public static final String getDicsPath() {
        return getContentPath()+"/dics";

    }

    public static final String getContentPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/graduationproject";
    }

    public static String changeToUTF8(String s){
        try {
            return new String(s.getBytes("UTF-16"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return s;
        }
    }
}
