package com.cnxxp.cabbagenet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.ScreenAdapter;
import com.cnxxp.cabbagenet.adapter.ShopAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.ShopBean;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.view.FlowDragLayoutManager.FlowDragLayoutConstant;
import com.cnxxp.cabbagenet.view.FlowDragLayoutManager.FlowDragLayoutManager;
import com.cnxxp.cabbagenet.xrecyclerview.callback.RecyclerViewItemClickListener;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ScreenActivity extends BaseActivity {
    private static final String TAG = "ScreenActivity";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.shop)
    RecyclerView shopRecyclerView;
    private ScreenAdapter mAdapter;
    private String cid;
    private String clazz;
    private ShopAdapter shopAdapter;
    private String origId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_screen);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("筛选");
        setTitleMoreTextVisibility(View.VISIBLE, "确定");
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ScreenAdapter(this);
        recyclerView.setAdapter(mAdapter);


        FlowDragLayoutManager flmanager = new FlowDragLayoutManager(FlowDragLayoutConstant.LEFT);
        shopRecyclerView.setLayoutManager(flmanager);
        shopAdapter = new ShopAdapter(this);
        shopRecyclerView.setAdapter(shopAdapter);

    }

    @Override
    protected void initEvents() {
        mAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                cid = mAdapter.cids[postion];
                clazz = mAdapter.mList.get(postion).getTitle();
            }
        });
        shopAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                origId = shopAdapter.mList.get(postion).getId();
            }
        });
    }

    @Override
    protected void initData() {
        cid = "";
        clazz = "";
        origId = "";
        httpShopList();
    }

    private void httpShopList() {
        showLoadDialog();
        API.getSingleton().shopInfo(TAG, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<ShopBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<ShopBean>>>() {
                }.getType());
                shopAdapter.setListData(myBean.getData());
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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    @OnClick(R.id.activity_more_text_bg)
    public void onClick() {
        if (TextUtils.isEmpty(cid)) {
            showCustomToast("您还没有选择分类");
            return;
        }
        Intent intent = getIntent();
        intent.putExtra("cid", cid);
        intent.putExtra("class", clazz);
        intent.putExtra("origid", origId);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
