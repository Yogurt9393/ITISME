package com.zp.itisme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment{

    protected String TAG=getClass().getSimpleName();
    protected View layout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        setData();
        setListener();
    }

    protected abstract void initView();

    protected void initData() {}

    protected void setData() {}

    protected abstract void setListener();

}
