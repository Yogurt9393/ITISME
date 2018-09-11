package com.zp.itisme.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zp.itisme.R;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tv_tonext;

    private boolean isFirst = true;
    private boolean isLogin = false;

    private int HANDLER_CODE = 0x001;
    private int time = 3;

    private MyHandler myHandler;

    private class MyHandler extends Handler {

        private WeakReference<WelcomeActivity> mReference;

        public MyHandler(WelcomeActivity activity) {
            mReference = new WeakReference<WelcomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WelcomeActivity activity = mReference.get();
            if (activity != null) {
                activity.doSomething(msg);
            }
        }
    }

    private void doSomething(Message msg) {
        switch (msg.what) {
            case 0x001:
                time--;
                if (time > 0) {
                    tv_tonext.setText(time + getString(R.string.tip_tonext));
                    myHandler.sendEmptyMessageDelayed(HANDLER_CODE, 1000);
                } else {
                    toNext();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private void initView() {
        myHandler = new MyHandler(WelcomeActivity.this);
        myHandler.sendEmptyMessageDelayed(HANDLER_CODE, 1000);
        tv_tonext = (TextView) findViewById(R.id.tv_tonext);
        tv_tonext.setText(time + getString(R.string.tip_tonext));
    }

    private void toNext() {
        isFirst = SPUtils.get(WelcomeActivity.this, "isFirst", true);
        isLogin = SPUtils.get(WelcomeActivity.this, "isLogin", false);
        Intent intent = null;
        if (isFirst) {
            intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
        } else {
            if (isLogin) {
                intent = new Intent(WelcomeActivity.this, MainActivity.class);
            } else {
                intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            }
        }
        startActivity(intent);
        WelcomeActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeMessages(HANDLER_CODE);
    }
}
