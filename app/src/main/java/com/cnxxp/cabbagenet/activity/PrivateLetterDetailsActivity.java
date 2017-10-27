package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.PrivateLetterDetailsAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.PrivateLetterDetailBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.view.ContainsEmojiEditText;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class PrivateLetterDetailsActivity extends BaseActivity {
    private static final String TAG = "PrivateLetterDetailsActivity";
    @Bind(R.id.recyclerView)
    XRecyclerView recyclerView;
    @Bind(R.id.et_message)
    ContainsEmojiEditText etMessage;
    private PrivateLetterDetailsAdapter mAdapter;
    private String name;
    private String ftid;
    private int page;
    private String to_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_private_letter_details);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mAdapter = new PrivateLetterDetailsAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvents() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                LetterList(page);
            }

            @Override
            public void onLoadMore() {
                LetterList(page);
            }
        });
    }

    @Override
    protected void initData() {
        name = getIntent().getExtras().getString("name");
        setTitleText("我和\"" + name + "\"对话");
        ftid = getIntent().getExtras().getString("ftid");
        to_id = getIntent().getExtras().getString("to_id");
        recyclerView.onRefresh();
    }

    private void LetterList(final int page) {
        showLoadDialog();
        API.getSingleton().letterList(TAG, Config.PublicParams.usid, ftid, String.valueOf(page), new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<PrivateLetterDetailBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<PrivateLetterDetailBean>>>() {
                }.getType());
                if (page == 1) {
                    mAdapter.setListData(myBean.getData());
                } else {
                    mAdapter.addList(myBean.getData());
                }
                PrivateLetterDetailsActivity.this.page++;
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

    @OnClick(R.id.tv_release)
    public void onClick() {
        String content = etMessage.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showCustomToast("请填写内容");
            return;
        }
        sendMsg(content);
    }

    private void sendMsg(String content) {
        showLoadDialog();
        API.getSingleton().msgPublish(TAG, Config.PublicParams.usid, to_id, content, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                showCustomToast(myBean.getData());
                etMessage.setText("");
                recyclerView.onRefresh();
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                recyclerView.refreshComplete();
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
