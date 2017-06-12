package com.zjhj.monitor.activity.shops;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjhj.commom.api.ItemApi;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.result.MapiServiceResult;
import com.zjhj.commom.util.RequestCallback;
import com.zjhj.commom.util.RequestExceptionCallback;
import com.zjhj.commom.widget.MainToast;
import com.zjhj.monitor.R;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.util.ControllerUtil;
import com.zjhj.monitor.view.ShopSliderLayout;
import com.zjhj.monitor.widget.MainAlertDialog;

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

    String id = "";
    MapiItemResult itemResult = null;

    MapiServiceResult xqjsResult;
    MapiServiceResult jbxxResult;
    MapiServiceResult fwResult;
    MapiServiceResult descResult;

    MainAlertDialog callDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        if(null!=getIntent())
            id = getIntent().getStringExtra("id");
        if(!TextUtils.isEmpty(id)){
            initView();
            initListener();
            load();
        }
    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);
        imgs = new ArrayList<>();
        callDialog = new MainAlertDialog(this);
    }

    private void initListener() {
        callDialog.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDialog.dismiss();
            }
        });

        callDialog.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(phone.getText())){
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.getText().toString().trim()));
                    startActivity(intent);
                    callDialog.dismiss();
                }else
                    MainToast.showShortToast("暂无联系方式");

            }
        });
    }

    private void load(){
        showLoading();
        ItemApi.merchantdetail(this, id, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                itemResult = JSONObject.parseObject(success.getJSONObject("data").toJSONString(),MapiItemResult.class);
                List<MapiResourceResult> bannerList = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("pic_list").toJSONString(),MapiResourceResult.class);
                xqjsResult = JSONObject.parseObject(success.getJSONObject("data").getJSONObject("xqjs").toJSONString(),MapiServiceResult.class);
                jbxxResult = JSONObject.parseObject(success.getJSONObject("data").getJSONObject("jbxx").toJSONString(),MapiServiceResult.class);
                fwResult = JSONObject.parseObject(success.getJSONObject("data").getJSONObject("fw").toJSONString(),MapiServiceResult.class);
                descResult = JSONObject.parseObject(success.getJSONObject("data").getJSONObject("desc").toJSONString(),MapiServiceResult.class);
                if(null!=bannerList&&!bannerList.isEmpty()){
                    imgs.clear();
                    imgs.addAll(bannerList);
                    shopSliderLayout.load(imgs);
                }

                if(null!=itemResult)
                    initInfo();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });


    }

    private void initInfo(){
        center.setText(TextUtils.isEmpty(itemResult.getName())?"":itemResult.getName());
        phone.setText(TextUtils.isEmpty(itemResult.getTel())?"":itemResult.getTel());
        addrTv.setText(TextUtils.isEmpty(itemResult.getAddress())?"":itemResult.getAddress());
        if(!TextUtils.isEmpty(itemResult.getTel())){
            callDialog.setLeftBtnText("取消").setRightBtnText("呼叫").setTitle(itemResult.getTel());
        }
    }

    @OnClick({R.id.back,R.id.desc_ll, R.id.phone_ll, R.id.addr_ll, R.id.feedback_ll, R.id.monitor_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.desc_ll:
                ControllerUtil.go2ShopDesc(xqjsResult,jbxxResult,fwResult,descResult);
                break;
            case R.id.phone_ll:
                if(!TextUtils.isEmpty(phone.getText())){
                    callDialog.show();
                }
                break;
            case R.id.addr_ll:
                if(null!=itemResult)
                     ControllerUtil.go2InfoWindow(itemResult);//go2GPSNavi();
                break;
            case R.id.feedback_ll:
                ControllerUtil.go2FeedBackList(itemResult);
                break;
            case R.id.monitor_ll:
                if(null!=itemResult)
                    ControllerUtil.go2CameraList(itemResult);
                break;
        }
    }
}
