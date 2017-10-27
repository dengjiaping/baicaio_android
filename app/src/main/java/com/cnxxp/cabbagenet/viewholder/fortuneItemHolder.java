package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/6 0006.
 */

public class fortuneItemHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.tv_score_from)
    public TextView tvScoreFrom;
    @Bind(R.id.tv_time)
    public TextView tvTime;
    @Bind(R.id.tv_detail)
    public TextView tvDetail;

    public fortuneItemHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
