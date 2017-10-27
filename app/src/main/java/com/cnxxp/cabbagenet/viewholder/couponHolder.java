package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class couponHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.tv_tickname)
    public TextView tvTickname;
    @Bind(R.id.tv_tickcode)
    public TextView tvTickcode;
    @Bind(R.id.tv_pwd)
    public TextView tvPwd;
    @Bind(R.id.tv_time)
    public TextView tvTime;
    @Bind(R.id.ll_bg)
    public LinearLayout llBg;

    public couponHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
