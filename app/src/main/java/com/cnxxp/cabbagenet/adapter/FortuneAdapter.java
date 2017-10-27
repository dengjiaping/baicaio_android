package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.myGradeBean;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.fortuneItemHolder;

/**
 * Created by admin on 2017/4/6 0006.
 */

public class FortuneAdapter extends BaseRecyclerAdapter<myGradeBean.ListBean, fortuneItemHolder> {
    private String type;

    public FortuneAdapter(Context context) {
        super(context);
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public fortuneItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_my_fortune_detail, parent, false);
        return new fortuneItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(fortuneItemHolder holder, int position) {
        myGradeBean.ListBean bean = mList.get(position);
        holder.tvScoreFrom.setText(bean.getAction());
        holder.tvTime.setText(TimeUtil.transationSysTime(Long.parseLong(bean.getAdd_time())));
        switch (type) {
            case "score":
                holder.tvDetail.setText(bean.getScore());
                break;
            case "offer":
                holder.tvDetail.setText(bean.getOffer());
                break;
            case "exp":
                holder.tvDetail.setText(bean.getExp());
                break;
            case "coin":
                holder.tvDetail.setText(bean.getCoin());
                break;
        }


    }
}
