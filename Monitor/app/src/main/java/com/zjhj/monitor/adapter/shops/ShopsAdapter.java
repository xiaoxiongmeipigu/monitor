package com.zjhj.monitor.adapter.shops;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.monitor.R;
import com.zjhj.monitor.interfaces.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/5/25.
 */
public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<MapiItemResult> mList;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ShopsAdapter(Context context, List<MapiItemResult> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_shops, parent, false));
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

        MapiItemResult mapiItemResult = mList.get(position);
        if(position==2){
            holder.image.setImageURI(Uri.parse("res:///" +R.drawable.shop_three));
        }else if(position==1){
            holder.image.setImageURI(Uri.parse("res:///" +R.drawable.shop_two));
        }else if(position==0){
            holder.image.setImageURI(Uri.parse("res:///" +R.drawable.shop_one));
        }

        holder.title.setText(TextUtils.isEmpty(mapiItemResult.getTitle())?"":mapiItemResult.getTitle());
        holder.phone.setText(TextUtils.isEmpty(mapiItemResult.getPhone())?"":mapiItemResult.getPhone());
        holder.addrTv.setText(TextUtils.isEmpty(mapiItemResult.getAddr())?"":mapiItemResult.getAddr());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.root_view)
        LinearLayout rootView;
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.phone)
        TextView phone;
        @Bind(R.id.addr_tv)
        TextView addrTv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
