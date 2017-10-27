package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;

public class BrokeDetailActivity extends BaseActivity {
private int width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_broke_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
    width = getWindowManager().getDefaultDisplay().getWidth();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        
    }
}
