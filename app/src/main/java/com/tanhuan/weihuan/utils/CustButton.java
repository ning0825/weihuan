package com.tanhuan.weihuan.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by chenYaNing on 2017/12/9.
 */

@SuppressLint("AppCompatCustomView")
public class CustButton extends Button {
    public CustButton(Context context) {
        super(context);
    }

    public CustButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
