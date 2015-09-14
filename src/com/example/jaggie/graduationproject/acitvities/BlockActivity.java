package com.example.jaggie.graduationproject.acitvities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.utils.SuUtil;

/**
 * Created by jaggie on 2015/1/26.
 */
public class BlockActivity extends BaseActivity {


    private String packagename;
    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        findViewById(R.id.beginstudy_tv).setOnClickListener(this);
        this.packagename = getIntent().getStringExtra("packname");
        this.pid = getIntent().getIntExtra("pid", -1);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.beginstudy_tv:
            SuUtil.kill(this.packagename);
            // SuUtil.killByPid(this.pid);
            startActivity(new Intent(BlockActivity.this, PlanListActivity.class));
            finish();
            break;

        }

    }
}
