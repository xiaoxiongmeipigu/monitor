package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.commom.api.ItemApi;
import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.util.RequestCallback;
import com.zjhj.commom.util.RequestExceptionCallback;
import com.zjhj.commom.widget.MainToast;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.MainAdapter;
import com.zjhj.monitor.adapter.shops.ShopTypeAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.interfaces.RecyOnItemClickListener;
import com.zjhj.monitor.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopTypeActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    List<MapiResourceResult> mList;
    ShopTypeAdapter mAdapter;
    ArrayList<MapiResourceResult> addrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_type);
        ButterKnife.bind(this);
        if(null!=getIntent()){
            addrs = (ArrayList<MapiResourceResult>) getIntent().getSerializableExtra("list");
        }
        initView();
        initListener();
        load();
    }

    private void initView() {

        mList = new ArrayList<>();
        back.setImageResource(R.mipmap.back_white);
        center.setText("透明厨房");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ShopTypeAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(null!=addrs){
                    ControllerUtil.go2Shops(addrs,mList.get(position).getId());
                }
            }
        });
    }

    private void load(){
        showLoading();
        ItemApi.merchantcatlist(this, new RequestCallback<List<MapiResourceResult>>() {
            @Override
            public void success(List<MapiResourceResult> success) {
                hideLoading();
                if(null==success||success.isEmpty())
                    return;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                mList.addAll(success);
                mAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
