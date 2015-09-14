package com.example.jaggie.graduationproject.utils;

/**
 * Created by jaggie on 2015/1/28.
 */

import java.io.IOException;
import java.io.OutputStream;

public class SuUtil {

    private static Process process;


    /**
     * 结束进程,执行操作调用即可
     */
    public static void kill(String packageName) {
        initProcess();
        killProcess(packageName);
        close();
    }


    public static void killByPid(int pid) {
        try {
            Runtime.getRuntime().exec("sudo kill -9 " + pid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化进程
     */
    private static void initProcess() {
        if (process == null) {
            try {
                process = Runtime.getRuntime().exec("su");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 结束进程
     */
    private static void killProcess(String packageName) {
        OutputStream out = process.getOutputStream();
        String cmd = "am force-stop " + packageName + " \n";
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输出流
     */
    private static void close() {
        if (process != null)
            try {
                process.getOutputStream().close();
                process = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

