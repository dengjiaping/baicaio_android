package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseRecyclerAdapter;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.PrivateLetterDetailBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.viewholder.PrivateLetterDetailHolder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by admin on 2017/8/28 0028.
 */
public class PrivateLetterDetailsAdapter extends BaseRecyclerAdapter<PrivateLetterDetailBean, PrivateLetterDetailHolder> {
    private static final String TAG = "PrivateLetterDetailsAdapter";

    public PrivateLetterDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getFrom_id().equals(Config.PublicParams.usid)) {
            //我发出的
            return 1;
        } else {
            //我收到的
            return 2;
        }
    }

    @Override
    public PrivateLetterDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(viewType == 1 ? R.layout.item_right : R.layout.item_left, parent, false);
        return new PrivateLetterDetailHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PrivateLetterDetailHolder holder, int position) {
        PrivateLetterDetailBean bean = mList.get(position);
        holder.tvName.setText(bean.getFrom_name());
        holder.tvContent.setText(bean.getInfo());
        httpGetHead(TAG, bean.getFrom_id(), holder.ivHead);
        String result = TimeUtil.programTimes(bean.getAdd_time());
        if (result.length() > 8) {
            result = TimeUtil.transationSysTime(Long.parseLong(result));
        }
        holder.tvTime.setText(result);
       
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
