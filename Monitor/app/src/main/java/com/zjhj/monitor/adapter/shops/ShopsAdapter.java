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

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.commom.util.DPUtil;
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

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(TextUtils.isEmpty(mapiItemResult.getCover_pic())?"":mapiItemResult.getCover_pic());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(100), DPUtil.dip2px(80)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.title.setText(TextUtils.isEmpty(mapiItemResult.getName())?"":mapiItemResult.getName());
        holder.phone.setText(TextUtils.isEmpty(mapiItemResult.getTel())?"":mapiItemResult.getTel());
        holder.addrTv.setText(TextUtils.isEmpty(mapiItemResult.getAddress())?"":mapiItemResult.getAddress());
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
