package com.atguigu.shopping.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shopping.R;
import com.atguigu.shopping.home.bean.HomeBean;
import com.atguigu.shopping.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 刘闯 on 2017/2/25.
 */

public class RecommendGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<HomeBean.ResultBean.RecommendInfoBean> datas;

    public RecommendGridViewAdapter(Context mContext, List<HomeBean.ResultBean.RecommendInfoBean> recommend_info) {
        this.mContext = mContext;
        this.datas = recommend_info;

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_recommend_grid_view, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置获取相应的数据
        HomeBean.ResultBean.RecommendInfoBean recommendInfoBean = datas.get(position);
        viewHolder.tvName.setText(recommendInfoBean.getName());
        viewHolder.tvPrice.setText("¥" + recommendInfoBean.getCover_price());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + recommendInfoBean.getFigure()).into(viewHolder.ivRecommend);

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_recommend)
        ImageView ivRecommend;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
