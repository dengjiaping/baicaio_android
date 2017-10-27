package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.ArticleDetailActivity;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.OriginalBean;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.originalHolder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by admin on 2017/4/6 0006.
 */

public class OriginalAdapter extends BaseRecyclerAdapter<OriginalBean, originalHolder> {
    private static final String TAG = "OriginalAdapter";

    private int screenWidth;

    public OriginalAdapter(Context context, int screenWidth) {
        super(context);

        this.screenWidth = screenWidth;
    }


    @Override
    public originalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_original, parent, false);
        return new originalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(originalHolder holder, int position) {
        final OriginalBean bean = mList.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("articleid", bean.getArticleid());
                startActivity(ArticleDetailActivity.class, bundle);
            }
        });
        Glide.with(mContext).load(bean.getImg()).error(R.drawable.ic_original_pic).into(holder.ivOriginalItem);
        holder.tvOriginalDetail.setText(bean.getIntro());
        holder.tvZan.setText(bean.getZan());
        holder.tvOriginalTitle.setText(bean.getTitle());
        holder.tvComments.setText(bean.getComments());
        holder.tvLikes.setText(bean.getLikes());
        if (bean.getAdd_time().length() != 1) {
            String result = TimeUtil.programTimes(bean.getAdd_time());
            if (result.length() > 8) {
                result = TimeUtil.transationSysTime(Long.parseLong(result));
            }
            holder.tvOriginalTime.setText(result);
        }
        holder.tvOriginalName.setText(bean.getAuthor());
        httpGetHead(TAG, bean.getUid(), holder.ivOriginalFace);
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

}
