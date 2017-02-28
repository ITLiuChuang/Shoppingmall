package com.atguigu.shopping.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shopping.R;
import com.atguigu.shopping.home.bean.GoodsBean;
import com.atguigu.shopping.shoppingcart.view.AddSubView;
import com.atguigu.shopping.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 刘闯 on 2017/2/28.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHoler> {
    private final Context mContent;
    private final List<GoodsBean> datas;

    public ShoppingCartAdapter(Context mContent, List<GoodsBean> list) {
        this.mContent = mContent;
        this.datas = list;
    }

    /**
     * 创建viewholder   创建视图
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHoler(View.inflate(mContent, R.layout.item_shop_cart, null));
    }

    @Override
    public void onBindViewHolder(MyViewHoler holder, int position) {

        //得到数据
        GoodsBean goodsBean = datas.get(position);
        Log.e("TAG", "datas" + datas.get(position));

        //图片
        Glide.with(mContent).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.ivGov);
        //设置名称
        holder.tvDescGov.setText(goodsBean.getName());
        //设置价格
        holder.tvPriceGov.setText("¥" + goodsBean.getCover_price());
        //设置数量
        holder.addSubView.setValue(goodsBean.getNumber());

        //设置最小值与库存
        holder.addSubView.getMinValue(1);
        holder.addSubView.getMaxValue(100);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHoler extends RecyclerView.ViewHolder {
        @InjectView(R.id.cb_gov)
        CheckBox cbGov;
        @InjectView(R.id.iv_gov)
        ImageView ivGov;
        @InjectView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @InjectView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @InjectView(R.id.addSubView)
        AddSubView addSubView;

        MyViewHoler(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }


}
