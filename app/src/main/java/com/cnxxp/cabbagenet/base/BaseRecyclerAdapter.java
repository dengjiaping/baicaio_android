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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.cnxxp.cabbagenet.xrecyclerview.callback.RecyclerViewItemClickListener;
import com.cnxxp.cabbagenet.xrecyclerview.callback.RecyclerViewItemLongClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerAdapter<T,V extends XRecyclerView.ViewHolder> extends XRecyclerView.Adapter<V> {

	protected int selectedPosition = 0;
	public List<T> mList;
	public Context mContext;
	public Gson mGson;
	public LayoutInflater mLayoutInflater;
	public Dialog mDialog;

	public RecyclerViewItemLongClickListener mLongClickListener;
	public RecyclerViewItemClickListener mItemListener;

	/**
	 * 设置Item点击监听
	 * @param listener
	 */
	public void setOnItemClickListener(RecyclerViewItemClickListener listener){
		this.mItemListener = listener;
	}

	public void setOnItemLongClickListener(RecyclerViewItemLongClickListener listener){
		this.mLongClickListener = listener;
	}

	public BaseRecyclerAdapter(Context context) {
		this.mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
		initDialog();
		mGson = new Gson();
	}

	public BaseRecyclerAdapter(Context context, List<T> list) {
		this(context);
		this.mList = list;
	}

	public void remove(int position) {
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

	public void setSelectedPosition(int position) {
		selectedPosition = position;
		notifyDataSetChanged();
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

	@Override
	public V onCreateViewHolder(ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(V holder, int position) {
	}

	@Override
	public int getItemCount() {
		return mList == null?0:mList.size();
	}

	/**
	 * 根据宽度自动调整(参照图片本身尺寸)高度<br/>
	 * 需要开启 android:adjustViewBounds="true"
	 * @param context
	 * @param view
	 * @param imageViewWidth    指定的宽度，<1时则取屏幕宽度
	 */
	protected void autoSizeImageViewHeight(Context context, ImageView view,
										   int imageViewWidth) {
		if (context == null || view == null) {
			return;
		}
		if (imageViewWidth < 1) {
			imageViewWidth = context.getResources().getDisplayMetrics().widthPixels;
		}
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		lp.width = imageViewWidth;
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		view.setLayoutParams(lp);
		view.setMaxWidth(imageViewWidth);
		view.setMaxHeight(imageViewWidth * 5);
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
