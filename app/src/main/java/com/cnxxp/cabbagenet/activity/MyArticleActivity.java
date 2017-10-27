package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.MyArticleAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyArticleBean;
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

public class MyArticleActivity extends BaseActivity {
    private static final String TAG = "MyArticleActivity";
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
    @Bind(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    private MyArticleAdapter mAdapter;
    private int currentIndex;
    private TextView[] textViews;
    private View[] views;
    private int page;
    private String[] types;
    private String currentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_article);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("我的文章");
        setTitleMoreTextVisibility(View.VISIBLE, "发布");
        initTab();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mAdapter = new MyArticleAdapter(this, getWindowManager().getDefaultDisplay().getWidth());
        mXRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvents() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpGetArticle(TAG, currentType, page);
            }

            @Override
            public void onLoadMore() {
                httpGetArticle(TAG, currentType, page);
            }
        });
    }

    @Override
    protected void initData() {
        mXRecyclerView.onRefresh();

    }

    private void switchTitle(int index) {
        if (currentIndex != index) {
            textViews[currentIndex].setSelected(false);
            views[currentIndex].setVisibility(View.INVISIBLE);
            textViews[index].setSelected(true);
            views[index].setVisibility(View.VISIBLE);
            currentIndex = index;
            currentType = types[currentIndex];
            if (index == 2) {
                mAdapter.setIsBao(true);
            } else {
                mAdapter.setIsBao(false);
            }
            mXRecyclerView.onRefresh();
        }
    }

    private void initTab() {
        currentIndex = 0;
        textViews = new TextView[]{tv1, tv2, tv3};
        views = new View[]{view1, view2, view3};
        types = new String[]{"gl", "sd", "qb"};
        currentType = types[currentIndex];
        textViews[currentIndex].setSelected(true);
        views[currentIndex].setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.activity_more_text_bg, R.id.ll_bg1, R.id.ll_bg2, R.id.ll_bg3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_more_text_bg:
                //发布
                startActivity(PublishArticleActivity.class);
                break;
            case R.id.ll_bg1:
                switchTitle(0);
                break;
            case R.id.ll_bg2:
                switchTitle(1);
                break;
            case R.id.ll_bg3:
                switchTitle(2);
                break;
        }
    }

    private void httpGetArticle(String tag, String type, final int page) {
        showLoadDialog();
        API.getSingleton().myArticle(tag, type, page, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<MyArticleBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<MyArticleBean>>>() {
                }.getType());
                if (page == 1) {
                    mAdapter.setListData(myBean.getData());
                } else {
                    mAdapter.addList(myBean.getData());
                }
                MyArticleActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                if (page == 1) {
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
                showCustomToast(msg);
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });

    }
}
