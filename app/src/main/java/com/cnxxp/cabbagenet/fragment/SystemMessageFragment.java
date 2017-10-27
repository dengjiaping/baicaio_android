package com.cnxxp.cabbagenet.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.MessageAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.bean.MessageBean;
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

/**
 * Created by admin on 2017/8/28 0028.
 */

public class SystemMessageFragment extends BaseFragment {
    private static final String TAG = "SystemMessageFragment";
    @Bind(R.id.recyclerView)
    XRecyclerView recyclerView;
    private View inflate;
  
    private MessageAdapter messageAdapter;
    private int page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_message, null);
        ButterKnife.bind(this, inflate);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        messageAdapter = new MessageAdapter(getActivity());
        recyclerView.setAdapter(messageAdapter);

    }

    @Override
    protected void initEvents() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpGetMessage(TAG, page);
            }

            @Override
            public void onLoadMore() {
                httpGetMessage(TAG, page);
            }
        });
    }

    @Override
    protected void initData() {
        recyclerView.onRefresh();
    }

    private void httpGetMessage(String tag, final int page) {
        showLoadDialog();
        API.getSingleton().getMsg(tag, String.valueOf(page), Config.PublicParams.usid, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<MessageBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<MessageBean>>>() {
                }.getType());
                if (page == 1) {
                    messageAdapter.setListData(myBean.getData());
                } else {
                    messageAdapter.addList(myBean.getData());
                }
                SystemMessageFragment.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                recyclerView.loadMoreComplete();
                recyclerView.refreshComplete();
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
