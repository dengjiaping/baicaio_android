package com.cnxxp.cabbagenet.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cnxxp.cabbagenet.R;
import com.yuyh.library.imgsel.ImageLoader;

import java.util.ArrayList;

/**
 * Created by admin on 2017/2/22 0022.
 */

public class photoChangeAdapter extends BaseAdapter {
    public ArrayList<String> selectedPicture = null;

    private ImageLoader mLoader;
    private Context mContext;


    public photoChangeAdapter(Context context, ImageLoader loader) {
        this.mContext = context;
        this.selectedPicture = new ArrayList<>();
        this.mLoader = loader;
        selectedPicture.add("add");
    }

    @Override
    public int getCount() {
        return selectedPicture.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            convertView = View.inflate(mContext, R.layout.item_add_picture, null);
            holder = new ViewHolder();

            holder.iv_select = (ImageView) convertView
                    .findViewById(R.id.iv_service_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String path = selectedPicture.get(position);
        if (!TextUtils.isEmpty(path) && path.equals("add")) {
            holder.iv_select.setImageResource(R.mipmap.ic_tianjiatupian);
        } else {
            mLoader.displayImage(mContext, path, holder.iv_select);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_select;
    }
}
