package com.cnxxp.cabbagenet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public class LuckActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout ll_back;
    TextView tv_title,tv_yuanjia,iv_jifen,iv_user,iv_time;
    ImageView iv_pic;
    String type,pic,id,title,price,jifen,user,time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_yuanjia = (TextView) findViewById(R.id.tv_yuanjia);
        iv_jifen = (TextView) findViewById(R.id.iv_jifen);
        iv_jifen.setOnClickListener(this);
        iv_user = (TextView) findViewById(R.id.iv_user);
        iv_time = (TextView) findViewById(R.id.iv_time);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);

         type = getIntent().getStringExtra("type");
         pic = getIntent().getStringExtra("pic");
         id = getIntent().getStringExtra("id");
         title = getIntent().getStringExtra("title");
         price = getIntent().getStringExtra("price");
         jifen = getIntent().getStringExtra("jifen");
         user = getIntent().getStringExtra("user");
         time = getIntent().getStringExtra("time");
        tv_title.setText(title);
        tv_yuanjia.setText("￥"+price);
        iv_jifen.setText(jifen+"积分抽奖");
        iv_user.setText(user);
        iv_time.setText(TimeUtil.transationSysTime(Long.valueOf(time))+"后结束");
        Glide.with(this).load(pic).placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(iv_pic);


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
            case R.id.iv_jifen:
                httpChange("抽奖",id,"1","","","","");
                break;

        }
    }

    private void httpChange(String tag, String id, String number, String consignee, String address, String zip, String mobile) {
        API.getSingleton().ec(tag, Config.PublicParams.usid, id, number, consignee, address, zip, mobile, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                showCustomToast(myBean.getData());

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
                showCustomToast(msg);
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }
}
