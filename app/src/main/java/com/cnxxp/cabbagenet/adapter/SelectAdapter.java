package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.AddressBean;
import com.cnxxp.cabbagenet.viewholder.SelectHolder;

import java.util.List;

/**
 * Created by admin on 2017/6/29 0029.
 */
public class SelectAdapter extends BaseRecyclerAdapter<AddressBean, SelectHolder> {
    private boolean hasCheck;
    private int selectIndex;

    public int getSelectIndex() {
        return selectIndex;
    }

    public SelectAdapter(Context context) {
        super(context);
        hasCheck = false;
        selectIndex = -1;
    }

    @Override
    public void setListData(List<AddressBean> list) {
        this.mList = list;
        for (AddressBean bean : list) {
            bean.setNormaladdress(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public SelectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_select_address, parent, false);
        return new SelectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SelectHolder holder, final int position) {
        AddressBean bean = mList.get(position);
        if (bean.isNormaladdress()) {
            holder.addCb.setChecked(true);
        } else {
            holder.addCb.setChecked(false);
        }
        holder.tvAddressDetail.setText(bean.getAddress());
        holder.tvAddressName.setText(bean.getConsignee());
        holder.tvZip.setText(bean.getZip());
        holder.tvPhoneNumber.setText(bean.getMobile());
        holder.addCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (holder.addCb.isChecked()) {
                    holder.addCb.setChecked(false);
                    mList.get(selectIndex).setNormaladdress(false);
                    selectIndex = -1;
                } else {
                    if (hasCheck) {
                        mList.get(selectIndex).setNormaladdress(false);
                        mList.get(position).setNormaladdress(true);
                        selectIndex = position;
                        notifyDataSetChanged();
                    } else {
                        mList.get(position).setNormaladdress(true);
                        selectIndex = position;
                        notifyDataSetChanged();
                    }

                }*/
                if (hasCheck) {
                    if (selectIndex == position) {
                        mList.get(position).setNormaladdress(false);
                        holder.addCb.setChecked(false);
                        selectIndex = -1;
                        hasCheck = false;
                    } else {
                        mList.get(selectIndex).setNormaladdress(false);
                        mList.get(position).setNormaladdress(true);
                        selectIndex = position;
                        notifyDataSetChanged();
                    }
                } else {
                    mList.get(position).setNormaladdress(true);
                    selectIndex = position;
                    holder.addCb.setChecked(true);
                    hasCheck = true;
                }

            }
        });
        super.onBindViewHolder(holder, position);
    }
}
