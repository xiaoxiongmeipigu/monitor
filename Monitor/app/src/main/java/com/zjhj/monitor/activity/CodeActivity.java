package com.zjhj.monitor.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjhj.monitor.R;
import com.zjhj.monitor.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CodeActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.image)
    SimpleDraweeView image;

    String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
        if(null!=getIntent()){
            code = getIntent().getStringExtra("code");
        }
        initView();
        initListener();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);
        center.setText("二维码");

        image.setImageURI(Uri.parse(code));//Uri.parse("res:///" +R.drawable.code_download_logo)

    }

    private void initListener() {

    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
