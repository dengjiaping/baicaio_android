package com.cnxxp.cabbagenet.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.OriginalAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.OriginalBean;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class OriginalActivity extends BaseActivity {
    private static final String TAG = "OriginalActivity";

    @Bind(R.id.rl_sousou)
    RelativeLayout rlSousou;
    @Bind(R.id.tv_switch_new)
    TextView tvSwitchNew;
    @Bind(R.id.tv_switch_hot)
    TextView tvSwitchHot;
    @Bind(R.id.et_sousuo)
    EditText etSousuo;

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
    private OriginalAdapter mOriginalAdapter;
    private View[] mView;
    private TextView[] mTextView;
    private int CurrentIndex;
    private int page;
    String isbest;
    private String key;
    @Bind(R.id.iv_cancel)
    ImageView ivCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_attention_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        if (getIntent().getExtras() != null) {
            initSwitch(0);
            initTitle(getIntent().getExtras().getInt("index", 1));
        } else {
            initSwitch(0);
            initTitle(1);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getIndexData(CurrentIndex);

            }

            @Override
            public void onLoadMore() {
                getIndexData(CurrentIndex);

            }
        });

        mOriginalAdapter = new OriginalAdapter(this, this.getWindowManager().getDefaultDisplay().getWidth());
        mXRecyclerView.setAdapter(mOriginalAdapter);
    }


    private void initSwitch(int type) {
        if (type == 1) {
            isbest = "1";
            tvSwitchNew.setSelected(false);
            tvSwitchHot.setSelected(true);
        } else {
            isbest = "0";
            tvSwitchNew.setSelected(true);
            tvSwitchHot.setSelected(false);
        }


    }

    @Override
    protected void initEvents() {
        etSousuo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    String key = etSousuo.getText().toString().trim();
                    if (TextUtils.isEmpty(key)) {
                        showCustomToast("请输入搜索内容");
                        return true;
                    } else {
                        mXRecyclerView.onRefresh();
                        //隐藏软键盘
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etSousuo.getWindowToken(), 0);

                        return true;
                    }
                }
                return false;
            }
        });
        etSousuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    ivCancel.setVisibility(View.VISIBLE);
                } else {
                    ivCancel.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                key = editable.toString().trim();
                if (editable.length() > 0) {
                    ivCancel.setVisibility(View.VISIBLE);
                } else {
                    ivCancel.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        key = "";
        mXRecyclerView.onRefresh();
    }

    private void initTitle(int index) {
        CurrentIndex = index;
        page = 1;
        mView = new View[]{view1, view2, view3};
        mTextView = new TextView[]{tv1, tv2, tv3};
        mTextView[CurrentIndex].setSelected(true);
        mView[CurrentIndex].setVisibility(View.GONE);
    }

    private void switchTitle(int index) {
        if (index != CurrentIndex) {
            mTextView[CurrentIndex].setSelected(false);
            mView[CurrentIndex].setVisibility(View.INVISIBLE);
            mTextView[index].setSelected(true);
            mView[index].setVisibility(View.VISIBLE);
            CurrentIndex = index;
            mXRecyclerView.onRefresh();
        }
    }

    private void getIndexData(int currentIndex) {
        switch (currentIndex) {
            case 0://全部
                HttpArticle(TAG, "0", page, key);
                break;
            case 1:
                //攻略
                HttpArticle(TAG, "9", page, key);
                break;
            case 2:
                //晒单
                HttpArticle(TAG, "10", page, key);
                break;

        }
    }


    private void HttpArticle(String tag, String cate_id, final int page, String key) {
        showLoadDialog();
        API.getSingleton().article(tag, isbest, cate_id, String.valueOf(page), key, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<OriginalBean>> bean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<OriginalBean>>>() {
                }.getType());
                if (OriginalActivity.this.page == 1) {
                    mOriginalAdapter.setListData(bean.getData());
                } else {
                    mOriginalAdapter.addList(bean.getData());
                }
                OriginalActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                if (page == 1) {
                    mOriginalAdapter.clearList();
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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }


    @OnClick({R.id.rl_sousou, R.id.tv_switch_new, R.id.tv_switch_hot, R.id.ll_bg1, R.id.ll_bg2, R.id.ll_bg3, R.id.iv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_sousou:
                break;
            case R.id.tv_switch_new:
                if (!tvSwitchNew.isSelected()) {
                    tvSwitchNew.setSelected(true);
                    tvSwitchHot.setSelected(false);
                    page = 1;
                    isbest = "0";
                    getIndexData(CurrentIndex);
                }
                break;
            case R.id.tv_switch_hot:
                if (!tvSwitchHot.isSelected()) {
                    tvSwitchHot.setSelected(true);
                    tvSwitchNew.setSelected(false);
                    page = 1;
                    isbest = "1";
                    getIndexData(CurrentIndex);
                }
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
            case R.id.iv_cancel:
                if (!TextUtils.isEmpty(key.trim())) {
                    key = "";
                    etSousuo.setText("");
                    mXRecyclerView.onRefresh();
                }

                break;
        }
    }
}
