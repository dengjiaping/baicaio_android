package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/5/9 0009.
 */
public class SCreenHolder extends XRecyclerView.ViewHolder {
    @Bind(R.id.tv_content)
    public TextView tvContent;
    public View mView;

    public SCreenHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
