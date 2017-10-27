package com.cnxxp.cabbagenet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/10 0010.
 */
public class OtherFindPwdFragment extends BaseFragment {
    private static final String TAG = "OtherFindPwdFragment";
    @Bind(R.id.et_username)
    EditText etUsername;
    private View inflate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_other_find_pwd, null);
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, inflate);
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

    @OnClick(R.id.tv_submit)
    public void onClick() {
        String username = etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            showCustomToast("用户名或邮箱不能为空");
            return;
        }
        httpFindPwd(TAG, username);
    }

    private void httpFindPwd(String tag, String username) {
        API.getSingleton().OtherFindPwd(tag, username, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast("请求成功,请注意查收邮件");
                getActivity().finish();
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
                showCustomToast(msg);
            }
        });
    }
}
