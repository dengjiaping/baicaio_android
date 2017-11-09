package com.cnxxp.cabbagenet.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DrawableUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.view.YhFlowLayout;
import com.fyales.tagcloud.library.TagBaseAdapter;
import com.fyales.tagcloud.library.TagCloudLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuanActivity extends BaseActivity {
    private static final String TAG = "QuanActivity";
    private List<String> mList;
    TagCloudLayout mContainer;
    TagBaseAdapter mAdapter;
    Button btn_souquan;
    EditText et_souquan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan);
        mContainer = (TagCloudLayout)findViewById(R.id.container);
        mContainer = (TagCloudLayout) findViewById(R.id.container);
        btn_souquan = (Button) findViewById(R.id.btn_souquan);
        et_souquan = (EditText) findViewById(R.id.et_souquan);
        mContainer.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
//                Toast.makeText(QuanActivity.this,mList.get(position),Toast.LENGTH_SHORT).show();

                Bundle  bundle = new Bundle();
                bundle.putString("keywords",  mList.get(position));
                startActivity(SearchQuanActivity.class, bundle);
            }
        });

        btn_souquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String words = et_souquan.getText().toString().trim();
                if (TextUtils.isEmpty(words)){
                    Toast.makeText(QuanActivity.this,"请输入关键词",Toast.LENGTH_SHORT);
                    return;
                }

                Bundle  bundle = new Bundle();
                bundle.putString("keywords",  words);
                startActivity(SearchQuanActivity.class, bundle);
            }
        });

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        httpKeyWords(TAG);

    }
    private void httpKeyWords(final String TAG){
        API.getSingleton().keywords(TAG, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<String>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<String>>>() {}.getType());
                mList = myBean.getData();

                Log.d(TAG,mList.toString());
                mAdapter = new TagBaseAdapter(QuanActivity.this,mList);
                mContainer.setAdapter(mAdapter);
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
