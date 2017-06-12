package com.zjhj.monitor.adapter.shops;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zjhj.commom.result.IndexData;
import com.zjhj.commom.result.MapiServiceResult;
import com.zjhj.commom.util.DebugLog;
import com.zjhj.commom.util.StringUtil;
import com.zjhj.monitor.R;
import com.zjhj.monitor.interfaces.RecyOnItemClickListener;
import com.zjhj.monitor.util.webview.WebChromeClientImpl;
import com.zjhj.monitor.util.webview.WebViewUtil;

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
    private final static int DESC = 4;

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
            case DESC:
                return new DESCViewHolder(inflater.inflate(R.layout.layout_service_desc, parent, false));
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
        } else if (holder instanceof DESCViewHolder) {
            setDesc((DESCViewHolder) holder, position);
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
            case "DESC":
                return DESC;
            default:
                return FIRST;
        }
    }

    class DESCViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.desc_web)
        WebView webView;
        public DESCViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

    private void setDesc(DESCViewHolder holder, int position) {
        MapiServiceResult serviceResult = (MapiServiceResult) mList.get(position).getData();

        if (!TextUtils.isEmpty(serviceResult.getDesc_url())) {
            holder.webView.setVisibility(View.VISIBLE);
            WebSettings webSetting = holder.webView.getSettings();
            webSetting.setAllowFileAccess(true);
            webSetting.setJavaScriptEnabled(true);
            webSetting.setDomStorageEnabled(true);
            webSetting.setDatabaseEnabled(true);
            webSetting.setAppCacheEnabled(true);
            webSetting.setBuiltInZoomControls(false);
            holder.webView.loadUrl(serviceResult.getDesc_url(), WebViewUtil.getWebviewHeader());//加载网页
        } else
            holder.webView.setVisibility(View.GONE);


    }

    private void setFirst(FIRSTViewHolder holder, int position) {
        MapiServiceResult serviceResult = (MapiServiceResult) mList.get(position).getData();
        if (!TextUtils.isEmpty(serviceResult.getYymj())) {
            holder.areaLl.setVisibility(View.VISIBLE);
            holder.areaTv.setText(serviceResult.getYymj());
        } else
            holder.areaLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getDtrl())) {
            holder.capacityLl.setVisibility(View.VISIBLE);
            holder.capacityTv.setText(serviceResult.getDtrl());
        } else
            holder.capacityLl.setVisibility(View.GONE);


        if (!TextUtils.isEmpty(serviceResult.getHjfg())) {
            holder.styleLl.setVisibility(View.VISIBLE);
            holder.styleTv.setText(serviceResult.getHjfg());
        } else
            holder.styleLl.setVisibility(View.GONE);


        if (!TextUtils.isEmpty(serviceResult.getSfbc())) {
            holder.bookLl.setVisibility(View.VISIBLE);
            holder.bookTv.setText(serviceResult.getSfbc());
        } else
            holder.bookLl.setVisibility(View.GONE);

    }

    private void setBasic(BASICViewHolder holder, int position) {
        MapiServiceResult serviceResult = (MapiServiceResult) mList.get(position).getData();
        if (!TextUtils.isEmpty(serviceResult.getYddh())) {
            holder.phoneLl.setVisibility(View.VISIBLE);
            holder.phoneTv.setText(serviceResult.getYddh());
        } else
            holder.phoneLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getCtcx())) {
            holder.cookLl.setVisibility(View.VISIBLE);
            holder.cookTv.setText(serviceResult.getCtcx());
        } else
            holder.cookLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getYysj())) {
            holder.timeLl.setVisibility(View.VISIBLE);
            holder.timeTv.setText(serviceResult.getYysj());
        } else
            holder.timeLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getYydz())) {
            holder.addrLl.setVisibility(View.VISIBLE);
            holder.addrTv.setText(serviceResult.getYydz());
        } else
            holder.addrLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getGjlx())) {
            holder.lineLl.setVisibility(View.VISIBLE);
            holder.lineTv.setText(serviceResult.getGjlx());
        } else
            holder.lineLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getShcj())) {
            holder.sceneLl.setVisibility(View.VISIBLE);
            holder.sceneTv.setText(serviceResult.getShcj());
        } else
            holder.sceneLl.setVisibility(View.GONE);

    }

    private void setService(SERVICEViewHolder holder, int position) {
        MapiServiceResult serviceResult = (MapiServiceResult) mList.get(position).getData();
        if (!TextUtils.isEmpty(serviceResult.getWzkx())) {
            holder.discountLl.setVisibility(View.VISIBLE);
            holder.discountTv.setText(serviceResult.getWzkx());
        } else
            holder.discountLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getWffx())) {
            holder.scoreLl.setVisibility(View.VISIBLE);
            holder.scoreTv.setText(serviceResult.getWffx());
        } else
            holder.scoreLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getZysx())) {
            holder.careLl.setVisibility(View.VISIBLE);
            holder.careTv.setText(serviceResult.getZysx());
        } else
            holder.careLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getNftc())) {
            holder.stopLl.setVisibility(View.VISIBLE);
            holder.stopTv.setText(serviceResult.getNftc());
        } else
            holder.stopLl.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(serviceResult.getNfsk())) {
            holder.cardLl.setVisibility(View.VISIBLE);
            holder.cardTv.setText(serviceResult.getNfsk());
        } else
            holder.cardLl.setVisibility(View.GONE);

    }

}
