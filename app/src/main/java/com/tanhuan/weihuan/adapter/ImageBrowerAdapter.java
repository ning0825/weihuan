package com.tanhuan.weihuan.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.utils.DisplayUtils;

import java.util.ArrayList;

/**
 * Created by chenYaNing on 2017/12/12.
 */

public class ImageBrowerAdapter extends PagerAdapter {

    private Activity context;
    private ArrayList<String> picUrls;
    private ArrayList<View> picViews;

    private ImageLoader imageLoader;

    public ImageBrowerAdapter(Activity context, ArrayList<String> picUrls) {
        this.context = context;
        this.picUrls = picUrls;
        this.imageLoader = ImageLoader.getInstance();
        initImgs();
    }

    private void initImgs() {
        picViews = new ArrayList<View>();

        for (int i = 0; i < picUrls.size(); i ++) {
            //填充显示图片的页面布局
            View view = View.inflate(context, R.layout.item_image_brower, null);
            picViews.add(view);
        }

    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View view = picViews.get(position);
        final ImageView ivImageBrower = view.findViewById(R.id.iv_item_image);
        String picUrl = picUrls.get(position);

        String url =  "http://ww3.sinaimg.cn/large/" + picUrl.substring(picUrl.lastIndexOf("/") + 1);
        imageLoader.loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                float scale = (float) loadedImage.getWidth() / loadedImage.getHeight();

                int screenWidthPixels = DisplayUtils.getScreenWidthPixels(context);
                int screenHeightPixels = DisplayUtils.getScreenHeightPixels(context);
                int height = (int) (screenWidthPixels * scale);

                if (height < screenHeightPixels) {
                    height = screenHeightPixels;
                }

                ViewGroup.LayoutParams layoutParams = ivImageBrower.getLayoutParams();
                layoutParams.height = height;
                layoutParams.width = screenWidthPixels;

                ivImageBrower.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public String getPic(int position) {
        return picUrls.get(position);
    }

    @Override
    public int getCount() {
        return picUrls.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Bitmap getBitmap(int position) {
        Bitmap bitmap = null;
        View view = picViews.get(position);
        ImageView ivImageBrower = (ImageView) view.findViewById(R.id.iv_image_browser);
        Drawable drawable = ivImageBrower.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            bitmap = bd.getBitmap();
        }
        return bitmap;
    }

}
