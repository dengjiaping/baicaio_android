package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.ScreenBean;
import com.cnxxp.cabbagenet.viewholder.SCreenHolder;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/9 0009.
 */

public class ScreenAdapter extends BaseRecyclerAdapter<ScreenBean, SCreenHolder> {
    public String[] cids;
    private int select;

    public ScreenAdapter(Context context) {
        super(context);
        select = -1;
        cids = new String[]{"333", "334", "336", "116", "338",
                "335", "1", "115", "339", "340", "50", "102",
                "114", "337", "341", "342"};
        mList = new ArrayList<>();
        mList.add(new ScreenBean("个护化妆"));
        mList.add(new ScreenBean("保健养生"));
        mList.add(new ScreenBean("图书音像"));
        mList.add(new ScreenBean("数码家电"));
        mList.add(new ScreenBean("旅游休闲"));
        mList.add(new ScreenBean("日用百货"));
        mList.add(new ScreenBean("服装鞋帽"));
        mList.add(new ScreenBean("母婴玩具"));
        mList.add(new ScreenBean("电影资讯"));
        mList.add(new ScreenBean("眼镜配饰"));
        mList.add(new ScreenBean("箱包手袋"));
        mList.add(new ScreenBean("运动户外"));
        mList.add(new ScreenBean("钟表首饰"));
        mList.add(new ScreenBean("食品酒饮"));
        mList.add(new ScreenBean("杂七杂八"));
        mList.add(new ScreenBean("活动公告"));
    }

    @Override
    public SCreenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_activity_screen,
                parent, false);
        return new SCreenHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SCreenHolder holder, final int position) {
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
        holder.tvContent.setText(mList.get(position).getTitle());
    }


}
