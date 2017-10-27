package com.cnxxp.cabbagenet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.OtherRegisterActivity;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.AccountValidatorUtil;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/6/21 0021.
 */

public class PhoneRegisterFragment extends BaseFragment {
    private static final String TAG = "PhoneregisterFragment";
    private static final int CODE_REGISTER = 1;
    @Bind(R.id.et_enter_phone)
    EditText etEnterPhone;
    @Bind(R.id.et_enter_code)
    EditText etEnterCode;
    @Bind(R.id.tv_get_security_code)
    TextView tvSecurity;
    private View inflate;
    private String uid;
    String phone;
    String smsCode;
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            tvSecurity.setText(l / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tvSecurity.setBackground(getActivity().getResources().getDrawable(R.drawable.green_shape));
            tvSecurity.setClickable(true);
            tvSecurity.setText("获取验证码");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_phone_register, null);
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

    @OnClick({R.id.tv_get_security_code, R.id.tv_regist_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_security_code:
                //获取验证码
                String mobile = etEnterPhone.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    showCustomToast("手机号不能为空");
                    return;
                }
                if (!AccountValidatorUtil.isMobile(mobile)) {
                    showCustomToast("手机号不正确,请重新输入");
                }

                httpMsmscode(TAG, mobile);
                break;
            case R.id.tv_regist_submit:
                phone = etEnterPhone.getText().toString().trim();

                smsCode = etEnterCode.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showCustomToast("手机号不能为空");
                    return;
                }
                if (!AccountValidatorUtil.isMobile(phone)) {
                    showCustomToast("输入手机号码不正确，请重新输入");
                    return;
                }
                if (TextUtils.isEmpty(smsCode)) {
                    showCustomToast("验证码不能为空");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                bundle.putString("msmcode", smsCode);
                Intent intent = new Intent(getActivity(), OtherRegisterActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, CODE_REGISTER);
                break;

        }
    }


    private void httpMsmscode(String tag, String phone) {
        showLoadDialog();
        API.getSingleton().smscode(tag, phone, "register", new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("获取验证码成功");
                tvSecurity.setClickable(false);
                timer.start();
                tvSecurity.setBackground(getActivity().getResources().getDrawable(R.drawable.e6_shape));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REGISTER && resultCode == Activity.RESULT_OK) {
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
