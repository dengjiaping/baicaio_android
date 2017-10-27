package com.cnxxp.cabbagenet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class ChangeNameActivity extends BaseActivity {

    @Bind(R.id.et_input)
    EditText etInput;
    private String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_name);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("更改昵称");
        setTitleMoreTextVisibility(View.VISIBLE, "确定");
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        firstName = getIntent().getStringExtra("firstname");
        if (!TextUtils.isEmpty(firstName)) {
            etInput.setText(firstName);
        }

    }

    @OnClick(R.id.activity_more_text_bg)
    public void onClick() {
        String name = etInput.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showCustomToast("昵称不能为空");
            return;
        } else {
            if (name.equals(firstName)) {
                showCustomToast("您还没有进行修改");
                return;
            }
        }
        setResult(Activity.RESULT_OK, getIntent().putExtra("name", name));
        finish();
    }
}
