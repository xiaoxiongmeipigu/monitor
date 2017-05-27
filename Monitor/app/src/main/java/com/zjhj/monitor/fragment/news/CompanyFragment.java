package com.zjhj.monitor.fragment.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjhj.commom.result.IndexData;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.news.NewsItemAdapter;
import com.zjhj.monitor.base.BaseFrag;
import com.zjhj.monitor.widget.BestSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyFragment extends BaseFrag {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;

    List<MapiItemResult> list;
    List<IndexData> mList;

    NewsItemAdapter mAdapter;

    public CompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_company, container, false);
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
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void load(){

        list.clear();

        MapiItemResult parentOne = new MapiItemResult();
        parentOne.setDate("2017-03-15");

        List<MapiItemResult> lists = new ArrayList<>();

        MapiItemResult itemResult = new MapiItemResult();
        itemResult.setTitle("积极参与 广泛宣传 长安分局助力文明城市");
        itemResult.setDesc("今年是创建全国文明城市攻坚年，为扩大全国文明城市创建的宣传面，营造共创美好家园的良好氛围");

        MapiItemResult itemResult2 = new MapiItemResult();
        itemResult2.setTitle("海洲所发出了辖区内首张”三小一摊“食品生产经营登记证");
        itemResult2.setDesc("近日，海洲所发出了辖区内首张”三小一摊“食品生产经营登记证。根据2017年5月1日起施行的《浙江省食品小作坊小餐饮店小食杂店和食品摊贩管理规定》");
        lists.add(itemResult);
        lists.add(itemResult2);

        parentOne.setList(lists);
        list.add(parentOne);

        MapiItemResult parentTwo = new MapiItemResult();
        parentTwo.setDate("2017-02-07");

        List<MapiItemResult> listsTwo = new ArrayList<>();

        MapiItemResult itemResult3 = new MapiItemResult();
        itemResult3.setTitle("袁花分局积极主动服务榨油小作坊建设");
        itemResult3.setDesc("2017年5月1日起正式实施的《浙江省食品小作坊小餐饮店小食杂店和食品摊贩管理规定》，把");

        MapiItemResult itemResult4 = new MapiItemResult();
        itemResult4.setTitle("海宁开展食品相关产品质量安全排雷”百日攻坚");
        itemResult4.setDesc("根据省及嘉兴市食品安全”百日攻坚“行动方案要求，结合海宁实际，海宁市市场监管局在全市范围内开展");
        listsTwo.add(itemResult3);
        listsTwo.add(itemResult4);

        parentTwo.setList(listsTwo);
        list.add(parentTwo);

        initData(list);

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
