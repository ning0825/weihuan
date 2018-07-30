package com.tanhuan.weihuan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.CommentsAPI;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.tanhuan.weihuan.BaseActivity;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.constants.WeiboConstants;
import com.tanhuan.weihuan.utils.AccessTokenKeeper;
import com.tanhuan.weihuan.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenYaNing on 2017/12/8.
 */

public class WriteStatusActivity extends BaseActivity {

    @BindView(R.id.tb_write)
    Toolbar mToolbar;
    @BindView(R.id.et_content)
    EditText etContent;
    StatusesAPI statusesAPI;
    // intent 码：0 代表写微博， 1 代表转发微博， 2 代表评论微博
    int ittCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_status_layout);
        ButterKnife.bind(this);

        statusesAPI = new StatusesAPI(this, WeiboConstants.WEICO_APP_KEY, AccessTokenKeeper.readAccessToken(this));

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);

        ittCode = getIntent().getIntExtra("ittCode", 0);

    }

    private void sendComment() {

        Long statusId = Long.valueOf(getIntent().getStringExtra("statusId"));
        CommentsAPI commentsAPI = new CommentsAPI(this, WeiboConstants.APP_KEY, AccessTokenKeeper.readAccessToken(this));
        String comment2Send = etContent.getText().toString();
        commentsAPI.create(comment2Send, statusId, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ToastUtils.showToast(WriteStatusActivity.this, "success", 1000);
                WriteStatusActivity.this.finish();
            }

            @Override
            public void onWeiboException(WeiboException e) {
                showToast(e.getMessage());
            }
        });
    }

    private void sendStatus() {
        String status2Send = etContent.getText().toString();
        statusesAPI.update(status2Send, "0.0", "0.0",  new RequestListener() {
            @Override
            public void onComplete(String s) {
                ToastUtils.showToast(WriteStatusActivity.this, "success", 2000);
                WriteStatusActivity.this.finish();
            }

            @Override
            public void onWeiboException(WeiboException e) {
                showToast(e.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_write, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_send:
                    if (ittCode == 0) {
                        sendStatus();
                    } else if (ittCode == 2) {
                        sendComment();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    };
}
