package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.LoginActivity;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.CouponChangeBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.viewholder.CouponChangeHolder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

/**
 * Created by admin on 2017/7/12 0012.
 */
public class CouponChangeAdapter extends BaseRecyclerAdapter<CouponChangeBean, CouponChangeHolder> {
    private static final String TAG = "CouponChangeAdapter";

    public CouponChangeAdapter(Context context) {
        super(context);
    }

    @Override
    public CouponChangeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_coupon_list, parent, false);
        return new CouponChangeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CouponChangeHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final CouponChangeBean bean = mList.get(position);
        if (TextUtils.isEmpty(bean.getImg())) {
            Glide.with(mContext).load(R.mipmap.ic_square_pic).into(holder.ivLogo);
        } else {
            if (bean.getImg().startsWith("http")) {
                Glide.with(mContext).load(bean.getImg()).error(R.mipmap.ic_square_pic).into(holder.ivLogo);
            } else {
                Glide.with(mContext).load("http://img.baicaio.com/data/upload/item_orig/" + bean.getImg()).error(R.mipmap.ic_square_pic).into(holder.ivLogo);
            }
        }
        holder.tvName.setText(bean.getName());
        holder.tvTimeLimit.setText("截止时间:" + bean.getEnd_time());
        holder.tvNumber.setText("剩余:" + bean.getSy() + "张");
        holder.tvGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Config.PublicParams.usid)) {
                    startActivity(LoginActivity.class);
                } else {

                    httpTickDh(TAG, bean.getId());
                }
            }
        });
        holder.tvChangeNumber.setText("已领:" + bean.getYl());
    }

    private void httpTickDh(String tag, String tickid) {
        API.getSingleton().tickDh(tag, Config.PublicParams.usid, tickid, new VolleyInterface(mContext) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());

                showCustomToast(myBean.getData());
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);
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
