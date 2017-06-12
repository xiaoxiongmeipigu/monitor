package com.zjhj.monitor.activity.shops;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjhj.commom.api.ItemApi;
import com.zjhj.commom.result.MapiItemResult;
import com.zjhj.commom.util.RequestCallback;
import com.zjhj.commom.util.RequestExceptionCallback;
import com.zjhj.commom.widget.MainToast;
import com.zjhj.monitor.R;
import com.zjhj.monitor.base.BaseActivity;
import com.zjhj.monitor.base.TempData;

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

    MapiItemResult itemResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        if(null!=getIntent())
            itemResult = (MapiItemResult) getIntent().getSerializableExtra("item");
        if(null!=itemResult){
            initView();
        }

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
                submit();
                break;
        }
    }

    private void submit(){

        String titleStr = titleEt.getText().toString();
        String infoStr = infoEt.getText().toString();
        String nameStr = nameEt.getText().toString();
        String phoneStr = phoneEt.getText().toString();

        if(TextUtils.isEmpty(titleStr)){
            MainToast.showShortToast("请输入主题");
            return;
        }

        if(TextUtils.isEmpty(infoStr)){
            MainToast.showShortToast("请输入反馈内容");
            return;
        }

        if(TextUtils.isEmpty(nameStr)){
            MainToast.showShortToast("请输入姓名");
            return;
        }

        if(TextUtils.isEmpty(phoneStr)){
            MainToast.showShortToast("请输入电话");
            return;
        }
        showLoading();
        ItemApi.merchantfeedback(this, itemResult.getId(), titleStr, infoStr, nameStr, phoneStr, new RequestCallback() {
            @Override
            public void success(Object success) {
                hideLoading();
                MainToast.showShortToast("您的反馈已提交，平台正在审核中...");
                finish();
            }
        }, new RequestExceptionCallback() {
            @Override
            public void error(Integer code, String message) {
                hideLoading();
                MainToast.showShortToast(message);
            }
        });

    }

}
