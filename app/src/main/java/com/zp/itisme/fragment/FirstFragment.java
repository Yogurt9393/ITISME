package com.zp.itisme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zp.itisme.R;
import com.zp.itisme.activity.MainActivity;
import com.zp.itisme.activity.PersonActivity;
import com.zp.itisme.utils.LoadImage;
import com.zp.itisme.utils.SPUtils;
import com.zp.itisme.view.CircleImageView;

public class FirstFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView iv_person;
    private String icon_path;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = View.inflate(getContext(), R.layout.fragment_first, null);
        return layout;
    }

    @Override
    protected void initView() {
        iv_person = (CircleImageView) layout.findViewById(R.id.iv_person);
    }

    private void setIcon() {
        icon_path = SPUtils.get(getContext(), "icon_path", "");
        LoadImage.set(getContext(), iv_person, icon_path);
    }

    @Override
    protected void setListener() {
        iv_person.setOnClickListener(this);
        PersonActivity.setOnIconRefreshListener(new PersonActivity.refreshIconListener() {
            @Override
            public void returnRefresh() {
                setIcon();
            }
        });
    }

    @Override
    protected void setData() {
        setIcon();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person:
                startActivity(new Intent(getContext(), PersonActivity.class));
                break;
        }
    }
}
