package com.atguigu.shopping.community.communityfragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shopping.base.BaseFragment;

/**
 * Created by 刘闯 on 2017/3/4.
 */

public class HotPostFragment extends BaseFragment {

    private TextView view;

    @Override
    public View initView() {
        view = new TextView(mContext);
        view.setTextColor(Color.RED);
        view.setTextSize(20);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        view.setText("热帖Fragment");
    }
}
