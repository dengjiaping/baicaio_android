package com.cnxxp.cabbagenet.fragment;

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
import com.cnxxp.cabbagenet.activity.PhoneFindPwdActivity;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.AccountValidatorUtil;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/9 0009.
 */

public class PhoneFindPwdFragment extends BaseFragment {
    private View inflate;
    private static final String TAG = "ForgetPwdActivity";
    @Bind(R.id.et_enter_phone)
    EditText etEnterPhone;
    @Bind(R.id.et_enter_code)
    EditText etEnterCode;
    @Bind(R.id.tv_get_security_code)
    TextView tvGetSecurity;

    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            tvGetSecurity.setText(l / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tvGetSecurity.setClickable(true);
            tvGetSecurity.setBackground(getActivity().getResources().getDrawable(R.drawable.green_shape));
            tvGetSecurity.setText("获取验证码");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_phone_find_pwd, null);
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


    @OnClick({R.id.tv_get_security_code, R.id.tv_forget_pwd_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_security_code:
                String phone = etEnterPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showCustomToast("请先填写手机号");
                    return;
                }
                if (!AccountValidatorUtil.isMobile(phone)) {
                    showCustomToast("手机号码不正确,请重新输入");
                    return;
                }
                tvGetSecurity.setBackground(getActivity().getResources().getDrawable(R.drawable.e6_shape));
                tvGetSecurity.setClickable(false);
                timer.start();
                httpGetsmsCode(TAG, phone);
                break;
            case R.id.tv_forget_pwd_submit:
                String mobile = etEnterPhone.getText().toString().trim();
                String smscode = etEnterCode.getText().toString().trim();

                if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(smscode)) {
                    showCustomToast("请先将信息填写完整");
                    return;
                }
                if (!AccountValidatorUtil.isMobile(mobile)) {
                    showCustomToast("手机号码不正确,请重新输入");
                    return;
                }
                etEnterPhone.setText("");
                etEnterCode.setText("");
                timer.cancel();
                tvGetSecurity.setClickable(true);
                tvGetSecurity.setBackground(getActivity().getResources().getDrawable(R.drawable.green_shape));
                tvGetSecurity.setText("获取验证码");
                Bundle bundle = new Bundle();
                bundle.putString("phone", mobile);
                bundle.putString("smscode", smscode);
                startActivity(PhoneFindPwdActivity.class, bundle);

                break;
        }
    }

    private void httpGetsmsCode(String tag, String phone) {
        API.getSingleton().smscode(tag, phone,"", new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("获取验证码成功");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }
}
