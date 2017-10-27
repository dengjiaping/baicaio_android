package com.cnxxp.cabbagenet.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.UseCouponActivity;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.TickBean;
import com.cnxxp.cabbagenet.viewholder.couponHolder;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class CouponAdapter extends BaseRecyclerAdapter<TickBean, couponHolder> {
    private String type;

    public CouponAdapter(Context context) {
        super(context);
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public couponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_my_coupon, parent, false);
        return new couponHolder(itemView);
    }

    @Override
    public void onBindViewHolder(couponHolder holder, int position) {
        final TickBean bean = mList.get(position);
        holder.tvTickname.setText(bean.getName());
        holder.tvTime.setText(bean.getEnd_time());
        if (bean.getTk_code() != null) {
            holder.tvTickcode.setText("券码:" + bean.getTk_code()+"   (长按可复制券码)");
        }
        if (bean.getTk_psw() != null) {
            holder.tvPwd.setText("密码:" + bean.getTk_psw());
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", bean.getLjdz());
                startActivity(UseCouponActivity.class, bundle);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData  
                ClipData mClipData = ClipData.newPlainText("Label", bean.getTk_code());
// 将ClipData内容放到系统剪贴板里。  
                cm.setPrimaryClip(mClipData);
                showCustomToast("券码复制成功");
                return true;
            }
        });

    }


}
