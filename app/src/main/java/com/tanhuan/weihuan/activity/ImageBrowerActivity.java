package com.tanhuan.weihuan.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tanhuan.weihuan.BaseActivity;
import com.tanhuan.weihuan.R;
import com.tanhuan.weihuan.adapter.ImageBrowerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenYaNing on 2017/12/11.
 */

public class ImageBrowerActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.vp_image_brower)
    ViewPager vpImageBrower;
    @BindView(R.id.tv_image_index)
    TextView tvImageIndex;
    @BindView(R.id.bt_save)
    Button btSave;

    private int position;
    private ImageBrowerAdapter adapter;
    private ArrayList<String> imgUrls;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_brower_activity);
        ButterKnife.bind(this);
        btSave.setOnClickListener(this);

        initData();
        setData();

    }

    private void initData() {
        imgUrls = getIntent().getStringArrayListExtra("urls");
        position = getIntent().getIntExtra("positon", 0);
    }

    private void setData() {
        adapter = new ImageBrowerAdapter(this, imgUrls);
        vpImageBrower.setAdapter(adapter);

        final int size = imgUrls.size();
        if (size > 1) {
            tvImageIndex.setVisibility(View.VISIBLE);
            tvImageIndex.setText((position + 1) + "/" + size);
        } else {
            tvImageIndex.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        String picUrl = adapter.getPic(vpImageBrower.getCurrentItem());

        switch (view.getId()) {
            case R.id.bt_save:
                Bitmap bitmap = adapter.getBitmap(vpImageBrower.getCurrentItem());

                String fileName = "img-" + picUrl;
                String title = fileName.substring(0, fileName.lastIndexOf("."));
                String insertImage = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, "weihuanImage");
                if (insertImage == null) {
                    showToast("image save failed");
                } else {
                    showToast("image save success");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
