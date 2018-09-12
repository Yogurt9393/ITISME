package com.zp.itisme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.utils.Config;
import com.zp.itisme.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_login = (TextView) findViewById(R.id.tv_login);
    }

    @Override
    protected void setListener() {
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                if (canToNext()) {
                    toLogin();
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

    private void toLogin() {
        RequestParams params = new RequestParams(Config.LOGIN_PATH);
        params.addBodyParameter("username", et_username.getText().toString());
        params.addBodyParameter("password", et_password.getText().toString());
        params.addBodyParameter("lasttime_login", System.currentTimeMillis() + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("toLogin", "onSuccess:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if (code == 0) {
                        JSONObject json = jsonObject.optJSONObject("data");

                        String id = json.optString("id");
                        SPUtils.put(LoginActivity.this, "id", id);

                        String username = json.optString("username");
                        SPUtils.put(LoginActivity.this, "username", username);

                        String nickname = json.optString("nickname");
                        SPUtils.put(LoginActivity.this, "nickname", nickname);

                        String sex = json.optString("sex");
                        SPUtils.put(LoginActivity.this, "sex", sex);

                        String age = json.optString("age");
                        SPUtils.put(LoginActivity.this, "age", age);

                        String register_time = json.optString("register_time");
                        SPUtils.put(LoginActivity.this, "register_time", register_time);

                        String lasttime_login = json.optString("lasttime_login");
                        SPUtils.put(LoginActivity.this, "lasttime_login", lasttime_login);

                        String icon_path = json.optString("icon_path");
                        SPUtils.put(LoginActivity.this, "icon_path", icon_path);

                        String sign = json.optString("sign");
                        SPUtils.put(LoginActivity.this, "sign", sign);

                        SPUtils.put(LoginActivity.this,"isLogin",true);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();
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
