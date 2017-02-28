package com.atguigu.shopping.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.atguigu.shopping.home.bean.GoodsBean;
import com.atguigu.shopping.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘闯 on 2017/2/27.
 */

public class CartStorage {

    public static final String JSON_CART = "json_cart";
    private static CartStorage instace;
    private final Context mContext;
    private final SparseArray<GoodsBean> sparseArray;

    public CartStorage(Context context) {
        this.mContext = context;
        sparseArray = new SparseArray<>();
        //获取本地数据
        listToSparesArray();
    }

    /**
     * 把list数据转换成SparseArray
     */
    private void listToSparesArray() {
        List<GoodsBean> list = getAllData();
        for (int i = 0; i < list.size(); i++) {
            GoodsBean goodsBean = list.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        }
    }

    /**
     * 懒汉式
     *
     * @param context
     * @return
     */
    public static CartStorage getInstance(Context context) {
        if (instace == null) {
            synchronized (CartStorage.class) {
                if (instace == null) {
                    instace = new CartStorage(context);
                }
            }
        }
        return instace;
    }

    /**
     * 得到所有数据
     *
     * @return
     */

    public List<GoodsBean> getAllData() {
        return getLocalData();
    }

    /**
     * 得到本地缓存数据
     *
     * @return
     */
    private List<GoodsBean> getLocalData() {
        List<GoodsBean> goodsBeens = new ArrayList<>();
        //从本地获取数据

        String json = CacheUtils.getString(mContext, JSON_CART);
        if (!TextUtils.isEmpty(json)) {
            //把他转化成列表
            goodsBeens = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        //把json数据转换成list数据
        return goodsBeens;
    }

    /**
     * 添加数据
     *
     * @param goodsBean
     */
    public void addData(GoodsBean goodsBean) {
        //1.添加数据到sparseArray
        GoodsBean tempGoodsBean = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempGoodsBean != null) {
            //添加过
            tempGoodsBean.setNumber(tempGoodsBean.getNumber() + goodsBean.getNumber());
        } else {
            //没添加过
            tempGoodsBean = goodsBean;
        }

        //添加到集合中
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), tempGoodsBean);

        //2.保存到本地
        saveLocal();
    }

    /**
     * 删除数据
     * @param goodsBean
     */
    public void daleteData(GoodsBean goodsBean){
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        saveLocal();
    }

    public void updataData (GoodsBean goodsBean){
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        saveLocal();
    }


    /**
     * 保存到本地
     */
    private void saveLocal() {
        //把sparseArray转成list
        List<GoodsBean> goodsBeen = sparseArrayToList();
        //用Gson把list转成json的string数据
        String json = new Gson().toJson(goodsBeen);
        //使用CacheUtils缓存数据
        CacheUtils.setString(mContext,JSON_CART,json);

    }

    private List<GoodsBean> sparseArrayToList() {
        List<GoodsBean> goodsBeen = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            goodsBeen.add(sparseArray.valueAt(i));
        }
        return goodsBeen;
    }

}
