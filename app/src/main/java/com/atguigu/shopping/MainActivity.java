package com.atguigu.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.atguigu.shopping.base.BaseFragment;
import com.atguigu.shopping.community.communityfragment.CommunityFragment;
import com.atguigu.shopping.home.homefragment.HomeFragment;
import com.atguigu.shopping.shoppingcart.shoppingcartfragment.ShoppingCartFragment;
import com.atguigu.shopping.type.typefragment.TypeFragment;
import com.atguigu.shopping.user.userfragment.UserFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.rg_main)
    RadioGroup rgMain;
    /**
     * 集合,装Fragment
     */
    private ArrayList<BaseFragment> fragments;
    /**
     * Fragment的下标
     */
    private int position;
    /**
     * 当前的Fragment
     */
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //初始化Fragment
        initFragment();

        //监听
        initListener();

    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }
                /**
                 * 根据位置获取不同的Fragment
                 */
                Fragment currentFragment = fragments.get(position);
                switchFragment(currentFragment);
            }
        });
        //默然为首页
        rgMain.check(R.id.rb_home);
    }

    private void switchFragment(Fragment currentFragment) {
        //判断切换的页面是不是当前页面
        if (tempFragment != currentFragment) {
            //开启事物,得到Fragmentmagre
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //判断是否添加过
            if (!currentFragment.isAdded()) {
                //隐藏缓存
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }

                //添加
                ft.add(R.id.fl_mian, currentFragment);
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

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int id = intent.getIntExtra("checkedid", R.id.rb_home);
        rgMain.check(id);
    }
}
