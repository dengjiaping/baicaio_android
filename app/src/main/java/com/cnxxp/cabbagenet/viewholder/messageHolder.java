package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.cnxxp.cabbagenet.R;

/**
 * Created by admin on 2017/4/11 0011.
 */

public class messageHolder extends XRecyclerView.ViewHolder {

    @Bind(R.id.iv_message_face)
    public ImageView ivMessageFace;
    @Bind(R.id.tv_message_type)
    public TextView tvMessageType;
    @Bind(R.id.tv_message_time)
    public TextView tvMessageTime;
    @Bind(R.id.tv_message_detail)
    public TextView tvMessageDetail;

    public View mView;

    public messageHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
