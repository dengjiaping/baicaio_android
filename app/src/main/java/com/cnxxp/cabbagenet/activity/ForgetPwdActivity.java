package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.fragment.OtherFindPwdFragment;
import com.cnxxp.cabbagenet.fragment.PhoneFindPwdFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseActivity {


    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.view2)
    View view2;

    private PhoneFindPwdFragment phoneFindPwdFragment;
    private OtherFindPwdFragment otherFindPwdFragment;
    private BaseFragment[] fragments;
    private int currentIndex;
    private View[] mView;
    private TextView[] mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_pwd);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("忘记密码");
        initFragment();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    private void initFragment() {
        currentIndex = 0;
        mView = new View[]{view1, view2};
        mTextView = new TextView[]{tv1, tv2};
        mTextView[currentIndex].setSelected(true);
        mView[currentIndex].setVisibility(View.VISIBLE);
        phoneFindPwdFragment = new PhoneFindPwdFragment();
        otherFindPwdFragment = new OtherFindPwdFragment();
        fragments = new BaseFragment[]{phoneFindPwdFragment, otherFindPwdFragment};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_find_pwd, fragments[0])
                .show(fragments[0])
                .commit();
    }

    public void switchFragment(int index) {
        if (currentIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fl_find_pwd, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            mView[currentIndex].setVisibility(View.GONE);
            mTextView[currentIndex].setSelected(false);
            mView[index].setVisibility(View.VISIBLE);
            mTextView[index].setSelected(true);
            currentIndex = index;
        }
    }

    @OnClick({R.id.ll_bg1, R.id.ll_bg2})
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
