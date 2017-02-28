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
import com.atguigu.shopping.shoppingcart.utils.CartStorage;
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
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    private final CheckBox checkboxDeleteAll;
    private String totalPrice;

    public ShoppingCartAdapter(Context mContent, List<GoodsBean> list, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox checkboxDeleteAll) {
        this.mContent = mContent;
        this.datas = list;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.checkboxDeleteAll = checkboxDeleteAll;
        showTotalPrice();
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("合计：" + getTotalPrice());
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
        final GoodsBean goodsBean = datas.get(position);
        Log.e("TAG", "datas" + datas.get(position));
        //绑定数据
        holder.cbGov.setChecked(goodsBean.isChecked());
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

        holder.addSubView.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void OnNumberChanger(int value) {
                //回调数量
                goodsBean.setNumber(value);
                CartStorage.getInstance(mContent).updataData(goodsBean);
                showTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 校验是否全选
     */
    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isChecked()) {
                    checkboxAll.setChecked(false);
                    checkboxDeleteAll.setChecked(false);
                } else {
                    number++;
                }
            }
            if (datas.size() == number) {
                checkboxDeleteAll.setChecked(true);
                checkboxAll.setChecked(true);

            }
        } else {
            checkboxAll.setChecked(false);
            checkboxDeleteAll.setChecked(false);
        }
    }

    /**
     * 删除
     */
    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChecked()) {
                    //内存删除
                    datas.remove(goodsBean);
                    //本地保存
                    CartStorage.getInstance(mContent).daleteData(goodsBean);
                    //刷新数据
                    notifyItemRemoved(i);
                    i--;
                }

            }

        }
    }

    public void checkAll_none(boolean isChecked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                //设置勾选状态
                goodsBean.setChecked(isChecked);
                checkboxAll.setChecked(isChecked);
                checkboxDeleteAll.setChecked(isChecked);
                //更新视图
                notifyItemChanged(i);
            }
        }

    }

    public double getTotalPrice() {
        double totalPrice = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChecked()) {
                    totalPrice += Double.parseDouble(goodsBean.getCover_price()) * goodsBean.getNumber();
                }
            }
        }
        return totalPrice;
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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemOnItemClick != null) {
                        itemOnItemClick.onItemClickListener(v, getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        public void onItemClickListener(View view, int position);
    }

    private OnItemClickListener itemOnItemClick;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.itemOnItemClick = l;

    }

}
