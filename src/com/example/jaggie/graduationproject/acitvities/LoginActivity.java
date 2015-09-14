package com.example.jaggie.graduationproject.acitvities;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.example.jaggie.graduationproject.Config;
import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.entities.User;
import com.example.jaggie.graduationproject.utils.DensityUtil;
import com.example.jaggie.graduationproject.view.PW_LinearLayout;

public class LoginActivity extends BaseActivity {


    private EditText username_et, password_et;
    private ImageView avatar_iv;
    private TextView login_tv, register_tv;

    private PW_LinearLayout main_ll;

    // 状态栏的高度
    private int statusBarHeight;
    // 软键盘的高度
    private int keyboardHeight;
    // 软键盘的显示状态
    private boolean isShowKeyboard;

    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.main_ll = (PW_LinearLayout) findViewById(R.id.main_ll);
        this.username_et = (EditText) findViewById(R.id.username_et);
        this.password_et = (EditText) findViewById(R.id.password_et);
        this.login_tv = (TextView) findViewById(R.id.login_tv);
        this.register_tv = (TextView) findViewById(R.id.register_tv);
        this.avatar_iv = (ImageView) findViewById(R.id.avatar);
        this.login_tv.setOnClickListener(this);
        this.register_tv.setOnClickListener(this);
        this.username_et.setOnClickListener(this);
        this.password_et.setOnClickListener(this);
        // this.main_ll.setKeyBordStateListener(this);
        this.statusBarHeight = getStatusBarHeight(getApplicationContext());
        this.screenHeight = DensityUtil.getScreenHeight(this);
        this.main_ll.getViewTreeObserver().addOnGlobalLayoutListener(this.globalLayoutListener);
        init();
    }

    private void init() {
        // BmobUser lastUser = BmobUser.getCurrentUser(this);

        BmobUser lastUser = null;
        if (lastUser == null) {

        } else {
            this.username_et.setText(lastUser.getUsername());
            Config.loginUser = lastUser;
            startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
            finish();
            // 缓存用户没有保存密码,查询回来的user也没有
            // password_et.setText(lastUser.getPassword());

        }
    }

    private void login() {
        this.login_tv.setEnabled(false);
        final BmobUser loginUser = new User();
        loginUser.setUsername(this.username_et.getText().toString());
        loginUser.setPassword(this.password_et.getText().toString());
        loginUser.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Config.loginUser = loginUser;
                startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginActivity.this, "登录失败，请检查用户名和密码是否正确" + s, Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(LoginActivity.this,
                // HomepageActivity.class));
                // finish();
                LoginActivity.this.login_tv.setEnabled(true);

            }
        });
    }

    private void register() {
        this.register_tv.setEnabled(false);
        BmobUser loginUser = new BmobUser();
        loginUser.setUsername(this.username_et.getText().toString());
        loginUser.setPassword(this.password_et.getText().toString());
        loginUser.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                LoginActivity.this.register_tv.setEnabled(true);

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                LoginActivity.this.register_tv.setEnabled(true);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.login_tv:
            login();
            break;
        case R.id.register_tv:
            register();
            break;
        case R.id.username_et:
            break;
        case R.id.password_et:
            break;

        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            // 应用可以显示的区域
            Rect r = new Rect();
            LoginActivity.this.main_ll.getWindowVisibleDisplayFrame(r);

            // 屏幕高度
            int screenHeight = LoginActivity.this.getWindow().getDecorView().getHeight();


            LoginActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            // //获取屏幕的高度
            // int screenHeight =
            // LoginActivity.this.getWindow().getDecorView().getRootView().getHeight();

            int heightDiff = screenHeight - (r.bottom - r.top);
            Log.d("heightdiff", "" + heightDiff);
            Log.d("screenHeight", "" + screenHeight);
            Log.d("statusBarHeight", "" + LoginActivity.this.statusBarHeight);
            Log.d("r.top", "" + r.top);
            Log.d("r.bottom", "" + r.bottom);
            // 在不显示软键盘时，heightDiff等于状态栏的高度
            // 在显示软键盘时，heightDiff会变大。所以heightDiff大于状态栏高度时表示软键盘出现了，这时可算出软键盘的高度，等于heightDiff减去状态栏的高度
            if (LoginActivity.this.keyboardHeight == 0 && heightDiff > LoginActivity.this.statusBarHeight) {
                LoginActivity.this.keyboardHeight = heightDiff - LoginActivity.this.statusBarHeight;
            }
            //
            // Log.d("screenHeight==", "" + LoginActivity.this.screenHeight);
            // if (0 == (LoginActivity.this.keyboardHeight =
            // LoginActivity.this.screenHeight - r.bottom)) {
            // LoginActivity.this.isShowKeyboard = false;
            // onHideKeyboard();
            // } else {
            // LoginActivity.this.isShowKeyboard = true;
            // onShowKeyboard();
            // }


            if (LoginActivity.this.isShowKeyboard) {
                if (heightDiff <= LoginActivity.this.statusBarHeight) {
                    LoginActivity.this.isShowKeyboard = false;
                    onHideKeyboard();
                }
            } else {
                if (heightDiff > LoginActivity.this.statusBarHeight) {
                    LoginActivity.this.isShowKeyboard = true;
                    onShowKeyboard();
                }
            }

        }
    };

    private void onShowKeyboard() {
        Log.d("keyboard", "show" + this.keyboardHeight);
        this.avatar_iv.setVisibility(View.GONE);
    }

    private void onHideKeyboard() {
        Log.d("keyboard", "hide" + this.keyboardHeight);
        this.avatar_iv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            this.main_ll.getViewTreeObserver().removeGlobalOnLayoutListener(this.globalLayoutListener);
        } else {
            this.main_ll.getViewTreeObserver().removeOnGlobalLayoutListener(this.globalLayoutListener);
        }
    }

    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
