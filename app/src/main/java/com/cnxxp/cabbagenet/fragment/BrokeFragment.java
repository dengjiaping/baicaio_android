package com.cnxxp.cabbagenet.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.bean.FetchBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.AccountValidatorUtil;
import com.cnxxp.cabbagenet.utils.CompressionImage;
import com.cnxxp.cabbagenet.utils.FileUtils;
import com.cnxxp.cabbagenet.view.ContainsEmojiEditText;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.util.*;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/4/10 0010.
 */

public class BrokeFragment extends BaseFragment {
    private static final String TAG = "BrokeFragment";
    private static final int REQUEST_RP_CAMERA = 1;
    private static final int REQUEST_RP_PHOTO = 2;
    private static final int REQUEST_CODE_CAMERA = 3;
    private static final int REQUEST_CODE_PHOTO = 4;
    public View inflate;
    @Bind(R.id.iv_img)
    ImageView ivImg;
    @Bind(R.id.tv_broke_title)
    ContainsEmojiEditText tvBrokeTitle;
    @Bind(R.id.et_broke_reson)
    ContainsEmojiEditText etBrokeReson;
    @Bind(R.id.et_url)
    ContainsEmojiEditText etUrl;
    @Bind(R.id.et_price)
    ContainsEmojiEditText etPrice;
    private FetchBean fetchData;
    private String url;
    protected File cameraFile;
    private String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_broke, null);
        ButterKnife.bind(this, inflate);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {
        initUploadPop();
    }

    @Override
    protected void initEvents() {
        etUrl.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    String editable = etUrl.getText().toString().trim();
                    if (AccountValidatorUtil.isUrl(editable)) {
                        httpGetUrlInfo(TAG, editable);
                        url = editable;
                    } else {
                        showCustomToast("您输入的不是正确的url");
                    }

                    return true;
                }
                return false;
            }

        });
    }

    @Override
    protected void initData() {
        path = "";
    }

    private PopupWindow popSelectImg;

    private void initUploadPop() {
        View view = View.inflate(getActivity(), R.layout.pop_change_face, null);
        view.findViewById(R.id.tv_select_from_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从本地相册获取
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_RP_PHOTO);
                } else {
                    selectPicFromLocal();
                }
                popSelectImg.dismiss();
            }
        });
        view.findViewById(R.id.tv_takephoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拍照
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //没有权限，需要申请权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                            REQUEST_RP_CAMERA);
                } else {
                    //有权限可以打开相机
                    selectPicFromCamera();
                }
                popSelectImg.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消

                popSelectImg.dismiss();
            }
        });

        popSelectImg = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popSelectImg.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popSelectImg.setFocusable(true);
        popSelectImg.setOutsideTouchable(true);
    }


    private void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }


    private void selectPicFromCamera() {
        cameraFile = new File(FileUtils.getCameraPath()
                + System.currentTimeMillis() + ".jpg");
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(getActivity(), "com.cnxxp.cabbagenet.provider", cameraFile);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(cameraFile);
        }
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    path = cameraFile.getAbsolutePath();
                    Glide.with(BrokeFragment.this).load(path).into(ivImg);

                    break;
                case REQUEST_CODE_PHOTO:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            path = getAbsolutePath(getActivity(), selectedImage);
                            Glide.with(BrokeFragment.this).load(path).into(ivImg);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_cancle, R.id.tv_release, R.id.iv_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                getActivity().finish();
                break;
            case R.id.tv_release:
                if (TextUtils.isEmpty(url)) {
                    showCustomToast("爆料Url路径不能为空,请先填写url");
                    return;
                }
                String content = etBrokeReson.getText().toString().trim();
                String title = tvBrokeTitle.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    showCustomToast("推荐理由不能为空");
                    return;
                }
                if (TextUtils.isEmpty(title)) {
                    showCustomToast("标题不能为空");
                    return;
                }
                if (TextUtils.isEmpty(price)) {
                    showCustomToast("价格不能为空");
                    return;
                }
                if (TextUtils.isEmpty(path)) {
                    String imgs = mGson.toJson(fetchData.getImgs());
                    httpRelease(TAG, content, url, imgs, title, price, fetchData.getImg());
                } else {
                    httpUploadImg(TAG, path, content, url, title, price);
                }
                break;
            case R.id.iv_img:
                popSelectImg.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private void httpGetUrlInfo(String tag, String url) {
        showLoadDialog();
        API.getSingleton().fetchItem(tag, url, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<FetchBean> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<FetchBean>>() {
                }.getType());
                fetchData = myBean.getData();
                if (fetchData.getImg() != null && fetchData.getPrice() != null && fetchData.getTitle() != null) {
                    Glide.with(BrokeFragment.this).load(myBean.getData().getImg()).placeholder(R.drawable.ic_original_pic).error(R.drawable.ic_original_pic).into(ivImg);
                    tvBrokeTitle.setText(myBean.getData().getTitle());
                    etPrice.setText(myBean.getData().getPrice());
                } else {
                    showCustomToast("无法获取该url商品信息,请手动填写");
                }

            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast("无法获取该url商品信息,请手动填写");
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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void httpRelease(String tag, String content, String url, String imgs, String title, String price, String img) {
        showLoadDialog();
        API.getSingleton().publishItem(tag, Config.PublicParams.usid, title, content, url, price, imgs, img, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                if (object.toString().contains("您的等级不够")) {
                    showCustomToast("您的等级不够,发布失败!");
                } else {
                    showCustomToast("发布成功");
                    getActivity().finish();
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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void httpUploadImg(String tag, String imgs, final String content, final String url, final String title, final String price) {
        showLoadDialog();
        String base64 = "data:image\\/jpg;base64," + CompressionImage.bitmapToString(imgs);
        API.getSingleton().uploadImage1(tag, base64, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                List<FetchBean.ImgsBean> list = new ArrayList<>();
                FetchBean.ImgsBean img = new FetchBean.ImgsBean();
                img.setUrl(myBean.getData());
                list.add(img);
                httpRelease(TAG, content, url, mGson.toJson(list), title, price, myBean.getData());
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

            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RP_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectPicFromCamera();
                } else {
                    showCustomToast("权限不够，请打开相机权限");
                }
                break;
            case REQUEST_RP_PHOTO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectPicFromLocal();
                } else {
                    showCustomToast("权限不够，请打开读取文件权限");
                }
                break;
        }
    }

    public String getAbsolutePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
