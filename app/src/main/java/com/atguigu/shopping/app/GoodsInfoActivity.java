package com.atguigu.shopping.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shopping.MainActivity;
import com.atguigu.shopping.R;
import com.atguigu.shopping.home.adapter.HomeAdapter;
import com.atguigu.shopping.home.bean.GoodsBean;
import com.atguigu.shopping.shoppingcart.utils.CartStorage;
import com.atguigu.shopping.shoppingcart.view.AddSubView;
import com.atguigu.shopping.utils.Constants;
import com.atguigu.shopping.utils.VirtualkeyboardHeight;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GoodsInfoActivity extends AppCompatActivity {

    @InjectView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @InjectView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @InjectView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @InjectView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @InjectView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @InjectView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @InjectView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @InjectView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @InjectView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @InjectView(R.id.progressbar)
    ProgressBar progressbar;
    @InjectView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @InjectView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @InjectView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @InjectView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @InjectView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @InjectView(R.id.tv_more_share)
    TextView tvMoreShare;
    @InjectView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @InjectView(R.id.tv_more_home)
    TextView tvMoreHome;
    @InjectView(R.id.btn_more)
    Button btnMore;
    @InjectView(R.id.ll_root)
    LinearLayout llRoot;
    private GoodsBean goodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.inject(this);
        getData();

    }

    public void getData() {

        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeAdapter.GOODS_BEAN);
        setData();
    }

    private void setData() {
        //设置图片
        Glide.with(this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).placeholder(R.drawable.new_user_icon_background).into(ivGoodInfoImage);
        //设置名称和价格
        tvGoodInfoName.setText(goodsBean.getName());
        tvGoodInfoPrice.setText("¥" + goodsBean.getCover_price());
        //设置网路加载
        setLoadWab("http://mp.weixin.qq.com/s/Cf3DrW2lnlb-w4wYaxOEZg");
    }

    private void setLoadWab(String url) {
        WebSettings settings = wbGoodInfoMore.getSettings();
        //支持js
        settings.setJavaScriptEnabled(true);
        //添加缩放按钮
        settings.setBuiltInZoomControls(true);
        //添加双击缩放
        settings.setUseWideViewPort(true);
        //添加WebViewClient,不添加会调用系统浏览器
        wbGoodInfoMore.setWebViewClient(new WebViewClient() {
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
        wbGoodInfoMore.loadUrl(url);
    }

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.btn_good_info_addcart, R.id.tv_more_search, R.id.tv_more_home, R.id.btn_more, R.id.tv_more_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.ib_good_info_more:
                if (llRoot.isShown()) {
                    llRoot.setVisibility(View.GONE);
                } else {
                    llRoot.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_good_info_callcenter:
//                Toast.makeText(GoodsInfoActivity.this, "联系客服", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoodsInfoActivity.this, CallCenterActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_good_info_collection:
                Toast.makeText(GoodsInfoActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
//                Toast.makeText(GoodsInfoActivity.this, "购物车", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("checkedid",R.id.rb_cart);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_good_info_addcart:
//                Toast.makeText(GoodsInfoActivity.this, "添加购物车", Toast.LENGTH_SHORT).show();
//                CartStorage.getInstance(this).addData(goodsBean);
                showPopwindow();
                break;

            case R.id.tv_more_search:
                Toast.makeText(GoodsInfoActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_share:
//                Toast.makeText(GoodsInfoActivity.this, "分享", Toast.LENGTH_SHORT).show();
                 intent = new Intent(GoodsInfoActivity.this, ZXingActivity.class);
                intent.putExtra("image",goodsBean.getFigure());
                startActivity(intent);

                break;
            case R.id.tv_more_home:
                finish();
                break;
            case R.id.btn_more:
                llRoot.setVisibility(View.GONE);
                break;


        }
    }

    //缓存
    private GoodsBean tempGoodsBean;

    private boolean isExist = false;

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {

        tempGoodsBean = CartStorage.getInstance(this).findDete(goodsBean.getProduct_id());
        if (tempGoodsBean == null) {
            isExist = false;
            tempGoodsBean = goodsBean;
        } else {
            isExist = true;
        }
        // 1 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_add_product, null);

        // 2下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 3 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);


        // 4 控件处理
        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        AddSubView nas_goodinfo_num = (AddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);

        // 加载图片
        Glide.with(GoodsInfoActivity.this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(iv_goodinfo_photo);

        // 名称
        tv_goodinfo_name.setText(tempGoodsBean.getName());
        // 显示价格
        tv_goodinfo_price.setText(tempGoodsBean.getCover_price());

        // 设置最大值和当前值
        nas_goodinfo_num.setMaxValue(100);//库存100
        nas_goodinfo_num.setValue(1);

        nas_goodinfo_num.setValue(tempGoodsBean.getNumber());

        nas_goodinfo_num.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void OnNumberChanger(int value) {
                tempGoodsBean.setNumber(value);
            }
        });


        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                if (isExist && tempGoodsBean.getNumber() == 1) {
                    tempGoodsBean.setNumber(tempGoodsBean.getNumber() + 1);
                }
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                //添加购物车
                CartStorage.getInstance(GoodsInfoActivity.this).updataData(tempGoodsBean);
                Log.e("TAG", "66:" + tempGoodsBean.toString());
                Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

        // 5 在底部显示
        window.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.ll_goods_root),
                Gravity.BOTTOM, 0, VirtualkeyboardHeight.getBottomStatusHeight(GoodsInfoActivity.this));

    }


}
