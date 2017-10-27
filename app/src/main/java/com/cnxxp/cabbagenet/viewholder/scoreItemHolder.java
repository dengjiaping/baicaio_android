package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/7 0007.
 */

public class scoreItemHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.tv_score_des)
    public TextView tvScoreDes;
    @Bind(R.id.tv_score_price)
    public TextView tvScorePrice;
    @Bind(R.id.tv_score_change)
    public TextView tvScoreChange;
    @Bind(R.id.iv_img)
    public ImageView ivImg;
    @Bind(R.id.tv_coin)
    public TextView tvCoin;

    public scoreItemHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
