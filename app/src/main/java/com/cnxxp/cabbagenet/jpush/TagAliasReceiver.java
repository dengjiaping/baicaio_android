package com.cnxxp.cabbagenet.jpush;

import android.content.Context;

import com.yuyh.library.imgsel.utils.LogUtils;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * Created by admin on 2017/8/15 0015.
 */

public class TagAliasReceiver extends JPushMessageReceiver {
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        LogUtils.e("-----1-----:"+jPushMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
        LogUtils.e("-----2-----:"+jPushMessage);
     
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);
        LogUtils.e("-----3-----:"+jPushMessage);
    }
}
