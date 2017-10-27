package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;

public class ExchangeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exchange);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("积分兑换");
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        
    }
}
