package com.zp.itisme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zp.itisme.R;
import com.zp.itisme.activity.AddNewActivity;
import com.zp.itisme.adapter.ShareXListViewAdapter;
import com.zp.itisme.bean.ShareBean;
import com.zp.itisme.utils.Config;
import com.zp.itisme.utils.SPUtils;
import com.zp.itisme.view.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFragment extends BaseFragment implements XListView.IXListViewListener {

    private XListView xlistview;
    private ShareXListViewAdapter mAdapter;
    private List<ShareBean> mData;
    private int page = 1;
    private String username;

    private View header;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = View.inflate(getContext(), R.layout.fragment_main, null);
        return layout;
    }

    @Override
    protected void initView() {

        mData = new ArrayList<>();
        xlistview = layout.findViewById(R.id.xlistview);
        header = View.inflate(getContext(), R.layout.header_share_xlistview, null);

        xlistview.addHeaderView(header);
        mAdapter = new ShareXListViewAdapter(getContext(), mData);
        xlistview.setAdapter(mAdapter);
        username = SPUtils.get(getContext(), "username", "");
        String str_data = SPUtils.get(getContext(), username + "str_share_data", "");
        if (!TextUtils.isEmpty(str_data)) {
            dealData(str_data);
        }
        loadData();
    }

    @Override
    protected void setListener() {
        xlistview.setPullLoadEnable(true);
        xlistview.setPullRefreshEnable(true);
        xlistview.setXListViewListener(this);
        AddNewActivity.setOnDataRefreshListener(new AddNewActivity.refreshDataListener() {
            @Override
            public void returnRefresh() {
                page = 1;
                loadData();
            }
        });
    }

    private void loadData() {
        xlistview.setRefreshTime(new Date().toLocaleString());
        RequestParams params = new RequestParams(Config.SHARELIST_PATH);
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (page == 1) {
                    mData.clear();
                    String str_data = SPUtils.get(getContext(), username + "str_share_data", "");
                    if (!result.equals(str_data)) {
                        SPUtils.put(getContext(), username + "str_share_data", result);
                    }
                    dealData(result);
                }else{
                    dealData(result);
                }
                xlistview.stopLoadMore();
                xlistview.stopRefresh();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                xlistview.stopLoadMore();
                xlistview.stopRefresh();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void dealData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.optInt("code");
            if (code == 0) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.optJSONObject(i);
                    ShareBean share = new ShareBean();
                    share.setId(json.optString("id"));
                    share.setUserid(json.optString("userid"));
                    share.setDetail(json.optString("detail"));
                    share.setPic_path(json.optString("pic_path"));
                    share.setUser_icon(json.optString("user_icon"));
                    share.setUsername(json.optString("username"));
                    share.setTime(json.optString("time"));
                    mData.add(share);
                }
                mAdapter.notifyDataSetChanged();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData();
    }
}
