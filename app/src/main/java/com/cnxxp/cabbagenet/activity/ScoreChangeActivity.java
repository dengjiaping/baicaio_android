package com.cnxxp.cabbagenet.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.ScoreItemAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AddressBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.ScoreItemBean;
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

public class ScoreChangeActivity extends BaseActivity {
    private static final String TAG = "ScoreChangeActivity";
    public static final int RQ_ADDRESS_CODE = 1;
    @Bind(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.tv_change_list)
    TextView tvChangeList;
    @Bind(R.id.ll_change_bg)
    LinearLayout llChangeBg;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.iv_cancel)
    ImageView ivCancel;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    private ScoreItemAdapter mScoreItemAdapter;
    private PopupWindow switchPop;
    private TextView virtualGift;
    private TextView practicalityGift;
    private TextView AllGift;
    private String cid;
    private int page;
    private String title;

    private TextView tvPopName;
    private TextView tvPopDesc;
    private TextView tvAddress;
    private TextView tvCancel;
    private TextView tvChange;

    private AddressBean address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_score_change);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("积分兑换");
        setTitleMoreTextVisibility(View.VISIBLE, "已兑换");
        initpop();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mScoreItemAdapter = new ScoreItemAdapter(this);
        mXRecyclerView.setAdapter(mScoreItemAdapter);
    }

    @Override
    protected void initEvents() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                httpScoreChageList(TAG, cid, title, page);
            }

            @Override
            public void onLoadMore() {
                httpScoreChageList(TAG, cid, title, page);
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    ivCancel.setVisibility(View.INVISIBLE);
                } else {
                    ivCancel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    ivCancel.setVisibility(View.VISIBLE);
                } else {
                    ivCancel.setVisibility(View.INVISIBLE);
                }
            }
        });
        mScoreItemAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
                                                     @Override
                                                     public void onItemClick(View view, final int postion) {
                                                         if (changePop == null) {
                                                             initChangePop();
                                                         }
                                                         tvPopName.setText(mScoreItemAdapter.mList.get(postion).getTitle());
                                                         tvPopDesc.setText("兑换此商品需要" + 1 * Integer.parseInt(mScoreItemAdapter.mList.get(postion).getCoin()) + "金币" + mScoreItemAdapter.mList.get(postion).getScore() + "积分,是否兑换此商品");
                                                         tvAddress.setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View view) {
                                                                 Intent intent = new Intent(ScoreChangeActivity.this, SelectAddressActivity.class);
                                                                 startActivityForResult(intent, RQ_ADDRESS_CODE);
                                                             }
                                                         });
                                                         tvChange.setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View view) {
                                                                 if (address != null) {

                                                                     httpChange(TAG, mScoreItemAdapter.mList.get(postion).getId(), "1", address.getConsignee(), address.getAddress(), address.getZip(), address.getMobile());
                                                                 } else {
                                                                     showCustomToast("请先选择收货地址");
                                                                 }
                                                             }
                                                         });
                                                         changePop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                                                     }

                                                 }

        );
    }

    @Override
    protected void initData() {
        tvChangeList.setText("全部");
        cid = "";
        page = 1;
        title = "";
        httpScoreChageList(TAG, cid, title, page);
    }

    private void initpop() {
        View view = View.inflate(this, R.layout.pop_score_change, null);
        AllGift = (TextView) view.findViewById(R.id.tv1);
        AllGift.setSelected(true);
        virtualGift = (TextView) view.findViewById(R.id.tv2);
        virtualGift.setSelected(false);
        AllGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //全部
                AllGift.setSelected(true);
                virtualGift.setSelected(false);
                practicalityGift.setSelected(false);
                tvChangeList.setText("全部");
                cid = "";
                mXRecyclerView.onRefresh();
                switchPop.dismiss();
            }
        });
        virtualGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //虚拟礼品
                AllGift.setSelected(false);
                virtualGift.setSelected(true);
                practicalityGift.setSelected(false);
                tvChangeList.setText("虚拟礼品");
                cid = "1";
                mXRecyclerView.onRefresh();
                switchPop.dismiss();
            }
        });
        practicalityGift = (TextView) view.findViewById(R.id.tv3);
        practicalityGift.setSelected(false);
        practicalityGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实物礼品
                virtualGift.setSelected(false);
                AllGift.setSelected(false);
                practicalityGift.setSelected(true);
                tvChangeList.setText("实物礼品");
                cid = "0";
                mXRecyclerView.onRefresh();
                switchPop.dismiss();

            }
        });
        switchPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switchPop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        switchPop.setFocusable(true);
        switchPop.setOutsideTouchable(true);
    }

    @OnClick({R.id.activity_more_text_bg, R.id.ll_change_bg, R.id.iv_cancel, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_more_text_bg:
                //已兑换
                startActivity(HasChangedActivity.class);
                break;
            case R.id.ll_change_bg:
                if (switchPop.isShowing()) {
                    switchPop.dismiss();
                } else {
                    switchPop.showAsDropDown(llChangeBg, 0, 0);
                }
                break;
            case R.id.iv_cancel:
                etSearch.setText("");
                title = "";
                break;
            case R.id.tv_search:
                title = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    showCustomToast("您还没有输入任何内容");
                    return;
                }
                page = 1;
                httpScoreChageList(TAG, cid, title, page);
                break;
        }
    }

    private void httpScoreChageList(String tag, String cid, String title, final int page) {
        showLoadDialog();
        API.getSingleton().scoreList(tag, cid, page + "", title, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<ScoreItemBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<ScoreItemBean>>>() {
                }.getType());
                if (page == 1) {
                    mScoreItemAdapter.setListData(myBean.getData());
                } else {
                    mScoreItemAdapter.addList(myBean.getData());
                }
                ScoreChangeActivity.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                if (page == 1) {
                    mScoreItemAdapter.clearList();
                }

            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                mXRecyclerView.refreshComplete();
                mXRecyclerView.loadMoreComplete();
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

    private PopupWindow changePop;

    private void initChangePop() {
        View changeView = LayoutInflater.from(this).inflate(R.layout.pop_change, null);
        changeView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        tvPopName = (TextView) changeView.findViewById(R.id.tv_name);
        tvPopDesc = (TextView) changeView.findViewById(R.id.tv_describ);
        tvAddress = (TextView) changeView.findViewById(R.id.tv_address_detail);
        tvCancel = (TextView) changeView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePop.dismiss();
            }
        });
        tvChange = (TextView) changeView.findViewById(R.id.tv_change);
        changePop = new PopupWindow(changeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        changePop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changePop.setFocusable(true);
        changePop.setOutsideTouchable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_ADDRESS_CODE && resultCode == RESULT_OK && data != null) {
            address = data.getParcelableExtra("address");
            tvAddress.setText(address.getConsignee() + "-" + address.getMobile() + "-" + address.getZip() + "-" + address.getAddress());
        }
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
                if (page == 1) {
                    mScoreItemAdapter.clearList();
                }

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
}
