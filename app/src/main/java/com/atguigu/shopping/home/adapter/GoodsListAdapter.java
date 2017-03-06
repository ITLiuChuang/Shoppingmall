package com.atguigu.shopping.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shopping.R;
import com.atguigu.shopping.home.bean.TypeListBean;
import com.atguigu.shopping.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 刘闯 on 2017/3/6.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<TypeListBean.ResultEntity.PageDataEntity> datas;
    private final LayoutInflater inflater;


    public GoodsListAdapter(Context mContext, List<TypeListBean.ResultEntity.PageDataEntity> datas) {
        this.mContext = mContext;
        this.datas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_goods_list, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TypeListBean.ResultEntity.PageDataEntity entity = datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + entity.getFigure()).placeholder(R.drawable.new_img_loading_2).into(holder.ivHot);
        holder.tvName.setText(entity.getName());
        holder.tvPrice.setText("￥" + entity.getCover_price());
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_hot)
        ImageView ivHot;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_price)
        TextView tvPrice;

        public MyViewHolder(View inflate) {
            super(inflate);
            ButterKnife.inject(this, inflate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        listener.onItemClickListener(datas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        public void onItemClickListener(TypeListBean.ResultEntity.PageDataEntity data);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }
}
