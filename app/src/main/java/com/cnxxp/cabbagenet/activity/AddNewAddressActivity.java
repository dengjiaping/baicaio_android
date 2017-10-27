package com.cnxxp.cabbagenet.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AddressBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.AccountValidatorUtil;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class AddNewAddressActivity extends BaseActivity {
    private static final String TAG = "AddNewAddressActivity";
    @Bind(R.id.et_consignee)
    EditText etConsignee;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.et_zip)
    EditText etZip;
    private AddressBean mbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_new_address);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("编辑收货地址");
        setTitleMoreTextVisibility(View.VISIBLE, "保存");
      
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        if (getIntent().getExtras() != null) {
            mbean = getIntent().getExtras().getParcelable("data");
            etAddress.setText(mbean.getAddress());
            etConsignee.setText(mbean.getConsignee());
            etPhone.setText(mbean.getMobile());
            etZip.setText(mbean.getZip());
        }
    }

    @OnClick({R.id.activity_more_text_bg, R.id.cb_default})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_more_text_bg:
                GetData();
                //保存
                break;

            case R.id.cb_default:
                //设为默认
                break;

        }
    }

    private void GetData() {
        String consignee = etConsignee.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String zip = etZip.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(consignee)) {
            showCustomToast("收件人不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showCustomToast("手机号码不能为空");
            return;
        }
        if (!AccountValidatorUtil.isMobile(phone)) {
            showCustomToast("手机号码不正确,请重新输入");
            return;
        }
        if (TextUtils.isEmpty(zip)) {
            showCustomToast("邮编不能为空");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            showCustomToast("收货地址不能为空");
            return;
        }

        if (mbean != null) {
            //修改
            httpEditAddress(TAG, mbean.getId(), consignee, phone, zip, address);
        } else {
            //新增
            httpAddAddress(TAG, consignee, phone, zip, address);
        }
    }

    private void httpAddAddress(String tag, String consignee, String phone, String zip, String address) {
        API.getSingleton().addAddress(tag, Config.PublicParams.usid, consignee, zip, phone, address, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("添加新地址成功");
                setResult(RESULT_OK, getIntent());
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
                showCustomToast(msg);
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void httpEditAddress(String tag, String id, String consignee, String phone, String zip, String address) {
        API.getSingleton().editAddress(tag, Config.PublicParams.usid, id, consignee, zip, phone, address, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("修改地址成功");
                setResult(RESULT_OK, getIntent());
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
                showCustomToast(msg);
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }
}
