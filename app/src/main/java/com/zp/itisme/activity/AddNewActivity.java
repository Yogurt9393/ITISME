package com.zp.itisme.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zp.itisme.R;

public class AddNewActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_cancel;
    private TextView tv_sure;
    private EditText et_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
    }

    @Override
    protected void initView() {
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        et_detail = (EditText) findViewById(R.id.et_detail);


    }

    @Override
    protected void setListener() {
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                toCancel();
                break;
            case R.id.tv_sure:
                toSubmit();
                break;
        }
    }

    private void toSubmit() {

    }

    private void toCancel() {
        AddNewActivity.this.finish();
    }

}
