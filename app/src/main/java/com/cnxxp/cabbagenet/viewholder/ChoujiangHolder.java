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

public class ChoujiangHolder  extends XRecyclerView.ViewHolder{
    public View mView;
    @Bind(R.id.iv_discount_pic)
    public ImageView iv_discount_pic;
    @Bind(R.id.iv_newtoday)
    public ImageView iv_newtoday;
    @Bind(R.id.tv_des)
    public TextView tv_des;
    @Bind(R.id.tv_opentime)
    public TextView tv_opentime;
    @Bind(R.id.tv_price)
    public TextView tv_price;
    @Bind(R.id.tv_yuanjia)
    public TextView tv_yuanjia;
    @Bind(R.id.tv_users)
    public TextView tv_users;
    @Bind(R.id.tv_endtime)
    public TextView tv_endtime;
    @Bind(R.id.iv_needjifen)
    public TextView iv_needjifen;



    public ChoujiangHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
