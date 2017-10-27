package com.cnxxp.cabbagenet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.MainActivity;
import com.cnxxp.cabbagenet.activity.ScreenActivity;
import com.cnxxp.cabbagenet.activity.SearchActivity;
import com.cnxxp.cabbagenet.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分类Fragment
 * Created by Administrator on 2017/3/21 0021.
 */

public class DiscountFragment extends BaseFragment {
    private static final int REQUEST_CODE_SCREEN = 1;
    public MainActivity mainActivity;

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.view3)
    View view3;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.view4)
    View view4;
    @Bind(R.id.tv6)
    TextView tv6;
    @Bind(R.id.view6)
    View view6;
    @Bind(R.id.tv_screen_result)
    TextView tvScreenResult;
    @Bind(R.id.iv_discount_screen)
    TextView tvScreen;


    private BaseFragment[] mFragments;
    private DiscountInfoFragment mDiscountInfoFragment;
    private HotBrokeFragment mHotBrokeFragment;
    private View inflate;
    private TextView[] textViews;
    private View[] views;
    public int currentIndex;
    private int showFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = View.inflate(getActivity(), R.layout.fragment_discount, null);
        ButterKnife.bind(this, inflate);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {
        initTitle();
    }


    @Override
    protected void initEvents() {
    }

    @Override
    protected void initData() {

    }


    private void initTitle() {
        mainActivity = (MainActivity) getActivity();
        if (mainActivity.switchDiscount == -1) {
            currentIndex = 0;
            showFragment = 0;
        } else {
            if (mainActivity.switchDiscount != 4) {
                currentIndex = mainActivity.switchDiscount;
                showFragment = 0;
            } else {
                currentIndex = mainActivity.switchDiscount;
                showFragment = 1;
            }
        }

        textViews = new TextView[]{tv1, tv2, tv3, tv4, tv6};
        views = new View[]{view1, view2, view3, view4, view6};
        mDiscountInfoFragment = new DiscountInfoFragment();
        mHotBrokeFragment = new HotBrokeFragment();
        mFragments = new BaseFragment[]{mDiscountInfoFragment, mHotBrokeFragment};
        textViews[currentIndex].setSelected(true);
        views[currentIndex].setVisibility(View.VISIBLE);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_discount_container, mFragments[showFragment])
                .show(mFragments[showFragment])
                .commit();
    }

    private void switchTitle(int index) {
        if (currentIndex != index) {
            if (currentIndex == 4) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.hide(mFragments[showFragment]);
                if (!mFragments[0].isAdded()) {
                    ft.add(R.id.fl_discount_container, mFragments[0]);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                ft.show(mFragments[0]).commit();
                showFragment = 0;

            } else if (currentIndex != 4 && index == 4) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.hide(mFragments[showFragment]);
                if (!mFragments[1].isAdded()) {
                    ft.add(R.id.fl_discount_container, mFragments[1]);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                ft.show(mFragments[1]).commit();
                showFragment = 1;

            }
            textViews[currentIndex].setSelected(false);
            views[currentIndex].setVisibility(View.INVISIBLE);
            textViews[index].setSelected(true);
            views[index].setVisibility(View.VISIBLE);
            currentIndex = index;
        }

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            if (mainActivity.switchDiscount != -1) {
                mainActivity.switchDiscount = -1;
                mDiscountInfoFragment.setCid("", "");
            }
        } else {
            if (mainActivity.switchDiscount != -1) {
                if (mainActivity.switchDiscount != 4) {
                    switchTitle(mainActivity.switchDiscount);
                    mDiscountInfoFragment.setIndex(mainActivity.switchDiscount);
                } else {
                    switchTitle(4);
                }
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_discount_screen, R.id.tv_screen_result, R.id.ll_sousuo, R.id.ll_bg1, R.id.ll_bg2, R.id.ll_bg3, R.id.ll_bg4, R.id.ll_bg6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_discount_screen:
                //筛选
                Intent intent = new Intent(getActivity(), ScreenActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCREEN);
                break;

            case R.id.ll_bg1:
                switchTitle(0);
                mDiscountInfoFragment.setIndex(0);
                break;
            case R.id.ll_bg2:
                switchTitle(1);
                mDiscountInfoFragment.setIndex(1);
                break;
            case R.id.ll_bg3:
                switchTitle(2);
                mDiscountInfoFragment.setIndex(2);
                break;
            case R.id.ll_bg4:
                switchTitle(3);
                mDiscountInfoFragment.setIndex(3);
                break;
            case R.id.ll_bg6:
                switchTitle(4);
                break;
            case R.id.ll_sousuo:
                startActivity(SearchActivity.class);
                break;
            case R.id.tv_screen_result:
                tvScreenResult.setText("");
                mDiscountInfoFragment.setCid("", "");
                tvScreenResult.setVisibility(View.GONE);
                tvScreen.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            if (requestCode == REQUEST_CODE_SCREEN && resultCode == Activity.RESULT_OK) {
                mDiscountInfoFragment.setCid(data.getStringExtra("cid"), data.getStringExtra("origid"));
                tvScreenResult.setText(data.getStringExtra("class"));
                tvScreen.setVisibility(View.GONE);
                tvScreenResult.setVisibility(View.VISIBLE);
            }
        }
    }
}
