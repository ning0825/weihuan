package com.tanhuan.weihuan.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.activity.ImageBrowerActivity;
import com.tanhuan.weihuan.activity.StatusDetailActivity;
import com.tanhuan.weihuan.activity.UserInfoActivity;
import com.tanhuan.weihuan.activity.WriteStatusActivity;
import com.tanhuan.weihuan.utils.DateUtils;
import com.tanhuan.weihuan.utils.StringUtils;
import com.tanhuan.weihuan.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenYaNing on 2017/12/10.
 */

public class StatusAdapter extends BaseAdapter {

    private Context context;
    private List<Status> datas;
    private SQLiteDatabase db;

    public StatusAdapter(Context context, List<Status> datas) {
        this.context = context;
        this.datas = datas;
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
            holder.llStatusItem = view.findViewById(R.id.ll_status_item);
            holder.civAvatar = view.findViewById(R.id.civ_avatar);
            holder.tvName =  view.findViewById(R.id.tv_name);
            holder.tvSource = view.findViewById(R.id.tv_source);
            holder.tvCreateTime = view.findViewById(R.id.tv_create_time);
            holder.btMore = view.findViewById(R.id.bt_status_more);
            holder.ivAvatarVip = view.findViewById(R.id.iv_avatar_vip);
            holder.tvContent = view.findViewById(R.id.tv_content);
            holder.statusImg = view.findViewById(R.id.fl_image);
            holder.gvImages = holder.statusImg.findViewById(R.id.gv_images);
            holder.ivImage = holder.statusImg.findViewById(R.id.iv_image);
            holder.includeRetweetedStatus = view.findViewById(R.id.include_retweeted_status);
            holder.tvRetweetedContent = holder.includeRetweetedStatus.findViewById(R.id.tv_retweeted_content);
            holder.retweetedImage = holder.includeRetweetedStatus.findViewById(R.id.retweeted_image);
            holder.gvRetweetedImages = holder.retweetedImage.findViewById(R.id.gv_images);
            holder.ivRetweedImage = holder.retweetedImage.findViewById(R.id.iv_image);
            holder.llShareBottom = view.findViewById(R.id.ll_share_bottom);
            holder.ivShareBottom = view.findViewById(R.id.iv_share_bottom);
            holder.tvShareBottom = view.findViewById(R.id.tv_share_bottom);
            holder.llCommentBottom = view.findViewById(R.id.ll_comment_bottom);
            holder.ivCommentBottom = view.findViewById(R.id.iv_comment_bottom);
            holder.tvCommentBottom = view.findViewById(R.id.tv_comment_bottom);
            holder.llLikeBottom = view.findViewById(R.id.ll_like_bottom);
            holder.ivLikeBottom = view.findViewById(R.id.iv_like_bottom);
            holder.tvLikeBottom = view.findViewById(R.id.tv_like_bottom);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //绑定数据
        final Status status = getItem(i);
        final User user = status.user;
        Glide.with(context).load(user.profile_image_url).into(holder.civAvatar);
        if (!user.verified) {
            holder.ivAvatarVip.setVisibility(View.GONE);
        }
        holder.tvName.setText(user.name);
        // 微博尾巴
        holder.tvSource.setText(Html.fromHtml(status.source).toString());
        // 发送时间
        holder.tvCreateTime.setText(DateUtils.getShortTime(status.created_at));
        // 微博内容
        holder.tvContent.setText(StringUtils.getWeiboContent(
                context, holder.tvContent, status.text));

        //头像点击事件
        holder.civAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra("uid", user.id);
                context.startActivity(intent);
            }
        });

        //昵称点击事件
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra("uid", user.id);
                context.startActivity(intent);
            }
        });

        //更多操作
        holder.btMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View contentView = LayoutInflater.from(context).inflate(R.layout.pop_status, null);
                PopupWindow popupWindow = new PopupWindow(contentView, ScrollView.LayoutParams.WRAP_CONTENT, ScrollView.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_pop));
                popupWindow.showAsDropDown(holder.btMore);

                TextView tvFav = contentView.findViewById(R.id.pop_fav);
                TextView tvLater = contentView.findViewById(R.id.pop_later);

                tvFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showToast(context, "已加入收藏", 1000);
                    }
                });

                tvLater.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContentValues values = new ContentValues();
                        values.put("avatar_url", user.profile_image_url);
                        values.put("name", user.name);
                        values.put("content", status.text);
                        db.insert("laters", null, values);
                        ToastUtils.showToast(context, "已加入稍后阅读", 1000);
                    }
                });

            }
        });

        //内容点击事件
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StatusDetailActivity.class);
                intent.putExtra("statusId", status.id);
                context.startActivity(intent);
            }
        });

        setImages(status, holder.statusImg, holder.gvImages, holder.ivImage);

        final Status retweeted_status = status.retweeted_status;
        if (retweeted_status != null) {
            User retUser = retweeted_status.user;

            holder.includeRetweetedStatus.setVisibility(View.VISIBLE);
            String retweetedContent = "@" + retUser.name + ":"
                    + retweeted_status.text;
            holder.tvRetweetedContent.setText(StringUtils.getWeiboContent(
                    context, holder.tvRetweetedContent, retweetedContent));

            setImages(retweeted_status,
                    holder.retweetedImage,
                    holder.gvRetweetedImages, holder.ivRetweedImage);
        } else {
            holder.includeRetweetedStatus.setVisibility(View.GONE);
        }

        holder.tvShareBottom.setText(status.reposts_count == 0 ?
                "转发" : status.reposts_count + "");

        holder.tvCommentBottom.setText(status.comments_count == 0 ?
                "评论" : status.comments_count + "");

        holder.tvLikeBottom.setText(status.attitudes_count == 0 ?
                "赞" : status.attitudes_count + "");

        holder.tvRetweetedContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatusDetailActivity.class);
                intent.putExtra("statusId", retweeted_status.id);
                context.startActivity(intent);
            }
        });

        // 转发功能
        holder.llShareBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WriteStatusActivity.class);
                intent.putExtra("statusId", status.id);
                intent.putExtra("ittCode", 1);
                context.startActivity(intent);
            }
        });

        // 评论功能
        holder.llCommentBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WriteStatusActivity.class);
                intent.putExtra("ittCode", 2);
                intent.putExtra("statusId", status.id);
                context.startActivity(intent);
            }
        });

        // 点赞功能
        holder.llLikeBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(context, "接口限制，无法点赞", Toast.LENGTH_SHORT);
            }
        });
        return view;
    }

    // 显示图片功能
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
        } else if (thumbnail_pic != null && thumbnail_pic.length() > 2 ) {
            imageContainer.setVisibility(View.VISIBLE);
            gv.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);

            Glide.with(context).load(status.bmiddle_pic).into(iv);
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
        public CircleImageView civAvatar;
        public TextView tvName;
        public TextView tvSource;
        public TextView tvCreateTime;
        public ImageView ivAvatarVip;
        public Button btMore;

        // 微博内容信息
        public TextView tvContent;
        public FrameLayout statusImg;
        public GridView gvImages;
        public ImageView ivImage;

        // 转发内容信息
        public LinearLayout includeRetweetedStatus;
        public TextView tvRetweetedContent;
        public FrameLayout retweetedImage;
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
