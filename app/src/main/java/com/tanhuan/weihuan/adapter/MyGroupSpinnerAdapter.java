package com.tanhuan.weihuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sina.weibo.sdk.openapi.models.Group;
import com.tanhuan.weihuan.R;

import java.util.List;

/**
 * Created by chenyaning on 2017/12/26.
 */

public class MyGroupSpinnerAdapter extends BaseAdapter {

    private List<Group> mGroups;
    private Context context;

    public MyGroupSpinnerAdapter(Context context, List<Group> groups) {
        this.context = context;
        this.mGroups = groups;
    }
    @Override
    public int getCount() {
        return mGroups.size();
    }

    @Override
    public Group getItem(int i) {
        return mGroups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_spinner, null);
        if (view != null) {
            TextView tvSpinner = view.findViewById(R.id.tv_spinner);
            tvSpinner.setText(getItem(i).name);
        }
        return view;
    }
}
