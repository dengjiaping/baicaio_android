package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.MessageBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.PopupwindowUtils;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.messageHolder;

import org.json.JSONObject;

import static com.cnxxp.cabbagenet.utils.TimeUtil.programTimes;

/**
 * Created by admin on 2017/4/11 0011.
 */

public class MessageAdapter extends BaseRecyclerAdapter<MessageBean, messageHolder> {
    private static final String TAG = "MessageAdapter";

    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public messageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_my_message, parent, false);
        return new messageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final messageHolder holder, final int position) {
        final MessageBean msgBean = mList.get(position);

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupwindowUtils.initPopupWindow(holder.mView, LayoutInflater.from(mContext), "是否删除该消息?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupwindowUtils.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PopupwindowUtils.delete();
                        httpDeleteMessage(TAG, position);
                    }
                });

                return true;
            }
        });
        String result = programTimes(msgBean.getAdd_time());
        if (result.length() > 8) {
            result = TimeUtil.transationSysTime(Long.parseLong(result));
            holder.tvMessageTime.setText(result.substring(5));
        } else {
            holder.tvMessageTime.setText(result);
        }
        holder.tvMessageDetail.setText(msgBean.getInfo());
        super.onBindViewHolder(holder, position);
    }

    private void httpDeleteMessage(String tag, final int position) {
        API.getSingleton().delMsg(tag, mList.get(position).getId(), Config.PublicParams.usid, new VolleyInterface(mContext) {
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
