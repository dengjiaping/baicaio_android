package com.cnxxp.cabbagenet.base;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cnxxp.cabbagenet.R;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/10/17.
 */
public abstract class BaseFragment extends Fragment {

    protected Gson mGson;
    protected Dialog mDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mGson = new Gson();
        initDialog();
        initViews();
        initEvents();
        initData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void initViews();
    protected abstract void initEvents();
    protected abstract void initData();
    /**
     * 初始化网络加载Dialog
     * */
    private void initDialog(){
        View mView = LayoutInflater.from(this.getActivity()).inflate(R.layout.progress_bar,null);
        mDialog = new Dialog(this.getActivity(), R.style.dialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setContentView(mView);
    }
    /**
     *显示r.strings 文本
     */
    protected void showLoadDialog(){
        if (mDialog != null && !mDialog.isShowing()){
            mDialog.show();
        }
    }

    /**
     * 隐藏 dialog
     * */
    protected void dismissLoadDialog() {
        if (mDialog != null && mDialog.isShowing())
        {
            mDialog.dismiss();
        }
    }
    /**
     * 删除dialog
     * */
    protected void deleteDialog(){
        if (mDialog != null){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /** 通过Class跳转界面 **/
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /** 含有Bundle通过Class跳转界面 **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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
        startActivity(intent);
    }
    protected void showCustomToast(String res) {
        View toastRoot = LayoutInflater.from(this.getActivity()).inflate(R.layout.toast_common, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(res);
        Toast toast = new Toast(this.getActivity());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }
    protected void showCustomToast(int res) {
        View toastRoot = LayoutInflater.from(this.getActivity()).inflate(R.layout.toast_common, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(res);
        Toast toast = new Toast(this.getActivity());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }
}
