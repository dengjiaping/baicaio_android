package com.cnxxp.cabbagenet.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.ExchangeAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AddressBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.ScoreDetailBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.PopupwindowUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.cnxxp.cabbagenet.config.Config.PublicParams.mobile;

public class ScoreDetailActivity extends BaseActivity {
    private static final String TAG = "ScoreDetailActivity";
    public static final int RQ_ADDRESS_CODE = 1;
    @Bind(R.id.iv_img)
    ImageView ivImg;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_repertory)
    TextView tvRepertory;
    @Bind(R.id.tv_has_change_number)
    TextView tvHasChangeNumber;
    @Bind(R.id.tv_can_change_number)
    TextView tvCanChangeNumber;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_change_number)
    TextView tvChangeNumber;

    private View[] views;
    private TextView[] tvs;
    private int currentindex;
    private String scoreid;
    private ExchangeAdapter mAdapter;
    private int maxSize;

    private TextView tvPopName;
    private TextView tvPopDesc;
    private TextView tvAddress;
    private TextView tvCancel;
    private TextView tvChange;

    private AddressBean address;
    private ScoreDetailBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_score_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("积分详情");
        initTab();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ExchangeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        scoreid = getIntent().getExtras().getString("scoreid");
        httpScoreDetail(TAG, scoreid);
    }

    private void initTab() {
        currentindex = 0;
        views = new View[]{view1, view2};
        tvs = new TextView[]{tv1, tv2};
        views[currentindex].setVisibility(View.VISIBLE);
        tvs[currentindex].setSelected(true);
        webView.setVisibility(View.VISIBLE);
    }

    private void switchTab(int index) {
        if (currentindex != index) {
            views[currentindex].setVisibility(View.GONE);
            tvs[currentindex].setSelected(false);
            views[index].setVisibility(View.VISIBLE);
            tvs[index].setSelected(true);
            currentindex = index;
            if (index == 0) {
                mRecyclerView.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
            } else {
                webView.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.ll_bg1, R.id.ll_bg2, R.id.iv_sub, R.id.iv_add, R.id.tv_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bg1:
                //兑换须知
                switchTab(0);
                break;
            case R.id.ll_bg2:
                //兑换记录
                switchTab(1);
                break;
            case R.id.iv_sub:
                //减
                if (Integer.parseInt(tvChangeNumber.getText().toString().trim()) > 1) {
                    tvChangeNumber.setText((Integer.parseInt(tvChangeNumber.getText().toString().trim()) - 1) + "");
                } else {
                    showCustomToast("购买数量不能小于1");
                    return;
                }
                break;
            case R.id.iv_add:
                if (Integer.parseInt(tvChangeNumber.getText().toString().trim()) < maxSize) {
                    tvChangeNumber.setText((Integer.parseInt(tvChangeNumber.getText().toString().trim()) + 1) + "");
                } else {
                    showCustomToast("已达到最大购买数");
                    return;
                }
                //加
                break;
            case R.id.tv_change:
                if (TextUtils.isEmpty(mobile)) {
                    PopupwindowUtils.initPopupWindow(getWindow().getDecorView(), LayoutInflater.from(this), "兑换商品需要绑定手机,是否去绑定?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopupwindowUtils.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            PopupwindowUtils.delete();
                            startActivity(SafeSettingActivity.class);
                        }
                    });
                } else {
                    if (changePop == null) {
                        initpop();
                    }

                    changePop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                }
                //兑换
                break;
        }
    }

    private PopupWindow changePop;

    private void initpop() {
        View changeView = LayoutInflater.from(this).inflate(R.layout.pop_change, null);
        changeView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        tvPopName = (TextView) changeView.findViewById(R.id.tv_name);
        tvPopName.setText(mData.getTitle());
        tvPopDesc = (TextView) changeView.findViewById(R.id.tv_describ);
        tvPopDesc.setText("兑换此商品需要" + Integer.parseInt(tvChangeNumber.getText().toString().trim()) * Integer.parseInt(mData.getCoin()) + "金币" + mData.getScore() + "积分,是否兑换此商品");
        tvAddress = (TextView) changeView.findViewById(R.id.tv_address_detail);
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreDetailActivity.this, SelectAddressActivity.class);
                startActivityForResult(intent, RQ_ADDRESS_CODE);
            }
        });
        tvCancel = (TextView) changeView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePop.dismiss();
            }
        });
        tvChange = (TextView) changeView.findViewById(R.id.tv_change);
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address != null) {

                    httpChange(TAG, mData.getId(), tvChangeNumber.getText().toString(), address.getConsignee(), address.getAddress(), address.getZip(), address.getMobile());
                } else {
                    showCustomToast("请先选择收货地址");
                }
            }
        });
        changePop = new PopupWindow(changeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        changePop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changePop.setFocusable(true);
        changePop.setOutsideTouchable(true);
    }

    private void httpScoreDetail(String tag, String scoreid) {
        showLoadDialog();
        API.getSingleton().scoreChangeDetail(tag, scoreid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<ScoreDetailBean> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<ScoreDetailBean>>() {
                }.getType());
                mData = myBean.getData();
                setData(mData);
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);

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

    private void setData(ScoreDetailBean data) {
        Glide.with(this).load(data.getImg()).placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(ivImg);
        tvTitle.setText(data.getTitle());
        tvPrice.setText(data.getCoin() + "金币  " + data.getScore() + "积分");
        tvRepertory.setText(data.getStock());
        tvHasChangeNumber.setText(data.getBuy_num());
        tvCanChangeNumber.setText(data.getUser_num());
        mAdapter.setListData(data.getList());
        maxSize = Integer.parseInt(data.getUser_num());
        webView.loadData(data.getDesc(), "text/html; charset=UTF-8", null);
    }

    private void httpChange(String tag, String id, String number, String consignee, String address, String zip, String mobile) {
        API.getSingleton().ec(tag, Config.PublicParams.usid, id, number, consignee, address, zip, mobile, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                showCustomToast(myBean.getData());
                changePop.dismiss();
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_ADDRESS_CODE && resultCode == RESULT_OK && data != null) {
            address = data.getParcelableExtra("address");
            tvAddress.setText(address.getConsignee() + "-" + address.getMobile() + "-" + address.getZip() + "-" + address.getAddress());
        }
    }
}
