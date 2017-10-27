package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.PopupwindowUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class ThreadBindActicvity extends BaseActivity {
    private static final String TAG = "ThreadBindActivity";
    @Bind(R.id.iv_thread_logo)
    ImageView ivThreadLogo;
    @Bind(R.id.tv_release_bind)
    TextView tvReleaseBind;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thread_bind_acticvity);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        type = getIntent().getStringExtra("type");
        switch (type) {
            case "wechat":
                Glide.with(this).load(R.mipmap.ic_bind_wx).into(ivThreadLogo);
                setTitleText("绑定微信");
                break;
            case "qq":
                Glide.with(this).load(R.mipmap.ic_bind_qq).into(ivThreadLogo);
                setTitleText("绑定QQ");
                break;
        }
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_release_bind)
    public void onClick() {
        PopupwindowUtils.initPopupWindow(getWindow().getDecorView(), LayoutInflater.from(this), "是否解除绑定?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupwindowUtils.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupwindowUtils.delete();
                httpReleaseBind(TAG, type);
            }
        });
    }


    private void httpReleaseBind(String tag, final String type) {
        API.getSingleton().removeBind(tag, type, Config.PublicParams.usid, new VolleyInterface(this) {

            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("解绑成功");
                deleteOauth(type);
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

    private void deleteOauth(String type) {
        SHARE_MEDIA share_media = type.equals("qq") ? SHARE_MEDIA.QQ : SHARE_MEDIA.WEIXIN;
        UMShareAPI.get(this).deleteOauth(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                setResult(RESULT_OK, getIntent());
                finish();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }
}
