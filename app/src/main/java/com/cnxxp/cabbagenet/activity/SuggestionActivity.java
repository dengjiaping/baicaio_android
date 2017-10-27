package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class SuggestionActivity extends BaseActivity {
    private static final String TAG = "SuggestionActivity";
    @Bind(R.id.et_suggestion)
    EditText etSuggestion;
    @Bind(R.id.et_contact)
    EditText etContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_suggestion);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("问题反馈");
        setTitleMoreTextVisibility(View.VISIBLE, "提交");

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.activity_more_text_bg)
    public void onClick() {
        //提交
        String suggest = etSuggestion.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        if (TextUtils.isEmpty(suggest)) {
            showCustomToast("建议内容不能为空");
            return;
        }
        httpReport(TAG, "[" + suggest + "]" + "联系方式:" + contact);
    }

    private void httpReport(String tag, String content) {
        API.getSingleton().reportBug(tag, content, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast(msg);
                finish();
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
