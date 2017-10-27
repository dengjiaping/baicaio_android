package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.ScoreChangedBean;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.scoreChangedHolder;

/**
 * Created by admin on 2017/4/7 0007.
 */

public class ScoreChangedAdapter extends BaseRecyclerAdapter<ScoreChangedBean, scoreChangedHolder> {
    public ScoreChangedAdapter(Context context) {
        super(context);
    }

    @Override
    public scoreChangedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_score_changed, parent, false);
        return new scoreChangedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(scoreChangedHolder holder, int position) {
        ScoreChangedBean bean = mList.get(position);
        holder.tvTitle.setText(bean.getItem_name());
        String result = TimeUtil.programTimes(bean.getAdd_time());
        if (result.length() > 8) {
            result = TimeUtil.transationSysTime(Long.parseLong(result));
        }
        holder.tvTime.setText(result);
        holder.tvNumber.setText("数量:" + bean.getItem_num());
        holder.tvRemark.setText(bean.getRemark());
    }


}
