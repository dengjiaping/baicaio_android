package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.view.ContainsEmojiEditText;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;


public class OtherRegisterActivity extends BaseActivity {
    private static final String TAG = "OhterRegisterActivity";
    @Bind(R.id.et_username)
    ContainsEmojiEditText etUsername;
    @Bind(R.id.et_password)
    ContainsEmojiEditText etPassword;
    @Bind(R.id.et_enter_password)
    ContainsEmojiEditText etEnterPassword;
    @Bind(R.id.cb_check)
    CheckBox cbCheck;
    private String phone;
    private String msmcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_other_register);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("手机注册信息完善");

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        phone = getIntent().getExtras().getString("phone");
        msmcode = getIntent().getExtras().getString("msmcode");
    }

    @OnClick({R.id.tv_regist_submit, R.id.tv_cabbage_rule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_regist_submit:
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String secondPassword = etEnterPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    showCustomToast("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(secondPassword)) {
                    showCustomToast("密码不能为空");
                    return;
                }
                if (!password.equals(secondPassword)) {
                    showCustomToast("两次密码不一致,请重新输入");
                    return;
                }
                if (!cbCheck.isChecked()) {
                    showCustomToast("您需要同意白菜哦平台协议");
                    return;
                }
                httpRegister(TAG, username, password);
                break;
            case R.id.tv_cabbage_rule:
                break;
        }
    }

    private void httpRegister(final String tag, String username, String password) {
        API.getSingleton().register(tag, username, password, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<Integer> mBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<Integer>>() {
                }.getType());
                httpBindPhone(tag, mBean.getData() + "", phone, msmcode);
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
                showCustomToast(msg);

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void httpBindPhone(String tag, String uid, String phone, String smsCode) {
        API.getSingleton().bindMobile(tag, phone, smsCode, uid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("注册成功");
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onStateFinish() {
                setResult(RESULT_OK,getIntent());
                finish();
            }

            @Override
            public void onAppKeyError() {

            }

            @Override
            public void onStateFail(String msg) {
                showCustomToast("注册成功,绑定手机号失败");
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }
}
