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
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.discountHolder;
import com.cnxxp.cabbagenet.viewholder.homeItemHolder;

import static com.cnxxp.cabbagenet.utils.TimeUtil.programTimes;

/**
 * Created by admin on 2017/4/7 0007.
 */

public class DiscountAdapter extends BaseRecyclerAdapter<DiscountBean, discountHolder> {
    private static final String TAG = "DiscountAdapter";


    public DiscountAdapter(Context context) {
        super(context);
    }

    @Override
    public discountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_discount, parent, false);
        return new discountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final discountHolder holder, int position) {
        final DiscountBean bean = mList.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("type",false);
                bundle.putString("shopid", bean.getShopid());
                startActivity(GoodsDetailActivity.class, bundle);
            }
        });
        holder.tvFrom.setText(bean.getName());
        Glide.with(mContext).load(bean.getImg()).placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(holder.ivDiscountPic);
        String title = bean.getTitle().replace("<span>", "<font color='#ff4444'>");
        title = title.replace("</span>", "</font>");
        holder.tvDes.setText(Html.fromHtml(title));
        holder.tvComments.setText(bean.getComments());
        holder.tvLikes.setText(bean.getZan());
        String result = programTimes(bean.getAdd_time());
        if (result.length() > 8) {
            result = TimeUtil.transationSysTime(Long.parseLong(result));
            holder.tvTime.setText(result.substring(5));
        } else {
            holder.tvTime.setText(result);
        }
        holder.tvCollect.setText(bean.getLikes());

        holder.tvIntoweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (bean.getGo_link() != null) {
                    bundle.putString("link", bean.getGo_link().getLink());
                } else {
                    bundle.putString("link", "");
                }
                startActivity(WebActivity.class, bundle);
            }
        });
        if (bean.getPrice() != null) {
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(bean.getPrice());
        }
    }


}
