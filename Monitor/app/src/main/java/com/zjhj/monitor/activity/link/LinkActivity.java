package com.zjhj.monitor.activity.link;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.util.DPUtil;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.link.LinkAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.widget.BestSwipeRefreshLayout;
import com.zjhj.monitor.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LinkActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    List<MapiResourceResult> mList;
    LinkAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);
        center.setText("常用链接");

        mList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(0.5f), getResources().getColor(R.color.divider_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new LinkAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {
        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void load() {

        MapiResourceResult mapiResourceResult = new MapiResourceResult();
        mapiResourceResult.setTitle("浙江省市场监管局");
        mapiResourceResult.setUrl("www.hzscjg.gov.cn");

        MapiResourceResult mapiResourceResult2 = new MapiResourceResult();
        mapiResourceResult2.setTitle("嘉兴市市场监管局");
        mapiResourceResult2.setUrl("www.jiaxingaic.gov.cn");

        MapiResourceResult mapiResourceResult3 = new MapiResourceResult();
        mapiResourceResult3.setTitle("海宁市场监管局");
        mapiResourceResult3.setUrl("www.zjhngs.gov.cn");

        mList.add(mapiResourceResult);
        mList.add(mapiResourceResult2);
        mList.add(mapiResourceResult3);
        mAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
