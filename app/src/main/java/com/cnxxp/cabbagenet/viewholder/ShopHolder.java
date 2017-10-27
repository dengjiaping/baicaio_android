package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/11 0011.
 */
public class ShopHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_content)
    public TextView tvContent;
    public View mView;

    public ShopHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
