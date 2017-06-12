package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.commom.api.ItemApi;
import com.zjhj.commom.result.MapiFeedResult;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.commom.util.DPUtil;
import com.zjhj.commom.util.RequestExceptionCallback;
import com.zjhj.commom.util.RequestPageCallback;
import com.zjhj.commom.widget.MainToast;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.shops.FeedBackListAdapter;
import com.zjhj.monitor.adapter.shops.ShopsAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.util.ControllerUtil;
import com.zjhj.monitor.widget.BestSwipeRefreshLayout;
import com.zjhj.monitor.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackListActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    private Integer pageIndex = 1;
    private Integer pageNum = 12;
    private Integer counts;

    FeedBackListAdapter mAdapter;

    MapiItemResult itemResult;

    List<MapiFeedResult> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_list);
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

        mList = new ArrayList<>();

        back.setImageResource(R.mipmap.back_white);
        center.setText("信息反馈");
        ivRight.setImageResource(R.mipmap.feedback_big_logo_white);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(10), getResources().getColor(R.color.background_gray)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new FeedBackListAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener() {
        swipeRefreshLayout.setBestRefreshListener(new BestSwipeRefreshLayout.BestRefreshListener() {
            @Override
            public void onBestRefresh() {
                refreshData();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && manager.findLastVisibleItemPosition() >= 0 && (manager.findLastVisibleItemPosition() == (manager.getItemCount() - 1))) {
                    loadNext();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void load() {
        showLoading();
        ItemApi.merchantfeedbacklist(this, itemResult.getId(), pageIndex + "", pageNum + "", new RequestPageCallback<List<MapiFeedResult>>() {
            @Override
            public void success(Integer isNext, List<MapiFeedResult> success) {
                hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                counts = isNext;
                if (success.isEmpty())
                    return;
                mList.addAll(success);
                mAdapter.notifyDataSetChanged();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                MainToast.showShortToast(message);
            }
        });
    }

    private void loadNext() {
        if (counts == null || counts <= pageIndex) {
            MainToast.showShortToast("没有更多数据了");
            return;
        }
        pageIndex++;
        load();
    }

    public void refreshData() {
        if (null != mList) {
            mList.clear();
            pageIndex = 1;
            mAdapter.notifyDataSetChanged();
            load();
        }
    }

    @OnClick({R.id.back, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_right:
                ControllerUtil.go2Feedback(itemResult);
                break;
        }
    }
}
