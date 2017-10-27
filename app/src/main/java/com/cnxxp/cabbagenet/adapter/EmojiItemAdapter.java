package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.Emoji;

/**
 * Created by admin on 2017/8/30 0030.
 */

public class EmojiItemAdapter extends BaseRecyclerAdapter<Emoji, EmojiItemHolder> {
    public EmojiItemAdapter(Context context) {
        super(context);
    }

    @Override
    public EmojiItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.emoji_item, parent, false);
        return new EmojiItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EmojiItemHolder holder, final int position) {
        Glide.with(mContext).load(mList.get(position).getImageUri()).into(holder.ivEmoji);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(holder.mView, position);
                }
            }
        });
    }
}
