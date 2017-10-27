package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.ScoreChangedAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.ScoreChangedBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;

public class HasChangedActivity extends BaseActivity {
    private static final String TAG = "HasChangedActivity";
    @Bind(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    private ScoreChangedAdapter mScoreChangedAdapter;

    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_has_changed);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("已兑换");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mScoreChangedAdapter = new ScoreChangedAdapter(this);
        mXRecyclerView.setAdapter(mScoreChangedAdapter);
    }

    @Override
    protected void initEvents() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpChangedGoods(TAG, page);
            }

            @Override
            public void onLoadMore() {
                httpChangedGoods(TAG, page);
            }
        });
    }

    @Override
    protected void initData() {
        page = 1;
        httpChangedGoods(TAG, page);
    }

    private void httpChangedGoods(String tag, final int page) {
        showLoadDialog();
        API.getSingleton().myScore(tag, Config.PublicParams.usid, page + "", new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<ScoreChangedBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<ScoreChangedBean>>>() {
                }.getType());
                if (page == 1) {
                    mScoreChangedAdapter.setListData(myBean.getData());
                } else {
                    mScoreChangedAdapter.addList(myBean.getData());
                }
                HasChangedActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                
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
                showCustomToast(msg);
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }
}
