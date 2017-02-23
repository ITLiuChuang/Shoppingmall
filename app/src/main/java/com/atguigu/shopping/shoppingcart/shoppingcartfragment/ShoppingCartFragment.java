package com.atguigu.shopping.shoppingcart.shoppingcartfragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shopping.base.BaseFragment;

/**
 * Created by 刘闯 on 2017/2/22.
 */

public class ShoppingCartFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContent);


        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("购物车");

    }
}
