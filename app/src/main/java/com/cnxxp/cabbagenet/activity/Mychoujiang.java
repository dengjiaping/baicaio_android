package com.cnxxp.cabbagenet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.ChoujianglastAdapter;
import com.cnxxp.cabbagenet.adapter.MyChoujiangAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.ChoujiangLastBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.MyluckBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

public class Mychoujiang extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "Mychoujiang";
    private LinearLayout ll_back;
    private TextView tv_gochoujiang;
    private XRecyclerView xr_myluck;
    private MyChoujiangAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychoujiang);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_gochoujiang  = (TextView) findViewById(R.id.tv_gochoujiang);
        xr_myluck = (XRecyclerView) findViewById(R.id.xr_myluck);
        ll_back.setOnClickListener(this);
        tv_gochoujiang.setOnClickListener(this);
        LinearLayoutManager sousuoManager2 = new LinearLayoutManager(this);
        sousuoManager2.setOrientation(LinearLayoutManager.VERTICAL);
        xr_myluck.setLayoutManager(sousuoManager2);
        ca = new MyChoujiangAdapter(this);
        xr_myluck.setAdapter(ca);
        httpchoujiang_my();
        xr_myluck.setPullRefreshEnabled(false);
        xr_myluck.setLoadingMoreEnabled(false);
    }



    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_gochoujiang:
//                Intent i = new Intent(this,ChoujiangActivity.class);
//                startActivity(i);
                finish();
                break;

            default:
                break;
        }

    }
    private void httpchoujiang_my() {
        API.getSingleton().myluckys(TAG, Config.PublicParams.usid, 30, 1, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                Log.d(TAG,object.toString());
                MyBean<List<MyluckBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<MyluckBean>>>() {
                }.getType());
                ca.setListData(myBean.getData());
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
