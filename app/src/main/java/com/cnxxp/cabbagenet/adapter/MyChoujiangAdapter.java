package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.LuckActivity;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.ChoujiangBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.MyluckBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.ChoujiangHolder;
import com.cnxxp.cabbagenet.viewholder.MyluckHolder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

/**
 * Created by admin on 2017/4/7 0007.
 */

public class MyChoujiangAdapter extends BaseRecyclerAdapter<MyluckBean, MyluckHolder> {
    private static final String TAG = "QuanAdapter";


    public MyChoujiangAdapter(Context context) {
        super(context);
    }

    @Override
    public MyluckHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.myluck, parent, false);
        return new MyluckHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyluckHolder holder, int position) {
        final MyluckBean bean = mList.get(position);
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putString("type", "活动进行中");
//                bundle.putString("pic", bean.getImg());
//                bundle.putString("title", bean.getTitle());
//                bundle.putString("price", bean.getPrice());
//                bundle.putString("jifen", bean.getScore());
//                bundle.putString("user", bean.getBuy_num());
//                bundle.putString("time", bean.getSign_date());
//                startActivity(LuckActivity.class, bundle);
//            }
//        });
//        holder.iv_newtoday.setVisibility(View.VISIBLE);

        holder.tv_time.setText(TimeUtil.transationSysTime(Long.valueOf(bean.getAdd_time())));
       holder.tv_status.setText(bean.getStatus());

        Glide.with(mContext).load("").placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(holder.iv_discount_pic);
//        String title = bean.getTitle().replace("<span>", "<font color='#ff4444'>");
//        title = title.replace("</span>", "</font>");
        holder.tv_des.setText(bean.getItem_name());


    }

    private void httpChange(String tag, String id, String number, String consignee, String address, String zip, String mobile) {
        API.getSingleton().ec(tag, Config.PublicParams.usid, id, number, consignee, address, zip, mobile, new VolleyInterface(mContext) {
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
