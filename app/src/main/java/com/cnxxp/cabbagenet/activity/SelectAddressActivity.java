package com.cnxxp.cabbagenet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.SelectAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AddressBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SelectAddressActivity extends BaseActivity {
    private static final String TAG = "SelectAddressActivity";
    private static final int RQ_CODE_ADD = 1;
    @Bind(R.id.activity_more_text)
    TextView activityMoreText;
    @Bind(R.id.address_list)
    RecyclerView addressList;
    SelectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_address);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("选择收货地址");
        setTitleMoreTextVisibility(View.VISIBLE);
        activityMoreText.setText("确定");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        addressList.setLayoutManager(layoutManager);
        mAdapter = new SelectAdapter(this);
        addressList.setAdapter(mAdapter);

    }

    @Override
    protected void initEvents() {
    }

    @Override
    protected void initData() {
        httpGetAddressList(TAG);
    }

    @OnClick({R.id.activity_more_text_bg, R.id.ll_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_more_text_bg:
                if (mAdapter.getSelectIndex() != -1) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("address", mAdapter.mList.get(mAdapter.getSelectIndex()));
                    Intent intent = getIntent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    showCustomToast("请先选择收货地址");
                    return;
                }
                break;
            case R.id.ll_add_address:
                Intent addAddress = new Intent(SelectAddressActivity.this, AddNewAddressActivity.class);
                startActivityForResult(addAddress, RQ_CODE_ADD);
                break;
        }
    }

    private void httpGetAddressList(String tag) {
        API.getSingleton().getAddress(tag, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                if (object.toString().equals("null")) {
                    return;
                }
                MyBean<List<AddressBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<AddressBean>>>() {
                }.getType());
                mAdapter.setListData(myBean.getData());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_CODE_ADD && resultCode == RESULT_OK) {
            httpGetAddressList(TAG);
        }
    }
}
