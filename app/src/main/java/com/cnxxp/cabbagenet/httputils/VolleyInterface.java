
package com.cnxxp.cabbagenet.httputils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.yuyh.library.imgsel.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class VolleyInterface {

    private static final String TAG = "VolleyInterface";

    private Context context;

    public VolleyInterface(Context context) {
        this.context = context;
    }

    /**
     * 请求成功数据不为空 10001
     */
    public abstract void onStateSuccess(String msg, JSONObject object);

    /**
     * 请求成功数据为空 10002
     */
    public abstract void onStateSuccessDataNull(String msg);

    /**
     * 请求成功数据 请求完成
     */
    public abstract void onStateFinish();

    /**
     * APPKEY 校验错误
     */
    public abstract void onAppKeyError();

    //    /**
//     * 用户校验失败
//     * */
//    public abstract void onUserError(String msg);
//    /** 请求失败 20001 */
    public abstract void onStateFail(String msg);
//    /** 请求失败 appKey验证失败30001 */
//    public abstract void onStateAppkeyFail();
//    /** 请求错误 40001 */
//    public abstract void onStateError();
//    /** 请求错误 50001 */

    /**
     * 请求错误
     */
    public abstract void onError(VolleyError error, String msg);


    public Listener<JSONObject> listener() {
        return new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                LogUtils.e("onResponse = " + object.toString());
                try {
                    int status  = object.getInt("state");
                    String msg = object.getString("msg");
                    int check = object.optInt("check");
                    switch (status) {
                            
                        case 10001://请求成功数据不为空 10001
                            onStateSuccess(msg, object);
                            break;
                        case 10000:
                        case 10002://请求成功数据为空 10002
                            onStateSuccessDataNull(msg);
                            break;
                        case 20001://请求失败 20001
                            onStateFail(msg);
                            break;
                        case 30001://请求失败 appKey验证失败30001
                            showCustomToast(msg);
                            onAppKeyError();
                            break;
                        case 40001://请求错误 40001
                            onError(null, msg);
                            showCustomToast(msg);
                            break;
                        case 50001://请求失败 appKey验证失败30001
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    onError(null, e.getMessage());
                    showCustomToast(e.getMessage());
                } finally {
                    onStateFinish();
                }
            }
        };
    }

    public ErrorListener errorListener() {
        return new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
                onError(error, null);
                showCustomToast(error);
                onStateFinish();
            }
        };
    }

    private void showCustomToast(String res) {
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_common, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(res);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

    private void showCustomToast(VolleyError error) {
        showCustomToast(VolleyErrorHelper.getMessage(error, context));
    }
}
