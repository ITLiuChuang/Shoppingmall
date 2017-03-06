package com.atguigu.shopping.community.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.shopping.base.BaseFragment;

import java.util.List;

/**
 * Created by 刘闯 on 2017/3/4.
 */

public class CommunityViewPagerAdapter extends FragmentPagerAdapter {
    private final List<BaseFragment> fragments;
    private String[] titles = new String[]{"新帖", "热帖"};

    public CommunityViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 显示TabLayout的标题才用到的
     * @param position
     * @return
     */
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
