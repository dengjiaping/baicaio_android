package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.ScoreDetailBean;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.ScoreExchangeHolder;

/**
 * Created by admin on 2017/6/15 0015.
 */
public class ExchangeAdapter extends BaseRecyclerAdapter<ScoreDetailBean.ListBean, ScoreExchangeHolder> {
    public ExchangeAdapter(Context context) {
        super(context);
    }

    @Override
    public ScoreExchangeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_score_exchange, parent, false);
        return new ScoreExchangeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScoreExchangeHolder holder, int position) {
        ScoreDetailBean.ListBean bean = mList.get(position);
        holder.tvName.setText(bean.getUname());
        holder.tvTime.setText(TimeUtil.transationSysTime(Long.parseLong(bean.getAdd_time())));
    }
}
