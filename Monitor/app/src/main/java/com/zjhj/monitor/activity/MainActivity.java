package com.zjhj.monitor.activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjhj.commom.api.BasicApi;
import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.util.DPUtil;
import com.zjhj.commom.util.DebugLog;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.news.NewsListAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.util.ControllerUtil;
import com.zjhj.monitor.util.JGJDataSource;
import com.zjhj.monitor.view.HomeSliderLayout;
import com.zjhj.monitor.widget.DividerListItemDecoration;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    NewsListAdapter mAdapter;
    List<MapiResourceResult> mList;
    List<MapiResourceResult> imgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView(){

        center.setText("市监通");
        ivRight.setImageResource(R.mipmap.code_logo);

        mList = new ArrayList<>();
        imgs = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(1), getResources().getColor(R.color.background_gray)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new NewsListAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener(){

    }

    private void load(){
        mList.clear();
        mList.addAll(JGJDataSource.getNews());
        mAdapter.notifyDataSetChanged();

        imgs.clear();
        imgs.add(new MapiResourceResult());
        imgs.add(new MapiResourceResult());
        imgs.add(new MapiResourceResult());

        homeSliderLayout.setSlider(true);
        homeSliderLayout.load(imgs);

    }

    @OnClick({R.id.iv_right, R.id.news_ll, R.id.monitor_ll, R.id.link_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                ControllerUtil.go2Code();
                break;
            case R.id.news_ll:
                ControllerUtil.go2News();
                break;
            case R.id.monitor_ll:
                ControllerUtil.go2Shops();
                break;
            case R.id.link_ll:
                ControllerUtil.go2Link();
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

    /**
     * 获取登录设备mac地址
     *
     * @return
     */
    protected String getMac() {

        WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macStr=null;
        if(info!=null){
            macStr= info.getMacAddress();
        }
        if (macStr == null) {
            macStr = getUUID();
        }
        return macStr;


//        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        String mac = wm.getConnectionInfo().getMacAddress();
//        return mac == null ? "" : mac;
    }

    public  String getUUID() {
        String uuid = "";
        synchronized (MainActivity.class) {
            final String androidId = Settings.Secure.getString(
                    getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                if ((null != androidId)
                        && !"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"))
                            .toString();
                } else {
                    final String deviceId = ((TelephonyManager)
                            getSystemService(Context.TELEPHONY_SERVICE))
                            .getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(
                            deviceId.getBytes("utf8")).toString() : UUID
                            .randomUUID().toString();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        return uuid;
    }

}
