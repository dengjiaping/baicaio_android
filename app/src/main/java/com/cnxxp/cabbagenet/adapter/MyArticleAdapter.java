package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.ArticleDetailActivity;
import com.cnxxp.cabbagenet.activity.GoodsDetailActivity;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.MyArticleBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.MyArticleHolder;

/**
 * Created by admin on 2017/4/6 0006.
 */

public class MyArticleAdapter extends BaseRecyclerAdapter<MyArticleBean, MyArticleHolder> {
    private static final String TAG = "OriginalAdapter";
    private boolean isBao;
    private int screenWidth;

    public MyArticleAdapter(Context context, int screenWidth) {
        super(context);
        isBao = false;
        this.screenWidth = screenWidth;
    }

    public void setIsBao(Boolean isBao) {
        this.isBao = isBao;
    }


    @Override
    public MyArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_my_article, parent, false);
        return new MyArticleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyArticleHolder holder, int position) {
        final MyArticleBean bean = mList.get(position);
        Glide.with(mContext).load(bean.getImg()).error(R.drawable.ic_original_pic).into(holder.ivOriginalItem);
        holder.tvOriginalDetail.setText(bean.getIntro());
        holder.tvZan.setText(bean.getZan());
        holder.tvOriginalTitle.setText(bean.getTitle());
        holder.tvComments.setText(bean.getComments());
        holder.tvLikes.setText(bean.getLikes());
        if (bean.getAdd_time().length() != 1) {
            String result = TimeUtil.programTimes(bean.getAdd_time());
            if (result.length() > 8) {
                result = TimeUtil.transationSysTime(Long.parseLong(result));
            }
            holder.tvOriginalTime.setText(result);
        }
        if (bean.getStatus() != null) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            switch (bean.getStatus()) {
                case "0":
                    holder.tvStatus.setText("待审核");
                    break;
                case "4":
                case "1":
                    holder.tvStatus.setText("已通过");
                    holder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            if (isBao) {
                                bundle.putString("shopid", bean.getId());
                                startActivity(GoodsDetailActivity.class, bundle);
                            } else {
                                bundle.putString("articleid", bean.getId());
                                startActivity(ArticleDetailActivity.class, bundle);
                            }


                        }
                    });
                    break;
                case "2":
                    holder.tvStatus.setText("草稿");
                    break;
                case "3":
                    holder.tvStatus.setText("退回");
                    break;
            }
        } else {
            holder.tvStatus.setVisibility(View.GONE);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    if (isBao) {
                        bundle.putBoolean("type",true);
                        bundle.putString("shopid", bean.getId());
                        startActivity(GoodsDetailActivity.class, bundle);
                    } else {
                        bundle.putString("articleid", bean.getId());
                        startActivity(ArticleDetailActivity.class, bundle);
                    }


                }
            });
        }
        Glide.with(mContext).load(Config.PublicParams.portrait).into(holder.ivOriginalFace);
    }


}
