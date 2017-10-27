package com.cnxxp.cabbagenet.httputils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.cnxxp.cabbagenet.base.BaseApplication;
import com.cnxxp.cabbagenet.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyHttpRequest {
    private volatile static VolleyHttpRequest httpRequest;

    private VolleyHttpRequest() {

    }

    public static VolleyHttpRequest getSingleton() {
        if (httpRequest == null) {
            synchronized (VolleyHttpRequest.class) {
                if (httpRequest == null) {
                    httpRequest = new VolleyHttpRequest();
                }
            }
        }
        return httpRequest;
    }

    private JSONObject setParams(JSONObject params) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appkey", Config.PublicParams.appkey);
            jsonObject.put("version", Config.PublicParams.version);
            jsonObject.put("from", Config.PublicParams.from);
            jsonObject.put("reqBody", params);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void RequstJsonPost(String tag, String URL, JSONObject jsonObject,
                               VolleyInterface volleyInterface) throws JSONException {

        Log.e(tag, "HTTP_URL = " + URL);
        Log.e(tag, "HTTP_JSON = " + setParams(jsonObject));

        JsonRequest<JSONObject> req = new JsonObjectRequest(
                Request.Method.POST, URL, setParams(jsonObject),
                volleyInterface.listener(), volleyInterface.errorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        BaseApplication instance = BaseApplication.getInstance();
        instance.addToRequestQueue(req, tag);
        setTimeOut(req, 10000);
    }

    /**
     * 设置超时时间
     */
    public <T> void setTimeOut(Request<T> request, int time) {
        request.setRetryPolicy(new DefaultRetryPolicy(time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void HttpRequest(String URL, Listener<JSONObject> listener,
                            ErrorListener errorListener, String tag) {
        JsonObjectRequest req = new JsonObjectRequest(URL, null, listener,
                errorListener);
        BaseApplication.getInstance().addToRequestQueue(req, tag);
    }
}
