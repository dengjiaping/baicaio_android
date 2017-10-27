package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.MyCommentAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.CommentBean;
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

public class MyCommentActivity extends BaseActivity {

    private final String TAG = "MyCommentActivity";

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.view2)
    View view2;

    @Bind(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    private TextView[] textViews;
    private View[] views;
    private int currentIndex;
    private MyCommentAdapter mCommentAdapter;
    private int page;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_comment);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("我的评论");
        initTab();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mCommentAdapter = new MyCommentAdapter(this);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mXRecyclerView.setAdapter(mCommentAdapter);

    }

    @Override
    protected void initEvents() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                getComments(TAG, type, 1, Config.PublicParams.usid);
            }

            @Override
            public void onLoadMore() {
                getComments(TAG, type, page, Config.PublicParams.usid);
            }
        });
    }

    @Override
    protected void initData() {
        page = 1;
        getComments(TAG, type, page, Config.PublicParams.usid);
    }

    private void getComments(String tag, final String type, final int page, String userid) {
        showLoadDialog();
        API.getSingleton().myComments(tag, type, page + "", userid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<CommentBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<CommentBean>>>() {
                }.getType());
                if (page == 1) {
                    mCommentAdapter.setListData(myBean.getData());
                } else {
                    mCommentAdapter.addList(myBean.getData());
                }
                MyCommentActivity.this.page++;

            }

            @Override
            public void onStateSuccessDataNull(String msg){
                if(page==1){
                    mCommentAdapter.clearList();
                }
            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
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

    private void switchTitle(int index) {
        if (currentIndex != index) {
            textViews[currentIndex].setSelected(false);
            views[currentIndex].setVisibility(View.INVISIBLE);
            textViews[index].setSelected(true);
            views[index].setVisibility(View.VISIBLE);
            currentIndex = index;
            mXRecyclerView.onRefresh();
        }
    }

    private void initTab() {
        currentIndex = 0;
        textViews = new TextView[]{tv1, tv2,};
        views = new View[]{view1, view2,};
        textViews[currentIndex].setSelected(true);
        views[currentIndex].setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.ll_bg1, R.id.ll_bg2,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bg1:
                type = "0";
                switchTitle(0);

                break;
            case R.id.ll_bg2:
                type = "1";
                switchTitle(1);
                break;

        }
    }
}
