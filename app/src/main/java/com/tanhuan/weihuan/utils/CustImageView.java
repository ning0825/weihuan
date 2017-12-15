package com.tanhuan.weihuan.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by chenYaNing on 2017/12/9.
 */

public class CustImageView extends AppCompatImageView {
    public CustImageView(Context context) {
        super(context);
    }

    public CustImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
