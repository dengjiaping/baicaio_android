package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class brokeHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.iv_pic)
    public ImageView ivPic;
    @Bind(R.id.tv_title)
    public TextView tvTitle;
    @Bind(R.id.tv_from)
    public TextView tvFrom;
    @Bind(R.id.tv_golink)
    public TextView tvGolink;
    @Bind(R.id.tv_likes)
    public TextView tvLikes;
    @Bind(R.id.tv_collect)
    public TextView tvCollect;
    @Bind(R.id.tv_time)
    public TextView tvTime;

    public brokeHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
