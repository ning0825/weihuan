package com.tanhuan.weihuan.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.tanhuan.weihuan.BaseActivity;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.adapter.StatusAdapter;
import com.tanhuan.weihuan.api.SimpleRequestListener;
import com.tanhuan.weihuan.api.WHApi;
import com.tanhuan.weihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenYaNing on 2017/11/28.
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "chen";
    private List<Status> statuses = new ArrayList<Status>();
    private View footView;
    private int curPage = 1;
    StatusAdapter statusAdapter;
    @BindView(R.id.tb_main)
    android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.plv_statuses)
    PullToRefreshListView plvStatuses;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.bg_main_menu);
        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);

        initView();
        loadStatus(1);
    }

    private void initView() {

        statusAdapter = new StatusAdapter(MainActivity.this, statuses);
        plvStatuses.setAdapter(statusAdapter);

        plvStatuses.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadStatus(1);
            }
        });

        plvStatuses.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                loadStatus(curPage + 1);
            }
        });

        footView = View.inflate(this, R.layout.footview_loading, null);
    }

    private void loadStatus(final int page) {

        WHApi whApi = new WHApi(this);
        whApi.statusesHome_timeline(page, new SimpleRequestListener(this, null) {
            @Override
            public void onComplete(String s) {
                super.onComplete(s);
                if (page == 1) {
                    statuses.clear();
                }

                curPage = page;

                addData(StatusList.parse(s));

            }

            @Override
            public void onAllDone() {
                super.onAllDone();
                plvStatuses.onRefreshComplete();
            }

            @Override
            public void onWeiboException(WeiboException e) {
                ToastUtils.showToast(MainActivity.this, e.getMessage(), 2000);
            }
        });
    }

    private void addData(StatusList resBean) {
        for (Status status : resBean.statusList) {
            if (!statuses.contains(status)) {
                statuses.add(status);
            }
        }
        statusAdapter.notifyDataSetChanged();


        if(curPage < resBean.total_number) {
            addFootView(plvStatuses, footView);
        } else {
            removeFootView(plvStatuses, footView);
        }

    }


    private void addFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() == 1) {
            lv.addFooterView(footView);
        }
    }

    private void removeFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() > 1) {
            lv.removeFooterView(footView);
        }
    }

    private android.support.v7.widget.Toolbar.OnMenuItemClickListener onMenuItemClickListener = new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String str = "";
            switch (item.getItemId()) {
                case R.id.bt_trend:
                    intent2Activity(DiscActivity.class);
                    break;
                case R.id.bt_notify:
                    intent2Activity(UserInfoActivity.class);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
