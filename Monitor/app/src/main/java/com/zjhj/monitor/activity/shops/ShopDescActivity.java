package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.commom.result.IndexData;
import com.zjhj.commom.result.MapiResourceResult;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_desc);
        ButterKnife.bind(this);
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
        mList.add(new IndexData(0, "FIRST", new MapiResourceResult()));
        mList.add(new IndexData(0, "BASIC", new MapiResourceResult()));
        mList.add(new IndexData(0, "SERVICE", new MapiResourceResult()));
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
