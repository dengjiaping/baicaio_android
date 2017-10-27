package com.cnxxp.cabbagenet.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.EmojiPagerAdapter;
import com.cnxxp.cabbagenet.adapter.OriginalCommentAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.OriginalCommentBean;
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

public class AllCommentActivity extends BaseActivity {
    private static final String TAG = "AllCommentActivity";
    @Bind(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.et_comment_detail)
    EditText etCommentDetail;
    @Bind(R.id.tv_hf)
    TextView tvHf;

    private OriginalCommentAdapter mAdapter;
    private int page;
    private String shopid;
    private String xid;
    private Boolean ishf = false;
    private String hfName = "";
    private String commentid = "";

    private EmojiPagerAdapter emojiAdapter;
    @Bind(R.id.vp_emoji)
    ViewPager vpEmoji;
    @Bind(R.id.tab_select)
    TabLayout tabLayout;
    @Bind(R.id.iv_emoji)
    ImageView ivEmoji;
    @Bind(R.id.ll_emoji)
    LinearLayout llEmoji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_all_comment);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mAdapter = new OriginalCommentAdapter(this);
        mXRecyclerView.setAdapter(mAdapter);
        initHfPop();
        emojiAdapter = new EmojiPagerAdapter(this);
        vpEmoji.setAdapter(emojiAdapter);
        tabLayout.setupWithViewPager(vpEmoji);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initEvents() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpGetAllComment(TAG, shopid, xid, page);
            }

            @Override
            public void onLoadMore() {
                httpGetAllComment(TAG, shopid, xid, page);
            }
        });
        mAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (llEmoji.getVisibility() == View.VISIBLE) {
                    llEmoji.setVisibility(View.GONE);
                    return;
                }
                if (ishf) {
                    hideSoftInputFromWindow();
                } else {
                    hfName = mAdapter.mList.get(postion).getUname();
                    commentid = mAdapter.mList.get(postion).getId();
                    hfPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                }
            }
        });
        etCommentDetail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llEmoji.setVisibility(View.GONE);
                }
            }
        });
        etCommentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llEmoji.setVisibility(View.GONE);
            }
        });
        emojiAdapter.setEmojiSelectInterface(new EmojiPagerAdapter.EmojiSelectInterface() {
            @Override
            public void emojiItemClick(String content, int img) {
                ImageSpan imageSpan = new ImageSpan(AllCommentActivity.this, BitmapFactory.decodeResource(AllCommentActivity.this.getResources(), img));
                SpannableString spannableString = new SpannableString(content);  //图片所表示的文字  
                spannableString.setSpan(imageSpan, 0, spannableString.length(), SpannableString.SPAN_MARK_MARK);
                etCommentDetail.append(spannableString);
            }
        });
    }

    private void hideSoftInputFromWindow() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

// 获取软键盘的显示状态
        boolean isOpen = imm.isActive();
// 如果软键盘已经显示，则隐藏，反之则显示 
        //   imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        if (isOpen) {
            imm.hideSoftInputFromWindow(etCommentDetail.getWindowToken(), 0);
        }
        ishf = false;
        tvHf.setVisibility(View.GONE);

    }

    @Override
    protected void initData() {
        switch (getIntent().getExtras().getString("from")) {
            case "strategy":
                //攻略
                setTitleText("攻略评论");
                xid = "3";
                break;
            case "share":
                //晒单
                setTitleText("晒单评论");
                xid = "3";
                break;
            case "broke":
                //爆料
                setTitleText("爆料评论");
                xid = "3";
                break;
            case "goods":
                //商品
                setTitleText("商品评论");
                xid = "1";
                break;
        }
        shopid = getIntent().getExtras().getString("shopid");
        mXRecyclerView.onRefresh();

    }

    @OnClick({R.id.tv_release, R.id.iv_emoji, R.id.iv_edit_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_release:
                //发送评论
                if (TextUtils.isEmpty(Config.PublicParams.usid)) {
                    startActivity(LoginActivity.class);
                    return;
                } else {
                    String content = etCommentDetail.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        showCustomToast("请填写评论内容");
                        return;
                    }
                    content += "|android";
                    if (ishf) {
                        hfComment(TAG, content, commentid);
                        hideSoftInputFromWindow();
                    } else {
                        httpReleaseComment(TAG, content, xid, shopid);
                    }
                    etCommentDetail.setText("");
                }
                break;
            case R.id.iv_emoji:
                hidenAndShow();
                break;
            case R.id.iv_edit_del:
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0,
                        0, KeyEvent.KEYCODE_ENDCALL);
                etCommentDetail.dispatchKeyEvent(event);
                break;
        }

    }

    private void hidenAndShow() {
        if (llEmoji.getVisibility() == View.VISIBLE) {
            llEmoji.setVisibility(View.GONE);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && AllCommentActivity.this.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(AllCommentActivity.this.getCurrentFocus()
                        .getWindowToken(), 0);
            }
            // 延迟一会，让键盘先隐藏再显示表情键盘，否则会有一瞬间表情键盘和软键盘同时显示
            llEmoji.postDelayed(new Runnable() {
                @Override
                public void run() {
                    llEmoji.setVisibility(View.VISIBLE);
                }
            }, 50);

        }
    }

    private PopupWindow hfPop;

    private void initHfPop() {
        View hfView = LayoutInflater.from(this).inflate(R.layout.comment_hf_view, null);
        hfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hfPop.dismiss();
            }
        });
        hfView.findViewById(R.id.tv_hf_ta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ishf = true;
                tvHf.setVisibility(View.VISIBLE);
                tvHf.setText("回复:" + hfName);
                hfPop.dismiss();
                etCommentDetail.setFocusable(true);
                etCommentDetail.setFocusableInTouchMode(true);
                etCommentDetail.requestFocus();
                AllCommentActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
        hfPop = new PopupWindow(hfView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        hfPop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        hfPop.setFocusable(true);
        hfPop.setOutsideTouchable(true);
    }

    private void httpGetAllComment(String tag, String shopid, String xid, final int page) {
        showLoadDialog();
        API.getSingleton().commentItem(tag, shopid, page + "", xid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<OriginalCommentBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<OriginalCommentBean>>>() {
                }.getType());
                if (page == 1) {
                    mAdapter.setListData(myBean.getData());
                } else {
                    mAdapter.addList(myBean.getData());
                }
                AllCommentActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);
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

    private void httpReleaseComment(String tag, String content, final String xid, String id) {
        API.getSingleton().releaseComment(tag, content, xid, id, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast(msg);
                page = 1;
                httpGetAllComment(TAG, shopid, xid, page);
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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void hfComment(String tag, String content, String commentid) {
        API.getSingleton().hfComment(tag, commentid, content, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast(msg);
                mXRecyclerView.onRefresh();
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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }
}
