package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/3 0003.
 */
public class AttentionHolder extends RecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.tv_tag)
    public TextView tvTag;
    @Bind(R.id.btn_del)
    public TextView tvDel;
    @Bind(R.id.ll_push)
    public LinearLayout llPush;
    @Bind(R.id.iv_push)
    public ImageView ivPush;

    public AttentionHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
