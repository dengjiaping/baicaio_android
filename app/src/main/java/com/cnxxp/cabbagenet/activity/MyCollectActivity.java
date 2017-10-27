package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.MyCollectAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.CollectBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.cnxxp.cabbagenet.xrecyclerview.callback.RecyclerViewItemClickListener;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.cnxxp.cabbagenet.R.id.mXRecyclerView;

public class MyCollectActivity extends BaseActivity {
    private static final String TAG = "MyCollectActivity";
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
    @Bind(R.id.tv5)
    TextView tv5;
    @Bind(R.id.view5)
    View view5;
    @Bind(R.id.tv6)
    TextView tv6;
    @Bind(R.id.view6)
    View view6;
    @Bind(mXRecyclerView)
    XRecyclerView mRecyclerView;

    private View[] mView;
    private TextView[] mTextView;
    private int CurrentIndex;

    private int page;
    private String[] types;
    private String currentType;

    private MyCollectAdapter myCollectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_collect);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("我的收藏");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        myCollectAdapter = new MyCollectAdapter(this);
        mRecyclerView.setAdapter(myCollectAdapter);
        initTab();
    }

    @Override
    protected void initEvents() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpGetCollect(TAG, currentType, page);
            }

            @Override
            public void onLoadMore() {
                httpGetCollect(TAG, currentType, page);
            }
        });
        myCollectAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Bundle bundle;

                switch (currentType) {
                    case "gn":
                    case "ht":
                    case "best":
                        bundle = new Bundle();
                        bundle.putString("shopid", myCollectAdapter.mList.get(postion).getId());
                        startActivity(GoodsDetailActivity.class, bundle);
                        break;
                    case "sd":
                    case "gl":
                        bundle = new Bundle();
                        bundle.putString("articleid", myCollectAdapter.mList.get(postion).getId());
                        startActivity(ArticleDetailActivity.class, bundle);
                        break;
                    case "zr":
                        break;
                }
            }
        });
    }


    @Override
    protected void initData() {
        page = 1;
        httpGetCollect(TAG, currentType, page);

    }

    private void initTab() {
        CurrentIndex = 0;
        mView = new View[]{view1, view2, view3, view4, view5, view6};
        mTextView = new TextView[]{tv1, tv2, tv3, tv4, tv5, tv5};
        mTextView[CurrentIndex].setSelected(true);
        mView[CurrentIndex].setVisibility(View.VISIBLE);
        types = new String[]{"gn", "ht", "best", "sd", "gl", "zr"};
        currentType = types[CurrentIndex];
        myCollectAdapter.setType(currentType);

    }

    private void switchTab(int index) {
        if (index != CurrentIndex) {
            mTextView[CurrentIndex].setSelected(false);
            mView[CurrentIndex].setVisibility(View.INVISIBLE);
            mTextView[index].setSelected(true);
            mView[index].setVisibility(View.VISIBLE);
            CurrentIndex = index;
            currentType = types[index];
            myCollectAdapter.setType(currentType);
            mRecyclerView.onRefresh();
        }
    }

    @OnClick({R.id.ll_bg1, R.id.ll_bg2, R.id.ll_bg3, R.id.ll_bg4, R.id.ll_bg5, R.id.ll_bg6})
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
            case R.id.ll_bg5:
                switchTab(4);
                break;
            case R.id.ll_bg6:
                switchTab(5);
                break;
        }
    }

    private void httpGetCollect(String tag, String currentType, final int page) {
        showLoadDialog();
        API.getSingleton().myCollection(tag, Config.PublicParams.usid, currentType, page + "", new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<CollectBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<CollectBean>>>() {
                }.getType());
                if (page == 1) {
                    myCollectAdapter.setListData(myBean.getData());
                } else {
                    myCollectAdapter.addList(myBean.getData());
                }
                MyCollectActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                if (page == 1) {
                    myCollectAdapter.clearList();
                }
            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
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
