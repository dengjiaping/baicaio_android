package com.cnxxp.cabbagenet.base;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.cnxxp.cabbagenet.config.Config;
import com.umeng.socialize.PlatformConfig;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/3/13.
 */

public class BaseApplication extends Application {
    public static final String TAG = "VolleyPatterns";
    private static BaseApplication mApplication;
    private RequestQueue mRequestQueue;
    private List<Activity> mActivityList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        setPublicParameVersion();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        PlatformConfig.setWeixin("wxa441c5596b23d32e", "dc782dbd654189c593800896ae3658c8");
        PlatformConfig.setQQZone("1106133519", "Rkg3ZCQ3DsTOUz3u");
        PlatformConfig.setSinaWeibo("1029124434", "7b108f0f0dbb40b5d1a86d0b45b3a35c", "http://open.weibo.com/apps/1029124434/privilege/oauth");
    }

    /**
     * 获取 BaseApplication 实例
     */
    public static BaseApplication getInstance() {
        return mApplication;
    }

    /**
     * 设置工程版本号给接口使用
     */
    private void setPublicParameVersion() {
        try {
            Config.PublicParams.version = getVersion();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取工程版本号
     */
    private String getVersion() throws PackageManager.NameNotFoundException {
        PackageManager pm = this.getPackageManager();
        PackageInfo info = pm.getPackageInfo(this.getPackageName(), 0);
        return info.versionName;
    }

    //添加到网络队列
    public void addToRequestQueue(JsonRequest<JSONObject> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    //添加网络请求队列
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        getRequestQueue().add(req);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    // 通过各Request对象的Tag属性取消请求
    public void cancelPendingRequests(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    // 通过各Request对象的obj属性取消请求
    public void cancelPendingRequests(Object obj) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(obj);
        }
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public List<Activity> getmActivityList() {
        return mActivityList;
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : mActivityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0); // 关闭JVM
    }
}
