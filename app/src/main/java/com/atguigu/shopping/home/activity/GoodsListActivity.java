package com.atguigu.shopping.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shopping.R;
import com.atguigu.shopping.app.GoodsInfoActivity;
import com.atguigu.shopping.home.adapter.ExpandableListViewAdapter;
import com.atguigu.shopping.home.adapter.GoodsListAdapter;
import com.atguigu.shopping.home.adapter.HomeAdapter;
import com.atguigu.shopping.home.bean.GoodsBean;
import com.atguigu.shopping.home.bean.TypeListBean;
import com.atguigu.shopping.home.homefragment.SearchActivity;
import com.atguigu.shopping.home.view.SpaceItemDecoration;
import com.atguigu.shopping.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class GoodsListActivity extends AppCompatActivity {


    @InjectView(R.id.ib_goods_list_back)
    ImageButton ibGoodsListBack;
    @InjectView(R.id.tv_goods_list_search)
    TextView tvGoodsListSearch;
    @InjectView(R.id.ib_goods_list_home)
    ImageButton ibGoodsListHome;
    @InjectView(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @InjectView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @InjectView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @InjectView(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @InjectView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @InjectView(R.id.ll_goods_list_head)
    LinearLayout llGoodsListHead;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.ib_drawer_layout_back)
    ImageButton ibDrawerLayoutBack;
    @InjectView(R.id.tv_ib_drawer_layout_title)
    TextView tvIbDrawerLayoutTitle;
    @InjectView(R.id.ib_drawer_layout_confirm)
    TextView ibDrawerLayoutConfirm;
    @InjectView(R.id.rb_select_hot)
    RadioButton rbSelectHot;
    @InjectView(R.id.rb_select_new)
    RadioButton rbSelectNew;
    @InjectView(R.id.rg_select)
    RadioGroup rgSelect;
    @InjectView(R.id.tv_drawer_price)
    TextView tvDrawerPrice;
    @InjectView(R.id.tv_drawer_recommend)
    TextView tvDrawerRecommend;
    @InjectView(R.id.rl_select_recommend_theme)
    RelativeLayout rlSelectRecommendTheme;
    @InjectView(R.id.tv_drawer_type)
    TextView tvDrawerType;
    @InjectView(R.id.rl_select_type)
    RelativeLayout rlSelectType;
    @InjectView(R.id.btn_select_all)
    Button btnSelectAll;
    @InjectView(R.id.ll_select_root)
    LinearLayout llSelectRoot;
    @InjectView(R.id.btn_drawer_layout_cancel)
    Button btnDrawerLayoutCancel;
    @InjectView(R.id.btn_drawer_layout_confirm)
    Button btnDrawerLayoutConfirm;
    @InjectView(R.id.iv_price_no_limit)
    CheckBox ivPriceNoLimit;
    @InjectView(R.id.rl_price_nolimit)
    RelativeLayout rlPriceNolimit;
    @InjectView(R.id.iv_price_0_15)
    CheckBox ivPrice015;
    @InjectView(R.id.rl_price_0_15)
    RelativeLayout rlPrice015;
    @InjectView(R.id.iv_price_15_30)
    CheckBox ivPrice1530;
    @InjectView(R.id.rl_price_15_30)
    RelativeLayout rlPrice1530;
    @InjectView(R.id.iv_price_30_50)
    CheckBox ivPrice3050;
    @InjectView(R.id.rl_price_30_50)
    RelativeLayout rlPrice3050;
    @InjectView(R.id.iv_price_50_70)
    CheckBox ivPrice5070;
    @InjectView(R.id.rl_price_50_70)
    RelativeLayout rlPrice5070;
    @InjectView(R.id.iv_price_70_100)
    CheckBox ivPrice70100;
    @InjectView(R.id.rl_price_70_100)
    RelativeLayout rlPrice70100;
    @InjectView(R.id.iv_price_100)
    CheckBox ivPrice100;
    @InjectView(R.id.rl_price_100)
    RelativeLayout rlPrice100;
    @InjectView(R.id.et_price_start)
    EditText etPriceStart;
    @InjectView(R.id.v_price_line)
    View vPriceLine;
    @InjectView(R.id.et_price_end)
    EditText etPriceEnd;
    @InjectView(R.id.rl_select_price)
    RelativeLayout rlSelectPrice;
    @InjectView(R.id.ll_price_root)
    LinearLayout llPriceRoot;
    @InjectView(R.id.btn_drawer_theme_cancel)
    Button btnDrawerThemeCancel;
    @InjectView(R.id.btn_drawer_theme_confirm)
    Button btnDrawerThemeConfirm;
    @InjectView(R.id.iv_theme_all)
    CheckBox ivThemeAll;
    @InjectView(R.id.rl_theme_all)
    RelativeLayout rlThemeAll;
    @InjectView(R.id.iv_theme_note)
    CheckBox ivThemeNote;
    @InjectView(R.id.rl_theme_note)
    RelativeLayout rlThemeNote;
    @InjectView(R.id.iv_theme_funko)
    CheckBox ivThemeFunko;
    @InjectView(R.id.rl_theme_funko)
    RelativeLayout rlThemeFunko;
    @InjectView(R.id.iv_theme_gsc)
    CheckBox ivThemeGsc;
    @InjectView(R.id.rl_theme_gsc)
    RelativeLayout rlThemeGsc;
    @InjectView(R.id.iv_theme_origin)
    CheckBox ivThemeOrigin;
    @InjectView(R.id.rl_theme_origin)
    RelativeLayout rlThemeOrigin;
    @InjectView(R.id.iv_theme_sword)
    CheckBox ivThemeSword;
    @InjectView(R.id.rl_theme_sword)
    RelativeLayout rlThemeSword;
    @InjectView(R.id.iv_theme_food)
    CheckBox ivThemeFood;
    @InjectView(R.id.rl_theme_food)
    RelativeLayout rlThemeFood;
    @InjectView(R.id.iv_theme_moon)
    CheckBox ivThemeMoon;
    @InjectView(R.id.rl_theme_moon)
    RelativeLayout rlThemeMoon;
    @InjectView(R.id.iv_theme_quanzhi)
    CheckBox ivThemeQuanzhi;
    @InjectView(R.id.rl_theme_quanzhi)
    RelativeLayout rlThemeQuanzhi;
    @InjectView(R.id.iv_theme_gress)
    CheckBox ivThemeGress;
    @InjectView(R.id.rl_theme_gress)
    RelativeLayout rlThemeGress;
    @InjectView(R.id.ll_theme_root)
    LinearLayout llThemeRoot;
    @InjectView(R.id.btn_drawer_type_cancel)
    Button btnDrawerTypeCancel;
    @InjectView(R.id.btn_drawer_type_confirm)
    Button btnDrawerTypeConfirm;
    @InjectView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @InjectView(R.id.ll_type_root)
    LinearLayout llTypeRoot;
    @InjectView(R.id.dl_left)
    DrawerLayout dlLeft;
    @InjectView(R.id.ck_price)
    CheckBox ckPrice;
    /**
     * 价格
     */
    private String tempPrice = "不限";
    /**
     * 主题
     */
    private String tempTheme = "全部";
    /**
     * 类别
     */
    private String tempType = "日常";
    /**
     * 请求网络
     */
    private String[] urls = new String[]{
            Constants.CLOSE_STORE,
            Constants.GAME_STORE,
            Constants.COMIC_STORE,
            Constants.COSPLAY_STORE,
            Constants.GUFENG_STORE,
            Constants.STICK_STORE,
            Constants.WENJU_STORE,
            Constants.FOOD_STORE,
            Constants.SHOUSHI_STORE,
    };
    private int position;
    private GoodsListAdapter adapter;
    private int click_count;
    private ArrayList<String> group;
    private ArrayList<List<String>> child;
    private ExpandableListViewAdapter expandableListViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.inject(this);
        getData();
    }

    private void getData() {
        position = getIntent().getIntExtra("position", 0);
        getDataFromNet(urls[position]);
        initView();
    }

    private void initView() {
        //设置综合排序高亮显示
        tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));

        //价格设置默认
        tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));

        //筛选设置默认
        tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));

        showSelectorLayout();//默认显示筛选页面
    }

    private void getDataFromNet(String url) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "联网失败了" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "联网成功了");
                processData(response);
            }
        });
    }

    private void processData(String json) {
        TypeListBean typeListBean = JSON.parseObject(json, TypeListBean.class);
        // Log.e("TAG",typeListBean.getResult().getPage_data().get(0).getName());
        List<TypeListBean.ResultEntity.PageDataEntity> datas = typeListBean.getResult().getPage_data();
        if (datas != null && datas.size() > 0) {
            //设置适配器
            adapter = new GoodsListAdapter(this, datas);
            recyclerview.setAdapter(adapter);
            //设置布局管理器
            recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerview.addItemDecoration(new SpaceItemDecoration(10));

            adapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(TypeListBean.ResultEntity.PageDataEntity data) {
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(data.getName());
                    goodsBean.setFigure(data.getFigure());
                    goodsBean.setCover_price(data.getCover_price());
                    goodsBean.setProduct_id(data.getProduct_id());

                    Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                    intent.putExtra(HomeAdapter.GOODS_BEAN, goodsBean);
                    startActivity(intent);

                }
            });
        }

    }

    @OnClick({R.id.ib_goods_list_back, R.id.tv_goods_list_search, R.id.ib_goods_list_home, R.id.tv_goods_list_sort, R.id.tv_goods_list_price, R.id.tv_goods_list_select, R.id.ib_drawer_layout_back, R.id.ib_drawer_layout_confirm, R.id.rl_select_price, R.id.rl_select_recommend_theme, R.id.rl_select_type, R.id.btn_drawer_layout_cancel, R.id.btn_drawer_layout_confirm, R.id.btn_drawer_theme_cancel, R.id.btn_drawer_theme_confirm, R.id.btn_drawer_type_cancel, R.id.btn_drawer_type_confirm, R.id.iv_price_no_limit, R.id.iv_price_0_15, R.id.iv_price_15_30, R.id.iv_price_30_50, R.id.iv_price_50_70, R.id.iv_price_70_100, R.id.iv_price_100, R.id.iv_theme_all, R.id.iv_theme_note, R.id.iv_theme_funko, R.id.iv_theme_gsc, R.id.iv_theme_origin, R.id.iv_theme_sword, R.id.iv_theme_food, R.id.iv_theme_moon, R.id.iv_theme_quanzhi, R.id.iv_theme_gress, R.id.ck_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_list_back:
                finish();
                break;
            case R.id.tv_goods_list_search:
//                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoodsListActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ib_goods_list_home:
                finish();
                break;
            case R.id.tv_goods_list_sort:
//                Toast.makeText(this, "综合排序", Toast.LENGTH_SHORT).show();
                click_count = 0;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
                //设置综合排序高亮显示
                tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));

                //价格设置默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));

                //筛选设置默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                break;
            case R.id.tv_goods_list_price:
