package com.tanhuan.weihuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tanhuan.weihuan.R;

/**
 * Created by chenyaning on 2017/12/25.
 */

public class MySpinnerAdapter extends BaseAdapter {

    private String[] sort;
    private Context context;

    public MySpinnerAdapter(Context context, String[] sort) {
        this.context = context;
        this.sort = sort;
    }
    @Override
    public int getCount() {
        return sort.length;
    }

    @Override
    public String getItem(int i) {
        return sort[i];
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
            tvSpinner.setText(getItem(i));
        }
        return view;
    }
}
