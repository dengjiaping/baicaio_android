package com.cnxxp.cabbagenet.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cnxxp.cabbagenet.activity.ArticleDetailActivity;
import com.cnxxp.cabbagenet.activity.GoodsDetailActivity;
import com.cnxxp.cabbagenet.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by admin on 2017/6/5 0005.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        //Log.d(TAG, "o nReceive - " + intent.getAction() + ", extras: " + AndroidUtil.printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");
            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String message = bundle.getString(JPushInterface.EXTRA_ALERT);
            Intent intents = new Intent();
            if (bundle.getString(JPushInterface.EXTRA_EXTRA) != null) {
                try {
                    String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    JSONObject json = new JSONObject(extras);
                    Bundle dataBundle = new Bundle();
                    if (json.getString("type").equals("shop")) {
                        intents.setClass(context, GoodsDetailActivity.class);
                        dataBundle.putString("shopid", json.getString("data"));
                        intents.putExtras(dataBundle);
                    } else {
                        intents.setClass(context, ArticleDetailActivity.class);
                        dataBundle.putString("articleid", json.getString("data"));
                        intents.putExtras(dataBundle);
                    }
                    intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                intents.setClass(context, MainActivity.class);
                intents.addCategory(Intent.CATEGORY_LAUNCHER);
                intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况
                // 关键的一步，设置启动模式，两种情况
            }
            context.startActivity(intents);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }


    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

    }

}
