package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class CheckingEmailActivity extends BaseActivity {

    @Bind(R.id.tv_email)
    TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_checking_email);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("邮箱验证");
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_change_bind)
    public void onClick() {
        //换绑操作
    }
}
