package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.AttentionAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AttentionBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.PopupwindowUtils;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class AttentionActivity extends BaseActivity {
    private static final String TAG = "AttentionActivity";
    @Bind(R.id.recyclerView)
    XRecyclerView recyclerView;
    @Bind(R.id.tv_key_num)
    TextView tvKeyNum;
    private AttentionAdapter mAdapter;
    private int tag_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_attention);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("关注内容管理");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AttentionAdapter(this);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvents() {
        mAdapter.setmAttentInterface(new AttentionAdapter.AttentInterface() {
            @Override
            public void ItemClick(String tag) {
                Bundle bundle = new Bundle();
                bundle.putString("tag", tag);
                //     bundle.putInt("tag_num", tag_num);
                startActivity(SearchActivity.class, bundle);
            }

            @Override
            public void DelClick(final String id, final int position, final String tag) {
                PopupwindowUtils.initPopupWindow(getWindow().getDecorView(), LayoutInflater.from(AttentionActivity.this), "是否删除？", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupwindowUtils.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DelTag(id, position, tag);
                        PopupwindowUtils.delete();
                    }
                });

            }

            @Override
            public void pushDel(String id, int position, String tag) {
                HttpPushDel(id, position, tag);
            }

            @Override
            public void pushCreate(String id, int position, String tag) {
                httpPushCreate(tag, position);
            }


        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        httpTagList();
        JPushInterface.getAllTags(getApplicationContext(),1);
      

    }

    @OnClick(R.id.ll_add_attention)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putInt("tag_num", tag_num);
        startActivity(SearchActivity.class, bundle);
    }

    private void httpTagList() {
        showLoadDialog();
        API.getSingleton().notifyTagByUser(TAG, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<AttentionBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<AttentionBean>>>() {
                }.getType());
                mAdapter.setListData(myBean.getData());
                tvKeyNum.setText("(" + myBean.getData().size() + "/14)");
                tag_num = myBean.getData().size();
                Config.PublicParams.u_attention = new HashMap<String, AttentionBean>();
                for (int i = 0; i < myBean.getData().size(); i++) {
                    Config.PublicParams.u_attention.put(myBean.getData().get(i).getTag(), myBean.getData().get(i));
                }
                
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                tvKeyNum.setText("(0/14)");
                tag_num = 0;
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

    private void DelTag(String id, final int position, final String tag) {
        showLoadDialog();
        API.getSingleton().followTagDel(TAG, tag, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                HashSet<String> set = new HashSet<String>();
                set.add(mAdapter.mList.get(position).getTag());
                //  JPushInterface.deleteTags(AttentionActivity.this, 1, set);
                mAdapter.remove(position);
                tvKeyNum.setText("(" + mAdapter.mList.size() + "/14)");
                Config.PublicParams.u_attention.remove(tag);
                JPushInterface.deleteTags(getApplicationContext(), 1, set);

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

    private void httpPushCreate(final String tag, final int position) {
        showLoadDialog();
        API.getSingleton().notifyTagCreate(TAG, tag, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                mAdapter.mList.get(position).setP_sign(1);
                mAdapter.notifyDataSetChanged();
                HashSet<String> set = new HashSet<String>();
                set.add(tag);
                JPushInterface.addTags(getApplicationContext(), 1, set);
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

    private void HttpPushDel(String id, final int position, final String tag) {
        showLoadDialog();
        API.getSingleton().notifyTagDel(TAG, id, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                mAdapter.mList.get(position).setP_sign(0);
                mAdapter.notifyDataSetChanged();
                HashSet<String> set = new HashSet<String>();
                set.add(tag);
                JPushInterface.deleteTags(getApplicationContext(), 1, set);
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
                showCustomToast(msg);
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }
}
