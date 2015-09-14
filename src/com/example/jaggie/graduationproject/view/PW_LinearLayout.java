package com.example.jaggie.graduationproject.view;


import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class PW_LinearLayout extends LinearLayout {

    public static final int KEYBORAD_HIDE = 0;
    public static final int KEYBORAD_SHOW = 1;
    private static final int SOFTKEYPAD_MIN_HEIGHT = 50;

    private Handler uiHandler = new Handler();

    public PW_LinearLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public PW_LinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onSizeChanged(int w, final int h, int oldw, final int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        // System.out.println("onSize改变");
        // this.uiHandler.post(new Runnable() {
        // @Override
        // public void run() {
        // if (oldh - h > PW_LinearLayout.SOFTKEYPAD_MIN_HEIGHT) {
        // Log.e("lp", "up");
        // System.out.println("弹起");
        // PW_LinearLayout.this.keyBoardStateListener.stateChange(PW_LinearLayout.KEYBORAD_SHOW);
        // } else {
        // System.out.println("隐藏");
        // Log.e("lp", "down");
        // if (PW_LinearLayout.this.keyBoardStateListener != null) {
        // PW_LinearLayout.this.keyBoardStateListener.stateChange(PW_LinearLayout.KEYBORAD_HIDE);
        // }
        // }
        // }
        // });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private KeyBoardStateListener keyBoardStateListener;

    public void setKeyBordStateListener(KeyBoardStateListener keyBoardStateListener) {
        this.keyBoardStateListener = keyBoardStateListener;
    }

    public interface KeyBoardStateListener {
        public void stateChange(int state);
    }

}