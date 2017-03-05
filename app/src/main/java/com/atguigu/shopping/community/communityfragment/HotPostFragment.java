package com.atguigu.shopping.community.communityfragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shopping.R;
import com.atguigu.shopping.base.BaseFragment;
import com.atguigu.shopping.community.adapter.HotPostListViewAdapter;
import com.atguigu.shopping.community.bean.NewPostBean;
import com.atguigu.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by 刘闯 on 2017/3/4.
 */

public class HotPostFragment extends BaseFragment {


    @InjectView(R.id.lv_hot_post)
    ListView lvHotPost;
    private HotPostListViewAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hot_post, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {

        OkHttpUtils.get()
                .url(Constants.NEW_POST_URL)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "新帖联网成功==");
                        processData(response);
                    }
                });
    }

    private void processData(String json) {
        NewPostBean bean = JSON.parseObject(json, NewPostBean.class);
        List<NewPostBean.ResultEntity> result = bean.getResult();
        if (result != null && result.size() > 0) {
            //设置适配器
            adapter = new HotPostListViewAdapter(mContext, result);
            lvHotPost.setAdapter(adapter);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
