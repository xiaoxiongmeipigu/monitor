package com.zjhj.monitor.adapter.news;

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
import com.zjhj.commom.result.IndexData;
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
public class NewsItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int DATE = 0;
    private final static int ITEM = 1;
    private final static int DIVIDER = 2;

    LayoutInflater inflater;

    private List<IndexData> mList;

    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NewsItemAdapter(Context context, List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DATE:
                return new DateViewHolder(inflater.inflate(R.layout.item_news_date, parent, false));
            case ITEM:
                return new ItemHotViewHolder(inflater.inflate(R.layout.item_news, parent, false));
            case DIVIDER:
                return new DividerViewHolder(inflater.inflate(R.layout.item_divider_background_hight_eight, parent, false));
            default:
                return new DividerViewHolder(inflater.inflate(R.layout.item_divider_background_hight_eight, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateViewHolder) {
            setDate((DateViewHolder) holder, position);
        } else if (holder instanceof ItemHotViewHolder) {
            setItem((ItemHotViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "DATE":
                return DATE;
            case "ITEM":
                return ITEM;
            case "DIVIDER":
                return DIVIDER;
            default:
                return DIVIDER;
        }
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.date_tv)
        TextView dateTv;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemHotViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.desc)
        TextView desc;
        @Bind(R.id.root_view)
        LinearLayout rootView;

        public ItemHotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setDate(DateViewHolder holder, int position) {
        MapiItemResult mapiItemResult = (MapiItemResult) mList.get(position).getData();
        holder.dateTv.setText(TextUtils.isEmpty(mapiItemResult.getDate()) ? "" : mapiItemResult.getDate());
    }

    private void setItem(ItemHotViewHolder holder, int position) {
        MapiItemResult mapiItemResult = (MapiItemResult) mList.get(position).getData();
        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.onItemClick(view, (Integer) view.getTag());
            }
        });
        holder.title.setText(TextUtils.isEmpty(mapiItemResult.getTitle()) ? "" : mapiItemResult.getTitle());
        holder.desc.setText(TextUtils.isEmpty(mapiItemResult.getDesc()) ? "" : mapiItemResult.getDesc());

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

    }

}
