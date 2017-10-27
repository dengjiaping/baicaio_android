package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.OriginalCommentBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.db.SearchDBHelper;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.EmojiUtils;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.originalCommentHolder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by admin on 2017/4/10 0010.
 */

public class OriginalCommentAdapter extends BaseRecyclerAdapter<OriginalCommentBean, originalCommentHolder> {
    private static final String TAG = "OriginalCommentAdapter";
    private SearchDBHelper dbHelper = null;

    public OriginalCommentAdapter(Context context) {
        super(context);
    }


    @Override
    public originalCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new originalCommentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final originalCommentHolder holder, final int position) {
        final OriginalCommentBean bean = mList.get(position);
        if (bean.getInfo().contains("|android")) {
            EmojiUtils.setEmojiText(bean.getInfo().replace("|android",""), holder.tvCommentDetail, mContext);
        } else if (bean.getInfo().contains("|iphone")) {
            EmojiUtils.setEmojiText( bean.getInfo().replace("|iphone",""), holder.tvCommentDetail, mContext);
        } else {
            EmojiUtils.setEmojiText(bean.getInfo(), holder.tvCommentDetail, mContext);
        }
        holder.tvCommentName.setText(bean.getUname());
        String result = TimeUtil.programTimes(bean.getAdd_time());
        if (result.length() > 8) {
            result = TimeUtil.transationSysTime(Long.parseLong(result));
        }
        holder.tvCommentTime.setText(result);
        holder.tvLc.setText(bean.getLc() + "楼");
        holder.tvLikes.setText(bean.getZan());
        holder.llOtherhf.removeAllViews();
        if (bean.getList() != null && bean.getList().size() > 0) {
            holder.llOtherhf.setVisibility(View.VISIBLE);
            for (int i = 0; i < bean.getList().size(); i++) {
                LinearLayout ll = (LinearLayout) mLayoutInflater.inflate(R.layout.comment_hf_item, null);
                TextView tvContent = (TextView) ll.findViewById(R.id.tv_content);
                if (bean.getList().get(i).getInfo().contains("|android")) {
                    EmojiUtils.setEmojiText(bean.getList().get(i).getUname() + " 回复:" + bean.getList().get(i).getInfo().replace("|android",""),tvContent,mContext);
                } else if (bean.getList().get(i).getInfo().contains("|iphone")) {
                    EmojiUtils.setEmojiText(bean.getList().get(i).getUname() + " 回复:" + bean.getList().get(i).getInfo().replace("|iphone",""),tvContent,mContext);
                } else {
                    EmojiUtils.setEmojiText(bean.getList().get(i).getUname() + " 回复:" + bean.getList().get(i).getInfo(), tvContent, mContext);
                }
                //  tvContent.setText(bean.getList().get(i).getUname() + " 回复:" + bean.getList().get(i).getInfo().split("|")[0]);
                holder.llOtherhf.addView(ll);
            }
        } else {
            holder.llOtherhf.setVisibility(View.GONE);
        }
        holder.ivZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper == null) {
                    dbHelper = SearchDBHelper.getInstance(mContext);
                }
                if (dbHelper.HasZanData(Config.PublicParams.usid, bean.getId())) {
                    showCustomToast("您已经点赞过");
                } else {
                    httpZan(bean.getId(), position);
                    dbHelper.insertZan(Config.PublicParams.usid, bean.getId());
                }
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
        httpGetHead(TAG, bean.getUid(), holder.ivCommentFace);

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

    private void httpZan(String commentid, final int position) {
        API.getSingleton().zan(TAG, commentid, "3", new VolleyInterface(mContext) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                mList.get(position).setZan((Integer.parseInt(mList.get(position).getZan()) + 1) + "");
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
