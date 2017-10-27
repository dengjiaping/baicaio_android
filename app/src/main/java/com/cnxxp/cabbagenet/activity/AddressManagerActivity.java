package com.cnxxp.cabbagenet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.AddressAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AddressBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.cnxxp.cabbagenet.xrecyclerview.callback.RecyclerViewItemClickListener;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class AddressManagerActivity extends BaseActivity {

    private static final String TAG = "AddressManagerActivity";
    private static final int REQUEST_CODE_EDIT_ADDRESS = 1;
    @Bind(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    private AddressAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_address_manager);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("收货地址管理");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.noMoreLoading();
        mAdapter = new AddressAdapter(this);
        mXRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvents() {
        mAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", mAdapter.mList.get(postion));
                Intent intent = new Intent(AddressManagerActivity.this, AddNewAddressActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_EDIT_ADDRESS);
            }
        });
    }

    @Override
    protected void initData() {
        httpGetAddressList(TAG);

    }

    @OnClick(R.id.ll_add_bg)
    public void onClick() {

        Intent intent = new Intent(AddressManagerActivity.this, AddNewAddressActivity.class);
        startActivityForResult(intent, REQUEST_CODE_EDIT_ADDRESS);
    }

    private void httpGetAddressList(String tag) {
        API.getSingleton().getAddress(tag, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
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
        if(requestCode==REQUEST_CODE_EDIT_ADDRESS&&resultCode== Activity.RESULT_OK){
            httpGetAddressList(TAG);
        }
    }
}
