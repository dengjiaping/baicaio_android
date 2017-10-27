package com.cnxxp.cabbagenet.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class PopupwindowUtils {
    public static PopupWindow mPopupWindow;

    public static void initPopupWindow(View v, LayoutInflater layoutInflater, String title, View.OnClickListener cancel , View.OnClickListener confirm){
        View mView = layoutInflater.inflate(R.layout.pop_out,null);
        TextView outTitle = (TextView) mView.findViewById(R.id.title);
        outTitle.setText(title);
        Button outCancel = (Button) mView.findViewById(R.id.pop_out_cancel);
        outCancel.setOnClickListener(cancel);
        Button outConfirm = (Button) mView.findViewById(R.id.pop_out_confirm);
        outConfirm.setOnClickListener(confirm);
        mPopupWindow = new PopupWindow(mView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    public static void dismiss(){
        mPopupWindow.dismiss();
    }
    public static void delete(){
        mPopupWindow.dismiss();
        mPopupWindow = null;
    }

}