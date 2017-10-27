package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/5/22 0022.
 */


public class homeTagHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.iv_tag)
    public ImageView ivTag;

    public homeTagHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
