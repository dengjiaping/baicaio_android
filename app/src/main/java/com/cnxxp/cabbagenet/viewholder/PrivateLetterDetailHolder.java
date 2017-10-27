package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

/**
 * Created by admin on 2017/8/28 0028.
 */
public class PrivateLetterDetailHolder extends RecyclerView.ViewHolder {
    public View mView;
    public ImageView ivHead;
    public TextView tvName;
    public TextView tvTime;
    public TextView tvContent;

    public PrivateLetterDetailHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ivHead = (ImageView) mView.findViewById(R.id.iv_head);
        tvName = (TextView) mView.findViewById(R.id.tv_name);
        tvTime = (TextView) mView.findViewById(R.id.tv_time);
        tvContent = (TextView) mView.findViewById(R.id.tv_content);
    }
}
