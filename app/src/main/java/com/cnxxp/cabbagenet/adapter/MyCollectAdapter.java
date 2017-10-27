package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.CollectBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.PopupwindowUtils;
import com.cnxxp.cabbagenet.viewholder.CollectHolder;

import org.json.JSONObject;

/**
 * Created by admin on 2017/6/15 0015.
 */

public class MyCollectAdapter extends BaseRecyclerAdapter<CollectBean, CollectHolder> {
    private static final String TAG = "MyCollectAdapter";
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public MyCollectAdapter(Context context) {
        super(context);
    }

    @Override
    public CollectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_collect, parent, false);
        return new CollectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CollectHolder holder, final int position) {
        final CollectBean bean = mList.get(position);
        Glide.with(mContext).load(bean.getImg()).placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(holder.ivImg);
        holder.tvDes.setText(bean.getTitle());
        holder.tvCommentNumber.setText(bean.getComments());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(holder.mView, position);
                }
            }
        });
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupwindowUtils.initPopupWindow(holder.mView, mLayoutInflater, "是否确认删除该收藏?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupwindowUtils.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupwindowUtils.delete();
                        String xid = "";
                        switch (type) {
                            case "gn":
                            case "ht":
                                xid = "1";
                                break;
                            case "sd":
                            case "gl":
                                xid = "3";
                                break;
                        }
                        CancelCollect(TAG, bean.getId(), xid, position);

                    }
                });

            }
        });
    }

    private void CancelCollect(String tag, String id, String xid, final int position) {
        API.getSingleton().setLikes(tag, id, xid, Config.PublicParams.usid, new VolleyInterface(mContext) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
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
