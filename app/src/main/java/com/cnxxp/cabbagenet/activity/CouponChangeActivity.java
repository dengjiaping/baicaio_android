package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.CouponChangeAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.CouponChangeBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class CouponChangeActivity extends BaseActivity {
    private static final String TAG = "CoponChangeActivity";
    @Bind(R.id.recyclerView)
    XRecyclerView recyclerView;
    private CouponChangeAdapter mAdapter;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_coupon_change);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("优惠券兑换");
        setTitleMoreTextVisibility(View.VISIBLE, "已兑换");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mAdapter = new CouponChangeAdapter(this);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initEvents() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpTickList(TAG, page);
            }

            @Override
            public void onLoadMore() {
                httpTickList(TAG, page);
            }
        });
    }

    @Override
    protected void initData() {
        recyclerView.onRefresh();
    }

    private void httpTickList(String tag, final int page) {
        showLoadDialog();
        API.getSingleton().tickList(tag, page, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<CouponChangeBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<CouponChangeBean>>>() {
                }.getType());
                if (page == 1) {
                    mAdapter.setListData(myBean.getData());
                } else {
                    mAdapter.addList(myBean.getData());
                }
                CouponChangeActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                recyclerView.refreshComplete();
                recyclerView.loadMoreComplete();
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

    @OnClick(R.id.activity_more_text_bg)
    public void onClick() {
        if (TextUtils.isEmpty(Config.PublicParams.usid)) {
            startActivity(LoginActivity.class);
        } else {
            startActivity(MyCouponActivity.class);
        }
    }
}
