package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/7 0007.
 */

public class scoreChangedHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.tv_title)
    public TextView tvTitle;
    @Bind(R.id.tv_number)
    public TextView tvNumber;
    @Bind(R.id.tv_time)
    public TextView tvTime;
    @Bind(R.id.tv_remark)
    public TextView tvRemark;


    public scoreChangedHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
