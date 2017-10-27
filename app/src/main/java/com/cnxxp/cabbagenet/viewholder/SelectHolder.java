package com.cnxxp.cabbagenet.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/6/29 0029.
 */
public class SelectHolder extends RecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.add_cb)
    public CheckBox addCb;
    @Bind(R.id.tv_address_name)
    public TextView tvAddressName;
    @Bind(R.id.tv_zip)
    public TextView tvZip;
    @Bind(R.id.tv_phone_number)
    public TextView tvPhoneNumber;
    @Bind(R.id.tv_address_detail)
    public TextView tvAddressDetail;

    public SelectHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }
}
