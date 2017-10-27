package com.cnxxp.cabbagenet.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cnxxp.cabbagenet.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class BaseBAdapter<T> extends BaseAdapter {

    public Context mContext;
    public LayoutInflater mLayoutInflater;
    public Gson mGson;
    public Dialog mDialog;
    public List<T> mList;

    public BaseBAdapter(Context context){
        this.mContext = context;
        mGson = new Gson();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        initDialog();
    }
    public BaseBAdapter(Context context, List<T> list) {
        this(context);
        this.mList = list;
    }

    public List<T> getList(){
        return mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
    public void setListData(List<T> list) { // 添加数据
        this.mList = list;
        notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        if (list != null && list.size() > 0) {
            if (this.mList == null) {
                this.mList = new ArrayList<T>();
            }
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }
    public void removeItem(int position) {
        if (mList != null) {
            mList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void clearList() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    /** 通过Class跳转界面 **/
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /** 通过Action跳转界面 **/
    protected void startActivity(String action) {
        startActivity(action, null);
    }

    /** 含有Bundle通过Action跳转界面 **/
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    /** 含有Bundle通过Class跳转界面 **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    // 网络加载
    public void initDialog() {

        View mView = LayoutInflater.from(mContext).inflate(
                R.layout.load_dialog, null);
        mDialog = new Dialog(mContext, R.style.dialog);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setContentView(mView);
    }
    public void showLoadDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }
    public void dismissLoadDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
    protected void showCustomToast(String res) {
        View toastRoot = LayoutInflater.from(mContext).inflate(R.layout.toast_common, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(res);
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }
    protected void showCustomToast(int res) {
        View toastRoot = LayoutInflater.from(mContext).inflate(R.layout.toast_common, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(res);
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }
}
