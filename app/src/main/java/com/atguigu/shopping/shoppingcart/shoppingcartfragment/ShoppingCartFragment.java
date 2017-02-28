package com.atguigu.shopping.shoppingcart.shoppingcartfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shopping.R;
import com.atguigu.shopping.base.BaseFragment;
import com.atguigu.shopping.home.bean.GoodsBean;
import com.atguigu.shopping.shoppingcart.adapter.ShoppingCartAdapter;
import com.atguigu.shopping.shoppingcart.utils.CartStorage;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 刘闯 on 2017/2/22.
 */

public class ShoppingCartFragment extends BaseFragment {


    @InjectView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @InjectView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @InjectView(R.id.btn_check_out)
    Button btnCheckOut;
    @InjectView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @InjectView(R.id.checkbox_delete_all)
    CheckBox checkboxDeleteAll;
    @InjectView(R.id.btn_delete)
    Button btnDelete;
    @InjectView(R.id.btn_collection)
    Button btnCollection;
    @InjectView(R.id.ll_delete)
    LinearLayout llDelete;
    @InjectView(R.id.iv_empty)
    ImageView ivEmpty;
    @InjectView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @InjectView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    private ShoppingCartAdapter adapter;
    private List<GoodsBean> list;

    @Override
    public View initView() {
        View view = View.inflate(mContent, R.layout.fragment_shopping_cart, null);
        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    public void initData() {
        super.initData();
        list = CartStorage.getInstance(mContent).getAllData();
        Toast.makeText(mContent, "list = " + list.size(), Toast.LENGTH_SHORT).show();
        if (list != null && list.size() > 0) {
            //有数据
            llEmptyShopcart.setVisibility(View.GONE);
            adapter = new ShoppingCartAdapter(mContent, list, tvShopcartTotal, checkboxAll, checkboxDeleteAll);
            //设置适配器
            recyclerview.setAdapter(adapter);
            //设置管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContent, LinearLayoutManager.VERTICAL, false));
            //点击事件监听
            adapter.setOnItemClickListener(new ShoppingCartAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {
                    //设置Bean对象取反
                    GoodsBean goodsBean = list.get(position);
                    goodsBean.setChecked(!goodsBean.isChecked());
                    adapter.notifyItemChanged(position);
                    //刷新价格
                    adapter.showTotalPrice();
                    //校验是否全选
                    adapter.checkAll();

                }
            });
            //校验是否全选
            adapter.checkAll();
        } else {
            //没数据
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_shopcart_edit, R.id.checkbox_all, R.id.btn_check_out, R.id.checkbox_delete_all, R.id.btn_delete, R.id.btn_collection, R.id.tv_empty_cart_tobuy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
                Toast.makeText(mContent, "编辑", Toast.LENGTH_SHORT).show();

                break;
            case R.id.checkbox_all:
                Toast.makeText(mContent, "全选", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_check_out:
                Toast.makeText(mContent, "结算", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_delete_all:
                Toast.makeText(mContent, "删除全选", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                Toast.makeText(mContent, "删除按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_collection:
                Toast.makeText(mContent, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_empty_cart_tobuy:
                Toast.makeText(mContent, "去逛逛", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