//                Toast.makeText(this, "价格", Toast.LENGTH_SHORT).show();
                //设置综合排序设置默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));

                //价格设置高亮显示
                tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));

                //筛选设置默认
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                click_count++;

                if (click_count % 2 == 1) {
                    // 箭头向下红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);
                } else {
                    // 箭头向上红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                }
                break;
            case R.id.tv_goods_list_select:
//                Toast.makeText(this, "筛选", Toast.LENGTH_SHORT).show();
                click_count = 0;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
                //设置综合排序默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));

                //价格设置默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));

                //筛选设置高亮显示
                tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));
                //打开DrawLayout
                dlLeft.openDrawer(Gravity.RIGHT);
                break;
            case R.id.ib_drawer_layout_back:
                //打开DrawLayout
                dlLeft.closeDrawers();
                break;
            case R.id.ib_drawer_layout_confirm:
                getDataFromNet(urls[position]);
                Toast.makeText(this, "价格:" + tempPrice + " 主题:" + tempTheme + " 类别" + tempType + "", Toast.LENGTH_SHORT).show();
                dlLeft.closeDrawers();
                break;
            case R.id.rl_select_price://显示价格
                llPriceRoot.setVisibility(View.VISIBLE);
                showPriceLayout();

                break;
            case R.id.rl_select_recommend_theme://显示主题
                llThemeRoot.setVisibility(View.VISIBLE);
                showThemeLayout();
                break;
            case R.id.rl_select_type://类别
                llTypeRoot.setVisibility(View.VISIBLE);
                showTypeLayout();
                break;
            case R.id.btn_drawer_layout_cancel:
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_layout_confirm://价格--确定
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                tvDrawerPrice.setText(tempPrice);
                break;
            case R.id.btn_drawer_theme_cancel:
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_theme_confirm://主题--确定
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                tvDrawerRecommend.setText(tempTheme);
                break;
            case R.id.btn_drawer_type_cancel:
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_type_confirm://类别--确定
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                tvDrawerType.setText(tempType);
                break;
            case R.id.iv_price_no_limit:
                showSelector();
                ivPriceNoLimit.setChecked(true);
                tempPrice = "不限";
                break;
            case R.id.iv_price_0_15:
                showSelector();
                ivPrice015.setChecked(true);
                tempPrice = "0-15";
                break;
            case R.id.iv_price_15_30:
                showSelector();
                ivPrice1530.setChecked(true);
                tempPrice = "15-30";
                break;
            case R.id.iv_price_30_50:
                showSelector();
                ivPrice3050.setChecked(true);
                tempPrice = "30-50";
                break;
            case R.id.iv_price_50_70:
                showSelector();
                ivPrice5070.setChecked(true);
                tempPrice = "50-70";
                break;
            case R.id.iv_price_70_100:
                showSelector();
                ivPrice70100.setChecked(true);
                tempPrice = "70-100";
                break;
            case R.id.iv_price_100:
                showSelector();
                ivPrice100.setChecked(true);
                tempPrice = "100以上";
                break;
            case R.id.ck_price:
                showSelector();
                ckPrice.setChecked(true);
                tempPrice = etPriceStart.getText().toString() + "-" + etPriceEnd.getText().toString();
                break;
            case R.id.iv_theme_all:
                showSelector();
                ivThemeAll.setChecked(true);
                tempTheme = "全部";
                break;
            case R.id.iv_theme_note:
                showSelector();
                ivThemeAll.setChecked(true);
                tempTheme = "盗墓笔记";
                break;
            case R.id.iv_theme_funko:
                showSelector();
                ivThemeFunko.setChecked(true);
                tempTheme = "FUNKO";
                break;
            case R.id.iv_theme_gsc:
                showSelector();
                ivThemeGsc.setChecked(true);
                tempTheme = "GSC";
                break;
            case R.id.iv_theme_origin:
                showSelector();
                ivThemeOrigin.setChecked(true);
                tempTheme = "古风原创";
                break;
            case R.id.iv_theme_sword:
                showSelector();
                ivThemeSword.setChecked(true);
                tempTheme = "剑侠情缘三";
                break;
            case R.id.iv_theme_food:
                showSelector();
                ivThemeFood.setChecked(true);
                tempTheme = "零食仓";
                break;
            case R.id.iv_theme_moon:
                showSelector();
                ivThemeMoon.setChecked(true);
                tempTheme = "秦时明月";
                break;
            case R.id.iv_theme_quanzhi:
                showSelector();
                ivThemeQuanzhi.setChecked(true);
                tempTheme = "全职高手";
                break;
            case R.id.iv_theme_gress:
                showSelector();
                ivThemeGress.setChecked(true);
                tempTheme = "长草颜文字";
                break;
        }
    }

    private void showSelector() {
        ivPriceNoLimit.setChecked(false);
        ivPrice015.setChecked(false);
        ivPrice1530.setChecked(false);
        ivPrice3050.setChecked(false);
        ivPrice5070.setChecked(false);
        ivPrice70100.setChecked(false);
        ivPrice100.setChecked(false);
        ckPrice.setChecked(false);
        ivThemeAll.setChecked(false);
        ivThemeNote.setChecked(false);
        ivThemeFunko.setChecked(false);
        ivThemeGsc.setChecked(false);
        ivThemeOrigin.setChecked(false);
        ivThemeFood.setChecked(false);
        ivThemeSword.setChecked(false);
        ivThemeMoon.setChecked(false);
        ivThemeQuanzhi.setChecked(false);
        ivThemeGress.setChecked(false);

    }

    //筛选页面
    private void showSelectorLayout() {
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }


    //价格页面
    private void showPriceLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //主题页面
    private void showThemeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //类别页面
    private void showTypeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);

        //初始化ExpandableListView
        initExpandableListView();
    }

    private void initExpandableListView() {
        //创建集合
        group = new ArrayList<>();
        child = new ArrayList<>();

        //添加数据
        addInfo("全部", new String[]{});
        addInfo("上衣", new String[]{"古风", "和风", "lolita", "日常"});
        addInfo("下装", new String[]{"日常", "泳衣", "汉风", "lolita", "创意T恤"});
        addInfo("外套", new String[]{"汉风", "古风", "lolita", "胖次", "南瓜裤", "日常"});

        //设置适配器
        expandableListViewAdapter = new ExpandableListViewAdapter(this, group, child);
        expandableListView.setAdapter(expandableListViewAdapter);

        //设置孩子的点击事件
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //把位置传入适配器中
                expandableListViewAdapter.isChildSelectable(groupPosition, childPosition);
                //刷新
                expandableListViewAdapter.notifyDataSetChanged();
                tempType = child.get(groupPosition).get(childPosition);
                return true;
            }
        });

        // 这里是控制如果列表没有孩子菜单不展开的效果
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (child.get(groupPosition).isEmpty()) {
                    return true;
                } else {
                    return false;

                }
            }
        });

    }

    /**
     * 添加数据信息
     *
     * @param g
     * @param c
     */
    private void addInfo(String g, String[] c) {
        group.add(g);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < c.length; i++) {
            list.add(c[i]);
        }
        child.add(list);
    }

}
