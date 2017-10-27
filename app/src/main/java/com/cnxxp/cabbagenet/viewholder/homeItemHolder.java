package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class homeItemHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.iv_discount_pic)
    public ImageView ivDiscountPic;
    @Bind(R.id.tv_des)
    public TextView tvDes;
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.tv_from)
    public TextView tvFrom;
    @Bind(R.id.tv_time)
    public TextView tvTime;
    @Bind(R.id.tv_comments)
    public TextView tvComments;
    @Bind(R.id.tv_zan)
    public TextView tvZan;
    @Bind(R.id.tv_intoweb)
    public TextView tvIntoweb;
    @Bind(R.id.tv_collect)
    public TextView tvCollect;


    public homeItemHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
