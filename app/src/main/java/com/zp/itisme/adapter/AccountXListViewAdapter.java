package com.zp.itisme.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.bean.MenuBean;

import java.util.List;

/**
 * Created by MS on 2018/9/13.
 */

public class AccountXListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<MenuBean> mData;

    public AccountXListViewAdapter(Context mContext, List<MenuBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_account_xlistview, null);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MenuBean menuBean = mData.get(position);
        holder.tv_name.setText(menuBean.getName());
        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
    }

}
