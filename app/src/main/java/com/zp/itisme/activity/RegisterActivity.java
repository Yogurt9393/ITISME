package com.zp.itisme.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.dialog.LoadingDialog;
import com.zp.itisme.utils.Config;
import com.zp.itisme.utils.SPUtils;
import com.zp.itisme.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private TextView tv_register;

    private Dialog loadingDialog;

    private TextView tv_tologin;

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
        tv_tologin = (TextView) findViewById(R.id.tv_tologin);
    }

    @Override
    protected void setListener() {
        tv_register.setOnClickListener(this);
        tv_tologin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                if (canToNext()) {
                    toRegister();
                }
                break;
            case R.id.tv_tologin:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                RegisterActivity.this.finish();
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
        loadingDialog = LoadingDialog.create(this);
        loadingDialog.show();
        RequestParams params = new RequestParams(Config.REGISTER_PATH);
        params.addBodyParameter("username", et_username.getText().toString());
        params.addBodyParameter("password", et_password.getText().toString());
        params.addBodyParameter("register_time", System.currentTimeMillis() + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    if (code == 0) {
                        toLogin();
                    } else if (code == 3) {
                        ToastUtils.show(RegisterActivity.this, msg);
                    } else {
                        ToastUtils.show(RegisterActivity.this, getString(R.string.str_toast_reg_failed));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void toLogin() {
        RequestParams params = new RequestParams(Config.LOGIN_PATH);
        params.addBodyParameter("username", et_username.getText().toString());
        params.addBodyParameter("password", et_password.getText().toString());
        params.addBodyParameter("lasttime_login", System.currentTimeMillis() + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if (code == 0) {
                        JSONObject json = jsonObject.optJSONObject("data");

                        String id = json.optString("id");
                        SPUtils.put(RegisterActivity.this, "id", id);

                        String username = json.optString("username");
                        SPUtils.put(RegisterActivity.this, "username", username);

                        String nickname = json.optString("nickname");
                        SPUtils.put(RegisterActivity.this, "nickname", nickname);

                        String sex = json.optString("sex");
                        SPUtils.put(RegisterActivity.this, "sex", sex);

                        String age = json.optString("age");
                        SPUtils.put(RegisterActivity.this, "age", age);

                        String register_time = json.optString("register_time");
                        SPUtils.put(RegisterActivity.this, "register_time", register_time);

                        String lasttime_login = json.optString("lasttime_login");
                        SPUtils.put(RegisterActivity.this, "lasttime_login", lasttime_login);

                        String icon_path = json.optString("icon_path");
                        SPUtils.put(RegisterActivity.this, "icon_path", icon_path);

                        String sign = json.optString("sign");
                        SPUtils.put(RegisterActivity.this, "sign", sign);

                        SPUtils.put(RegisterActivity.this, "isLogin", true);

                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        RegisterActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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