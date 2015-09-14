package com.example.jaggie.graduationproject.acitvities;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.utils.PathUtils;

public class HomepageActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        findViewById(R.id.settingsappblock_tv).setOnClickListener(this);
        findViewById(R.id.beginstudy_tv).setOnClickListener(this);
        init();
    }

    /**
     * 应用启动的一些初始化操作
     */
    private void init() {
        // 新建一个本应用内容目录
        File contentDirectory = new File(PathUtils.getContentPath());
        contentDirectory.mkdir();
        // test Drawer

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.settingsappblock_tv:
            startActivity(new Intent(this, SelectAppActivity.class));
            break;
        case R.id.beginstudy_tv:
            startActivity(new Intent(this, PlanListActivity.class));
            break;
        }

    }
}
