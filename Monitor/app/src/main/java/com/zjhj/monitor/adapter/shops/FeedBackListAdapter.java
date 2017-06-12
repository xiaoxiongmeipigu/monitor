package com.zjhj.monitor.adapter.shops;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjhj.commom.result.MapiFeedResult;
import com.zjhj.monitor.R;
import com.zjhj.monitor.interfaces.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/6/3.
 */
public class FeedBackListAdapter extends RecyclerView.Adapter<FeedBackListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    List<MapiFeedResult> mList;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FeedBackListAdapter(Context context, List<MapiFeedResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_feed_back_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.onItemClick(view, (Integer) view.getTag());
            }
        });

        MapiFeedResult feedResult = mList.get(position);
        holder.title.setText(TextUtils.isEmpty(feedResult.getTitle())?"":feedResult.getTitle());
        holder.addtime.setText(TextUtils.isEmpty(feedResult.getAddtime())?"":feedResult.getAddtime());
        holder.name.setText(TextUtils.isEmpty(feedResult.getName())?"":feedResult.getName());
        holder.content.setText(TextUtils.isEmpty(feedResult.getContent())?"":feedResult.getContent());
        if(TextUtils.isEmpty(feedResult.getReply())){
            holder.reply.setVisibility(View.GONE);
        }else{
            holder.reply.setVisibility(View.VISIBLE);
            holder.reply.setText("回复："+feedResult.getReply());
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.root_view)
        LinearLayout rootView;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.addtime)
        TextView addtime;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.reply)
        TextView reply;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
