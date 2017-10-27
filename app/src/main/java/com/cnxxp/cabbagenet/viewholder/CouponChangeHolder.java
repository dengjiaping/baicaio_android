package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/12 0012.
 */
public class CouponChangeHolder extends RecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.iv_logo)
    public ImageView ivLogo;
    @Bind(R.id.tv_name)
    public TextView tvName;
    @Bind(R.id.tv_time_limit)
    public TextView tvTimeLimit;
    @Bind(R.id.tv_number)
    public TextView tvNumber;
    @Bind(R.id.tv_get)
    public TextView tvGet;
    @Bind(R.id.tv_change_number)
    public TextView tvChangeNumber;

    public CouponChangeHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
