package com.tanhuan.weihuan.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 封装 Toast 的工具类
 * Created by chenYaNing on 2017/11/28.
 */

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
