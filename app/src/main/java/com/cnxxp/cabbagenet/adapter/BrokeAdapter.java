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
import com.cnxxp.cabbagenet.viewholder.brokeHolder;

import static com.cnxxp.cabbagenet.utils.TimeUtil.programTimes;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class BrokeAdapter extends BaseRecyclerAdapter<DiscountBean, brokeHolder> {
    public BrokeAdapter(Context context) {
        super(context);
    }

    @Override
    public brokeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_hot_broke, parent, false);
        return new brokeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(brokeHolder holder, int position) {
        final DiscountBean bean = mList.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("type", true);
                bundle.putString("shopid", bean.getShopid());
                startActivity(GoodsDetailActivity.class, bundle);
            }
        });
        Glide.with(mContext).load(bean.getImg()).placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(holder.ivPic);
        String title = bean.getTitle().replace("<span>", "<font color='#ff4444'>");
        title = title.replace("</span>", "</font>");
        holder.tvTitle.setText(Html.fromHtml(title));
        holder.tvFrom.setText(bean.getName());
        holder.tvLikes.setText(bean.getZan());
        holder.tvCollect.setText(bean.getLikes());
        String result = programTimes(bean.getAdd_time());
        if (result.length() > 8) {
            result = TimeUtil.transationSysTime(Long.parseLong(result));
            holder.tvTime.setText(result.substring(5));
        } else {
            holder.tvTime.setText(result);
        }
        holder.tvGolink.setOnClickListener(new View.OnClickListener() {
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
        super.onBindViewHolder(holder, position);
    }


}
