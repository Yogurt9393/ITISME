package com.zp.itisme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.fragment.FirstFragment;
import com.zp.itisme.fragment.SecondFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private RadioGroup rg_main;
    private List<RadioButton> list_rb;
    private List<Fragment> list_fragment;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private int curIndex, tarIndex;

    private TextView tv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {

        tv_add = (TextView) findViewById(R.id.tv_add);

        mManager = getSupportFragmentManager();
        list_fragment = new ArrayList<>();
        list_rb = new ArrayList<>();
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        list_rb.add((RadioButton) rg_main.getChildAt(0));
        list_rb.add((RadioButton) rg_main.getChildAt(1));

        FirstFragment firstFragment = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();

        list_fragment.add(firstFragment);
        list_fragment.add(secondFragment);
        list_rb.get(0).setChecked(true);
        mTransaction = mManager.beginTransaction();
        mTransaction.add(R.id.ll_main, list_fragment.get(0));
        mTransaction.commit();
        curIndex = 0;

    }

    @Override
    protected void setListener() {
        tv_add.setOnClickListener(this);
        rg_main.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        for (int i = 0; i < list_rb.size(); i++) {
            if (list_rb.get(i).getId() == checkedId) {
                tarIndex = i;
                break;
            }
        }
        changeFragment();
    }

    private void changeFragment() {
        if (tarIndex != curIndex) {
            Fragment curF = list_fragment.get(curIndex);
            Fragment tarF = list_fragment.get(tarIndex);
            FragmentTransaction transaction = mManager.beginTransaction();
            if (tarF.isAdded()) {
                transaction.hide(curF).show(tarF);
            } else {
                transaction.hide(curF).add(R.id.ll_main, tarF);
            }
            transaction.commit();
            curIndex = tarIndex;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                startActivity(new Intent(MainActivity.this,AddNewActivity.class));
                break;
        }
    }

}
