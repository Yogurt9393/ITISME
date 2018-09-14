package com.zp.itisme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.activity.PersonActivity;
import com.zp.itisme.adapter.AccountXListViewAdapter;
import com.zp.itisme.bean.MenuBean;
import com.zp.itisme.utils.LoadImage;
import com.zp.itisme.utils.SPUtils;
import com.zp.itisme.view.CircleImageView;
import com.zp.itisme.view.XListView;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView iv_person;
    private String icon_path;
    private String user_nickname;
    private TextView tv_nickname;

    private XListView xlistview;
    private View header;
    private AccountXListViewAdapter mAdapter;
    private List<MenuBean> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = View.inflate(getContext(), R.layout.fragment_account, null);
        return layout;
    }

    @Override
    protected void initView() {
        mData = new ArrayList<>();
        xlistview = layout.findViewById(R.id.xlistview);
        header = View.inflate(getContext(), R.layout.header_account_xlistview, null);
        iv_person = (CircleImageView) header.findViewById(R.id.iv_person);
        tv_nickname = (TextView) header.findViewById(R.id.tv_nickname);
        xlistview.addHeaderView(header);
        mAdapter = new AccountXListViewAdapter(getContext(), mData);
        xlistview.setAdapter(mAdapter);

    }

    @Override
    protected void setData() {
        setIcon();
        user_nickname = SPUtils.get(getContext(), "nickname", "");
        if (!TextUtils.isEmpty(user_nickname)) {
            tv_nickname.setText(user_nickname);
        } else {
            tv_nickname.setText("No NickName");
        }

        MenuBean menuBean1 = new MenuBean();
        menuBean1.setName("AboutUs");
        mData.add(menuBean1);

        MenuBean menuBean = new MenuBean();
        menuBean.setName("Setting");
        mData.add(menuBean);
        mAdapter.notifyDataSetChanged();

    }

    private void setIcon() {
        icon_path = SPUtils.get(getContext(), "icon_path", "");
        LoadImage.set(getContext(), iv_person, icon_path);
    }

    @Override
    protected void setListener() {
        iv_person.setOnClickListener(this);
        xlistview.setPullLoadEnable(false);
        xlistview.setPullRefreshEnable(false);
        PersonActivity.setOnIconRefreshListener(new PersonActivity.refreshIconListener() {
            @Override
            public void returnRefresh() {
                setIcon();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person:
            case R.id.tv_nickname:
                startActivity(new Intent(getContext(), PersonActivity.class));
                break;
        }
    }

}
