package com.cnxxp.cabbagenet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.FortuneAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.myGradeBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import butterknife.Bind;

public class MyFortuneActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MyFortuneActivity";
    @Bind(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    private View mHeadView;
    private TextView mScore;
    private TextView mLevel;
    private TextView mOffer;
    private TextView mCoin;
    private TextView mExp;
    private FortuneAdapter mAdapter;
    private int page;
    private boolean state;
    private TextView tvSign;
    private View[] mView;
    private TextView[] mTextView;
    private int CurrentIndex;

    private String[] types;
    private String currentType;

    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;

    private View view1;
    private View view2;
    private View view3;
    private View view4;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_fortune);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("我的财富");
        initHeadView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.addHeaderView(mHeadView);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mAdapter = new FortuneAdapter(this);
        mXRecyclerView.setAdapter(mAdapter);
        initTab();
    }

    @Override
    protected void initEvents() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpGetGradeList(TAG, page, currentType);
            }

            @Override
            public void onLoadMore() {
                httpGetGradeList(TAG, page, currentType);
            }
        });
        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvSign.isSelected()) {
                    httpSign(TAG);
                } else {
                    showCustomToast("您已经签到");
                    return;
                }
            }
        });
    }


    @Override
    protected void initData() {
        state = getIntent().getExtras().getBoolean("state");
        if (state) {
            tvSign.setSelected(false);
            tvSign.setText("已签到");
        } else {
            tvSign.setSelected(true);
            tvSign.setText("签到");
        }
        mExp.setText("经验:" + Config.PublicParams.userInfo.getExp());
        mScore.setText("当前积分:" + Config.PublicParams.userInfo.getScore());
        mCoin.setText("金币:" + Config.PublicParams.userInfo.getCoin());
        mOffer.setText("贡献:" + Config.PublicParams.userInfo.getOffer());
        page = 1;
        mAdapter.setType(currentType);
        httpGetGradeList(TAG, page, currentType);

    }

    private void initHeadView() {
        mHeadView = LayoutInflater.from(this).inflate(R.layout.head_my_fortune, null);
        mHeadView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mScore = (TextView) mHeadView.findViewById(R.id.tv_score);
        mLevel = (TextView) mHeadView.findViewById(R.id.tv_level);
        tvSign = (TextView) mHeadView.findViewById(R.id.tv_sign);
        mExp = (TextView) mHeadView.findViewById(R.id.tv_exp);
        mOffer = (TextView) mHeadView.findViewById(R.id.tv_offer);
        mCoin = (TextView) mHeadView.findViewById(R.id.tv_coin);
        ll1 = (LinearLayout) mHeadView.findViewById(R.id.ll_bg1);
        ll2 = (LinearLayout) mHeadView.findViewById(R.id.ll_bg2);
        ll3 = (LinearLayout) mHeadView.findViewById(R.id.ll_bg3);
        ll4 = (LinearLayout) mHeadView.findViewById(R.id.ll_bg4);
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);
        ll4.setOnClickListener(this);
        view1 = mHeadView.findViewById(R.id.view1);
        view2 = mHeadView.findViewById(R.id.view2);
        view3 = mHeadView.findViewById(R.id.view3);
        view4 = mHeadView.findViewById(R.id.view4);
        tv1 = (TextView) mHeadView.findViewById(R.id.tv1);
        tv2 = (TextView) mHeadView.findViewById(R.id.tv2);
        tv3 = (TextView) mHeadView.findViewById(R.id.tv3);
        tv4 = (TextView) mHeadView.findViewById(R.id.tv4);
        
        
        
        mHeadView.findViewById(R.id.tv_rule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转晋级规则
                startActivity(RisingRuleActivity.class);
            }
        });
    }

    private void httpGetGradeList(String tag, final int page, String type) {
        showLoadDialog();
        API.getSingleton().myGrade(tag, page, type, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<myGradeBean> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<myGradeBean>>() {
                }.getType());
                if (page == 1) {
                    mAdapter.setListData(myBean.getData().getList());
                    if (TextUtils.isEmpty(mLevel.getText().toString().trim())) {
                        mLevel.setText("我的等级:" + myBean.getData().getGrade());
                    }
                } else {
                    mAdapter.addList(myBean.getData().getList());
                }
                MyFortuneActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                
                if(page==1){
                    mAdapter.clearList();
                }
            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                mXRecyclerView.refreshComplete();
                mXRecyclerView.loadMoreComplete();
            }

            @Override
            public void onAppKeyError() {

            }

            @Override
            public void onStateFail(String msg) {

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void initTab() {
        CurrentIndex = 0;
        mView = new View[]{view1, view2, view3, view4};
        mTextView = new TextView[]{tv1, tv2, tv3, tv4};
        mTextView[CurrentIndex].setSelected(true);
        mView[CurrentIndex].setVisibility(View.VISIBLE);
        types = new String[]{"score", "coin", "offer", "exp"};
        currentType = types[CurrentIndex];

    }


    private void httpSign(String tag) {
        API.getSingleton().sign(tag, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast(msg);
                tvSign.setSelected(false);
                tvSign.setText("已签到");

            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);
            }

            @Override
            public void onStateFinish() {

            }

            @Override
            public void onAppKeyError() {

            }

            @Override
            public void onStateFail(String msg) {
                showCustomToast(msg);
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (state == false) {
            Intent intent = getIntent();
            intent.putExtra("score", mScore.getText().toString().trim());
            setResult(RESULT_OK, intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bg1:
                switchTab(0);
                break;
            case R.id.ll_bg2:
                switchTab(1);
                break;
            case R.id.ll_bg3:
                switchTab(2);
                break;
            case R.id.ll_bg4:
                switchTab(3);
                break;

        }
    }

    private void switchTab(int index) {
        if (index != CurrentIndex) {
            mTextView[CurrentIndex].setSelected(false);
            mView[CurrentIndex].setVisibility(View.INVISIBLE);
            mTextView[index].setSelected(true);
            mView[index].setVisibility(View.VISIBLE);
            CurrentIndex = index;
            currentType = types[index];
            mAdapter.setType(currentType);
            mXRecyclerView.onRefresh();
        }
    }

}
