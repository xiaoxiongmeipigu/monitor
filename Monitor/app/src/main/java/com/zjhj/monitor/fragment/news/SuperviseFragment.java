package com.zjhj.monitor.fragment.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjhj.commom.api.ItemApi;
import com.zjhj.commom.result.IndexData;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.commom.util.RequestExceptionCallback;
import com.zjhj.commom.util.RequestPageCallback;
import com.zjhj.commom.widget.MainToast;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.news.NewsItemAdapter;
import com.zjhj.monitor.base.BaseFrag;
import com.zjhj.monitor.interfaces.RecyOnItemClickListener;
import com.zjhj.monitor.util.ControllerUtil;
import com.zjhj.monitor.widget.BestSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuperviseFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    List<MapiItemResult> list;
    List<IndexData> mList;

    NewsItemAdapter mAdapter;

    private Integer pageIndex = 1;
    private Integer pageNum = 12;
    private Integer counts;

    public SuperviseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        initView();
        initListener();
        load();
        return view;
    }

    private void initView(){

        list = new ArrayList<>();
        mList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new NewsItemAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initListener(){
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

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MapiItemResult mapiItemResult = (MapiItemResult) mList.get(position).getData();
                String url = mapiItemResult.getPost_url();
                if(!TextUtils.isEmpty(url))
                    ControllerUtil.go2WebView(url,"新闻详情","","","",false);
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

    public void load(){
        showLoading();
        ItemApi.postlist(getActivity(), pageIndex + "", pageNum + "", "4", new RequestPageCallback<List<MapiItemResult>>() {
            @Override
            public void success(Integer isNext, List<MapiItemResult> success) {
                hideLoading();
                swipeRefreshLayout.setRefreshing(false);
                counts = isNext;
                if (success.isEmpty())
                    return;
                list.clear();
                list.addAll(success);
                initData(list);
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

    private void initData(List<MapiItemResult> data){
        int count = 0;
        if(mList.size()>0)
            count = mList.size();

        for (MapiItemResult mapiItemResult : data) {
            mList.add(new IndexData(count++, "DATE", mapiItemResult));
            if(null!=mapiItemResult.getList()){
                int pos = 0;
                for(MapiItemResult itemResult : mapiItemResult.getList()) {
                    pos++;
                    mList.add(new IndexData(count++, "ITEM", itemResult));
                    if(pos<mapiItemResult.getList().size())
                        mList.add(new IndexData(count++, "DIVIDER", new Object()));
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
