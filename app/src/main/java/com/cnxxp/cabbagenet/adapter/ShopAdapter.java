package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.ShopBean;
import com.cnxxp.cabbagenet.viewholder.ShopHolder;

/**
 * Created by admin on 2017/7/11 0011.
 */
public class ShopAdapter extends BaseRecyclerAdapter<ShopBean, ShopHolder> {
    public ShopAdapter(Context context) {
        super(context);
        select = -1;
    }

    private int select;

    @Override
    public ShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_shop, parent, false);
        return new ShopHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShopHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.tvContent.setText(mList.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(holder.mView, position);
                    holder.tvContent.setSelected(true);
                    if (select != -1) {
                        mList.get(select).setSelected(false);
                    }
                    mList.get(position).setSelected(true);
                    select = position;
                    notifyDataSetChanged();
                }
            }
        });
        if (mList.get(position).isSelected()) {
            holder.tvContent.setSelected(true);
        } else {
            holder.tvContent.setSelected(false);
        }
    }
}
