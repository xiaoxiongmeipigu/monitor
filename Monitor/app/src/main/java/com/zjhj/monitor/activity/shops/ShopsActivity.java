package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zjhj.commom.api.ItemApi;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.commom.result.MapiResourceResult;
import com.zjhj.commom.util.DPUtil;
import com.zjhj.commom.util.RequestExceptionCallback;
import com.zjhj.commom.util.RequestPageCallback;
import com.zjhj.commom.widget.MainToast;
import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.shops.ShopsAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.interfaces.RecyOnItemClickListener;
import com.zjhj.monitor.util.ControllerUtil;
import com.zjhj.monitor.widget.BestSwipeRefreshLayout;
import com.zjhj.monitor.widget.DividerListItemDecoration;
import com.zjhj.monitor.widget.ItemPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopsActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.search_ll)
    LinearLayout searchLl;
    @Bind(R.id.addr_cb)
    CheckBox addrCb;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    BestSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.bg_color)
    View bgColor;
    @Bind(R.id.clear_iv)
    ImageView clearIv;
    @Bind(R.id.search_et)
    EditText searchEt;

    private List<MapiItemResult> mList;
    ShopsAdapter mAdapter;

    ItemPopWindow addrPop;
    List<MapiResourceResult> addrsList;
    String addrId = "";
    String cat_id = "";

    private Integer pageIndex = 1;
    private Integer pageNum = 12;
    private Integer counts;
    String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        ButterKnife.bind(this);
        initView();
        initListener();
        loadAddr();
        load();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);

        mList = new ArrayList<>();
        addrsList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerListItemDecoration(this, OrientationHelper.HORIZONTAL, DPUtil.dip2px(4), getResources().getColor(R.color.background_gray)));
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ShopsAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

        addrPop = new ItemPopWindow(this, 0, addrsList, R.style.PopupWindowAnimation);

    }

    private void initListener() {

        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    keyword = searchEt.getText().toString();
                    refreshData();
                }
                return true;
            }
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 0) {
                    clearIv.setVisibility(View.VISIBLE);
                } else {
                    clearIv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
        addrPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                addrCb.setChecked(false);
                bgColor.setVisibility(View.GONE);
            }
        });

        addrPop.setOnPopItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (null != view) {
                    if (postion > 0) {
                        addrId = addrsList.get(postion).getId();
                        addrCb.setText(addrsList.get(postion).getName());
                    } else {
                        addrId = addrsList.get(postion).getId();
                        addrCb.setText("区域");
                    }
                    refreshData();
                }
                addrPop.dismiss();
            }
        });

        mAdapter.setOnItemClickListener(new RecyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ControllerUtil.go2ShopDetail(mList.get(position).getId());
            }
        });

    }

    private void loadAddr() {
        if (null != getIntent()) {
            cat_id = getIntent().getStringExtra("cat_id");
            List<MapiResourceResult> list = (List<MapiResourceResult>) getIntent().getSerializableExtra("list");
            if (null != list && !list.isEmpty()) {
                addrsList.clear();
                addrsList.add(new MapiResourceResult("", "区域"));
                addrsList.addAll(list);
//                addrsList.addAll(JGJDataSource.getAddrs());
                addrPop.refreshData(addrsList);
                addrCb.setText("区域");
            }

        }
    }

    private void load() {

        showLoading();
        ItemApi.merchantlist(this, pageIndex + "", pageNum + "", keyword, addrId, cat_id,new RequestPageCallback<List<MapiItemResult>>() {
            @Override
            public void success(Integer isNext, List<MapiItemResult> success) {
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

    @OnClick({R.id.back, R.id.search_ll, R.id.addr_cb, R.id.clear_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_ll:

                break;
            case R.id.addr_cb:
                if (null != addrPop) {
                    addrPop.showPopupWindow(view);
                    bgColor.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.clear_iv:
                searchEt.setText("");
                break;
        }
    }
}
