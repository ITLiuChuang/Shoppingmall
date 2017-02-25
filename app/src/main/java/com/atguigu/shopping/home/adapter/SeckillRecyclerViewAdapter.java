package com.atguigu.shopping.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.ViewHolder> {
    private final Context mContext;
    private final List<HomeBean.ResultBean.SeckillInfoBean.ListBean> datas;


    public SeckillRecyclerViewAdapter(Context mContext, HomeBean.ResultBean.SeckillInfoBean seckill_info) {
        this.mContext = mContext;
        this.datas = seckill_info.getList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_seckill, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //根据为知获得数据
        HomeBean.ResultBean.SeckillInfoBean.ListBean listBean = datas.get(position);
        //绑定数据
        holder.tvCoverPrice.setText("¥" + listBean.getCover_price());
        holder.tvOriginPrice.setText("¥" + listBean.getOrigin_price());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + listBean.getFigure()).into(holder.ivFigure);

    }



    @Override
    public int getItemCount() {
        return datas.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_figure)
        ImageView ivFigure;
        @InjectView(R.id.tv_cover_price)
        TextView tvCoverPrice;
        @InjectView(R.id.tv_origin_price)
        TextView tvOriginPrice;
        @InjectView(R.id.ll_root)
        LinearLayout llRoot;

        ViewHolder(final View view) {
            super(view);
            ButterKnife.inject(this, view);
            //设置点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(view,getLayoutPosition());
                    }
                }
            });
        }
    }
    //item点击接口
    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    private ViewPagerAdapter.OnItemClickListener listener;

    //item点击事件


    public void setOnItemClickListener(ViewPagerAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
