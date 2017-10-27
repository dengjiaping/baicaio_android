package com.cnxxp.cabbagenet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.PerfectInfoBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.AccountValidatorUtil;
import com.cnxxp.cabbagenet.view.ContainsEmojiEditText;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class PerfectInfoActivity extends BaseActivity {
    private static final String TAG = "PerfectInfoActivity";
    @Bind(R.id.et_username)
    ContainsEmojiEditText etUsername;
    @Bind(R.id.et_password)
    ContainsEmojiEditText etPassword;
    @Bind(R.id.et_email)
    ContainsEmojiEditText etEmail;
    @Bind(R.id.iv_man)
    ImageView ivMan;
    @Bind(R.id.iv_woman)
    ImageView ivWoman;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_perfect_info);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("完善信息");
        ivMan.setSelected(true);
        gender = "1";
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_man, R.id.ll_woman, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_man:
                if (ivWoman.isSelected()) {
                    ivWoman.setSelected(false);
                }
                ivMan.setSelected(true);
                gender = "1";
                break;
            case R.id.ll_woman:
                if (ivMan.isSelected()) {
                    ivMan.setSelected(false);
                }
                ivWoman.setSelected(true);
                gender = "0";
                break;
            case R.id.tv_submit:
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
                    showCustomToast("请将信息填写完整");
                    return;
                }
                if (!AccountValidatorUtil.isEmail(email)) {
                    showCustomToast("邮箱不正确");
                    return;
                }
                httpRegistOpen(TAG, username, password, email, gender);
                break;
        }
    }

    private void httpRegistOpen(String tag, String username, String password, String email, String gender) {
        API.getSingleton().registerOpen(tag, username, password, email, gender, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast(msg);
                MyBean<PerfectInfoBean> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<PerfectInfoBean>>() {
                }.getType());
                Intent intent = getIntent();
                intent.putExtra("userid", (myBean.getData().getUserid()+""));
                Config.PublicParams.usid = myBean.getData().getUserid()+"";
                Config.PublicParams.uname = etUsername.getText().toString().trim();                        
                setResult(RESULT_OK, intent);
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
}
