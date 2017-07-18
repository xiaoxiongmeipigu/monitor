package com.zjhj.monitor.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.util.DPUtil;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.news.NewsItemAdapter;
import com.zjhj.monitor.adapter.news.NewsListAdapter;
import com.zjhj.monitor.interfaces.RecyOnItemClickListener;
import com.zjhj.monitor.util.ControllerUtil;
import com.zjhj.monitor.widget.DividerListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/7/4.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<MapiResourceResult> mList;
    private RecyOnItemClickListener onItemClickListener;
    Context mContext;
    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MainAdapter(Context context, List<MapiResourceResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MapiResourceResult mapiResourceResult = mList.get(position);
        holder.title.setText(TextUtils.isEmpty(mapiResourceResult.getName())?"":mapiResourceResult.getName());

        final List<MapiResourceResult> list = mapiResourceResult.getPost_list();
        if(null!=list){
            holder.recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            holder.recyclerView.addItemDecoration(new DividerListItemDecoration(mContext, OrientationHelper.HORIZONTAL, DPUtil.dip2px(0.5f),mContext.getResources().getColor(R.color.background_gray)));
            holder.recyclerView.setLayoutManager(linearLayoutManager);
            NewsListAdapter mAdapter = new NewsListAdapter(mContext, list);
            holder.recyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String url = list.get(position).getPost_url();
                    if(!TextUtils.isEmpty(url))
                        ControllerUtil.go2WebView(url,"新闻详情","","","",false);
                }
            });
        }else
            holder.recyclerView.setVisibility(View.GONE);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.recyclerView)
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
