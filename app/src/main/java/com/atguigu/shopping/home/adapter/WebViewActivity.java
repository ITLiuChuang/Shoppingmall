package com.atguigu.shopping.home.adapter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shopping.R;
import com.atguigu.shopping.app.GoodsInfoActivity;
import com.atguigu.shopping.home.bean.GoodsBean;
import com.atguigu.shopping.home.bean.H5Bean;
import com.atguigu.shopping.home.bean.WebViewBean;
import com.atguigu.shopping.utils.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.atguigu.shopping.home.adapter.HomeAdapter.GOODS_BEAN;

public class WebViewActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_more)
    ImageButton ibMore;
    @InjectView(R.id.webview)
    WebView webview;
    @InjectView(R.id.progressbar)
    ProgressBar progressbar;
    @InjectView(R.id.activity_web_view)
    LinearLayout activityWebView;
    private Object data;
    private WebViewBean webViewBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.inject(this);
        getData();
    }

    private void getData() {
        webViewBean = (WebViewBean) getIntent().getSerializableExtra(HomeAdapter.WEBVIEW_BEAN);
        tvTitle.setText(webViewBean.getName());

        //加载连接地址
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.addJavascriptInterface(new MyJavascriptInterface(), "cyc");
        webview.loadUrl(Constants.BASE_URL_IMAGE + webViewBean.getUrl());
    }


    @OnClick({R.id.ib_back, R.id.ib_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_more:
                Toast.makeText(WebViewActivity.this, "更多", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    class MyJavascriptInterface {
        @JavascriptInterface
        public void jumpForAndroid(String data) {
//            Toast.makeText(WebViewActivity.this, "data = " + data, Toast.LENGTH_SHORT).show();
            if(!TextUtils.isEmpty(data)){
                H5Bean h5Bean =  JSON.parseObject(data,H5Bean.class);
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setProduct_id(h5Bean.getValue().getProduct_id()+"");
                goodsBean.setCover_price("10080");
                goodsBean.setFigure("/1478770583834.png");
                goodsBean.setName("尚硅谷Android");
                Intent intent = new Intent(WebViewActivity.this, GoodsInfoActivity.class);
                intent.putExtra(GOODS_BEAN,goodsBean);
                startActivity(intent);
            }
        }
    }
}
