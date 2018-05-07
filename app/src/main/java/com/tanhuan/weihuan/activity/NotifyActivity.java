package com.tanhuan.weihuan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;

import com.tanhuan.weihuan.BaseActivity;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.adapter.MySpinnerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyaning on 2017/12/24.
 */

public class NotifyActivity extends BaseActivity {

    @BindView(R.id.sp_notify)
    Spinner mSpinner;
    @BindView(R.id.tb_noti)
    Toolbar mToolbar;
    MySpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_activity);
        ButterKnife.bind(this);

        //初始化 spinner
        String[] sort = getResources().getStringArray(R.array.notify);
        spinnerAdapter = new MySpinnerAdapter(this, sort);
        mSpinner.setAdapter(spinnerAdapter);

        //初始化 toolbar
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
