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
//iv_discount_pic   tv_des    tv_time   tv_status
public class MyluckHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.iv_discount_pic)
    public ImageView iv_discount_pic;
    @Bind(R.id.tv_des)
    public TextView tv_des;
    @Bind(R.id.tv_time)
    public TextView tv_time;
    @Bind(R.id.tv_status)
    public TextView tv_status;

    public MyluckHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
