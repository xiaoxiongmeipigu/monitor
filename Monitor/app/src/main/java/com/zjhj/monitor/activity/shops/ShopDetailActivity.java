package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.monitor.R;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.util.ControllerUtil;
import com.zjhj.monitor.view.ShopSliderLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopDetailActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.shopSliderLayout)
    ShopSliderLayout shopSliderLayout;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.addr_tv)
    TextView addrTv;

    List<MapiResourceResult> imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);
        center.setText("商户名称");
        imgs = new ArrayList<>();
    }

    private void initListener() {

    }

    private void load(){
        imgs.clear();
        imgs.add(new MapiResourceResult());
        imgs.add(new MapiResourceResult());
        imgs.add(new MapiResourceResult());
        shopSliderLayout.load(imgs);
    }

    @OnClick({R.id.back,R.id.desc_ll, R.id.phone_ll, R.id.addr_ll, R.id.feedback_ll, R.id.monitor_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.desc_ll:
                ControllerUtil.go2ShopDesc();
                break;
            case R.id.phone_ll:
                break;
            case R.id.addr_ll:
                break;
            case R.id.feedback_ll:
                ControllerUtil.go2Feedback();
                break;
            case R.id.monitor_ll:
                ControllerUtil.go2Live();
                break;
        }
    }
}
