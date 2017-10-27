package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.view.ContainsEmojiEditText;

import org.json.JSONObject;

import butterknife.Bind;

public class PhoneFindPwdActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "PhoneFindPwdActivity";
    @Bind(R.id.et_password)
    ContainsEmojiEditText etPwd;
    @Bind(R.id.et_enter_password)
    ContainsEmojiEditText etPwdAgain;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    private String phone;
    private String smsCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_phone_find_pwd);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("手机找回密码");


    }

    @Override
    protected void initEvents() {
        tvSubmit.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        phone = getIntent().getExtras().getString("phone");
        smsCode = getIntent().getExtras().getString("smscode");

    }

    private void httpRsetPassword(String tag, String phone, String smscode, String password) {
        API.getSingleton().phoneFindPwd(tag, phone, smscode, password, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("修改成功");
                finish();
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);
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

    @Override
    public void onClick(View v) {
        String pwd = etPwd.getText().toString().trim();
        String pwdAgain = etPwdAgain.getText().toString().trim();
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
            showCustomToast("密码不能为空");
            return;
        }
        if (!pwd.equals(pwdAgain)) {
            showCustomToast("两次密码不一致");
            return;
        }
        httpRsetPassword(TAG, phone, smsCode, pwd);
    }
}
