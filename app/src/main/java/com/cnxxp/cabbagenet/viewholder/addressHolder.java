package com.cnxxp.cabbagenet.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/6 0006.
 */

public class addressHolder extends XRecyclerView.ViewHolder {
    public View mView;
    @Bind(R.id.tv_address_name)
    public TextView tvAddressName;
    @Bind(R.id.tv_zip)
    public TextView tvZip;
    @Bind(R.id.tv_phone_number)
    public TextView tvPhoneNumber;
    @Bind(R.id.tv_address_detail)
    public TextView tvAddressDetail;
    @Bind(R.id.cb_address)
    public CheckBox cbAddress;
    @Bind(R.id.ll_address_edit)
    public LinearLayout addressEdit;
    @Bind(R.id.ll_address_dele)
    public LinearLayout addressDele;

    public addressHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, mView);
    }


}
