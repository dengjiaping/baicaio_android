package com.cnxxp.cabbagenet.base;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cnxxp.cabbagenet.R;
import com.google.gson.Gson;

import butterknife.ButterKnife;
/**
 * Created by Administrator on 2017/3/13.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected Gson mGson;
    protected Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mGson = new Gson();
        initDialog();
        initViews();
        initEvents();
        initData();
        BaseApplication.getInstance().addActivity(this);
    }

    protected abstract void initViews();

    protected abstract void initEvents();

    protected abstract void initData();

    /**
     * 初始化网络加载Dialog
     */
    private void initDialog() {
        View mView = LayoutInflater.from(this).inflate(R.layout.progress_bar, null);
        mDialog = new Dialog(this, R.style.dialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setContentView(mView);
    }

    /**
     * 显示r.strings 文本
     */
    protected void showLoadDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    /**
     * 隐藏 dialog
     */
    protected void dismissLoadDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 删除dialog
     */
    protected void deleteDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action跳转界面
     **/
    protected void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示toast String 类型
     */
    protected void showCustomToast(String res) {
        View toastRoot = LayoutInflater.from(this).inflate(R.layout.toast_common, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(res);
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

    /**
     * 显示toast int 类型
     */
    protected void showCustomToast(int res) {
        View toastRoot = LayoutInflater.from(this).inflate(R.layout.toast_common, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(res);
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

    /**
     * 退出当前界面
     */
    protected void defaultFinish() {
        finish();
    }

    /**
     * title back but
     */
    public void onTitleBack(View v) {
        defaultFinish();
    }

    /**
     * 设置title 文本
     */
    protected void setTitleText(String text) {
        ((TextView) findViewById(R.id.activity_title_text)).setText(text);
    }

    /**
     * 设置title resid
     */
    protected void setTitleText(int resid) {
        ((TextView) findViewById(R.id.activity_title_text)).setText(resid);
    }

    /**
     * 设置title 更多按钮图片类型的是否显示
     */
    protected void setTitleMoreImageVisibility(int visibility) {
        ((LinearLayout) findViewById(R.id.activity_more_image_bg)).setVisibility(visibility);
    }

    /**
     * 设置title 更多按钮文本类型的是否显示
     */
    protected void setTitleMoreTextVisibility(int visibility) {
        ((LinearLayout) findViewById(R.id.activity_more_text_bg)).setVisibility(visibility);
    }

    /**
     * 设置title 更多按钮文本类型的是否显示 以及更多需要显示的文字 text
     */
    protected void setTitleMoreTextVisibility(int visibility, String text) {
        ((LinearLayout) findViewById(R.id.activity_more_text_bg)).setVisibility(visibility);
        ((TextView) findViewById(R.id.activity_more_text)).setText(text);
    }

    /**
     * 设置title 更多按钮图片类型的是否显示 以及更多需要显示的文字 resid
     */
    protected void setTitleMoreTextVisibility(int visibility, int resid) {
        ((LinearLayout) findViewById(R.id.activity_more_text_bg)).setVisibility(visibility);
        ((TextView) findViewById(R.id.activity_more_text)).setText(resid);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}