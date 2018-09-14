package com.zp.itisme.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zp.itisme.R;
import com.zp.itisme.bean.ShareBean;
import com.zp.itisme.utils.LoadImage;
import com.zp.itisme.utils.TimeUtils;
import com.zp.itisme.view.CircleImageView;

import java.util.List;

public class ShareXListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShareBean> mData;

    public ShareXListViewAdapter(Context mContext, List<ShareBean> mData) {
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
            convertView = View.inflate(mContext, R.layout.item_xlistview_share, null);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_username = convertView.findViewById(R.id.tv_username);
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            holder.tv_detail = convertView.findViewById(R.id.tv_detail);
            holder.iv_image = convertView.findViewById(R.id.iv_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ShareBean shareBean = mData.get(position);
        if (!TextUtils.isEmpty(shareBean.getPic_path())) {
            LoadImage.set(mContext, holder.iv_image, shareBean.getPic_path());
            holder.iv_image.setVisibility(View.VISIBLE);
        } else {
            holder.iv_image.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(shareBean.getUser_icon())) {
            LoadImage.set(mContext, holder.iv_icon, shareBean.getUser_icon());
        }
        holder.tv_username.setText(shareBean.getUsername());
        holder.tv_detail.setText(shareBean.getDetail());

        String addTime = shareBean.getTime();
        String nowTime = System.currentTimeMillis() + "";
        if (!TextUtils.isEmpty(addTime) && !TextUtils.isEmpty(nowTime)) {
            if ((TimeUtils.getD(nowTime) - TimeUtils.getD(addTime)) == 0) {//当天
                if ((TimeUtils.getH(nowTime) - TimeUtils.getH(addTime)) == 0) {//这个小时
                    if ((TimeUtils.getm(nowTime) - TimeUtils.getm(addTime)) < 2) {//两分钟内
                        holder.tv_time.setText("just now");
                    } else {
                        holder.tv_time.setText((TimeUtils.getm(nowTime) - TimeUtils.getm(addTime)) + " mins ago");
                    }
                } else {
                    holder.tv_time.setText(TimeUtils.getH(nowTime) - TimeUtils.getH(addTime) + " hours ago");
                }
            } else if ((TimeUtils.getD(nowTime) - TimeUtils.getD(addTime)) == 1) {//昨天
                holder.tv_time.setText("Yesterday " + TimeUtils.setHHmm(shareBean.getTime()));
            } else {//时间很久了
                holder.tv_time.setText(TimeUtils.setMMddHHmm(shareBean.getTime()));
            }
        }
        return convertView;
    }

    class ViewHolder {
        CircleImageView iv_icon;
        TextView tv_username;
        TextView tv_time;
        TextView tv_detail;
        ImageView iv_image;
    }

}
