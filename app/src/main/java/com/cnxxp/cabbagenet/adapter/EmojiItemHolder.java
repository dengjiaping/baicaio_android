package com.cnxxp.cabbagenet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cnxxp.cabbagenet.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/30 0030.
 */
public class EmojiItemHolder extends RecyclerView.ViewHolder{
    public View mView;
    @Bind(R.id.iv_emoji)
    public ImageView ivEmoji;
    public EmojiItemHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this,mView);
    }
}
