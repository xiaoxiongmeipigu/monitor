package com.zjhj.monitor.activity.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.monitor.R;
import com.zjhj.monitor.adapter.TabFragmentAdapter;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.fragment.news.ConsumeFragment;
import com.zjhj.monitor.fragment.news.NoticeFragment;
import com.zjhj.monitor.fragment.news.PolicyFragment;
import com.zjhj.monitor.fragment.news.PunishFragment;
import com.zjhj.monitor.fragment.news.SuperviseFragment;

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

    NoticeFragment newsFragment;
    ConsumeFragment companyFragment;
    PunishFragment punishFragment;
    SuperviseFragment superviseFragment;
    PolicyFragment policyFragment;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        if(null!=getIntent())
            pos = getIntent().getIntExtra("position",0);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        back.setImageResource(R.mipmap.back_white);
        center.setText("市监动态");

        newsFragment = new NoticeFragment();
        companyFragment = new ConsumeFragment();
        punishFragment = new PunishFragment();
        superviseFragment = new SuperviseFragment();
        policyFragment = new PolicyFragment();

        list.add(newsFragment);
        list.add(companyFragment);
        list.add(punishFragment);
        list.add(superviseFragment);
        list.add(policyFragment);

        list_title.add("公告公示");
        list_title.add("消费维权");
        list_title.add("处罚信息");
        list_title.add("监管动态");
        list_title.add("政策法规");

        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)));
        tablayout.addTab(tablayout.newTab().setText(list_title.get(4)));
        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), list, list_title);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(pos);
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
