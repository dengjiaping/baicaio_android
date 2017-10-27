package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.PrivateLetterBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.PopupwindowUtils;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.PrivateLetterHolder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by admin on 2017/8/28 0028.
 */
public class PrivateLetterAdapter extends BaseRecyclerAdapter<PrivateLetterBean, PrivateLetterHolder> {
    private static final String TAG = "PrivateLetterAdapter";

    public PrivateLetterAdapter(Context context) {
        super(context);
    }

    @Override
    public PrivateLetterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_private_letter, parent, false);
        return new PrivateLetterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PrivateLetterHolder holder, final int position) {
        final PrivateLetterBean bean = mList.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(holder.mView, position);
                }
            }
        });
        httpGetHead(TAG, bean.getTa_id(), holder.ivHead);
        if (bean.getFrom_id().equals(Config.PublicParams.usid)) {
            holder.tvAToB.setText("我对Ta说:" + bean.getInfo());
        } else {
            holder.tvAToB.setText("Ta对我说:" + bean.getInfo());
        }
        holder.tvLetterNum.setText("共" + bean.getNum() + "条私信");
        String result = TimeUtil.programTimes(bean.getAdd_time());
        if (result.length() > 8) {
            result = TimeUtil.transationSysTime(Long.parseLong(result));
        }
        holder.tvTime.setText(result);
        holder.tvLetterPeople.setText(bean.getTa_name());
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupwindowUtils.initPopupWindow(v, LayoutInflater.from(mContext), "是否删除与他的所有对话?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupwindowUtils.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PopupwindowUtils.delete();
                        DeleteLetter(bean.getFtid(), position);

                    }
                });
                return true;
            }
        });
        super.onBindViewHolder(holder, position);
    }


    private void httpGetHead(String tag, String userid, final ImageView view) {
        API.getSingleton().getImage(tag, userid, new VolleyInterface(mContext) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                if (myBean.getData().startsWith("http")) {
                    Glide.with(mContext).load(myBean.getData()).bitmapTransform(new CropCircleTransformation(mContext)).placeholder(R.mipmap.ic_person_head).error(R.mipmap.ic_person_head).into(view);
                } else {
                    Glide.with(mContext).load("http://www.baicaio.com" + myBean.getData()).bitmapTransform(new CropCircleTransformation(mContext)).placeholder(R.mipmap.ic_person_head).error(R.mipmap.ic_person_head).into(view);
                }
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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void DeleteLetter(String ftid, final int position) {
        API.getSingleton().deletePrivateletter(TAG, Config.PublicParams.usid, ftid, new VolleyInterface(mContext) {
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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }
}
