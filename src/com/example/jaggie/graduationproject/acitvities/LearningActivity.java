package com.example.jaggie.graduationproject.acitvities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.entities.Task;
import com.example.jaggie.graduationproject.entities.Word;
import com.example.jaggie.graduationproject.fragments.RememberFragment;


/**
 * Created by jaggie on 2015/3/27.
 */
public class LearningActivity extends BaseActivity {

    public static final int OK_DOWNLOAD = 1;
    public static final int ERROR_DOWNLOAD = 2;
    public static final int LIMIT_ERROR = 5;

    private List<Word> planWords = new ArrayList<Word>();
    private List<Word> forgotWords = new ArrayList<Word>();
    private List<Word> rememberedWords = new ArrayList<Word>();
    private int planNum;
    private String selectedDic;
    private Task task;
    private List<String> words;
    private boolean isCloseByHand = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaning);
        this.selectedDic = getIntent().getStringExtra("dic");
        this.planNum = getIntent().getIntExtra("planNum", -1);
        this.task = (Task) getIntent().getSerializableExtra("task");
        ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getString(R.string.now_loading));
        pd.setCancelable(false);
        pd.show();
        loadWordContent(pd);


    }


    private void loadFragment() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frame_container);
        if (fragment == null) {
            RememberFragment rf = RememberFragment.newInstance((ArrayList<Word>) this.forgotWords,
                (ArrayList<Word>) this.rememberedWords);
            fm.beginTransaction().add(R.id.frame_container, rf).commit();
        }
    }

    private void loadWordContent(final ProgressDialog pd) {


        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                case OK_DOWNLOAD:
                    loadFragment();
                    break;
                case ERROR_DOWNLOAD:
                    finish();
                    Toast.makeText(LearningActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                }
                pd.dismiss();
            }
        };

        BmobQuery<Word> query = new BmobQuery<Word>();
        query.setSkip(this.task.getCompleted());
        query.setLimit(this.task.getTodayRemain());
        query.findObjects(this, new FindListener<Word>() {
            @Override
            public void onSuccess(List<Word> words) {
                LearningActivity.this.planWords = words;
                LearningActivity.this.forgotWords = LearningActivity.this.planWords;
                handler.sendEmptyMessage(LearningActivity.OK_DOWNLOAD);
            }

            @Override
            public void onError(int i, String s) {
                handler.sendEmptyMessage(LearningActivity.ERROR_DOWNLOAD);
            }
        });


        // new Thread() {
        // public void run() {
        //
        // int count = 0;
        // int error = 0;
        // while (count < planNum && error < LIMIT_ERROR) {
        // String url = Config.WORD_PRE + "?access_token=" + Config.accessToken
        // + "&word=" + words.get(count);
        //
        // HttpGet httpGet = new HttpGet(url);
        // HttpParams params = new BasicHttpParams();
        // HttpConnectionParams.setConnectionTimeout(params, 3000); //设置连接超时
        // HttpConnectionParams.setSoTimeout(params, 3000); //设置请求超时
        //
        // httpGet.setParams(params);
        //
        // HttpClient httpClient = new DefaultHttpClient();
        // try {
        // HttpResponse httpResponse = httpClient.execute(httpGet);
        //
        // if (httpResponse.getStatusLine().getStatusCode() == 200) {
        // String wordJson = EntityUtils.toString(httpResponse
        // .getEntity());
        // System.out.println("wordJson==" + wordJson);
        // forgotWords.add(new Word(wordJson));
        // count++;
        // } else {
        // error++;
        // }
        // } catch (ClientProtocolException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (IOException e) {
        // error++;
        // e.printStackTrace();
        // } catch (JSONException e) {
        // e.printStackTrace();
        // }
        // }
        //
        // if (count == planNum) {
        // handler.sendEmptyMessage(OK_DOWNLOAD);
        // } else {
        // handler.sendEmptyMessage(ERROR_DOWNLOAD);
        // }
        //
        //
        // }
        // }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.isCloseByHand = true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {

        if (isFinishing() && !this.isCloseByHand) {
            this.task.setCompleted(this.task.getTodayRemain() + this.task.getCompleted());
            this.task.setTodayRemain(0);
            this.task.update(this, new UpdateListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(LearningActivity.this, "更新成功", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(LearningActivity.this, "更新失败" + s, Toast.LENGTH_SHORT).show();
                }
            });
        }
        super.onDestroy();
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
