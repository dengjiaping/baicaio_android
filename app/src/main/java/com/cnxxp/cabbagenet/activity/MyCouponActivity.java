package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.CouponAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.TickBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;

public class MyCouponActivity extends BaseActivity {
    private static final String TAG = "MyCouponActivity";
    @Bind(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    private CouponAdapter mCouponAdapter;
    private int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_coupon);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("我的优惠券");
        setTitleMoreTextVisibility(View.VISIBLE, "去兑换");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mCouponAdapter = new CouponAdapter(this);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mXRecyclerView.setAdapter(mCouponAdapter);
    }


    @Override
    protected void initEvents() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpMyTick(TAG, page, "0");
            }

            @Override
            public void onLoadMore() {
                httpMyTick(TAG, page, "0");
            }
        });
        findViewById(R.id.activity_more_text_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CouponChangeActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        mXRecyclerView.onRefresh();
    }


    private void httpMyTick(String tag, final int page, String gq) {
        API.getSingleton().tick(tag, Config.PublicParams.usid, page, gq, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<TickBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<TickBean>>>() {
                }.getType());
                if (page == 1) {
                    mCouponAdapter.setListData(myBean.getData());
                } else {
                    mCouponAdapter.setListData(myBean.getData());
                }
                MyCouponActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                if (page == 1) {
                    mCouponAdapter.clearList();
                }
            }

            @Override
            public void onStateFinish() {
                mXRecyclerView.loadMoreComplete();
                mXRecyclerView.refreshComplete();
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
}
