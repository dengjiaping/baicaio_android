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

public class commentHolder extends XRecyclerView.ViewHolder {
    @Bind(R.id.iv_comment_face)
    public ImageView iv_comment_face;
    @Bind(R.id.tv_comment_name)
    public TextView tv_comment_name;
    @Bind(R.id.tv_my_comment)
    public TextView tv_my_comment;
    @Bind(R.id.iv_comment_goods_pic)
    public ImageView iv_comment_goods_pic;
    @Bind(R.id.tv_des)
    public TextView tv_des;
    @Bind(R.id.tv_price)
    public TextView tv_price;
    @Bind(R.id.tv_from)
    public TextView tv_from;
    @Bind(R.id.tv_time)
    public TextView tv_time;
    @Bind(R.id.tv_zan)
    public TextView tv_zan;
    @Bind(R.id.tv_comments)
    public TextView tv_comments;
    @Bind(R.id.tv_delete)
    public TextView tvDelete;

    public View mView;

    public commentHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
