package com.tanhuan.weihuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.GroupAPI;
import com.sina.weibo.sdk.openapi.models.Group;
import com.sina.weibo.sdk.openapi.models.GroupList;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.openapi.models.User;
import com.tanhuan.weihuan.BaseActivity;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.adapter.MyGroupSpinnerAdapter;
import com.tanhuan.weihuan.adapter.StatusAdapter;
import com.tanhuan.weihuan.constants.WeiboConstants;
import com.tanhuan.weihuan.db.WhDbHelper;
import com.tanhuan.weihuan.utils.AccessTokenKeeper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenYaNing on 2017/11/28.
 */
public class MainActivity extends BaseActivity {

    private ActionBarDrawerToggle mToggle;
    private static final String TAG = "MainActivity";
    private List<Status> statuses = new ArrayList<>();
    private View footView;
    private int curPage = 1;
    StatusAdapter statusAdapter;
    @BindView(R.id.dl_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.tb_main)
    android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.plv_statuses)
    PullToRefreshListView plvStatuses;
    @BindView(R.id.tv_drawer_name)
    TextView drawerName;
    @BindView(R.id.civ_drawer_avatar)
    CircleImageView drawerAvatar;
    @BindView(R.id.sp_main)
    Spinner mainSpinner;
    @BindView(R.id.ll_drawer_write)
    LinearLayout llDwWrite;
    @BindView(R.id.ll_drawer_like)
    LinearLayout llDwLike;
    @BindView(R.id.ll_drawer_fav)
    LinearLayout llDwFav;
    @BindView(R.id.ll_drawer_drafts)
    LinearLayout llDwDrafts;
    @BindView(R.id.ll_drawer_later)
    LinearLayout llDwLater;
    MyGroupSpinnerAdapter mySpinnerAdapter;
    long uid;
    UsersAPI usersAPI;
    GroupAPI groupAPI;
    User user;
    String uidStr;
    WhDbHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        dbHelper = new WhDbHelper(this);

        uidStr = AccessTokenKeeper.readAccessToken(this).getUid();
        uid = Long.valueOf(uidStr).longValue();

        groupAPI = new GroupAPI(this, WeiboConstants.APP_KEY, AccessTokenKeeper.readAccessToken(this));
        groupAPI.groups(new RequestListener() {
            @Override
            public void onComplete(String s) {
                List<Group> groups = GroupList.parse(s).groupList;

                mySpinnerAdapter = new MyGroupSpinnerAdapter(MainActivity.this, groups);
                mainSpinner.setAdapter(mySpinnerAdapter);
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });


        usersAPI = new UsersAPI(this, WeiboConstants.APP_KEY, AccessTokenKeeper.readAccessToken(this));

        //设置 toolbar
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.bg_main_menu);
        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);

        //设置 drawerlayout
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setHomeButtonEnabled(true);

        initView();
        loadStatus(1);


        usersAPI.show(uid, new RequestListener() {
            @Override
            public void onComplete(String s) {
                user = User.parse(s);
                Glide.with(MainActivity.this).load(user.avatar_large).into(drawerAvatar);
                drawerName.setText(user.screen_name);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                showToast(e.getMessage());
            }
        });

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

        drawerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("uid", uidStr);
                startActivity(intent);
            }
        });

        drawerAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("uid", uidStr);
                startActivity(intent);
            }
        });

        llDwWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WriteStatusActivity.class);
                intent.putExtra("ittCode", 0);
                startActivity(intent);
            }
        });

        llDwLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyLikeActivity.class);
                startActivity(intent);
            }
        });

        llDwFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyFavActivity.class);
                startActivity(intent);
            }
        });

        llDwDrafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DraftsActivity.class);
                startActivity(intent);
            }
        });

        llDwLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LaterReadActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadStatus(final int page) {

        StatusesAPI mStautsesApi = new StatusesAPI(this, WeiboConstants.APP_KEY, AccessTokenKeeper.readAccessToken(this));
        mStautsesApi.friendsTimeline(0, 0, 20, page, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {

                if (page == 1) {
                    statuses.clear();
                }
                curPage = page;
                addData(StatusList.parse(s));

                plvStatuses.onRefreshComplete();
            }

            @Override
            public void onWeiboException(WeiboException e) {
                showToast(e.getMessage());
            }
        });
    }

    //添加微博数据
    private void addData(StatusList resBean) {
        for (Status status : resBean.statusList) {
            if (!statuses.contains(status)) {
                statuses.add(status);
            }
        }
        statusAdapter.notifyDataSetChanged();

        if (curPage < resBean.total_number) {
            addFootView(plvStatuses, footView);
        } else {
            removeFootView(plvStatuses, footView);
        }

    }

    //添加 footview
    private void addFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() == 1) {
            lv.addFooterView(footView);
        }
    }

    //移除 footview
    private void removeFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() > 1) {
            lv.removeFooterView(footView);
        }
    }

    //toolbar 按钮监听事件
    private android.support.v7.widget.Toolbar.OnMenuItemClickListener onMenuItemClickListener = new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bt_trend:
                    intent2Activity(DiscActivity.class);
                    break;
                case R.id.bt_notify:
                    intent2Activity(NotifyActivity.class);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    //toolbar 按钮
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
