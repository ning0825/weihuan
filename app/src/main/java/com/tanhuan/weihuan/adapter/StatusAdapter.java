package com.tanhuan.weihuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.activity.ImageBrowerActivity;
import com.tanhuan.weihuan.activity.StatusDetailActivity;
import com.tanhuan.weihuan.activity.UserInfoActivity;
import com.tanhuan.weihuan.activity.WriteStatusActivity;
import com.tanhuan.weihuan.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenYaNing on 2017/12/10.
 */

public class StatusAdapter extends BaseAdapter {

    private Context context;
    private List<Status> datas;
    private ImageLoader imageLoader;

    public StatusAdapter(Context context, List<Status> datas) {
        this.context = context;
        this.datas = datas;
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(configuration);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Status getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = View.inflate(context, R.layout.status_item, null);
            holder.llStatusItem = (LinearLayout) view.findViewById(R.id.ll_status_item);
            holder.civAvatar = (ImageView) view.findViewById(R.id.civ_avatar);
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            holder.tvSource = (TextView) view.findViewById(R.id.tv_source);
            holder.tvCreateTime = (TextView) view.findViewById(R.id.tv_create_time);
            holder.ivAvatarVip = (ImageView) view.findViewById(R.id.iv_avatar_vip);
            holder.tvContent = (TextView) view.findViewById(R.id.tv_content);
            holder.flImg = (FrameLayout) view.findViewById(R.id.fl_img);
            holder.gvImages = (GridView) view.findViewById(R.id.gv_images);
            holder.ivImage = (ImageView) view.findViewById(R.id.iv_image);
            holder.includeRetweetedStatus = (LinearLayout) view.findViewById(R.id.include_retweeted_status);
            holder.tvRetweetedContent = (TextView) view.findViewById(R.id.tv_retweeted_content);
            holder.includeRetweetedStatusImage = (FrameLayout) view.findViewById(R.id.include_status_image);
            holder.gvRetweetedImages = (GridView) view.findViewById(R.id.gv_images);
            holder.ivRetweedImage = (ImageView) view.findViewById(R.id.iv_image);
            holder.llShareBottom = (LinearLayout) view.findViewById(R.id.ll_share_bottom);
            holder.ivShareBottom = (ImageView) view.findViewById(R.id.iv_share_bottom);
            holder.tvShareBottom = (TextView) view.findViewById(R.id.tv_share_bottom);
            holder.llCommentBottom = (LinearLayout) view.findViewById(R.id.ll_comment_bottom);
            holder.ivCommentBottom = (ImageView) view.findViewById(R.id.iv_comment_bottom);
            holder.tvCommentBottom = (TextView) view.findViewById(R.id.tv_comment_bottom);
            holder.llLikeBottom = (LinearLayout) view.findViewById(R.id.ll_like_bottom);
            holder.ivLikeBottom = (ImageView) view.findViewById(R.id.iv_like_bottom);
            holder.tvLikeBottom = (TextView) view.findViewById(R.id.tv_like_bottom);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //绑定数据
        final Status status = getItem(i);
        final User user = status.user;
        imageLoader.displayImage(user.profile_image_url, holder.civAvatar);
//        Glide.with(context).load(user.profile_image_url).into(holder.civAvatar);
        holder.tvName.setText(user.name);
        // 待处理
        holder.tvSource.setText(Html.fromHtml(status.source));
        //待处理
        holder.tvCreateTime.setText(status.created_at);
        //待处理
        holder.tvContent.setText(status.text);

        //头像点击事件
        holder.civAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra("userName", user.name);
                context.startActivity(intent);
            }
        });

        //昵称点击事件
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra("userName", user.name);
                context.startActivity(intent);
            }
        });

