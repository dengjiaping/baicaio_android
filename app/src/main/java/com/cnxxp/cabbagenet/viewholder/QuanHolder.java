package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by yulindyy@163.com on 2017/11/8.
 */

public class QuanHolder extends XRecyclerView.ViewHolder{
    @Bind(R.id.iv_discount_pic)
    public ImageView iv_discount_pic;
    @Bind(R.id.tv_des)
    public TextView tv_des;
    @Bind(R.id.tv_price)
    public TextView tv_price;
    @Bind(R.id.tv_from)
    public TextView tv_from;
    @Bind(R.id.tv_users)
    public TextView tv_users;
    public View mView;


    public QuanHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}