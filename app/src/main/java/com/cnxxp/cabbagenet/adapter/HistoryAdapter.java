package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.db.SearchDBHelper;
import com.cnxxp.cabbagenet.viewholder.HistoryHolder;

/**
 * Created by admin on 2017/5/22 0022.
 */
public class HistoryAdapter extends BaseRecyclerAdapter<String, HistoryHolder> {
    private SearchDBHelper dbHelper;

    public HistoryAdapter(Context context) {
        super(context);
        dbHelper = SearchDBHelper.getInstance(mContext);
    }


    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.item_sousuo, parent, false);
        return new HistoryHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final HistoryHolder holder, final int position) {
        holder.tvHistory.setText(mList.get(position));
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteHistory(mList.get(position));
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(holder.mView, position);
                }
            }
        });
    }

}
