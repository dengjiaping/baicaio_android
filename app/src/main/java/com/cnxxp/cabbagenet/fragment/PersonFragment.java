package com.cnxxp.cabbagenet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.LoginActivity;
import com.cnxxp.cabbagenet.activity.MyArticleActivity;
import com.cnxxp.cabbagenet.activity.MyCollectActivity;
import com.cnxxp.cabbagenet.activity.MyCommentActivity;
import com.cnxxp.cabbagenet.activity.MyCouponActivity;
import com.cnxxp.cabbagenet.activity.MyFortuneActivity;
import com.cnxxp.cabbagenet.activity.MyMessageActivity;
import com.cnxxp.cabbagenet.activity.MyPushActivity;
import com.cnxxp.cabbagenet.activity.PersonSettingActivity;
import com.cnxxp.cabbagenet.activity.ScoreChangeActivity;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.UserInfo;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.SPUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.ContentValues.TAG;

/**
 * 个人 Fragment
 * Created by Administrator on 2017/3/21 0021.
 */

public class PersonFragment extends BaseFragment {

    private static final int CODE_LOGIN = 1;
    private static final int CODE_SIGN = 2;
    @Bind(R.id.iv_person_face)
    ImageView ivPersonFace;
    @Bind(R.id.tv_person_name)
    TextView tvPersonName;
    @Bind(R.id.tv_person_score)
    TextView tvPersonScore;
    @Bind(R.id.tv_person_sign)
    TextView tvPersonSign;
    @Bind(R.id.tv_sign_state)
    TextView tvSignState;
    @Bind(R.id.tv_sign_des)
    TextView tvSignDes;
    private View inflate;
    private UserInfo mUserInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_person, null);
        ButterKnife.bind(this, inflate);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {
        //初始化title
        ((TextView) inflate.findViewById(R.id.activity_title_text)).setText("个人");
        inflate.findViewById(R.id.activity_back_bg).setVisibility(View.GONE);
        inflate.findViewById(R.id.activity_more_image_bg).setVisibility(View.VISIBLE);
        ((ImageView) inflate.findViewById(R.id.activity_more_image)).setImageResource(R.mipmap.ic_mesg);


    }

    @Override
    protected void initEvents() {
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(Config.PublicParams.usid)) {
            if (TextUtils.isEmpty(Config.PublicParams.portrait)) {
                httpGetHead();
            } else {
                Glide.with(this).load(Config.PublicParams.portrait).bitmapTransform(new CropCircleTransformation(getActivity())).signature(new StringSignature(Config.PublicParams.signature)).error(R.mipmap.ic_person_head).into(ivPersonFace);
            }
            httpGetInfo();
            tvPersonName.setText(Config.PublicParams.uname);
        } else {
            tvPersonName.setText("未登录");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.activity_more_image_bg, R.id.iv_person_face, R.id.ll_my_article, R.id.ll_my_share, R.id.ll_my_comment, R.id.ll_my_collect, R.id.rl_my_score_exchange, R.id.rl_my_coupon, R.id.rl_person_setting, R.id.tv_person_sign, R.id.rl_my_message, R.id.rl_my_push})
    public void onClick(View view) {
        Intent intent;
        Bundle bundle;
        if (TextUtils.isEmpty(Config.PublicParams.usid)) {
            intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, CODE_LOGIN);
        } else {
            switch (view.getId()) {
                case R.id.activity_more_image_bg:
                    //我的消息

                    startActivity(MyMessageActivity.class);

                    break;
                case R.id.iv_person_face:
                    //个人设置
                    intent = new Intent(getActivity(), PersonSettingActivity.class);
                    bundle = new Bundle();
                    bundle.putString("gender", mUserInfo.getGender());
                    intent.putExtras(bundle);
                    startActivity(intent);

                    break;
                case R.id.ll_my_article:
                    //我的文章->我的财富
                    if (tvPersonSign.isSelected()) {
                        intent = new Intent(getActivity(), MyFortuneActivity.class);
                        bundle = new Bundle();
                        bundle.putBoolean("state", false);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, CODE_SIGN);
                    } else {
                        bundle = new Bundle();
                        bundle.putBoolean("state", true);
                        startActivity(MyFortuneActivity.class, bundle);
                    }


                    //startActivity(MyArticleActivity.class);

                    break;
                case R.id.ll_my_share:
                    //我的文章

                    startActivity(MyArticleActivity.class);

                    break;
                case R.id.ll_my_comment:
                    //我的评论

                    startActivity(MyCommentActivity.class);

                    break;
                case R.id.ll_my_collect:
                    //我的收藏

                    startActivity(MyCollectActivity.class);

                    break;
                case R.id.rl_my_score_exchange:
                    //积分兑换

                    startActivity(ScoreChangeActivity.class);

                    break;
                case R.id.rl_my_coupon:
                    //我的优惠券

                    startActivity(MyCouponActivity.class);

                    break;
              /*  case R.id.rl_my_fortune:
                    //我的财富

                    startActivity(MyFortuneActivity.class);

                    break;*/
                case R.id.rl_person_setting:
                    //个人设置

                    intent = new Intent(getActivity(), PersonSettingActivity.class);
                    bundle = new Bundle();
                    bundle.putString("gender", mUserInfo.getGender());
                    intent.putExtras(bundle);
                    startActivity(intent);

                    break;
                case R.id.tv_person_sign:
                    //签到
                    if (!tvPersonSign.isSelected()) {
                        showCustomToast("您已经签到");
                        return;
                    }
                    httpSign(TAG);
                    break;
                case R.id.rl_my_message:
                    startActivity(MyMessageActivity.class);
                    break;
                case R.id.rl_my_push:
                    startActivity(MyPushActivity.class);
                    break;

            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_LOGIN && resultCode == Activity.RESULT_OK) {
            tvPersonName.setText(Config.PublicParams.uname);
            httpGetHead();
            httpGetInfo();

        }
        if (requestCode == CODE_SIGN) {
            httpGetInfo();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Config.PublicParams.hasChange) {
            if (TextUtils.isEmpty(Config.PublicParams.usid)) {
                ivPersonFace.setImageResource(R.mipmap.ic_person_head);
                tvPersonName.setText("未登录");
                tvPersonScore.setText("");
                tvPersonSign.setVisibility(View.GONE);
            } else {
                httpGetHead();
            }
            Config.PublicParams.hasChange = false;
        } else {
            return;
        }

    }

    private void httpGetHead() {
        API.getSingleton().getImage(TAG, Config.PublicParams.usid, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                if (myBean.getData().startsWith("http")) {
                    if (TextUtils.isEmpty(Config.PublicParams.portrait)) {
                        Config.PublicParams.portrait = myBean.getData();
                        SPUtils.put(getActivity(), "portrait", myBean.getData());
                        Glide.with(PersonFragment.this).load(myBean.getData()).bitmapTransform(new CropCircleTransformation(getActivity())).signature(new StringSignature(Config.PublicParams.signature)).into(ivPersonFace);
                    } else {
                        Glide.with(PersonFragment.this).load(myBean.getData()).bitmapTransform(new CropCircleTransformation(getActivity())).signature(new StringSignature(Config.PublicParams.signature)).into(ivPersonFace);
                    }
                } else if (TextUtils.isEmpty(Config.PublicParams.portrait)) {
                    Glide.with(PersonFragment.this).load("http://www.baicaio.com" + myBean.getData()).bitmapTransform(new CropCircleTransformation(getActivity())).signature(new StringSignature(Config.PublicParams.signature)).error(R.mipmap.ic_person_head).into(ivPersonFace);
                    SPUtils.put(getActivity(), "portrait", "http://www.baicaio.com" + myBean.getData());
                    Config.PublicParams.portrait = "http://www.baicaio.com" + myBean.getData();
                }
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

    private void httpGetInfo() {
        showLoadDialog();
        API.getSingleton().getUserInfo(TAG, Config.PublicParams.usid, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<UserInfo> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<UserInfo>>() {
                }.getType());
                mUserInfo = myBean.getData();
                Config.PublicParams.userInfo = myBean.getData();
                tvPersonScore.setText(myBean.getData().getScore());
                tvPersonSign.setVisibility(View.VISIBLE);
                if ((myBean.getData().getIs_sign()) == 0) {
                    tvPersonSign.setText("签到");
                    tvPersonSign.setSelected(true);
                } else {
                    tvPersonSign.setText("已签到");
                    tvPersonSign.setSelected(false);
                }
                if (myBean.getData().getMobile() != null) {
                    Config.PublicParams.mobile = myBean.getData().getMobile();
                }
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

    private void httpSign(String tag) {
        API.getSingleton().sign(TAG, Config.PublicParams.usid, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                tvPersonSign.setSelected(false);
                tvPersonSign.setText("已签到");
                showCustomToast(myBean.getData());
                httpGetInfo();
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


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (!TextUtils.isEmpty(Config.PublicParams.usid)) {
                httpGetInfo();
                if (TextUtils.isEmpty(Config.PublicParams.portrait)) {
                    httpGetHead();
                    tvPersonName.setText(Config.PublicParams.uname);
                }
            }
        }
    }
}
