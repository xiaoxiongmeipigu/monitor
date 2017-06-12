package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.commom.api.ItemApi;
import com.zjhj.commom.result.MapiCameraResult;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.commom.util.DPUtil;
import com.zjhj.commom.util.RequestCallback;
import com.zjhj.commom.util.RequestExceptionCallback;
import com.zjhj.commom.widget.MainToast;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.shops.CameraListAdapter;
import com.zjhj.monitor.adapter.shops.ShopsAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.base.TempData;
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

public class CameraListActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
//    @Bind(R.id.swipeRefreshLayout)
//    BestSwipeRefreshLayout swipeRefreshLayout;

    private List<MapiItemResult> mList;
    CameraListAdapter mAdapter;

    MapiItemResult itemResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_list);
        ButterKnife.bind(this);
        if(null!=getIntent())
            itemResult = (MapiItemResult) getIntent().getSerializableExtra("item");
        if(null!=itemResult){
            initView();
            initListener();
            load();
        }

    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);
        center.setText("监控列表");
        mList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(4), getResources().getColor(R.color.background_gray)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CameraListAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MapiCameraResult cameraResult = mList.get(position).getParam();
                if(null!=cameraResult&& null!=TempData.getIns().getLoginData())
                    ControllerUtil.go2Live(cameraResult);
                else
                    MainToast.showShortToast("未检测到监控信息");
            }
        });
    }

    private void load() {

        showLoading();
        ItemApi.merchantcamera(this, itemResult.getId(), new RequestCallback<List<MapiItemResult>>() {
            @Override
            public void success(List<MapiItemResult> success) {
                hideLoading();
                if(null==success||success.isEmpty())
                    return;
                mList.clear();
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
