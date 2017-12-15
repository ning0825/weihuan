package com.tanhuan.weihuan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tanhuan.weihuan.constants.CommonConstants;
import com.tanhuan.weihuan.utils.Logger;
import com.tanhuan.weihuan.utils.ToastUtils;

/**
 * Activity 基类
 * Created by chenYaNing on 2017/11/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;
    protected BaseApplication application;
    protected SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
        /**
         * 强制竖屏
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        application = (BaseApplication) getApplication();
        sp = getSharedPreferences(CommonConstants.SP_NAME,MODE_PRIVATE);
    }

    protected void intent2Activity(Class< ? extends AppCompatActivity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    protected void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    protected void showLog(String msg) {
        Logger.show(TAG, msg);
    }







}
