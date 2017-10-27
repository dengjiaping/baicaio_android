package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/6/15 0015.
 */
public class PublishHolder extends RecyclerView.ViewHolder {
    public View mView;

    public PublishHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
