package com.example.jaggie.graduationproject.acitvities;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.entities.Task;


/**
 * Created by jaggie on 2015/4/9.
 */
public class PlanListActivity extends BaseActivity {

    private ProgressDialog pd;
    private TextView nowStudy_tv, planNum_tv, todayRemainNum_tv;
    private Task mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planlist);
        this.nowStudy_tv = (TextView) findViewById(R.id.nowstudy_tv);
        this.planNum_tv = (TextView) findViewById(R.id.plannum_tv);
        this.todayRemainNum_tv = (TextView) findViewById(R.id.todayremainnum_tv);
        this.nowStudy_tv.setOnClickListener(this);
        this.pd = new ProgressDialog(this);
        this.pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.pd.setMessage(getString(R.string.now_loading));
        this.pd.setCancelable(false);
        this.pd.show();

    }


    @Override
    protected void onStart() {
        super.onStart();
        init();
    }


    private void loadTask(Task task) {
        this.planNum_tv.setText(task.getPlannum() + "");
        this.todayRemainNum_tv.setText(task.getTodayRemain() + "");

    }


    private void init() {
        BmobQuery<Task> task = new BmobQuery<Task>();
        String username = "619613156@qq.com";
        task.addWhereContains("executor", username);
        task.findObjects(this, new FindListener<Task>() {
            @Override
            public void onSuccess(List<Task> tasks) {

                PlanListActivity.this.pd.dismiss();
                Toast.makeText(PlanListActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                if (tasks == null || tasks.size() == 0) {
                    startActivity(new Intent(PlanListActivity.this, PlanActivity.class));
                } else {
                    PlanListActivity.this.mTask = tasks.get(0);
                    loadTask(PlanListActivity.this.mTask);
                }

            }

            @Override
            public void onError(int i, String s) {
                PlanListActivity.this.pd.dismiss();
                startActivity(new Intent(PlanListActivity.this, PlanActivity.class));
                Toast.makeText(PlanListActivity.this, "查找失败" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.nowstudy_tv:

            if (this.mTask.getTodayRemain() > 0) {
                Intent intent = new Intent(PlanListActivity.this, LearningActivity.class);
                intent.putExtra("task", this.mTask);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(PlanListActivity.this, "今天任务已完成！！", Toast.LENGTH_SHORT).show();

            }
            break;
        }
    }
}
