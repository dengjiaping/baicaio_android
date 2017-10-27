package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/6/15 0015.
 */
public class CollectHolder extends RecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.iv_img)
    public ImageView ivImg;
    @Bind(R.id.tv_des)
    public TextView tvDes;
    @Bind(R.id.tv_comment_number)
    public TextView tvCommentNumber;
    @Bind(R.id.btn_del)
    public Button btnDel;

    public CollectHolder(View itemView) {

        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
