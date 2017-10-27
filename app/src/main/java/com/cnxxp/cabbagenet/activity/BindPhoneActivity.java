package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity {
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_phone);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("手机验证");
    }

    @Override
    protected void initEvents() {


    }

    @Override
    protected void initData() {
        mobile = getIntent().getExtras().getString("mobile");
        tvPhone.setText(mobile);

    }

    @OnClick(R.id.tv_rebind)
    public void onClick() {
        
        startActivity(CheckingPhoneActivity.class);

    }

}
