package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.AttentionBean;
import com.cnxxp.cabbagenet.viewholder.AttentionHolder;

/**
 * Created by admin on 2017/8/3 0003.
 */
public class AttentionAdapter extends BaseRecyclerAdapter<AttentionBean, AttentionHolder> {
    private AttentInterface mAttentInterface;

    public interface AttentInterface {
        public void ItemClick(String tag);

        public void DelClick(String id, int position, String tag);

        public void pushDel(String id, int position, String tag);

        public void pushCreate(String id, int position, String tag);
    }

    public AttentionAdapter(Context context) {
        super(context);
    }

    public void setmAttentInterface(AttentInterface attentInterface) {
        this.mAttentInterface = attentInterface;
    }

    @Override
    public AttentionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.attention_item, parent, false);
        return new AttentionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AttentionHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final AttentionBean bean = mList.get(position);
        holder.tvTag.setText(bean.getTag());
        holder.ivPush.setSelected(bean.getP_sign() == 1 ? true : false);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAttentInterface != null) {
                    mAttentInterface.ItemClick(bean.getTag());
                }
            }
        });
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAttentInterface != null) {
                    mAttentInterface.DelClick(bean.getId(), position, bean.getTag());
                }
            }
        });
        holder.llPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttentInterface != null) {
                    if (holder.ivPush.isSelected()) {
                        mAttentInterface.pushDel(bean.getId(), position, bean.getTag());
                    } else {
                        mAttentInterface.pushCreate(bean.getId(), position, bean.getTag());
                    }
                }
            }
        });
    }
}
