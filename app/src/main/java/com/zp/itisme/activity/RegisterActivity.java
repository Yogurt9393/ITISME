package com.zp.itisme.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.utils.Config;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_register = (TextView) findViewById(R.id.tv_register);
    }

    @Override
    protected void setListener() {
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                if (canToNext()) {
                    toRegister();
                }
                break;
        }
    }

    private boolean canToNext() {
        if (et_username.getText().length() > 6 || et_username.getText().length() < 12) {
            if (et_password.getText().length() > 8 || et_password.getText().length() < 16) {
                return true;
            }
        }
        return false;
    }

    private void toRegister() {
        RequestParams params = new RequestParams(Config.REGISTER_PATH);
        Log.e("toRegister", "Config.REGISTER_PATH:" + Config.REGISTER_PATH);
        params.addBodyParameter("username", et_username.getText().toString());
        params.addBodyParameter("password", et_password.getText().toString());
        params.addBodyParameter("register_time", System.currentTimeMillis() + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("toRegister", "onSuccess:" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}