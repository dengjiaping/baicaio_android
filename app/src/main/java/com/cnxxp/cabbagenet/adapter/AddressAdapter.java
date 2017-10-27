package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.AddressBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.PopupwindowUtils;
import com.cnxxp.cabbagenet.viewholder.addressHolder;

import org.json.JSONObject;

/**
 * Created by admin on 2017/4/6 0006.
 */

public class AddressAdapter extends BaseRecyclerAdapter<AddressBean, addressHolder> {
    private static final String TAG = "AddressAdapter";

    public AddressAdapter(Context context) {
        super(context);
    }

    @Override
    public addressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_address, parent, false);
        return new addressHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final addressHolder holder, final int position) {
        final AddressBean bean = mList.get(position);
        holder.tvAddressName.setText(bean.getConsignee());
        holder.tvZip.setText(bean.getZip());
        holder.tvPhoneNumber.setText(bean.getMobile());
        holder.tvAddressDetail.setText(bean.getAddress());
        holder.addressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(holder.addressEdit, position);
                }
            }
        });
        holder.addressDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupwindowUtils.initPopupWindow(holder.mView, LayoutInflater.from(mContext), "是否删除地址?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupwindowUtils.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        httpDelete(TAG, position, bean.getId());

                        PopupwindowUtils.delete();

                    }
                });
            }
        });

    }

    private void httpDelete(String tag, final int position, String id) {
        API.getSingleton().deleteAddress(tag, Config.PublicParams.usid, id, new VolleyInterface(mContext) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("删除成功");
                mList.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {

            }

            @Override
            public void onAppKeyError() {

            }

            @Override
            public void onStateFail(String msg) {
                showCustomToast(msg);
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

}
