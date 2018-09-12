package com.zp.itisme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zp.itisme.R;

public class ThridFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = View.inflate(getContext(), R.layout.fragment_thrid, null);
        return layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }
}
