package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yulindyy@163.com on 2017/11/9.
 */

public class ChoujiangLastHolder extends XRecyclerView.ViewHolder{
    public View mView;
    @Bind(R.id.iv_discount_pic)
    public ImageView iv_discount_pic;
    @Bind(R.id.tv_des)
    public TextView tv_des;
    @Bind(R.id.tv_opentime)
    public TextView tv_opentime;
    @Bind(R.id.tv_zhaongjiang_user)
    public TextView tv_zhaongjiang_user;
    @Bind(R.id.tv_zhongjiang_num)
    public TextView tv_zhongjiang_num;
    @Bind(R.id.tv_suijishu)
    public TextView tv_suijishu;
    @Bind(R.id.tv_num_id)
    public TextView tv_num_id;
    @Bind(R.id.tv_num_id2)
    public TextView tv_num_id2;
    @Bind(R.id.tv_users)
    public TextView tv_users;

    public ChoujiangLastHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
