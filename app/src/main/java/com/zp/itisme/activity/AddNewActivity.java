package com.zp.itisme.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.view.KeyboardLayout;

public class AddNewActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_cancel;
    private TextView tv_sure;
    private EditText et_detail;
    private KeyboardLayout keyboard;
    private View view_bottom;

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
        view_bottom = (View) findViewById(R.id.view_bottom);

        keyboard = (KeyboardLayout) findViewById(R.id.keyboard);
        et_detail.setFocusable(true);
        et_detail.setFocusableInTouchMode(true);
        et_detail.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    @Override
    protected void setListener() {
        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        keyboard.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                if (isActive) { // 输入法打开

                    //do something
                } else {
                    Log.e("AddNewActivity", "keyboardHeight:" + keyboardHeight);
                }
                Log.e("AddNewActivity", "keyboardHeight:" + keyboardHeight);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_bottom.getLayoutParams();
                params.bottomMargin = keyboardHeight;
                view_bottom.setLayoutParams(params);
            }
        });
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
