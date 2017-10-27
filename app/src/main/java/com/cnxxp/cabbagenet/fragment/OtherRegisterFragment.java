package com.cnxxp.cabbagenet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.AccountValidatorUtil;
import com.cnxxp.cabbagenet.view.ContainsEmojiEditText;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/6/21 0021.
 */

public class OtherRegisterFragment extends BaseFragment {
    private static final String TAG = "OtherRegisterFragment";
    @Bind(R.id.et_enter_phone)
    ContainsEmojiEditText etEnterPhone;
    @Bind(R.id.et_email)
    ContainsEmojiEditText etEmail;
    @Bind(R.id.et_enter_password)
    ContainsEmojiEditText etEnterPassword;
    @Bind(R.id.cb_check)
    CheckBox cbCheck;
    @Bind(R.id.et_password_again)
    ContainsEmojiEditText etPasswordAgain;
    private View inflate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_other_register, null);
        ButterKnife.bind(this, inflate);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_regist_submit, R.id.tv_cabbage_rule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_regist_submit:
                if (!cbCheck.isChecked()) {
                    showCustomToast("您需要同意白菜哦平台协议");
                    return;
                }
                String username = etEnterPhone.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etEnterPassword.getText().toString().trim();
                String passwordagain = etPasswordAgain.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordagain)) {
                    showCustomToast("用户名、密码、邮箱不能为空");
                    return;
                }
                if (!AccountValidatorUtil.isEmail(email)) {
                    showCustomToast("邮箱不正确,请重新输入");
                    return;
                }
                if (!passwordagain.equals(password)) {
                    showCustomToast("两次输入密码不一致");
                    return;
                }
                if (!cbCheck.isChecked()) {
                    showCustomToast("您需要同意白菜哦平台协议");
                    return;
                }
                httpOtherLogin(TAG, username, email, password);
                break;
            case R.id.tv_cabbage_rule:
                break;
        }
    }

    private void httpOtherLogin(String tag, String username, String email, String password) {
        showLoadDialog();
        API.getSingleton().registerOpen(tag, username, password, email, "0", new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("注册成功");
                getActivity().finish();
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);
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
