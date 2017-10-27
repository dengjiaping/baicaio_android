package com.cnxxp.cabbagenet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class GenderActivity extends BaseActivity {
    private static final String TAG = "GenderActivity";
    @Bind(R.id.cb_man)
    CheckBox cbMan;
    @Bind(R.id.cb_woman)
    CheckBox cbWoman;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gender);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("性别");
        setTitleMoreTextVisibility(View.VISIBLE, "保存");

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        gender = getIntent().getStringExtra("gender");
        if (gender.equals("0")) {
            cbWoman.setChecked(true);
            cbMan.setChecked(false);
        } else {
            cbMan.setChecked(true);
            cbWoman.setChecked(false);
        }
    }

    @OnClick({R.id.cb_man, R.id.cb_woman, R.id.activity_more_text_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_man:

                cbMan.setChecked(true);
                cbWoman.setChecked(false);
                gender = "1";
                break;
            case R.id.cb_woman:
                cbMan.setChecked(false);
                cbWoman.setChecked(true);
                gender = "0";
                break;
            case R.id.activity_more_text_bg:
                if (gender.equals(getIntent().getStringExtra("gender"))) {
                    finish();
                } else {
                    httpChangeGender(TAG, gender);
                }
                break;
        }
    }

    private void httpChangeGender(String tag, final String gender) {
        API.getSingleton().updateSex(tag, Config.PublicParams.usid, gender, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast(msg);
                Intent intent = getIntent();
                intent.putExtra("gender", gender);
                setResult(RESULT_OK, intent);
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
