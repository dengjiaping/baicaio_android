package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.ArticleDetailActivity;
import com.cnxxp.cabbagenet.activity.GoodsDetailActivity;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.CommentBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.EmojiUtils;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.commentHolder;

import org.json.JSONObject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.cnxxp.cabbagenet.utils.TimeUtil.programTimes;

/**
 * Created by admin on 2017/4/6 0006.
 */

public class MyCommentAdapter extends BaseRecyclerAdapter<CommentBean, commentHolder> {
    private static final String TAG = "MyCommentAdapter";

    public MyCommentAdapter(Context context) {
        super(context);
    }

    @Override
    public commentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_my_comment, parent, false);
        return new commentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(commentHolder holder, final int position) {
        final CommentBean commentBean = mList.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (commentBean.getInfo().contains("|android")) {
            EmojiUtils.setEmojiText(commentBean.getInfo().replace("|android", ""), holder.tv_my_comment, mContext);
        } else if (commentBean.getInfo().contains("|iphone")) {
            EmojiUtils.setEmojiText(commentBean.getInfo().replace("|iphone", ""), holder.tv_my_comment, mContext);
        } else {
            EmojiUtils.setEmojiText(commentBean.getInfo(), holder.tv_my_comment, mContext);
        }
        if (commentBean.getAdd_time() != null) {
            String result = programTimes(commentBean.getAdd_time());
            if (result.length() > 8) {
                result = TimeUtil.transationSysTime(Long.parseLong(result));
                holder.tv_time.setText(result.substring(5));
            } else {
                holder.tv_time.setText(result);
            }
        }
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpDeleteComment(TAG, commentBean.getCommentid(), position);
            }
        });
        holder.tv_des.setText(commentBean.getTitle());
        Glide.with(mContext).load(commentBean.getImg()).placeholder(R.mipmap.ic_square_pic).error(R.mipmap.ic_square_pic).into(holder.iv_comment_goods_pic);
        Glide.with(mContext).load(Config.PublicParams.portrait).bitmapTransform(new CropCircleTransformation(mContext)).placeholder(R.mipmap.ic_person_head).error(R.mipmap.ic_person_head).into(holder.iv_comment_face);
        holder.tv_comment_name.setText(Config.PublicParams.uname);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                switch (commentBean.getXid()) {
                    case "1":
                        bundle.putBoolean("type", false);
                        bundle.putString("shopid", commentBean.getItemid());
                        startActivity(GoodsDetailActivity.class, bundle);
                        break;
                    case "2":
                        break;
                    case "3":
                        bundle.putString("articleid", commentBean.getItemid());
                        startActivity(ArticleDetailActivity.class, bundle);
                        break;
                }
            }
        });
    }

    private void httpDeleteComment(String tag, String id, final int position) {
        API.getSingleton().deleteComment(tag, id, new VolleyInterface(mContext) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast(msg);
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
