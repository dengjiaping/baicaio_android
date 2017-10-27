package com.cnxxp.cabbagenet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AttentionBean;
import com.cnxxp.cabbagenet.bean.LoginBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.SPUtils;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yuyh.library.imgsel.utils.LogUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.cnxxp.cabbagenet.config.Config.PublicParams.usid;

public class LoginActivity extends BaseActivity {
    public static final String TAG = "LoginActivity";
    private static final int REQUEST_PERFECT_INFO = 2;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_password)
    EditText etPassword;

    private String type;
    private String openid;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("登录");

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_login, R.id.tv_regist, R.id.tv_forget_password, R.id.ll_login_qq_bg, R.id.ll_login_wx_bg, R.id.ll_login_wb_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                //登录
                String phone = etPhone.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    showCustomToast("用户名或密码不能为空");
                    return;
                }
                showLoadDialog();
                API.getSingleton().login(TAG, phone, password, new VolleyInterface(this) {
                    @Override
                    public void onStateSuccess(String msg, JSONObject object) {
                        MyBean<LoginBean> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<LoginBean>>() {
                        }.getType());
                        usid = myBean.getData().getUserid();
                        Config.PublicParams.uname = myBean.getData().getUsername();
                        SPUtils.put(LoginActivity.this, "usid", usid);
                        SPUtils.put(LoginActivity.this, "uname", myBean.getData().getUsername());
                        SPUtils.put(LoginActivity.this, "signature", System.currentTimeMillis() + "");
                        Config.PublicParams.signature = System.currentTimeMillis() + "";
                        showCustomToast("登录成功");
                        httpTags();
                        setAlias();
                        setResult(RESULT_OK, getIntent());
                        finish();
                    }

                    @Override
                    public void onStateSuccessDataNull(String msg) {

                    }

                    @Override
                    public void onStateFinish() {
                        dismissLoadDialog();
                    }

                    @Override
                    public void onAppKeyError() {

                    }

                    @Override
                    public void onStateFail(String msg) {
                        showCustomToast("用户名或密码错误");
                    }

                    @Override
                    public void onError(VolleyError error, String msg) {

                    }
                });
                break;
            case R.id.tv_regist:
                startActivity(RegistAcitivity.class);
                //注册
                break;
            case R.id.tv_forget_password:
                startActivity(ForgetPwdActivity.class);
                //忘记密码
                break;
            case R.id.ll_login_qq_bg:
                UMLogin(this, SHARE_MEDIA.QQ);
                break;
            case R.id.ll_login_wx_bg:
                UMLogin(this, SHARE_MEDIA.WEIXIN);
                break;
            case R.id.ll_login_wb_bg:
                UMLogin(this, SHARE_MEDIA.SINA);
                break;
        }
    }

    private void UMLogin(Context context, SHARE_MEDIA share_media) {
        UMShareAPI.get(context).getPlatformInfo(LoginActivity.this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.e("开始授权...");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                if (share_media == SHARE_MEDIA.QQ) {
                    //QQ授权
                    openid = map.get("unionid");
                    accessToken = map.get("accessToken");
                    type = "qq";

                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                    //微信授权
                    openid = map.get("unionid");
                    accessToken = map.get("access_token");
                    type = "wechat";
                } else {
                    //新浪授权
                    openid = map.get("uid");
                    accessToken = map.get("access_token");
                    type = "sina";
                }
                httpCheckBind(TAG, openid, type);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERFECT_INFO && resultCode == RESULT_OK) {
            String userid = Config.PublicParams.usid;
            httpBind(TAG, userid, type, openid, accessToken);
        }
    }

    private void httpCheckBind(String tag, String keyid, String type) {
        API.getSingleton().checkBind(tag, type, keyid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                if (object.toString().contains("未绑定社交平台")) {
                    Intent intent = new Intent(LoginActivity.this, PerfectInfoActivity.class);
                    startActivityForResult(intent, REQUEST_PERFECT_INFO);
                } else {
                    MyBean<LoginBean> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<LoginBean>>() {
                    }.getType());
                    usid = myBean.getData().getUserid();
                    Config.PublicParams.uname = myBean.getData().getUsername();
                    SPUtils.put(LoginActivity.this, "usid", usid);
                    SPUtils.put(LoginActivity.this, "uname", myBean.getData().getUsername());
                    httpTags();
                    setAlias();
                    showCustomToast("登录成功");
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {

            }

            @Override
            public void onAppKeyError() {

            }

            @Override
            public void onStateFail(String msg) {

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void httpBind(String tag, String userid, String type, String keyid, String accessToken) {
        API.getSingleton().bind(tag, userid, type, keyid, accessToken, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                showCustomToast(myBean.getData());
                SPUtils.put(LoginActivity.this, "usid", Config.PublicParams.usid);
                SPUtils.put(LoginActivity.this, "uname", Config.PublicParams.uname);
                SPUtils.put(LoginActivity.this, "signature", System.currentTimeMillis() + "");
                Config.PublicParams.signature = System.currentTimeMillis() + "";
                httpTags();
                setAlias();
                setResult(RESULT_OK, getIntent());
                finish();
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {

            }

            @Override
            public void onAppKeyError() {

            }

            @Override
            public void onStateFail(String msg) {

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void setAlias() {
        String alias = (String) SPUtils.get(this, "jpush", "");
        if (!TextUtils.isEmpty(alias)) {
            return;
        }
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, Config.PublicParams.usid));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };


    private void httpTags() {
        API.getSingleton().notifyTagByUser(TAG, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<AttentionBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<AttentionBean>>>() {
                }.getType());
                Config.PublicParams.u_attention = new HashMap<String, AttentionBean>();
                HashSet<String> set = new HashSet<String>();
                for (int i = 0; i < myBean.getData().size(); i++) {

                    Config.PublicParams.u_attention.put(myBean.getData().get(i).getTag().toLowerCase(), myBean.getData().get(i));
                    if (myBean.getData().get(i).getP_sign() == 1) {
                        set.add(myBean.getData().get(i).getTag());
                    }
                }
                JPushInterface.addTags(getApplicationContext(), 1, set);
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                Config.PublicParams.u_attention = new HashMap<String, AttentionBean>();
            }

            @Override
            public void onStateFinish() {

            }

            @Override
            public void onAppKeyError() {

            }

            @Override
            public void onStateFail(String msg) {

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

}
