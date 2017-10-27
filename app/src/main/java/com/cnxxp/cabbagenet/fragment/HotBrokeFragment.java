package com.cnxxp.cabbagenet.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.BrokeAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.bean.DiscountBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import static com.cnxxp.cabbagenet.R.id.mXRecyclerView;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class HotBrokeFragment extends BaseFragment {
    private static final String TAG = "HotBrokeFragment";
    private View inflate;
    private XRecyclerView mRecyclerView;
    private BrokeAdapter mAdapter;
    private int page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_hot_broke, null);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {
        mRecyclerView = (XRecyclerView) inflate.findViewById(mXRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mAdapter = new BrokeAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvents() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpGetData(TAG, page);
            }

            @Override
            public void onLoadMore() {
                httpGetData(TAG, page);
            }
        });
    }

    @Override
    protected void initData() {
        mRecyclerView.onRefresh();
    }

    private void httpGetData(String tag, final int page) {
        showLoadDialog();
        API.getSingleton().shopList(tag, "2", "", "", page + "", "10", "", "1", new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<DiscountBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<DiscountBean>>>() {
                }.getType());
                if (page == 1) {
                    mAdapter.setListData(myBean.getData());
                } else {
                    mAdapter.addList(myBean.getData());
                }
                HotBrokeFragment.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }
}
