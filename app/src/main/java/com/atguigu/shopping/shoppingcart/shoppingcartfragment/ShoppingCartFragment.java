package com.atguigu.shopping.shoppingcart.shoppingcartfragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shopping.MainActivity;
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

    //编辑状态
    public static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;
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
    private boolean isChecked;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        ButterKnife.inject(this, view);

        return view;
    }

    private void hideDelete() {
        //1.设置编辑
        tvShopcartEdit.setTag(ACTION_EDIT);
        //2.隐藏显示控件
        llCheckAll.setVisibility(View.VISIBLE);
        llDelete.setVisibility(View.GONE);
        //4.设置文本为--编辑
        tvShopcartEdit.setText("编辑");
        //5.把所有的数据设置成勾选状态
        if (adapter != null) {
            adapter.checkAll_none(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }
    }

    private void showDelete() {
        //1.设置完成
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        //2.显示删除控件
        llDelete.setVisibility(View.VISIBLE);
        //3.隐藏结算控件
        llCheckAll.setVisibility(View.GONE);
        //4.设置文本为--完成
        tvShopcartEdit.setText("完成");
        //5.把所有的数据设置非选择状态
        if (adapter != null) {
            adapter.checkAll_none(false);
            adapter.checkAll();

        }

    }


    @Override
    public void initData() {
        super.initData();
        showData();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        showData();
    }

    private void showData() {
        list = CartStorage.getInstance(mContext).getAllData();
        if (list != null && list.size() > 0) {
            //有数据
            llEmptyShopcart.setVisibility(View.GONE);
            adapter = new ShoppingCartAdapter(mContext, list, tvShopcartTotal, checkboxAll, checkboxDeleteAll);
            //设置适配器
            recyclerview.setAdapter(adapter);
            //设置管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
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
                //设置编辑状态
                tvShopcartEdit.setTag(ACTION_EDIT);
                tvShopcartEdit.setText("编辑");
                //显示要去结算的布局
                llCheckAll.setVisibility(View.VISIBLE);
                tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //1.得到状态
                        int action = (int) v.getTag();
                        //2.根据不同的状态做不同的处理
                        if (action == ACTION_EDIT) {
                            //切换完成状态
                            showDelete();
                        } else {
                            //切换成编辑状态
                            hideDelete();
                        }
                    }
                });
                break;
            case R.id.checkbox_all:
                Toast.makeText(mContext, "全选", Toast.LENGTH_SHORT).show();
                isChecked = checkboxAll.isChecked();
                //全选和反全选
                adapter.checkAll_none(isChecked);
                //显示总价格
                adapter.showTotalPrice();
                break;
            case R.id.btn_check_out:
//                Toast.makeText(mContext, "结算", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, OutActivity.class);
                startActivity(intent);


                break;
            case R.id.checkbox_delete_all:
                isChecked = checkboxDeleteAll.isChecked();
                //全选和反全选
                adapter.checkAll_none(isChecked);
                //显示总价格
                adapter.showTotalPrice();
                break;
            case R.id.btn_delete:
                adapter.deleteData();
                adapter.checkAll();
                showEempty();
                break;
            case R.id.btn_collection:
                Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_empty_cart_tobuy:
                 intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("checkedid", R.id.rb_home);
                startActivity(intent);
                break;
        }
    }

    private void showEempty() {
        if (adapter.getItemCount() == 0) {
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }


}
