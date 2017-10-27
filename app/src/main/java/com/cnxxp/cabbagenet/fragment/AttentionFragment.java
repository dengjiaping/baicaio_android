package com.cnxxp.cabbagenet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.AttentionActivity;
import com.cnxxp.cabbagenet.adapter.DiscountAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.bean.DiscountBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 原创Fragment
 * Created by Administrator on 2017/3/21 0021.
 */

public class AttentionFragment extends BaseFragment {
    private static final String TAG = "AttentionFragment";
    @Bind(R.id.iv_setting)
    ImageView ivSetting;
    @Bind(R.id.recyclerView)
    XRecyclerView mRecyclerView;
    private DiscountAdapter mdDiscountAdapter;
    private int page;
    private String key;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.fragment_original, null);
        ButterKnife.bind(this, inflate);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getKey();
                shopList(key, page);

            }

            @Override
            public void onLoadMore() {
                shopList(key, page);

            }
        });
        mdDiscountAdapter = new DiscountAdapter(getActivity());
        mRecyclerView.setAdapter(mdDiscountAdapter);


    }

    private void getKey() {
        key = "";
        for (String keyValues : Config.PublicParams.u_attention.keySet()) {
            key += Config.PublicParams.u_attention.get(keyValues).getTag() + "|";
        }
        if (!TextUtils.isEmpty(key)) {
            key = key.substring(0, key.length() - 1);
        }
    }


    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        if(Config.PublicParams.u_attention!=null&&Config.PublicParams.u_attention.size()>0){
            mRecyclerView.onRefresh();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.iv_setting)
    public void onClick() {
        startActivity(AttentionActivity.class);
    }

    private void shopList(String key, int page) {
        API.getSingleton().shopList_g(TAG, String.valueOf(page), key, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                if (object!=null){
                    MyBean<List<DiscountBean>> bean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<DiscountBean>>>() {
                    }.getType());
                    if (AttentionFragment.this.page == 1) {
                        mdDiscountAdapter.setListData(bean.getData());
                    } else {
                        mdDiscountAdapter.addList(bean.getData());
                    }
                    AttentionFragment.this.page++;
                }
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                if (mRecyclerView != null) {
                    mRecyclerView.loadMoreComplete();
                    mRecyclerView.refreshComplete();
                }
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            mRecyclerView.onRefresh();
        }
    }
}
