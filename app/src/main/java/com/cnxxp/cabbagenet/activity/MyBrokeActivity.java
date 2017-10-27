package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.OriginalAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.OriginalBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MyBrokeActivity extends BaseActivity {
    private static final String TAG = "MyBrokeActivity";

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
    private OriginalAdapter mOriginalAdapter;
    private TextView[] tvs;
    private View[] views;
    private int currentIndex;
    private int page;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_broke);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("我的爆料");
        initTabView();
        setTitleMoreTextVisibility(View.VISIBLE, "发布");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mOriginalAdapter = new OriginalAdapter(this, getWindowManager().getDefaultDisplay().getWidth());
        mXRecyclerView.setAdapter(mOriginalAdapter);
    }

    @Override
    protected void initEvents() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpPublish(TAG, type, page);
            }

            @Override
            public void onLoadMore() {
                httpPublish(TAG, type, page);
            }
        });
    }

    @Override
    protected void initData() {
        page = 1;
        type = "ht";
        httpPublish(TAG, type, page);

    }

    private void initTabView() {
        currentIndex = 0;
        tvs = new TextView[]{tv1, tv2};
        views = new View[]{view1, view2};
        tvs[currentIndex].setSelected(true);
        views[currentIndex].setSelected(true);
    }

    private void switchTab(int index) {
        if (currentIndex != index) {
            tvs[currentIndex].setSelected(false);
            tvs[index].setSelected(true);
            views[currentIndex].setSelected(false);
            views[index].setSelected(true);
            currentIndex = index;
            mXRecyclerView.onRefresh();
        }
    }

    @OnClick(R.id.activity_more_text_bg)
    public void onClick() {

    }

    @OnClick({R.id.ll_bg1, R.id.ll_bg2, R.id.activity_more_text_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bg1:
                type = "ht";
                switchTab(0);

                break;
            case R.id.ll_bg2:
                type = "gn";
                switchTab(1);
                break;
            case R.id.activity_more_text_bg:
                startActivity(PublishArticleActivity.class);
                break;
        }
    }

    private void httpPublish(String tag, String type, final int page) {
        showLoadDialog();
        API.getSingleton().publish(tag, type, Config.PublicParams.usid, page + "", new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<OriginalBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<OriginalBean>>>() {
                }.getType());
                if (page == 1) {
                    mOriginalAdapter.setListData(myBean.getData());
                } else {
                    mOriginalAdapter.addList(myBean.getData());
                }
                MyBrokeActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                
                if(page==1){
                    mOriginalAdapter.clearList();
                }
            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
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
