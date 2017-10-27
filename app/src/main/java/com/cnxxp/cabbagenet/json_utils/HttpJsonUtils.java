package com.cnxxp.cabbagenet.json_utils;

import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/13.
 */

public class HttpJsonUtils {
    private volatile static HttpJsonUtils mHttpJsonUtils;
    private HttpJsonUtils() {
    }
    public static HttpJsonUtils getSingleton() {
        if (mHttpJsonUtils == null) {
            synchronized (API.class) {
                if (mHttpJsonUtils == null) {
                    mHttpJsonUtils = new HttpJsonUtils();
                }
            }
        }
        return mHttpJsonUtils;
    }

    // 动态模块
    public void dynmicJson(String TAG,String mPage,VolleyInterface volleyInterface) {
        JSONObject json = new JSONObject();
        try {
            json.put("deviceid", "601609290018");
            json.put("api", "getdrivenotice");
            json.put("page", "" + mPage);
            json.put("pagesize", "10");
            API.getSingleton().userAPI(TAG, json, volleyInterface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
