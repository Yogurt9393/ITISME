package com.zp.itisme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zp.itisme.R;
import com.zp.itisme.utils.SPUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_person;
    private String icon_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        iv_person = (ImageView) findViewById(R.id.iv_person);
    }

    @Override
    protected void setData() {

        icon_path = SPUtils.get(MainActivity.this, "icon_path", "");
        if (!TextUtils.isEmpty(icon_path)) {
            Picasso.with(MainActivity.this).load(icon_path).into(iv_person);
        }
    }

    @Override
    protected void setListener() {
        iv_person.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person:
                startActivity(new Intent(MainActivity.this, PersonSettingActivity.class));
                break;
        }
    }
}
