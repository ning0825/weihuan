package com.tanhuan.weihuan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tanhuan.weihuan.BaseActivity;
import com.tanhuan.weihuan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenYaNing on 2017/12/9.
 */

public class DiscActivity extends BaseActivity {

    @BindView(R.id.tb_disc)
    Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery_layout);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.bg_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
