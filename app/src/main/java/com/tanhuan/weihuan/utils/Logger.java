package com.tanhuan.weihuan.utils;

import android.util.Log;

import com.tanhuan.weihuan.constants.CommonConstants;

/**
 * 封装 Log 的工具类
 * Created by chenYaNing on 2017/11/28.
 */

public class Logger {

    public static void show(String TAG, String msg) {
        if (!CommonConstants.isShowLog) {
            return;
        }
        show(TAG, msg, Log.INFO);
    }

    public static void show(String TAG, String msg, int level) {
        if (!CommonConstants.isShowLog) {
            return;
        }
        switch (level) {
            case Log.VERBOSE:
                Log.v(TAG, msg);
                break;
            case Log.DEBUG:
                Log.d(TAG, msg);
                break;
            case Log.INFO:
                Log.i(TAG, msg);
                break;
            default:
        }

    }


}
