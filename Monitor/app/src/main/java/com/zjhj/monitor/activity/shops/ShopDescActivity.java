package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.commom.result.IndexData;
import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.result.MapiServiceResult;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.shops.ShopDescAdapter;
import com.zjhj.monitor.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopDescActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    ShopDescAdapter mAdapter;
    List<IndexData> mList;

    MapiServiceResult xqjsResult;
    MapiServiceResult jbxxResult;
    MapiServiceResult fwResult;
    MapiServiceResult descResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_desc);
        ButterKnife.bind(this);
        if(null!=getIntent()){
            xqjsResult = (MapiServiceResult) getIntent().getSerializableExtra("xqjs");
            jbxxResult = (MapiServiceResult) getIntent().getSerializableExtra("jbxx");
            fwResult = (MapiServiceResult) getIntent().getSerializableExtra("fw");
            descResult = (MapiServiceResult) getIntent().getSerializableExtra("desc");
        }
        initView();
        initListener();
        load();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);
        center.setText("详情介绍");

        mList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ShopDescAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {

    }

    public void load(){
        mList.clear();
        if(null!=descResult)
            mList.add(new IndexData(0, "DESC",descResult));
        if(null!=xqjsResult)
            mList.add(new IndexData(1, "FIRST",xqjsResult));
        if(null!=jbxxResult)
            mList.add(new IndexData(2, "BASIC",jbxxResult));
        if(null!=fwResult)
            mList.add(new IndexData(3, "SERVICE",fwResult));

        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
