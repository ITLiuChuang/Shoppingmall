package com.atguigu.shopping.home.bean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shopping.R;
import com.atguigu.shopping.utils.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 刘闯 on 2017/3/8.
 */

public class SearchAdapter extends BaseAdapter {
    private final List<User> datas;
    private Context mContext;

    public SearchAdapter(Context mContext, List<User> users) {
        this.mContext = mContext;
        this.datas = users;

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.search_type, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        List<String> userName = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            userName.add(datas.get(i).getName());
        }
        viewHolder.tvTitle.setText(userName.get(position));
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
