package com.atguigu.shopping.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 刘闯 on 2017/2/22.
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 上下文
     */
    public Context mContent;

    /**
     * 当Fragment被创建调用
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = getActivity();
    }

    /**
     * 实例化视图的时候系统调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 当子类需要把数据绑定到控件上
     *
     * 或需要联网请求,把数据绑定到视图上的时候重写该方法
     */
    public void initData() {

    }

    /**
     * 子类实现,显示不同的视图
     * @return
     */
    public abstract View initView();


}
