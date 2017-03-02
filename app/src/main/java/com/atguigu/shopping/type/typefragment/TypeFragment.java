package com.atguigu.shopping.type.typefragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.atguigu.shopping.R;
import com.atguigu.shopping.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 刘闯 on 2017/2/22.
 */

public class TypeFragment extends BaseFragment {


    @InjectView(R.id.tl_1)
    SegmentTabLayout tl1;
    @InjectView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @InjectView(R.id.fl_type)
    FrameLayout flType;
    String[] titls = {"分类", "标签"};
    private List<BaseFragment> fragments;
    private Fragment tempFragment;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initSegmentTabLayout();
        initFragment();
    }

    private void initSegmentTabLayout() {
        tl1.setTabData(titls);
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
            //    Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new TagFragment());
        //moren显示页面
        switchFragment(fragments.get(0));
    }

    private void switchFragment(BaseFragment currentFragment) {
        //判断切换的页面是不是当前页面

        if (tempFragment != currentFragment) {
            //开启事物,得到Fragmentmagre
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            //判断是否添加过
            if (!currentFragment.isAdded()) {
                //隐藏缓存
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }

                //添加
                ft.add(R.id.fl_type, currentFragment);
            } else {
                //隐藏缓存
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //显示
                ft.show(currentFragment);
            }
            //提交事物
            ft.commit();
            //把当前的赋值为缓存的
            tempFragment = currentFragment;
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.iv_type_search)
    public void onClick() {
        Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
    }
}
