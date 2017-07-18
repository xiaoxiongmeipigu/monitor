package com.zjhj.monitor.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjhj.commom.api.ItemApi;
import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.result.MapiUserResult;
import com.zjhj.commom.util.DesBase64Tool;
import com.zjhj.commom.util.RequestCallback;
import com.zjhj.commom.util.RequestExceptionCallback;
import com.zjhj.commom.widget.MainToast;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.MainAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.util.ControllerUtil;
import com.zjhj.monitor.view.HomeSliderLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.homeSliderLayout)
    HomeSliderLayout homeSliderLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    MainAdapter mAdapter;
    List<MapiResourceResult> mList;
    List<MapiResourceResult> imgs;
    ArrayList<MapiResourceResult> addrList;

    String qrcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {

        center.setText("市监通");
        ivRight.setImageResource(R.mipmap.code_logo);

        mList = new ArrayList<>();
        imgs = new ArrayList<>();
        addrList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MainAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);


    }

    private void initListener() {
      /*  mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String url = mList.get(position).getPost_url();
                if(!TextUtils.isEmpty(url))
                    ControllerUtil.go2WebView(url,"新闻详情","","","",false);
            }
        });*/
    }

    private void load() {
        showLoading();
        ItemApi.defaultindex(this, new RequestCallback<JSONObject>() {
            @Override
            public void success(JSONObject success) {
                hideLoading();
                List<MapiResourceResult> bannerList = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("banner").toJSONString(), MapiResourceResult.class);
                List<MapiResourceResult> postList = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("post_cat").toJSONString(), MapiResourceResult.class);
                List<MapiResourceResult> streetList = JSONArray.parseArray(success.getJSONObject("data").getJSONArray("street").toJSONString(), MapiResourceResult.class);
                qrcode = success.getJSONObject("data").getString("qrcode");
                String jsonPsdStr = success.getJSONObject("data").getString("account");
                if (!TextUtils.isEmpty(jsonPsdStr)) {
                    try {
                        String jsonStr = DesBase64Tool.desDecrypt(jsonPsdStr);

                        System.out.println("解密结果：" + jsonStr);

                        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                        String username = TextUtils.isEmpty(jsonObject.getString("username")) ? "" : jsonObject.getString("username");
                        String password = TextUtils.isEmpty(jsonObject.getString("password")) ? "" : jsonObject.getString("password");
                        String ip = TextUtils.isEmpty(jsonObject.getString("ip")) ? "" : jsonObject.getString("ip");
                        String route_id = TextUtils.isEmpty(jsonObject.getString("route_id")) ? "" : jsonObject.getString("route_id");

                        if (null != userSP.getUserBean()) {
                            MapiUserResult mapiUserResult = userSP.getUserBean();
                            mapiUserResult.setUsername(username);
                            mapiUserResult.setPassword(password);
                            mapiUserResult.setIp(ip);
                            mapiUserResult.setRoute_id(route_id);
                            userSP.saveUserBean(mapiUserResult);
                        } else {
                            MapiUserResult mapiUserResult = new MapiUserResult();
                            mapiUserResult.setUsername(username);
                            mapiUserResult.setPassword(password);
                            mapiUserResult.setIp(ip);
                            mapiUserResult.setRoute_id(route_id);
                            userSP.saveUserBean(mapiUserResult);
                        }

//                        TempData.getIns().setLoginData(mapiUserResult);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                if (null != bannerList && !bannerList.isEmpty()) {
                    imgs.clear();
                    imgs.addAll(bannerList);
                    homeSliderLayout.setSlider(true);
                    homeSliderLayout.load(imgs);
                }

                if (null != postList && !postList.isEmpty()) {
                    mList.clear();
                    mList.addAll(postList);
//                    mList.addAll(JGJDataSource.getNews());
                    mAdapter.notifyDataSetChanged();
                }

                if (null != streetList && !streetList.isEmpty()) {
                    addrList.clear();
                    addrList.addAll(streetList);
                }

            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });

    }

    @OnClick({R.id.iv_right, R.id.news_ll, R.id.monitor_ll, R.id.link_ll,R.id.notice_ll, R.id.consume_ll, R.id.punish_ll, R.id.supervise_ll, R.id.policy_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                ControllerUtil.go2Code(qrcode);
                break;
            case R.id.news_ll:
                ControllerUtil.go2News(0);
                break;
            case R.id.monitor_ll:
                ControllerUtil.go2ShopType(addrList);
                break;
            case R.id.link_ll:
                ControllerUtil.go2Link();
                break;
            case R.id.notice_ll:
                ControllerUtil.go2News(0);
                break;
            case R.id.consume_ll:
                ControllerUtil.go2News(1);
                break;
            case R.id.punish_ll:
                ControllerUtil.go2News(2);
                break;
            case R.id.supervise_ll:
                ControllerUtil.go2News(3);
                break;
            case R.id.policy_ll:
                ControllerUtil.go2News(4);
                break;


        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
