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
import com.cnxxp.cabbagenet.adapter.ChoujiangAdapter;
import com.cnxxp.cabbagenet.adapter.ChoujianglastAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.ChoujiangBean;
import com.cnxxp.cabbagenet.bean.ChoujiangLastBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.viewholder.ChoujiangLastHolder;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

public class ChoujiangActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ChoujiangActivity";
    private LinearLayout ll_back;
    private TextView tv_mychoujiang,tv_findmore;
    private XRecyclerView xr_luck,xr_luck_last;
    private ChoujiangAdapter ac;
    private ChoujianglastAdapter ca;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choujiang);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_mychoujiang  = (TextView) findViewById(R.id.tv_mychoujiang);
        tv_findmore = (TextView) findViewById(R.id.tv_findmore);
        xr_luck = (XRecyclerView) findViewById(R.id.xr_luck);
        xr_luck_last = (XRecyclerView) findViewById(R.id.xr_luck_last);
        ll_back.setOnClickListener(this);
        tv_mychoujiang.setOnClickListener(this);
        tv_findmore.setOnClickListener(this);
        LinearLayoutManager sousuoManager = new LinearLayoutManager(this);
        sousuoManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager sousuoManager2 = new LinearLayoutManager(this);
        sousuoManager2.setOrientation(LinearLayoutManager.VERTICAL);
        xr_luck.setLayoutManager(sousuoManager);
        xr_luck_last.setLayoutManager(sousuoManager2);
        ac = new ChoujiangAdapter(this);
        ca = new ChoujianglastAdapter(this);
        xr_luck_last.setAdapter(ca);
        xr_luck.setAdapter(ac);
        httpchoujiang();
        httpchoujiang_last();
        xr_luck.setPullRefreshEnabled(false);
        xr_luck_last.setPullRefreshEnabled(false);
        xr_luck.setLoadingMoreEnabled(false);
        xr_luck_last.setLoadingMoreEnabled(false);
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
            case R.id.tv_mychoujiang:
                Intent i = new Intent(this,Mychoujiang.class);
                startActivity(i);
                break;
            case R.id.tv_findmore:
                Intent i2 = new Intent(this,MoreChouJiangActivity.class);
                startActivity(i2);
                break;
            default:
                break;
        }
    }

    private void httpchoujiang(){
        API.getSingleton().luck_list(TAG, Config.PublicParams.usid, 30, 1, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                Log.d(TAG,object.toString());
                MyBean<List<ChoujiangBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<ChoujiangBean>>>() {
                }.getType());
                ac.setListData(myBean.getData());
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
    private void httpchoujiang_last(){
        API.getSingleton().lucky_list_past(TAG, Config.PublicParams.usid, 5, 1, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                Log.d(TAG,object.toString());
                MyBean<List<ChoujiangLastBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<ChoujiangLastBean>>>() {
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
