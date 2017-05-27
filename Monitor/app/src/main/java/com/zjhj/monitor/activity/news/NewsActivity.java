package com.zjhj.monitor.activity.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.TabFragmentAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.fragment.news.CompanyFragment;
import com.zjhj.monitor.fragment.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private List<String> list_title = new ArrayList<>();
    private List<Fragment> list = new ArrayList<>();
    TabFragmentAdapter mAdapter;

    NewsFragment newsFragment;
    CompanyFragment companyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);
        center.setText("市监动态");

        newsFragment = new NewsFragment();
        companyFragment = new CompanyFragment();

        list.add(newsFragment);
        list.add(companyFragment);

        list_title.add("新闻资讯");
        list_title.add("公司公告");

        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);

    }

    private void initListener() {

    }

    private void load() {

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

}
