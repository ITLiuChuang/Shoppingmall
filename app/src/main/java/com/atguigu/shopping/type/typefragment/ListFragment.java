package com.atguigu.shopping.type.typefragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shopping.R;
import com.atguigu.shopping.base.BaseFragment;
import com.atguigu.shopping.type.adapter.TypeLeftAdapter;
import com.atguigu.shopping.type.adapter.TypeRightAdapter;
import com.atguigu.shopping.type.bean.TypeBean;
import com.atguigu.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by 刘闯 on 2017/3/2.
 */

public class ListFragment extends BaseFragment {


    @InjectView(R.id.lv_left)
    ListView lvLeft;
    @InjectView(R.id.rv_right)
    RecyclerView rvRight;
    /**
     * 联网集合
     */
    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};

    private TypeLeftAdapter leftAdapter;
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};
    private TypeRightAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_list, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //设置适配器
        leftAdapter = new TypeLeftAdapter(mContext, titles);
        lvLeft.setAdapter(leftAdapter);
        //设置item的点击事件监听
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到点击的位置
                leftAdapter.changeSelected(position);
                //刷新适配器
                leftAdapter.notifyDataSetChanged();

                //联网请求
                getDataFromNet(urls[position]);
            }
        });
        //联网请求
        getDataFromNet(urls[0]);
    }

    private void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "联网请求失败" + e.getMessage());

            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "联网请求成功");
                processData(response);
            }
        });
    }

    private void processData(String response) {
        TypeBean typeBean = JSON.parseObject(response, TypeBean.class);
        List<TypeBean.ResultBean> result = typeBean.getResult();
        if(result!=null&&result.size()>0) {
         //设置适配器
            adapter = new TypeRightAdapter(mContext,result);
            rvRight.setAdapter(adapter);

            //设置管理器
            GridLayoutManager manager = new GridLayoutManager(mContext, 3);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position ==0) {
                        return 3;
                    }else {
                        return 1;
                    }
                }
            });
            rvRight.setLayoutManager(manager);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
