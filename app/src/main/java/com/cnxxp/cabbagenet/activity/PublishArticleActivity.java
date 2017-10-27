package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.fragment.ArticleFragment;
import com.cnxxp.cabbagenet.fragment.BrokeFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class PublishArticleActivity extends BaseActivity {

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.view2)
    View view2;
    private View[] mView;
    private TextView[] mTextView;
    private int CurrentIndex;
    private BaseFragment[] mFragments;
    BrokeFragment mBrokeFragment;
    ArticleFragment articleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_publish_article);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("发表文章");
        initFragment();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    private void initFragment() {
        CurrentIndex = 0;
        mView = new View[]{view1, view2};
        mTextView = new TextView[]{tv1, tv2};
        mTextView[CurrentIndex].setSelected(true);
        mView[CurrentIndex].setVisibility(View.VISIBLE);
        mBrokeFragment = new BrokeFragment();
        articleFragment = new ArticleFragment();
        mFragments = new BaseFragment[]{mBrokeFragment, articleFragment};
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_publish_article, mFragments[CurrentIndex])
                .show(mFragments[CurrentIndex])
                .commit();
    }

    private void switchFragment(int index) {
        if (index != CurrentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[CurrentIndex]);
            if (!mFragments[index].isAdded()) {
                ft.add(R.id.fl_publish_article, mFragments[index]);
            }
            ft.setTransition(FragmentTransaction.TRANSIT_NONE);// 设置无动画效果
            ft.show(mFragments[index]).commit();
            mTextView[CurrentIndex].setSelected(false);
            mView[CurrentIndex].setVisibility(View.INVISIBLE);
            mTextView[index].setSelected(true);
            mView[index].setVisibility(View.VISIBLE);
            CurrentIndex = index;
        }
    }

    @OnClick({R.id.activity_more_text_bg, R.id.ll_bg1, R.id.ll_bg2})
    public void onClick(View view) {
        switch (view.getId()) {
          
            case R.id.ll_bg1:
                switchFragment(0);
                break;
            case R.id.ll_bg2:
                switchFragment(1);
                break;
        }
    }
}
