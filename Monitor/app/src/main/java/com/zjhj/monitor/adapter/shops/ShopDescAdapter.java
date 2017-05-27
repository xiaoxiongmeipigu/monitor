package com.zjhj.monitor.adapter.shops;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjhj.commom.result.IndexData;
import com.zjhj.monitor.R;
import com.zjhj.monitor.interfaces.RecyOnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/5/25.
 */
public class ShopDescAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int FIRST = 0;
    private final static int BASIC = 1;
    private final static int SERVICE = 3;

    LayoutInflater inflater;

    private List<IndexData> mList;
    private Context mContext;
    private RecyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ShopDescAdapter(Context context, List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case FIRST:
                return new FIRSTViewHolder(inflater.inflate(R.layout.layout_service_first, parent, false));
            case BASIC:
                return new BASICViewHolder(inflater.inflate(R.layout.layout_service_basic, parent, false));
            case SERVICE:
                return new SERVICEViewHolder(inflater.inflate(R.layout.layout_service_service, parent, false));
            default:
                return new FIRSTViewHolder(inflater.inflate(R.layout.layout_service_first, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FIRSTViewHolder) {
            setFirst((FIRSTViewHolder) holder, position);
        } else if (holder instanceof BASICViewHolder) {
            setBasic((BASICViewHolder) holder, position);
        } else if (holder instanceof SERVICEViewHolder) {
            setService((SERVICEViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getType()) {
            case "FIRST":
                return FIRST;
            case "BASIC":
                return BASIC;
            case "SERVICE":
                return SERVICE;
            default:
                return FIRST;
        }
    }

    class FIRSTViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.area_tv)
        TextView areaTv;
        @Bind(R.id.area_ll)
        LinearLayout areaLl;
        @Bind(R.id.capacity_tv)
        TextView capacityTv;
        @Bind(R.id.capacity_ll)
        LinearLayout capacityLl;
        @Bind(R.id.style_tv)
        TextView styleTv;
        @Bind(R.id.style_ll)
        LinearLayout styleLl;
        @Bind(R.id.book_tv)
        TextView bookTv;
        @Bind(R.id.book_ll)
        LinearLayout bookLl;

        public FIRSTViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BASICViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.phone_tv)
        TextView phoneTv;
        @Bind(R.id.phone_ll)
        LinearLayout phoneLl;
        @Bind(R.id.cook_tv)
        TextView cookTv;
        @Bind(R.id.cook_ll)
        LinearLayout cookLl;
        @Bind(R.id.time_tv)
        TextView timeTv;
        @Bind(R.id.time_ll)
        LinearLayout timeLl;
        @Bind(R.id.addr_tv)
        TextView addrTv;
        @Bind(R.id.addr_ll)
        LinearLayout addrLl;
        @Bind(R.id.line_tv)
        TextView lineTv;
        @Bind(R.id.line_ll)
        LinearLayout lineLl;
        @Bind(R.id.scene_tv)
        TextView sceneTv;
        @Bind(R.id.scene_ll)
        LinearLayout sceneLl;

        public BASICViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class SERVICEViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.discount_tv)
        TextView discountTv;
        @Bind(R.id.discount_ll)
        LinearLayout discountLl;
        @Bind(R.id.score_tv)
        TextView scoreTv;
        @Bind(R.id.score_ll)
        LinearLayout scoreLl;
        @Bind(R.id.care_tv)
        TextView careTv;
        @Bind(R.id.care_ll)
        LinearLayout careLl;
        @Bind(R.id.stop_tv)
        TextView stopTv;
        @Bind(R.id.stop_ll)
        LinearLayout stopLl;
        @Bind(R.id.card_tv)
        TextView cardTv;
        @Bind(R.id.card_ll)
        LinearLayout cardLl;
        public SERVICEViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setFirst(FIRSTViewHolder holder, int position) {

    }

    private void setBasic(BASICViewHolder holder, int position) {

    }

    private void setService(SERVICEViewHolder holder, int position) {


    }

}
