package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/28 0028.
 */
public class PrivateLetterHolder extends RecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.iv_head)
    public ImageView ivHead;
    @Bind(R.id.tv_letter_people)
    public TextView tvLetterPeople;
    @Bind(R.id.tv_letter_num)
    public TextView tvLetterNum;
    @Bind(R.id.tv_a_to_b)
    public TextView tvAToB;
    @Bind(R.id.tv_time)
    public TextView tvTime;

    public PrivateLetterHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
