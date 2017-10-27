package com.cnxxp.cabbagenet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.base.BaseApplication;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {
    private static final String TAG = "ResetPasswordActivity";
    @Bind(R.id.et_enter_phone)
    EditText etEnterPhone;
    @Bind(R.id.et_enter_code)
    EditText etEnterCode;
    @Bind(R.id.et_enter_password)
    EditText etEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reset_password);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("重置密码");
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_change_pwd_submit)
    public void onClick(View view) {
        String password = etEnterPhone.getText().toString().trim();
        String newpassword = etEnterPassword.getText().toString().trim();
        String secondpassword = etEnterCode.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showCustomToast("原密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(secondpassword) || TextUtils.isEmpty(newpassword)) {
            showCustomToast("新密码不能为空");
            return;
        }
        if (!secondpassword.equals(newpassword)) {
            showCustomToast("两次输入的密码不一致");
            return;
        }
        showLoadDialog();
        API.getSingleton().resetPassword(TAG, password, Config.PublicParams.usid,newpassword, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("修改登录密码成功");
                List<Activity> list;
                list = BaseApplication.getInstance().getmActivityList();
                list.get(list.size() - 2).finish();
                list.remove(list.size() - 2);
                list.get(list.size() - 2).finish();
                list.remove(list.size() - 2);
                startActivity(LoginActivity.class);
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
                showCustomToast(msg);
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

}
