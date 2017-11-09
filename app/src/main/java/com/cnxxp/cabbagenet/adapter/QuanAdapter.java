package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.GoodsDetailActivity;
import com.cnxxp.cabbagenet.activity.WebActivity;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.DiscountBean;
import com.cnxxp.cabbagenet.bean.QuanBean;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.QuanHolder;
import com.cnxxp.cabbagenet.viewholder.discountHolder;

import static com.cnxxp.cabbagenet.utils.TimeUtil.programTimes;

/**
 * Created by admin on 2017/4/7 0007.
 */

public class QuanAdapter extends BaseRecyclerAdapter<QuanBean, QuanHolder> {
    private static final String TAG = "QuanAdapter";


    public QuanAdapter(Context context) {
        super(context);
    }

    @Override
    public QuanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_quan, parent, false);
        return new QuanHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuanHolder holder, int position) {
        final QuanBean bean = mList.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "0");
                if (bean.getCoupon_url() != null) {
                    bundle.putString("link",bean.getCoupon_url());
                } else {
                    bundle.putString("link", "");
                }
                startActivity(WebActivity.class, bundle);
            }
        });
        holder.tv_from.setText("领"+bean.getCoupon()+"元券");
        Glide.with(mContext).load(bean.getImg()).placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(holder.iv_discount_pic);
//        String title = bean.getTitle().replace("<span>", "<font color='#ff4444'>");
//        title = title.replace("</span>", "</font>");
        holder.tv_des.setText(bean.getTitle());
        holder.tv_price.setText(bean.getPrice()+"");
       holder.tv_users.setText(bean.getVolume()+"人领用");

    }




}
