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

public class originalHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.iv_original_item)
    public ImageView ivOriginalItem;
    @Bind(R.id.tv_original_title)
    public TextView tvOriginalTitle;
    @Bind(R.id.tv_original_detail)
    public TextView tvOriginalDetail;
    @Bind(R.id.iv_original_face)
    public ImageView ivOriginalFace;
    @Bind(R.id.tv_original_name)
    public TextView tvOriginalName;
    @Bind(R.id.tv_original_time)
    public TextView tvOriginalTime;
    @Bind(R.id.tv_zan)
    public TextView tvZan;
    @Bind(R.id.tv_comments)
    public TextView tvComments;
    @Bind(R.id.tv_likes)
    public TextView tvLikes;


    public originalHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