//        setImages(status, holder.flImg, holder.gvImages, holder.ivImage);

        final Status retweeted_status = status.retweeted_status;
        if(retweeted_status != null) {
            User retUser = retweeted_status.user;

            holder.includeRetweetedStatus.setVisibility(View.VISIBLE);
            String retweetedContent = "@" + retUser.name + ":"
                    + retweeted_status.text;
            holder.tvRetweetedContent.setText(StringUtils.getWeiboContent(
                    context, holder.tvRetweetedContent, retweetedContent));

            setImages(retweeted_status,
                    holder.includeRetweetedStatusImage,
                    holder.gvRetweetedImages, holder.ivRetweedImage);
        } else {
            holder.includeRetweetedStatusImage.setVisibility(View.GONE);
        }

        holder.tvShareBottom.setText(status.reposts_count == 0 ?
                "转发" : status.reposts_count + "");

        holder.tvCommentBottom.setText(status.comments_count == 0 ?
                "评论" : status.comments_count + "");

        holder.tvLikeBottom.setText(status.attitudes_count == 0 ?
                "赞" : status.attitudes_count + "");

//        holder.ll_card_content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, StatusDetailActivity.class);
//                intent.putExtra("status", status);
//                context.startActivity(intent);
//            }
//        });

        holder.includeRetweetedStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatusDetailActivity.class);
//                intent.putExtra("status", retweeted_status);
                context.startActivity(intent);
            }
        });

        holder.llShareBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WriteStatusActivity.class);
//                intent.putExtra("status", status);
                context.startActivity(intent);
            }
        });
//
//        holder.llCommentBottom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(status.comments_count > 0) {
//                    Intent intent = new Intent(context, StatusDetailActivity.class);
//                    intent.putExtra("status", status);
//                    intent.putExtra("scroll2Comment", true);
//                    context.startActivity(intent);
//                } else {
//                    Intent intent = new Intent(context, WriteCommentActivity.class);
//                    intent.putExtra("status", status);
//                    context.startActivity(intent);
//                }
//                ToastUtils.showToast(context, "评个论~", Toast.LENGTH_SHORT);
//            }
//        });
//
//        holder.ll_like_bottom.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showToast(context, "点个赞~", Toast.LENGTH_SHORT);
//            }
//        });

        return view;
    }

    private void setImages(final Status status, FrameLayout imageContainer, GridView gv, ImageView iv) {
        final ArrayList<String> pic_urls = status.pic_urls;
        final String thumbnail_pic = status.thumbnail_pic;

        if (pic_urls != null && pic_urls.size() > 1) {
            imageContainer.setVisibility(View.VISIBLE);
            gv.setVisibility(View.VISIBLE);
            iv.setVisibility(View.GONE);

            StatusGridImgsAdapter gvAdapter = new StatusGridImgsAdapter(context, pic_urls);
            gv.setAdapter(gvAdapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(context, ImageBrowerActivity.class);
                    intent.putExtra("urls", pic_urls);
                    intent.putExtra("position", i);
                    context.startActivity(intent);

                }
            });
        } else if (thumbnail_pic != null) {
            imageContainer.setVisibility(View.VISIBLE);
            gv.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);

//            imageLoader.displayImage(thumbnail_pic, iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageBrowerActivity.class);
                    intent.putExtra("urls", thumbnail_pic);
                    context.startActivity(intent);
                }
            });
        } else {
            imageContainer.setVisibility(View.GONE);
        }
    }



    public static class ViewHolder {

        // 微博头部信息
        public LinearLayout llStatusItem;
        public ImageView civAvatar;
        public TextView tvName;
        public TextView tvSource;
        public TextView tvCreateTime;
        public ImageView ivAvatarVip;
        public Button btIsFav;

        // 微博内容信息
        public TextView tvContent;
        public FrameLayout flImg;
        public GridView gvImages;
        public ImageView ivImage;

        // 转发内容信息
        public LinearLayout includeRetweetedStatus;
        public TextView tvRetweetedContent;
        public FrameLayout includeRetweetedStatusImage;
        public GridView gvRetweetedImages;
        public ImageView ivRetweedImage;

        // item 点赞转发评论栏
        public LinearLayout llShareBottom;
        public ImageView ivShareBottom;
        public TextView tvShareBottom;

        public LinearLayout llCommentBottom;
        public ImageView ivCommentBottom;
        public TextView tvCommentBottom;

        public LinearLayout llLikeBottom;
        public ImageView ivLikeBottom;
        public TextView tvLikeBottom;


    }
}
