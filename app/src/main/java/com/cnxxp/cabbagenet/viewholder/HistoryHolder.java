package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/5/22 0022.
 */
public class HistoryHolder extends RecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.tv_history)
    public TextView tvHistory;
    @Bind(R.id.iv_delete)
    public ImageView ivDelete;

    public HistoryHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
