package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.AccountValidatorUtil;
import com.cnxxp.cabbagenet.view.ContainsEmojiEditText;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;


public class CheckingPhoneActivity extends BaseActivity {
    private static final String TAG = "CheckingPhoneActivity";
    @Bind(R.id.et_phone_number)
    ContainsEmojiEditText etPhoneNumber;//手机号
    @Bind(R.id.et_sms_code)
    EditText etSmsCode;
    @Bind(R.id.tv_security)
    TextView tvSecurity;


    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvSecurity.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tvSecurity.setClickable(true);
            tvSecurity.setBackground(getResources().getDrawable(R.drawable.green_shape));
            tvSecurity.setText("获取验证码");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_checking_phone);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("手机验证");
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_next_step, R.id.tv_security})
    public void onClick(View view) {
        String phone = etPhoneNumber.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_security:
                if (TextUtils.isEmpty(phone)) {
                    showCustomToast("请先填写手机号码");
                    return;
                }
                if (!AccountValidatorUtil.isMobile(phone)) {
                    showCustomToast("输入的手机号码不正确");
                    return;
                }
                timer.start();
                tvSecurity.setClickable(false);
                tvSecurity.setBackground(getResources().getDrawable(R.drawable.e6_shape));
                httpGetSmsCode(TAG, phone);
                break;
            case R.id.tv_next_step:
                String smsCode = etSmsCode.getText().toString().trim();
                if (!AccountValidatorUtil.isMobile(phone)) {
                    showCustomToast("手机号码不正确,请重新输入");
                    return;
                }
                if (TextUtils.isEmpty(smsCode)) {
                    showCustomToast("验证码不能为空");
                    return;
                }
                httpBindMobile(TAG, phone, smsCode);
                break;
        }


    }

    private void httpGetSmsCode(String tag, String phone) {
        API.getSingleton().smscode(tag, phone,"", new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("获取验证码成功,请查收短信");
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

    private void httpBindMobile(String tag, final String phone, String smsCode) {
        showLoadDialog();
        API.getSingleton().bindMobile(tag, phone, smsCode, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                Bundle bundle = new Bundle();
                bundle.putString("mobile", phone);
                Config.PublicParams.mobile = phone;
                startActivity(BindPhoneActivity.class, bundle);
                finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }
}
