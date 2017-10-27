package com.cnxxp.cabbagenet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

/**
 * Created by admin on 2017/6/2 0002.
 */

public class MyDialog {

    /**
     * @param context
     *            上下文
     * @param layout
     *            布局文件
     * @param theme
     *            主题
     */
    public static Dialog getDialog(Context context, View layout, int theme) {
        Dialog dialog = new Dialog(context, theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(
                android.view.WindowManager.LayoutParams.MATCH_PARENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
        return dialog;
    }

    /**
     * @param context
     *            上下文
     * @param layout
     *            布局文件
     * @param theme
     *            主题
     */
    public static Dialog getDialog_(Context context, View layout, int theme) {

        Dialog dialog = new Dialog(context, theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setGravity(Gravity.CENTER);

        // dialog.getWindow().setAttributes(layoutParams);

        dialog.getWindow().setLayout(
                android.view.WindowManager.LayoutParams.MATCH_PARENT,
                android.view.WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setContentView(layout);
        return dialog;
    }
}
