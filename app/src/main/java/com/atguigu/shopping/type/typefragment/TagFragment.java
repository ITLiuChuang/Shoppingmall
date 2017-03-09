package com.atguigu.shopping.type.typefragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shopping.R;
import com.atguigu.shopping.base.BaseFragment;
import com.atguigu.shopping.type.adapter.TagGridViewAdapter;
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

public class TagFragment extends BaseFragment {


    @InjectView(R.id.gv_tag)
    GridView gvTag;
    private List<TypeBean.ResultBean> result;
    private TagGridViewAdapter adapter;
    private LayoutInflater inflater;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.inject(this, view);
        inflater = LayoutInflater.from(getActivity());
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.TAG_URL)
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
        result = typeBean.getResult();
        if (result != null && result.size() > 0) {
            //设置适配器
            adapter = new TagGridViewAdapter(mContext, result);

            gvTag.setAdapter(adapter);


            //设置item的点击事件
            gvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TypeBean.ResultBean resultBean = result.get(position);
                    Toast.makeText(mContext, "" + resultBean.toString(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }



}
