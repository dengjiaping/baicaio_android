package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/10 0010.
 */

public class originalCommentHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.iv_comment_face)
    public ImageView ivCommentFace;
    @Bind(R.id.tv_comment_name)
    public TextView tvCommentName;
    @Bind(R.id.tv_comment_time)
    public TextView tvCommentTime;
    @Bind(R.id.tv_comment_detail)
    public TextView tvCommentDetail;
    @Bind(R.id.tv_lc)
    public TextView tvLc;
    @Bind(R.id.tv_likes)
    public TextView tvLikes;
    @Bind(R.id.commnent_like)
    public ImageView ivZan;
    @Bind(R.id.ll_other_hf)
    public LinearLayout llOtherhf;

    public originalCommentHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
