package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.ScoreDetailActivity;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.ScoreItemBean;
import com.cnxxp.cabbagenet.viewholder.scoreItemHolder;

/**
 * Created by admin on 2017/4/7 0007.
 */

public class ScoreItemAdapter extends BaseRecyclerAdapter<ScoreItemBean, scoreItemHolder> {
    public ScoreItemAdapter(Context context) {
        super(context);
    }

    @Override
    public scoreItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_score, null);
        return new scoreItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final scoreItemHolder holder, final int position) {
        final ScoreItemBean bean = mList.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("scoreid", bean.getId());
                startActivity(ScoreDetailActivity.class, bundle);
            }
        });
        holder.tvCoin.setText(bean.getCoin());
        holder.tvScorePrice.setText(bean.getScore());
        holder.tvScoreDes.setText(bean.getTitle());
        holder.tvScoreChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(holder.tvScoreChange, position);
                }
            }
        });
        Glide.with(mContext).load(bean.getImg()).placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(holder.ivImg);
    }


}
