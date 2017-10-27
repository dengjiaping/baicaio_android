package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/6/15 0015.
 */
public class ScoreExchangeHolder extends RecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.tv_name)
    public TextView tvName;
    @Bind(R.id.tv_time)
    public TextView tvTime;

    public ScoreExchangeHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
