package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.util.DPUtil;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.link.LinkAdapter;
import com.zjhj.monitor.adapter.shops.ShopsAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.interfaces.RecyOnItemClickListener;
import com.zjhj.monitor.util.ControllerUtil;
import com.zjhj.monitor.util.JGJDataSource;
import com.zjhj.monitor.widget.BestSwipeRefreshLayout;
import com.zjhj.monitor.widget.DividerListItemDecoration;
import com.zjhj.monitor.widget.ItemPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopsActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.search_ll)
    LinearLayout searchLl;
    @Bind(R.id.addr_cb)
    CheckBox addrCb;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.bg_color)
    View bgColor;

    private List<MapiItemResult> mList;
    ShopsAdapter mAdapter;

    ItemPopWindow addrPop;
    List<MapiResourceResult> addrsList;
    String addrId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        ButterKnife.bind(this);
        initView();
        initListener();
        load();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);

        mList = new ArrayList<>();
        addrsList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(4), getResources().getColor(R.color.background_gray)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ShopsAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

        addrPop = new ItemPopWindow(this, 0, addrsList, R.style.PopupWindowAnimation);

    }

    private void initListener() {
        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        addrPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                addrCb.setChecked(false);
                bgColor.setVisibility(View.GONE);
            }
        });

        addrPop.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion > 0) {
                        addrId = addrsList.get(postion).getId();
                        addrCb.setText(addrsList.get(postion).getName());
                    } else {
                        addrId = "";
                        addrCb.setText("硖石街道");
                    }
//                    refreshData();
                }
                addrPop.dismiss();
            }
        });

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2ShopDetail();
            }
        });

    }

    private void load() {

        MapiItemResult itemResultOne = new MapiItemResult();
        itemResultOne.setTitle("台资味");
        itemResultOne.setPhone("13562541286");
        itemResultOne.setAddr("海宁市海洲街道工人路240号");

        MapiItemResult itemResultTwo = new MapiItemResult();
        itemResultTwo.setTitle("老娘舅");
        itemResultTwo.setPhone("13562541286");
        itemResultTwo.setAddr("海宁市硖石街道海昌路1号");

        MapiItemResult itemResultThree = new MapiItemResult();
        itemResultThree.setTitle("必胜客");
        itemResultThree.setPhone("13562541286");
        itemResultThree.setAddr("海宁市海洲街道工人路60号");

        mList.add(itemResultOne);
        mList.add(itemResultTwo);
        mList.add(itemResultThree);

        mAdapter.notifyDataSetChanged();

        addrsList.clear();
        addrsList.addAll(JGJDataSource.getAddrs());
        addrPop.refreshData(addrsList);

    }

    @OnClick({R.id.back, R.id.search_ll, R.id.addr_cb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_ll:

                break;
            case R.id.addr_cb:
                if (null != addrPop) {
                    addrPop.showPopupWindow(view);
                    bgColor.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
