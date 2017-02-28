package com.atguigu.shopping.shoppingcart.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shopping.R;

/**
 * Created by 刘闯 on 2017/2/28.
 */

public class AddSubView extends LinearLayout {

    private ImageView iv_sub;
    private TextView tv_value;
    private ImageView iv_add;

    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value + "");//这样才可以

    }

    public int getMinValue(int i) {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue(int i) {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.add_sub_view, AddSubView.this);
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        tv_value = (TextView) findViewById(R.id.tv_value);
        iv_add = (ImageView) findViewById(R.id.iv_add);

        //设置点击事件
        iv_sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //不能小于最小值
                if (value > minValue) {
                    value--;
                }
                setValue(value);
                if (listener != null) {
                    listener.OnNumberChanger(value);
                }
            }
        });

        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value < maxValue) {
                    value++;
                }

                setValue(value);

                if (listener != null) {
                    listener.OnNumberChanger(value);
                }

            }
        });

        if (attrs != null) {
            //取出属性
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                iv_add.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                iv_sub.setImageDrawable(subDrawable);
            }
        }
    }


    /**
     * 定义一个接口
     */
    public interface OnNumberChangerListener {
        public void OnNumberChanger(int value);

    }

    private OnNumberChangerListener listener;

    public void setOnNumberChangerListener(OnNumberChangerListener listener) {
        this.listener = listener;

    }
}
