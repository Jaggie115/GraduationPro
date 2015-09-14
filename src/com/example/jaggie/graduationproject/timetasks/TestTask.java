package com.example.jaggie.graduationproject.timetasks;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.jaggie.graduationproject.acitvities.BlockActivity;
import com.example.jaggie.graduationproject.acitvities.SelectAppActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by jaggie on 2015/1/16.
 */
public class TestTask extends TimerTask {


    public static final String TAG = "usage";
    private Context mContext;
    private ActivityManager mActivityManager;
    private List<PackageInfo> appPackageInfos;
    private List<PackageInfo> otherAppPackageInfos;
    private List<PackageInfo> selectAppPackageInfos;
    private PackageManager myPackageManager;

    public TestTask(Context context) {
        mContext = context;
        mActivityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        myPackageManager = context.getPackageManager();
        appPackageInfos = myPackageManager.getInstalledPackages(0);
        otherAppPackageInfos = new ArrayList<PackageInfo>();
        for (PackageInfo pi : appPackageInfos) {

            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                //第三方应用
                if (!pi.packageName.equals(context.getPackageName())) {
                    otherAppPackageInfos.add(pi);
                }
            } else {
                //系统应用
            }

        }

    }


    public List<PackageInfo> getOtherAppPackageInfos() {
        return otherAppPackageInfos;
    }

    public void setSelectApps(List<PackageInfo> selectAppPackageInfos) {
        this.selectAppPackageInfos = selectAppPackageInfos;
    }

    public List<PackageInfo> getSelectAppPackageInfos() {
        return this.selectAppPackageInfos;
    }

    @Override
    public void run() {

        ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
        ActivityManager.RunningTaskInfo runningTaskInfo = mActivityManager.getRunningTasks(1).get(0);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = mActivityManager.getRunningTasks(4);

        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = mActivityManager.getRunningAppProcesses();
//        for (ActivityManager.RunningTaskInfo r : runningTaskInfos) {
//            appsIcons.add(r.thumbnail);
//            appsLabels.add(r.topActivity.flattenToShortString());
//            appsLabels.add("app");
//        }


        if (selectAppPackageInfos != null && selectAppPackageInfos.size() != 0) {
            for (PackageInfo pi : selectAppPackageInfos) {
                if (pi.packageName.equals(topActivity.getPackageName())) {
                    /**
                     * Context中有一个startActivity方法，Activity继承自Context，重载了startActivity方法。
                     * 如果使用 Activity的startActivity方法，不会有任何限制，
                     * 而如果使用Context的startActivity方法的话，就需要开启一个新的task，
                     * 遇到上面那个异常的，都是因为使用了Context的startActivity方法。解决办法是，加一个flag。
                     * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     */
                    Intent intent = new Intent(mContext, BlockActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packname", pi.packageName);


                    mContext.startActivity(intent);

                    // System.out.println("start Block!!");
                }

            }

        }

        String packageName = topActivity.getPackageName();
        String className = topActivity.getClassName();
        mContext.sendBroadcast(new Intent(SelectAppActivity.UPDATE_ACTION));
        //因权限操作复杂，暂时停止测试该方法
//        getPkgUsageStats();

        Log.d("test--pack", packageName);
        Log.d("test--class", className);


    }

    /**
     * 因权限操作复杂，调试难度大，所以该方法停用
     */
//    private void getPkgUsageStats() {
//        try {
//            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
//            Method mGetService = cServiceManager.getMethod("getService", java.lang.String.class);
//            Object oRemoteService = mGetService.invoke(null, "usagestats");
//
//            Class<?> cStub = Class
//                    .forName("com.android.internal.app.IUsageStats$Stub");
//            Method mUsageStatsService = cStub.getMethod("asInterface",
//                    android.os.IBinder.class);
//            Object oIUsageStats = mUsageStatsService.invoke(null,
//                    oRemoteService);
//
//            // PkgUsageStats[] oPkgUsageStatsArray =
//            // mUsageStatsService.getAllPkgUsageStats();
//            Class<?> cIUsageStatus = Class
//                    .forName("com.android.internal.app.IUsageStats");
//            Method mGetAllPkgUsageStats = cIUsageStatus.getMethod(
//                    "getAllPkgUsageStats", (Class[]) null);
//            Object[] oPkgUsageStatsArray = (Object[]) mGetAllPkgUsageStats
//                    .invoke(oIUsageStats, (Object[]) null);
//            Class<?> cPkgUsageStats = Class
//                    .forName("com.android.internal.os.PkgUsageStats");
//            StringBuffer sb = new StringBuffer();
//            sb.append("nerver used : ");
//            for (Object pkgUsageStats : oPkgUsageStatsArray) {
//                // get pkgUsageStats.packageName, pkgUsageStats.launchCount,
//                // pkgUsageStats.usageTime
//                String packageName = (String) cPkgUsageStats.getDeclaredField(
//                        "packageName").get(pkgUsageStats);
//                int launchCount = cPkgUsageStats
//                        .getDeclaredField("launchCount").getInt(pkgUsageStats);
//                long usageTime = cPkgUsageStats.getDeclaredField("usageTime")
//                        .getLong(pkgUsageStats);
//                if (launchCount > 0)
//                    Log.d(TAG, "[getPkgUsageStats] " + packageName + "  count: "
//                            + launchCount + "  time:  " + usageTime);
//                else {
//                    sb.append(packageName + "; ");
//                }
//            }
//            Log.d(TAG, "[getPkgUsageStats] " + sb.toString());
//
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//    }
}
