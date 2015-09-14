package com.example.jaggie.graduationproject.acitvities;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.adapters.AppInfoAdapter;
import com.example.jaggie.graduationproject.services.TimerService;
import com.example.jaggie.graduationproject.timetasks.TestTask;


public class SelectAppActivity extends BaseActivity {


    public final static String UPDATE_ACTION = "update";
    private BroadcastReceiver updateBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == SelectAppActivity.UPDATE_ACTION) {
                updateData();
            }
        }
    };
    BaseAdapter myAdapter;
    private TimerService.ServiceBinder timerService;
    private GridView gridView;
    private TestTask timerTask;
    private ServiceConnection serviceconnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gridView = (GridView) findViewById(R.id.apps_gv);

        startService(new Intent(this, TimerService.class));
        bindService(new Intent(this, TimerService.class), this.serviceconnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                SelectAppActivity.this.timerService = (TimerService.ServiceBinder) service;
                bindData();
                registerReceiver(SelectAppActivity.this.updateBroadcastReceiver, new IntentFilter(SelectAppActivity.UPDATE_ACTION));
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);


    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(this.updateBroadcastReceiver);
        unbindService(this.serviceconnection);
        ((AppInfoAdapter) this.myAdapter).setCurrentBlockApps();
        super.onDestroy();
    }

    private void updateData() {
        this.myAdapter.notifyDataSetChanged();
    }

    private void bindData() {

        this.timerTask = this.timerService.getmTimerTask();
        this.myAdapter = new AppInfoAdapter(this, this.timerTask);
        this.gridView.setAdapter(this.myAdapter);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((AppInfoAdapter) SelectAppActivity.this.myAdapter).setChecked(position);
                SelectAppActivity.this.myAdapter.notifyDataSetChanged();
            }
        });
    }


    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }


}
