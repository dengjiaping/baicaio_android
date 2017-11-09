package com.cnxxp.cabbagenet.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.QuanAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.QuanBean;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

public class SearchQuanActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SearchQuanActivity";
    private LinearLayout activity_back_bg,ll_searh;
    private EditText et_sousuo;
    private ImageView iv_clear;
    private TextView tv_search,tv_xl,tv_zk,tv_jg,tv_qme;
    private XRecyclerView rv_search;
    private String keywords;
    private QuanAdapter quanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_quan);
        activity_back_bg = (LinearLayout) findViewById(R.id.activity_back_bg);
        ll_searh = (LinearLayout) findViewById(R.id.ll_searh);
        et_sousuo = (EditText) findViewById(R.id.et_sousuo);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_xl = (TextView) findViewById(R.id.tv_xl);
        tv_zk = (TextView) findViewById(R.id.tv_zk);
        tv_jg = (TextView) findViewById(R.id.tv_jg);
        tv_qme = (TextView) findViewById(R.id.tv_qme);
        rv_search = (XRecyclerView) findViewById(R.id.rv_search);
        activity_back_bg.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_xl.setOnClickListener(this);
        tv_zk.setOnClickListener(this);
        tv_jg.setOnClickListener(this);
        tv_qme.setOnClickListener(this);
        LinearLayoutManager sousuoManager = new LinearLayoutManager(this);
        sousuoManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_search.setLayoutManager(sousuoManager);
        quanAdapter = new QuanAdapter(this);
        rv_search.setAdapter(quanAdapter);

         keywords = getIntent().getExtras().getString("keywords");
        et_sousuo.setText(keywords);
        httpSearchQuan(TAG,"v",keywords);
        rv_search.setPullRefreshEnabled(false);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_back_bg:
                finish();
                break;
            case R.id.iv_clear:
                break;
            case R.id.tv_search:
                String words = et_sousuo.getText().toString().trim();
                if (TextUtils.isEmpty(words)){
                    Toast.makeText(this,"搜索词不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                keywords = words;
                tv_xl.setTextColor(Color.parseColor("#FF4081"));
                tv_zk.setTextColor(Color.parseColor("#333333"));
                tv_jg.setTextColor(Color.parseColor("#333333"));
                tv_qme.setTextColor(Color.parseColor("#333333"));
                httpSearchQuan(TAG,"v",keywords);

                break;
            case R.id.tv_xl:
                tv_xl.setTextColor(Color.parseColor("#FF4081"));
                tv_zk.setTextColor(Color.parseColor("#333333"));
                tv_jg.setTextColor(Color.parseColor("#333333"));
                tv_qme.setTextColor(Color.parseColor("#333333"));
                httpSearchQuan(TAG,"v",keywords);
                break;
            case R.id.tv_zk:
                tv_xl.setTextColor(Color.parseColor("#333333"));
                tv_zk.setTextColor(Color.parseColor("#FF4081"));
                tv_jg.setTextColor(Color.parseColor("#333333"));
                tv_qme.setTextColor(Color.parseColor("#333333"));
                httpSearchQuan(TAG,"z",keywords);
                break;
            case R.id.tv_jg:
                tv_xl.setTextColor(Color.parseColor("#333333"));
                tv_zk.setTextColor(Color.parseColor("#333333"));
                tv_jg.setTextColor(Color.parseColor("#FF4081"));
                tv_qme.setTextColor(Color.parseColor("#333333"));
                httpSearchQuan(TAG,"p",keywords);
                break;
            case R.id.tv_qme:
                tv_xl.setTextColor(Color.parseColor("#333333"));
                tv_zk.setTextColor(Color.parseColor("#333333"));
                tv_jg.setTextColor(Color.parseColor("#333333"));
                tv_qme.setTextColor(Color.parseColor("#FF4081"));
                httpSearchQuan(TAG,"c",keywords);
                break;
        }

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
    private void httpSearchQuan(String tag,String type,String words){
        API.getSingleton().search_quan(tag, words, type,new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                Log.d(TAG,object.toString());
                MyBean<List<QuanBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<QuanBean>>>() {
                }.getType());
//                Log.d(TAG, myBean.getData().toString());

                quanAdapter.setListData(myBean.getData());

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                // 获取软键盘的显示状态
                boolean isOpen = imm.isActive();

                // 如果软键盘已经显示，则隐藏，反之则显示
                //   imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                if (isOpen) {
                    imm.hideSoftInputFromWindow(et_sousuo.getWindowToken(), 0);
                }
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
