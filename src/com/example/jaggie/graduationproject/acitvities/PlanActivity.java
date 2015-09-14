package com.example.jaggie.graduationproject.acitvities;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.jaggie.graduationproject.Config;
import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.entities.Dictionary;
import com.example.jaggie.graduationproject.entities.Task;

/**
 * Created by jaggie on 2015/2/6.
 */
public class PlanActivity extends BaseActivity {


    private String selectedDic;
    private TextView selectHintTv;
    private EditText planNumEt;
    private String planNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        findViewById(R.id.selectdic_tv).setOnClickListener(this);
        findViewById(R.id.ensure_tv).setOnClickListener(this);
        this.selectHintTv = (TextView) findViewById(R.id.selecthint_tv);
        this.planNumEt = (EditText) findViewById(R.id.plannum_et);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.selectdic_tv:
            BmobQuery<Dictionary> query = new BmobQuery<Dictionary>();
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
            query.setLimit(1000);
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage(getString(R.string.now_loading));
            pd.setCancelable(false);
            pd.show();
            query.findObjects(this, new FindListener<Dictionary>() {
                @Override
                public void onSuccess(List<Dictionary> dictionaries) {
                    pd.dismiss();
                    final String[] dicsNames = new String[dictionaries.size()];
                    for (int i = 0; i < dictionaries.size(); i++) {
                        dicsNames[i] = dictionaries.get(i).getName();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlanActivity.this);
                    builder.setTitle(R.string.select_a_dic);
                    builder.setSingleChoiceItems(dicsNames, -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            PlanActivity.this.selectedDic = dicsNames[which];
                            PlanActivity.this.selectHintTv.setText(PlanActivity.this.selectedDic);
                            Toast.makeText(getApplicationContext(), dicsNames[which], Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

                @Override
                public void onError(int i, String s) {
                    Toast.makeText(PlanActivity.this, "更新词典列表失败", Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                }
            });

            break;
        case R.id.ensure_tv:
            if (check()) {
                int num = Integer.parseInt(this.planNum);
                Task task = new Task();
                task.setPlannum(num);
                task.setType(this.selectedDic);
                task.setCompleted(0);
                task.setFinished(false);
                task.setExecutor(Config.loginUser.getUsername());
                task.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(PlanActivity.this, " 保存成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(PlanActivity.this, " 保存失败", Toast.LENGTH_SHORT).show();

                    }
                });
                // Intent intent = new Intent(this, LearningActivity.class);
                // intent.putExtra("planNum", num);
                // intent.putExtra("dic", selectedDic);
                // startActivity(intent);
            }

            break;

        }
    }

    private boolean check() {
        this.planNum = this.planNumEt.getText().toString();
        if (this.selectedDic == null || "".equals(this.selectedDic)) {
            Toast.makeText(this, R.string.not_select_dic, Toast.LENGTH_SHORT).show();
            return false;
        } else if (this.planNum == null || "".equals(this.planNum)) {
            Toast.makeText(this, R.string.not_select_plannum, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.parseInt(this.planNum) <= 0) {
            Toast.makeText(this, R.string.mustmorethan0, Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }
}
