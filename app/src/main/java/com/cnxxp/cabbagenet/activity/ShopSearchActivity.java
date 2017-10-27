package com.cnxxp.cabbagenet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.DiscountAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AttentionBean;
import com.cnxxp.cabbagenet.bean.DiscountBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;

public class ShopSearchActivity extends BaseActivity {
    private static final String TAG = "ShopSearchActivity";
    private static final int CODE_LOGIN = 1;
    @Bind(R.id.recyclerView)
    XRecyclerView recyclerView;
    private DiscountAdapter mAdapter;
    private int page;
    private String tag;
    private String orig_id;


    //关注头部
    private LinearLayout llHead;
    private TextView tvTag;
    private TextView tvAttention;
    private LinearLayout llPush;
    private ImageView ivPush;

    private Boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tag_search);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        initHead();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.addHeaderView(llHead);
        mAdapter = new DiscountAdapter(this);
        recyclerView.setAdapter(mAdapter);

    }

    private void initHead() {
        llHead = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.attention_head, null);
        tvTag = (TextView) llHead.findViewById(R.id.tv_tag);
        tvAttention = (TextView) llHead.findViewById(R.id.tv_attention);
        llPush = (LinearLayout) llHead.findViewById(R.id.ll_push);
        ivPush = (ImageView) llHead.findViewById(R.id.iv_push);
        isLoading = false;
    }

    @Override
    protected void initEvents() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpShopSearch(page);
            }

            @Override
            public void onLoadMore() {
                httpShopSearch(page);
            }
        });

        tvAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoading) {
                    return;
                }
                isLoading = true;
                if (TextUtils.isEmpty(Config.PublicParams.usid)) {
                    Intent intent = new Intent();
                    intent.setClass(ShopSearchActivity.this, LoginActivity.class);
                    startActivityForResult(intent, CODE_LOGIN);
                    isLoading =false;
                } else {
                    if (tvAttention.getText().toString().trim().equals("关注")) {
                        //未关注   去关注
                        if (Config.PublicParams.u_attention.size() < 14) {
                            httpAttentionTag(tag);
                        } else {
                            showCustomToast("最多关注14条");
                            isLoading = false;
                            return;
                        }
                    } else {
                        //已关注  取消关注
                        delFollowTag(tag);
                    }
                }
            }
        });
        llPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoading) {
                    return;
                }
                isLoading = true;
                if (TextUtils.isEmpty(Config.PublicParams.usid)) {
                    Intent intent = new Intent();
                    intent.setClass(ShopSearchActivity.this, LoginActivity.class);
                    startActivityForResult(intent, CODE_LOGIN);
                    isLoading =false;
                } else {


                    if (ivPush.isSelected()) {
                        //已开启   删除推送
                        HttpPushDel(Config.PublicParams.u_attention.get(tag.toLowerCase()).getId(), tag);
                    } else {
                        //未开启  
                        if (tvAttention.isSelected()) {
                            httpPushCreate(tag);
                        } else if (Config.PublicParams.u_attention.size() >= 14) {
                            showCustomToast("最多关注14条");
                            isLoading = false;
                            return;
                        } else {
                            httpPushCreate(tag);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        tag = getIntent().getExtras().getString("tag");
        orig_id = getIntent().getExtras().getString("orig_id");
        setTitleText(tag);
        recyclerView.onRefresh();

    }

    private void httpShopSearch(final int page) {
        showLoadDialog();
        API.getSingleton().shopList(TAG, "2", "", orig_id, page + "", "10", "", "", new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                tvTag.setText(tag);
                setTvAttention(tag.replace(" ", ""));
                tag = tag.replace(" ", "");
                MyBean<List<DiscountBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<DiscountBean>>>() {
                }.getType());
                if (page == 1) {
                    mAdapter.setListData(myBean.getData());
                } else {
                    mAdapter.addList(myBean.getData());
                }
                ShopSearchActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);
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

    private void setTvAttention(String key) {
        if (TextUtils.isEmpty(Config.PublicParams.usid)) {
            tvAttention.setSelected(false);
            tvAttention.setText("关注");
        } else {
            if (Config.PublicParams.u_attention.containsKey(key.toLowerCase())) {
                tvAttention.setText("已关注");
                tvAttention.setSelected(true);
                if (Config.PublicParams.u_attention.get(key
                        .toLowerCase()).getP_sign() == 1) {
                    ivPush.setSelected(true);
                } else {
                    ivPush.setSelected(false);
                }
            } else {
                tvAttention.setText("关注");
                tvAttention.setSelected(false);
            }
        }
        isLoading = false;
    }

    private void httpAttentionTag(final String tag) {
        API.getSingleton().followTagCreate(TAG, tag, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                tvAttention.setText("已关注");
                tvAttention.setSelected(true);
                Config.PublicParams.u_attention.put(tag.toLowerCase(), new AttentionBean(myBean.getData(), 1, 0, tag));
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {
                isLoading = false;
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


    private void delFollowTag(final String key) {
        API.getSingleton().followTagDel(TAG, key, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                tvAttention.setText("关注");
                tvAttention.setSelected(false);
                Config.PublicParams.u_attention.remove(key.toLowerCase());
                HashSet<String> set = new HashSet<String>();
                set.add(key);
                JPushInterface.deleteTags(getApplicationContext(), 1, set);
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {
                isLoading = false;
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

    private void httpPushCreate(final String tag) {
        API.getSingleton().notifyTagCreate(TAG, tag, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                HashSet<String> set = new HashSet<String>();
                set.add(tag);
                JPushInterface.addTags(getApplicationContext(), 1, set);
                if (!Config.PublicParams.u_attention.containsKey(tag.toLowerCase())) {
                    Config.PublicParams.u_attention.put(tag.toLowerCase(), new AttentionBean(myBean.getData(), 1, 1, tag));
                    tvAttention.setText("已关注");
                    tvAttention.setSelected(true);
                }
                ivPush.setSelected(true);
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {

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

    private void HttpPushDel(String id, final String tag) {
        showLoadDialog();
        API.getSingleton().notifyTagDel(TAG, id, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                HashSet<String> set = new HashSet<String>();
                set.add(tag);
                JPushInterface.deleteTags(getApplicationContext(), 1, set);
                Config.PublicParams.u_attention.get(tag.toLowerCase()).setP_sign(0);
                ivPush.setSelected(false);
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_LOGIN && resultCode == RESULT_OK) {
            setTvAttention(tag);
        }
    }
}
