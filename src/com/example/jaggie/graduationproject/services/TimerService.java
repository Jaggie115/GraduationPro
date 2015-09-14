package com.example.jaggie.graduationproject.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.jaggie.graduationproject.timetasks.TestTask;

import java.util.Timer;

/**
 * Created by jaggie on 2015/1/16.
 */
public class TimerService extends Service {


    private static final int FOREGROUND_ID = 0;
    private Timer timer;
    private TestTask mTimerTask;

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(FOREGROUND_ID, new Notification());
        startTimer();
        super.onStartCommand(intent, flags, startId);
        //这里返回START_STICKY，会在内存低kill掉后内存足够就重启
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        timer.cancel();
        timer.purge();
        timer = null;
        super.onDestroy();
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
            mTimerTask = new TestTask(this);
            timer.schedule(mTimerTask, 1L, 1000L);
        }
    }

    public class ServiceBinder extends Binder {
        public TestTask getmTimerTask() {
            return mTimerTask;
        }
    }
}
