package com.atguigu.shopping.home.homefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shopping.R;
import com.atguigu.shopping.app.GoodsInfoActivity;
import com.atguigu.shopping.base.BaseFragment;
import com.atguigu.shopping.home.adapter.HomeAdapter;
import com.atguigu.shopping.home.bean.GoodsBean;
import com.atguigu.shopping.home.bean.HomeBean;
import com.atguigu.shopping.utils.Constants;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.atguigu.shopping.home.adapter.HomeAdapter.GOODS_BEAN;

/**
 * Created by 刘闯 on 2017/2/22.
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.tv_search_home)
    TextView tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(R.id.ib_top)
    ImageButton ibTop;
    @InjectView(R.id.ll_main_scan)
    LinearLayout llMainScan;
    private TextView textView;
    private Object dataFromNet;
    private HomeAdapter homeAdapter;
    private int REQUEST_CODE;

    private HomeBean homeBean;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils.get()
                .url(Constants.HOME_URL)
                .id(100).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "成功失败");
                        processData(response);
                    }
                });

    }

    /**
     * 解析数据
     * 设置适配器
     *
     * @param response
     */
    private void processData(String response) {
        homeBean = new Gson().fromJson(response, HomeBean.class);
        Log.e("TAG", "解析数据成功");

        homeAdapter = new HomeAdapter(mContext, homeBean.getResult());
        rvHome.setAdapter(homeAdapter);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        //设置布局管理器
        rvHome.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    ibTop.setVisibility(View.GONE);
                } else {
                    ibTop.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top, R.id.ll_main_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
//                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_message_home:
                Toast.makeText(mContext, "消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
//                Toast.makeText(mContext, "返回顶部", Toast.LENGTH_SHORT).show();
                rvHome.scrollToPosition(0);
                break;
            case R.id.ll_main_scan:
                intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    getGoodBean(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void getGoodBean(String result) {
        List<HomeBean.ResultBean.RecommendInfoBean> recommend_info = homeBean.getResult().getRecommend_info();
        for (int i = 0; i < recommend_info.size(); i++) {
            GoodsBean goodsBean = new GoodsBean();
            if (result.equals(recommend_info.get(i).getFigure())) {
                goodsBean.setName(recommend_info.get(i).getName());
                goodsBean.setCover_price(recommend_info.get(i).getCover_price());
                goodsBean.setFigure(recommend_info.get(i).getFigure());

                Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                intent.putExtra(GOODS_BEAN, goodsBean);
                mContext.startActivity(intent);
            }
        }


    }
}
