package com.tanhuan.weihuan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.Status;
import com.tanhuan.weihuan.BaseActivity;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.constants.WeiboConstants;
import com.tanhuan.weihuan.utils.AccessTokenKeeper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenYaNing on 2017/12/8.
 */


public class StatusDetailActivity extends BaseActivity {


    @BindView(R.id.tv_detail_name)
    TextView deName;
    @BindView(R.id.tv_detail_content)
    TextView deContent;
    String statusId;
    StatusesAPI statusesAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_detail_activity);
        ButterKnife.bind(this);

        statusesAPI = new StatusesAPI(this, WeiboConstants.APP_KEY, AccessTokenKeeper.readAccessToken(this));

        statusId = getIntent().getStringExtra("statusId");

        show(statusId);


    }

    private void show(String id) {
        Long sId = Long.valueOf(id);
        statusesAPI.show(sId, new RequestListener() {
            @Override
            public void onComplete(String s) {
                initView(s);
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });
    }

    private void initView(String s) {
        Status status = Status.parse(s);

        deName.setText(status.user.screen_name);
        deContent.setText(status.text);

    }
}
