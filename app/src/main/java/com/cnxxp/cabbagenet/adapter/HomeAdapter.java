package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.GoodsDetailActivity;
import com.cnxxp.cabbagenet.activity.WebActivity;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.DiscountBean;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.homeItemHolder;
import com.cnxxp.cabbagenet.viewholder.homeTagHolder;

import static com.cnxxp.cabbagenet.utils.TimeUtil.programTimes;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class HomeAdapter extends BaseRecyclerAdapter<DiscountBean, RecyclerView.ViewHolder> {
    private int hours;
    private int day;

    public HomeAdapter(Context context) {
        super(context);

    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == (hours + 1) || position == (hours + day + 2)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        RecyclerView.ViewHolder holder = null;
        if (viewType == 0) {
            itemView = mLayoutInflater.inflate(R.layout.item_discount, parent, false);
            holder = new homeItemHolder(itemView);
        } else {
            itemView = mLayoutInflater.inflate(R.layout.item_home_tag, parent, false);
            holder = new homeTagHolder(itemView);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof homeTagHolder) {
            if (position == 0) {
                Glide.with(mContext).load(R.drawable.ic_home_hot_1).into(((homeTagHolder) holder).ivTag);
            } else if (position > 0 && position <= (hours + 1)) {
                Glide.with(mContext).load(R.drawable.ic_home_hot_2).into(((homeTagHolder) holder).ivTag);
            } else {
                Glide.with(mContext).load(R.drawable.ic_home_hot_3).into(((homeTagHolder) holder).ivTag);
            }
        } else {
            final DiscountBean bean;
            homeItemHolder homeItemHolder = (com.cnxxp.cabbagenet.viewholder.homeItemHolder) holder;
            if (position < (hours + 1)) {
                bean = mList.get(position - 1);
            } else if ((position > (hours + 1)) && (position < (hours + day + 2))) {
                bean = mList.get(position - 2);
            } else {
                bean = mList.get(position - 3);
            }
            homeItemHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("type", false);
                    bundle.putString("shopid", bean.getShopid());
                    startActivity(GoodsDetailActivity.class, bundle);
                }
            });
            homeItemHolder.tvFrom.setText(bean.getName());
            Glide.with(mContext).load(bean.getImg()).placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(homeItemHolder.ivDiscountPic);
            String title = bean.getTitle().replace("<span>", "<font color='#ff4444'>");
            title = title.replace("</span>", "</font>");
            homeItemHolder.tvDes.setText(Html.fromHtml(title));
            homeItemHolder.tvComments.setText(bean.getComments());
            homeItemHolder.tvZan.setText(bean.getZan());
            homeItemHolder.tvCollect.setText(bean.getLikes());
            if (bean.getAdd_time() != null) {
                String result = programTimes(bean.getAdd_time());
                if (result.length() > 8) {
                    result = TimeUtil.transationSysTime(Long.parseLong(result));
                    homeItemHolder.tvTime.setText(result.substring(5));
                } else {
                    homeItemHolder.tvTime.setText(result);
                }
            }

            if (bean.getPrice() != null) {
                homeItemHolder.tvPrice.setVisibility(View.VISIBLE);
                homeItemHolder.tvPrice.setText(bean.getPrice());
            }
            homeItemHolder.tvIntoweb.setOnClickListener(new View.OnClickListener() {
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

        }


    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size() + 3;
        } else {
            return 0;
        }
    }
}
