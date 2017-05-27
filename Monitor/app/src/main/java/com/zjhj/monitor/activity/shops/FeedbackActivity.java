package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.monitor.R;
import com.zjhj.monitor.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.center)
    TextView center;
    @Bind(R.id.title_et)
    EditText titleEt;
    @Bind(R.id.info_et)
    EditText infoEt;
    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.phone_et)
    EditText phoneEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        center.setText("信息反馈");
        back.setImageResource(R.mipmap.back_white);
    }

    @OnClick({R.id.back, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                break;
        }
    }
}
