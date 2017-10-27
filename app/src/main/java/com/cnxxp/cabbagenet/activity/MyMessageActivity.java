package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.fragment.PrivateLetterFragment;
import com.cnxxp.cabbagenet.fragment.SystemMessageFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/4/5 0005.
 */

public class MyMessageActivity extends BaseActivity {

    private static final String TAG = "MyMessageActivity";
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.view2)
    View view2;
    private SystemMessageFragment systemMessageFragment;
    private PrivateLetterFragment privateLetterFragment;
    private Fragment[] fragments;
    private View[] views;
    private TextView[] textViews;
    public int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("我的消息");
        initTitle();
    }

    private void initTitle() {
        currentIndex = 0;
        textViews = new TextView[]{tv1, tv2};
        views = new View[]{view1, view2};
        systemMessageFragment = new SystemMessageFragment();
        privateLetterFragment = new PrivateLetterFragment();
        fragments = new BaseFragment[]{systemMessageFragment, privateLetterFragment};
        textViews[currentIndex].setSelected(true);
        views[currentIndex].setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_message, fragments[currentIndex])
                .show(fragments[currentIndex])
                .commit();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    private void switchTitle(int index) {
        if (currentIndex != index) {
            FragmentTransaction trx = this.getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fl_message, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            views[currentIndex].setVisibility(View.GONE);
            textViews[currentIndex].setSelected(false);
            views[index].setVisibility(View.VISIBLE);
            textViews[index].setSelected(true);
            currentIndex = index;
        }
    }


    @OnClick({R.id.ll_bg1, R.id.ll_bg2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bg1:
                switchTitle(0);
                break;
            case R.id.ll_bg2:
                switchTitle(1);
                break;
        }
    }
}
