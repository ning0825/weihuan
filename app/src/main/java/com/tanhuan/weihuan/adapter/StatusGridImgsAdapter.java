package com.tanhuan.weihuan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tanhuan.weihuan.R;

import java.util.ArrayList;

/**
 * Created by chenYaNing on 2017/12/11.
 */

public class StatusGridImgsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> datas;
    private ImageLoader imageLoader;

    public StatusGridImgsAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(configuration);  
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("newapi")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_grid_image, null);
            viewHolder.iv_image = (ImageView) view.findViewById(R.id.iv_item_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GridView gv = (GridView) viewGroup;
        int horizontalSpacing = gv.getHorizontalSpacing();
        int numColumns = gv.getNumColumns();
        int itemWidth = (gv.getWidth() - (numColumns - 1) * horizontalSpacing - gv.getPaddingLeft() - gv.getPaddingRight()) / numColumns;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemWidth, itemWidth);
        viewHolder.iv_image.setLayoutParams(params);
        String url = getItem(i);
        imageLoader.displayImage(url, viewHolder.iv_image);

        return view;
    }

    public static class ViewHolder {
        public ImageView iv_image;
    }
}
