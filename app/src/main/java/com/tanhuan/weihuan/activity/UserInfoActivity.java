package com.tanhuan.weihuan.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;
import com.tanhuan.weihuan.BaseActivity;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.constants.WeiboConstants;
import com.tanhuan.weihuan.utils.AccessTokenKeeper;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenYaNing on 2017/12/8.
 */

public class UserInfoActivity extends BaseActivity {

    ImageLoader imageLoader;
    @BindView(R.id.bt_info_back)
    Button infoBack;
    @BindView(R.id.bt_info_follow)
    Button infoFollow;
    @BindView(R.id.civ_info_avatar)
    CircleImageView infoAvatar;
    @BindView(R.id.tv_info_name)
    TextView infoName;
    @BindView(R.id.tv_info_des)
    TextView infoDes;
    @BindView(R.id.tv_num_weibo)
    TextView numWeibo;
    @BindView(R.id.tv_num_following)
    TextView numFollowing;
    @BindView(R.id.tv_num_follower)
    TextView numFollower;
    @BindView(R.id.gv_info_four_image)
    GridLayout infoImage;
    @BindView(R.id.rl_user_info)
    RelativeLayout userInfoRl;
    @BindDrawable(R.drawable.bg_info_gradient_female)
    Drawable bgInfoFemale;
    @BindDrawable(R.drawable.bg_info_gradient)
    Drawable bgInfoMale;

    long uid;
    String userName;
    UsersAPI usersAPI;
    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        ButterKnife.bind(this);

        usersAPI = new UsersAPI(this, WeiboConstants.APP_KEY, AccessTokenKeeper.readAccessToken(this));
        if (!TextUtils.isEmpty(getIntent().getStringExtra("uid"))) {
            uid = Long.valueOf(getIntent().getStringExtra("uid"));
            initViewByUid();
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("userName"))) {
            userName = getIntent().getStringExtra("userName");
            initViewByName();
        }
    }

    private void initViewByUid() {

        usersAPI.show(uid, new RequestListener() {
            @Override
            public void onComplete(String s) {
                initView(s);
            }
            @Override
            public void onWeiboException(WeiboException e) {
                showToast(e.getMessage());
            }
        });
    }

    private void initViewByName() {

        usersAPI.show(userName, new RequestListener() {
            @Override
            public void onComplete(String s) {
                initView(s);
            }
            @Override
            public void onWeiboException(WeiboException e) {
                showToast(e.getMessage());
            }
        });
    }

    private void initView(String s) {

        user = User.parse(s);
        if (user.gender.equals("f")) {
            userInfoRl.setBackground(bgInfoFemale);
        } else if (user.gender.equals("m")) {
            userInfoRl.setBackground(bgInfoMale);
        } else {
            userInfoRl.setBackground(bgInfoMale);
        }

        if (user.following) {
            infoFollow.setText("following");
        } else {
            infoFollow.setText("follow");
        }
        Glide.with(UserInfoActivity.this).load(user.avatar_large).into(infoAvatar);
        infoName.setText(String.valueOf(user.screen_name));
        if (user.verified) {
            infoDes.setText("认证：" + user.verified_reason + "\n" + "简介：" + user.description);
        } else {
            infoDes.setText("简介：" + user.description);
        }
        numWeibo.setText(String.valueOf(user.statuses_count));
        numFollowing.setText(String.valueOf(user.friends_count));
        numFollower.setText(String.valueOf(user.followers_count));
    }
}
