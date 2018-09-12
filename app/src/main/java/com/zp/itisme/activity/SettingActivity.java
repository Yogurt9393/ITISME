package com.zp.itisme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.myapp.MyApp;
import com.zp.itisme.utils.SPUtils;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initView() {
        tv_logout = (TextView) findViewById(R.id.tv_logout);
    }

    @Override
    protected void setListener() {
        tv_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_logout:
                SPUtils.put(SettingActivity.this, "id", "");
                SPUtils.put(SettingActivity.this, "username", "");
                SPUtils.put(SettingActivity.this, "nickname", "");
                SPUtils.put(SettingActivity.this, "sex", "");
                SPUtils.put(SettingActivity.this, "age", "");
                SPUtils.put(SettingActivity.this, "register_time", "");
                SPUtils.put(SettingActivity.this, "lasttime_login", "");
                SPUtils.put(SettingActivity.this, "icon_path", "");
                SPUtils.put(SettingActivity.this, "sign", "");
                SPUtils.put(SettingActivity.this, "isLogin", false);
                MyApp.exit();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                break;
        }
    }

}
