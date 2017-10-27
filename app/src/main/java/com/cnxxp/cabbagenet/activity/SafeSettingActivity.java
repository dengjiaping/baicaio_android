package com.cnxxp.cabbagenet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.BindInfo;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yuyh.library.imgsel.utils.LogUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class SafeSettingActivity extends BaseActivity {
    private static final String TAG = "SafeSettingActivity";
    private static final int RELEASE_WX_BIND = 1;
    private static final int RELEASE_QQ_BIND = 2;
    @Bind(R.id.tv_wx_state)
    TextView tvWxState;
    @Bind(R.id.tv_qq_state)
    TextView tvQqState;
    @Bind(R.id.tv_mobile)
    TextView tvMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_safe_setting);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("安全设置");
        tvQqState.setText("未绑定");
        tvWxState.setText("未绑定");

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        httpGetBind(TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(Config.PublicParams.mobile)) {
            tvMobile.setText(Config.PublicParams.mobile);
        }
    }

    @OnClick({R.id.activity_more_text_bg, R.id.rl_bind_wx, R.id.rl_bind_qq, R.id.rl_change_password, R.id.rl_check_email, R.id.rl_check_phone})
    public void onClick(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.activity_more_text_bg:
                //确认
                break;
            case R.id.rl_bind_wx:
                //绑定微信
                if (tvWxState.getText().toString().equals("已绑定")) {
                    intent = new Intent(SafeSettingActivity.this, ThreadBindActicvity.class);
                    intent.putExtra("type", "wechat");
                    startActivityForResult(intent, RELEASE_WX_BIND);
                } else {
                    LogUtils.e("调用友盟第三方登录");
                    UMLogin(SafeSettingActivity.this, SHARE_MEDIA.WEIXIN);
                }

                break;
            case R.id.rl_bind_qq:
                //绑定QQ
                if (tvQqState.getText().toString().equals("已绑定")) {
                    intent = new Intent(SafeSettingActivity.this, ThreadBindActicvity.class);
                    intent.putExtra("type", "qq");
                    startActivityForResult(intent, RELEASE_QQ_BIND);
                } else {
                    UMLogin(SafeSettingActivity.this, SHARE_MEDIA.QQ);
                }

                break;
            case R.id.rl_change_password:
                //修改登录密码
                startActivity(ResetPasswordActivity.class);
                break;
            case R.id.rl_check_email:
                //邮箱验证
                startActivity(CheckingEmailActivity.class);
                break;
            case R.id.rl_check_phone:
                //手机验证
                if (TextUtils.isEmpty(Config.PublicParams.mobile)) {
                    startActivity(CheckingPhoneActivity.class);
                } else {
                    bundle = new Bundle();
                    bundle.putString("mobile", Config.PublicParams.mobile);
                    startActivity(BindPhoneActivity.class, bundle);
                }
                break;
        }
    }

    private void httpGetBind(String tag) {
        API.getSingleton().getBind(tag, Config.PublicParams.usid, new VolleyInterface(this) {

            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<BindInfo>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<BindInfo>>>() {
                }.getType());
                for (BindInfo info : myBean.getData()) {
                    if (info.getType().equals("wechat")) {
                        tvWxState.setText("已绑定");
                        continue;
                    }
                    if (info.getType().equals("qq")) {
                        tvQqState.setText("已绑定");
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RELEASE_QQ_BIND && resultCode == RESULT_OK) {
            tvQqState.setText("未绑定");
        }
        if (requestCode == RELEASE_WX_BIND && resultCode == RESULT_OK) {
            tvWxState.setText("未绑定");
        }

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private void UMLogin(Context context, SHARE_MEDIA share_media) {
        UMShareAPI.get(context).getPlatformInfo(SafeSettingActivity.this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.e("onstart:" + share_media);
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                LogUtils.e("oncomplete:" + share_media);
                String openid;
                String accessToken;
                String type;
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
                httpBind(TAG, Config.PublicParams.usid, type, openid, accessToken);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    private void httpBind(String tag, String userid, final String type, String keyid, String accessToken) {
        API.getSingleton().bind(tag, userid, type, keyid, accessToken, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("绑定成功");
                if (type.equals("wechat")) {
                    tvWxState.setText("已绑定");
                } else {
                    tvQqState.setText("已绑定");
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

}
