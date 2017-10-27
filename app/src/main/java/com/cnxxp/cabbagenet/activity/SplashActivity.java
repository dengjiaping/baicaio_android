package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;


public class SplashActivity extends BaseActivity {
    private Button btnUse;
    private Runnable mRunnable;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        btnUse = (Button) findViewById(R.id.btn_how_to_use);
    }

    @Override
    protected void initEvents() {
        btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UsingTutorialsActivity.class);
                handler.removeCallbacks(mRunnable);
                finish();
            }
        });

    }

    @Override
    protected void initData() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
                finish();
            }
        };
        handler.postDelayed(mRunnable, 3000);
    }
}
