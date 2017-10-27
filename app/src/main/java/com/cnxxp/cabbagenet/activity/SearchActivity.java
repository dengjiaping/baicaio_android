package com.cnxxp.cabbagenet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.DiscountAdapter;
import com.cnxxp.cabbagenet.adapter.HistoryAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AttentionBean;
import com.cnxxp.cabbagenet.bean.DiscountBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.db.SearchDBHelper;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.cnxxp.cabbagenet.xrecyclerview.callback.RecyclerViewItemClickListener;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    private static final int CODE_LOGIN = 1;

    @Bind(R.id.activity_back_bg)
    LinearLayout activityBackBg;
    @Bind(R.id.et_sousuo)
    EditText etSousuo;
    @Bind(R.id.iv_clear)
    ImageView ivClear;
    @Bind(R.id.rv_hisotry)
    RecyclerView rvHisotry;
    @Bind(R.id.ll_history)
    LinearLayout llHistory;
    @Bind(R.id.rv_search)
    XRecyclerView rvSearch;
    @Bind(R.id.ll_searh)
    LinearLayout llSearh;
    private HistoryAdapter historyAdapter;
    private DiscountAdapter sousuoAdapter;
    private SearchDBHelper dbHelper;
    //搜索结果page
    private int page;
    private String key;

    //关注头部
    private LinearLayout llHead;
    private TextView tvTag;
    private TextView tvAttention;
    private LinearLayout llPush;
    private ImageView ivPush;

    boolean isLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViews() {
        dbHelper = SearchDBHelper.getInstance(this);
        LinearLayoutManager historyManager = new LinearLayoutManager(this);
        historyManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHisotry.setLayoutManager(historyManager);
        historyAdapter = new HistoryAdapter(this);
        rvHisotry.setAdapter(historyAdapter);
        initHead();
        LinearLayoutManager sousuoManager = new LinearLayoutManager(this);
        sousuoManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(sousuoManager);
        rvSearch.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rvSearch.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        rvSearch.setArrowImageView(R.drawable.iconfont_downgrey);
        rvSearch.addHeaderView(llHead);
        llHead.setVisibility(View.GONE);
        sousuoAdapter = new DiscountAdapter(this);
        rvSearch.setAdapter(sousuoAdapter);
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
        etSousuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    ivClear.setVisibility(View.INVISIBLE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.INVISIBLE);
                }
            }
        });
        etSousuo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    llSearh.setVisibility(View.INVISIBLE);
                    llHistory.setVisibility(View.VISIBLE);
                    historyAdapter.setListData(dbHelper.searchAll());
                } else {
                    llHistory.setVisibility(View.INVISIBLE);
                    llSearh.setVisibility(View.VISIBLE);
                }
            }
        });
        historyAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                etSousuo.setText(historyAdapter.mList.get(postion));
                llHistory.setVisibility(View.INVISIBLE);
                llSearh.setVisibility(View.VISIBLE);
                page = 1;
                key = historyAdapter.mList.get(postion);
                httpSearch(TAG, page, key);
            }

        });
        sousuoAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Bundle bundle = new Bundle();
                bundle.putString("shopid", sousuoAdapter.mList.get(postion).getShopid());
                startActivity(GoodsDetailActivity.class, bundle);
            }
        });

        rvSearch.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpSearch(TAG, page, key);
            }

            @Override
            public void onLoadMore() {
                httpSearch(TAG, page, key);
            }
        });
        etSousuo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    key = etSousuo.getText().toString().trim().replace(" ", "");
                    if (TextUtils.isEmpty(key)) {
                        showCustomToast("您还没有输入任何内容");
                        return true;
                    } else {
                        llHistory.setVisibility(View.INVISIBLE);
                        llSearh.setVisibility(View.VISIBLE);
                        if (!dbHelper.HasHistory(key)) {
                            dbHelper.insertHistory(key);
                        }
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etSousuo.getWindowToken(), 0);
                        httpSearch(TAG, page, key);
                        return true;
                    }
                }
                return false;
            }
        });
        etSousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSousuo.setFocusableInTouchMode(true);
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
                    intent.setClass(SearchActivity.this, LoginActivity.class);
                    startActivityForResult(intent, CODE_LOGIN);
                    isLoading = false;
                } else {
                    if (tvAttention.getText().toString().trim().equals("关注")) {
                        //未关注   去关注
                        if (Config.PublicParams.u_attention.size() < 14) {
                            httpAttentionTag(key);
                        } else {
                            showCustomToast("最多关注14条");
                            isLoading = false;
                            return;
                        }
                    } else {
                        //已关注  取消关注
                        delFollowTag(key);
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
                    intent.setClass(SearchActivity.this, LoginActivity.class);
                    startActivityForResult(intent, CODE_LOGIN);
                    isLoading = false;
                } else {
                    if (ivPush.isSelected()) {
                        //已开启   删除推送
                        HttpPushDel(Config.PublicParams.u_attention.get(key.toLowerCase()).getId(), key);
                    } else {
                        //未开启  
                        if (tvAttention.isSelected()) {
                            httpPushCreate(key);
                        } else if (Config.PublicParams.u_attention.size() >= 14) {
                            showCustomToast("最多关注14条");
                            isLoading = false;
                            return;
                        } else {
                            httpPushCreate(key);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        historyAdapter.setListData(dbHelper.searchAll());
        page = 1;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("tag") != null) {
                key = getIntent().getExtras().getString("tag");
                etSousuo.setText(key);
                llHistory.setVisibility(View.INVISIBLE);
                llSearh.setVisibility(View.VISIBLE);
                dbHelper.insertHistory(key);
                etSousuo.setFocusable(false);
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etSousuo.getWindowToken(), 0);
                httpSearch(TAG, page, key);
            } else {
                key = "";
            }
        } else {
            key = "";
        }


    }

    @OnClick({R.id.activity_back_bg, R.id.iv_clear, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_back_bg:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

// 获取软键盘的显示状态
                boolean isOpen = imm.isActive();

// 如果软键盘已经显示，则隐藏，反之则显示 
                //   imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                if (isOpen) {
                    imm.hideSoftInputFromWindow(etSousuo.getWindowToken(), 0);
                }
                finish();
                break;
            case R.id.iv_clear:
                etSousuo.setText("");
                llSearh.setVisibility(View.INVISIBLE);
                historyAdapter.setListData(dbHelper.searchAll());
                llHistory.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_search:
              /*  if (TextUtils.isEmpty(Config.PublicParams.usid)) {
                    startActivity(LoginActivity.class);
                } else {
                    startActivity(AttentionActivity.class);
                }*/
                key = etSousuo.getText().toString().trim().replace(" ", "");
                if (TextUtils.isEmpty(key)) {
                    showCustomToast("您还没有输入任何内容");
                    return;
                } else {
                    llHistory.setVisibility(View.INVISIBLE);
                    llSearh.setVisibility(View.VISIBLE);
                    if (!dbHelper.HasHistory(key)) {
                        dbHelper.insertHistory(key);
                    }
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etSousuo.getWindowToken(), 0);
                    rvSearch.onRefresh();

                }
                break;
        }
    }

    private void httpSearch(String tag, final int page, final String key) {
        showLoadDialog();
        API.getSingleton().shopList(tag, "2", "", "", String.valueOf(page), "10", key, "", new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                llHead.setVisibility(View.VISIBLE);
                tvTag.setText(key);
                setTvAttention(key);
                MyBean<List<DiscountBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<DiscountBean>>>() {
                }.getType());
                if (page == 1) {
                    sousuoAdapter.setListData(myBean.getData());
                } else {
                    sousuoAdapter.addList(myBean.getData());
                }
                SearchActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);
            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                rvSearch.loadMoreComplete();
                rvSearch.refreshComplete();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_LOGIN && resultCode == RESULT_OK) {
            setTvAttention(key);
        }
    }

    private void setTvAttention(String key) {
        if (TextUtils.isEmpty(Config.PublicParams.usid)) {
            tvAttention.setSelected(false);
            tvAttention.setText("关注");
            ivPush.setSelected(false);
        } else {
            if (Config.PublicParams.u_attention == null) {
                Config.PublicParams.usid = "";
                tvAttention.setSelected(false);
                tvAttention.setText("关注");
                ivPush.setSelected(false);
                return;
            }
            if (Config.PublicParams.u_attention.containsKey(key.toLowerCase())) {
                tvAttention.setText("已关注");
                tvAttention.setSelected(true);
                if (Config.PublicParams.u_attention.get(key.toLowerCase()).getP_sign() == 1) {
                    ivPush.setSelected(true);
                } else ivPush.setSelected(false);
            } else {
                tvAttention.setText("关注");
                tvAttention.setSelected(false);
                ivPush.setSelected(false);
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
                ivPush.setSelected(false);
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
        showLoadDialog();
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
                isLoading = false;
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
                isLoading = false;
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
