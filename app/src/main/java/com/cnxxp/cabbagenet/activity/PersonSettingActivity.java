package com.cnxxp.cabbagenet.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.CleanMessageUtil;
import com.cnxxp.cabbagenet.utils.CompressionImage;
import com.cnxxp.cabbagenet.utils.FileUtils;
import com.cnxxp.cabbagenet.utils.SPUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yuyh.library.imgsel.utils.LogUtils;

import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.cnxxp.cabbagenet.config.Config.PublicParams.hasChange;


public class PersonSettingActivity extends BaseActivity {
    private static final String TAG = "PersonSettingActivity";
    private static final int REQUEST_CODE_NAME = 1;
    private static final int REQUEST_CODE_SEX = 2;
    private static final int REQUEST_CODE_CAMERA = 3;
    private static final int REQUEST_CODE_PHOTO = 4;
    private static final int REQUEST_CODE_CROP = 5;
    private static final int REQUEST_RP_PHOTO = 6;
    private static final int REQUEST_RP_CAMERA = 7;
    private static final int SHARE_PR_CODE = 8;
    @Bind(R.id.iv_setting_face)
    ImageView ivSettingFace;
    @Bind(R.id.tv_setting_name)
    TextView tvSettingName;
    @Bind(R.id.tv_setting_sex)
    TextView tvSettingSex;
    @Bind(R.id.tv_cache)
    TextView tvCache;
    private PopupWindow popLogOut;
    private PopupWindow popChangeFace;
    private String gender;


    protected File cameraFile;
    private String cropImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_person_setting);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("个人设置");
        initPopChangeFace();
        initPopLogOut();
        initSharepop();
    }


    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        gender = getIntent().getExtras().getString("gender");
        if (gender.equals("0")) {
            tvSettingSex.setText("女");
        } else {
            tvSettingSex.setText("男");
        }
        try {
            tvCache.setText(CleanMessageUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvSettingName.setText(Config.PublicParams.uname);
        Glide.with(this).load(Config.PublicParams.portrait).bitmapTransform(new CropCircleTransformation(this)).signature(new StringSignature(Config.PublicParams.signature)).error(R.mipmap.ic_person_head).into(ivSettingFace);
    }


    @OnClick({R.id.rl_setting_face, R.id.rl_change_name,
            R.id.rl_change_sex, R.id.rl_setting_address,
            R.id.rl_setting_share, R.id.rl_setting_suggestion,
            R.id.rl_setting_safe, R.id.rl_setting_log_out,
            R.id.rl_setting_clear})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_setting_face:
                //修改头像
                popChangeFace.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;

            case R.id.rl_change_sex:
                //修改性别
                intent = new Intent(this, GenderActivity.class);
                intent.putExtra("gender", gender);
                startActivityForResult(intent, REQUEST_CODE_SEX);
                break;
            case R.id.rl_setting_address:
                //设置收货地址
                startActivity(AddressManagerActivity.class);
                break;


            case R.id.rl_setting_share:
                //分享推广
                if (Build.VERSION.SDK_INT >= 23) {
                    if ((ContextCompat.checkSelfPermission(PersonSettingActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                            (ContextCompat.checkSelfPermission(PersonSettingActivity.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            ) {
                        if (sharePop.isShowing()) {
                            sharePop.dismiss();
                        } else {

                            sharePop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                        }

                    } else {
                        //请求获取摄像头权限
                        ActivityCompat.requestPermissions(PersonSettingActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, SHARE_PR_CODE);

                    }
                } else {
                    if (sharePop.isShowing()) {
                        sharePop.dismiss();
                    } else {

                        sharePop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                    }
                }

                break;
            case R.id.rl_setting_suggestion:
                //问题反馈
                startActivity(SuggestionActivity.class);
                break;
            case R.id.rl_setting_safe:
                //安全设置
                startActivity(SafeSettingActivity.class);
                break;
            case R.id.rl_setting_log_out:
                //退出
                popLogOut.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
            case R.id.rl_setting_clear:
                CleanMessageUtil.clearAllCache(this);
                try {
                    tvCache.setText(CleanMessageUtil.getTotalCacheSize(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showCustomToast("清理完成");
                break;
        }
    }


    private void initPopChangeFace() {
        View view = View.inflate(this, R.layout.pop_change_face, null);
        view.findViewById(R.id.tv_select_from_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从本地相册获取
                if (ContextCompat.checkSelfPermission(PersonSettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PersonSettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_RP_PHOTO);
                } else {
                    selectPicFromLocal();
                }
                popChangeFace.dismiss();
            }
        });
        view.findViewById(R.id.tv_takephoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拍照
                if (ContextCompat.checkSelfPermission(PersonSettingActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //没有权限，需要申请权限
                    ActivityCompat.requestPermissions(PersonSettingActivity.this, new String[]{Manifest.permission.CAMERA},
                            REQUEST_RP_CAMERA);
                } else {
                    //有权限可以打开相机
                    selectPicFromCamera();
                }
                popChangeFace.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消

                popChangeFace.dismiss();
            }
        });

        popChangeFace = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popChangeFace.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popChangeFace.setFocusable(true);
        popChangeFace.setOutsideTouchable(true);
    }


    private void initPopLogOut() {
        View view = View.inflate(this, R.layout.pop_log_out, null);
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消

                popLogOut.dismiss();
            }
        });
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定
                Config.PublicParams.uname = "";
                Config.PublicParams.usid = "";
                Config.PublicParams.portrait = "";
                SPUtils.clear(PersonSettingActivity.this);
                popLogOut.dismiss();
                hasChange = true;
                JPushInterface.cleanTags(getApplicationContext(),1);
                finish();
            }
        });

        popLogOut = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popLogOut.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popLogOut.setFocusable(true);
        popLogOut.setOutsideTouchable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NAME:
                    break;
                case REQUEST_CODE_SEX:
                    gender = data.getStringExtra("gender");
                    tvSettingSex.setText(tvSettingSex.getText().toString().toString().equals("男") ? "女" : "男");
                    hasChange = true;
                    break;
                case REQUEST_CODE_CAMERA:
                    crop(cameraFile.getAbsolutePath());

                    break;
                case REQUEST_CODE_PHOTO:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            crop(getAbsolutePath(this, selectedImage));
                        }
                    }
                    break;
                case REQUEST_CODE_CROP:
                    Glide.with(this).load(cropImagePath).bitmapTransform(new CropCircleTransformation(this)).signature(new StringSignature(Config.PublicParams.signature)).error(R.mipmap.ic_person_head).into(ivSettingFace);
                    httpUpdateHead(TAG, cropImagePath);
                    break;

            }
        }
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
            imageUri = FileProvider.getUriForFile(PersonSettingActivity.this, "com.cnxxp.cabbagenet.provider", cameraFile);//通过FileProvider创建一个content类型的Uri
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

    //裁剪
    private void crop(String imagePath) {
        File file = new File(com.yuyh.library.imgsel.utils.FileUtils.createRootPath(this) + "/" + System.currentTimeMillis() + ".jpg");

        cropImagePath = file.getAbsolutePath();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            cursor.close();
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            cursor.close();
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
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
            case SHARE_PR_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (sharePop == null) {
                        initSharepop();
                    }
                    sharePop.showAsDropDown(getWindow().getDecorView());
                } else {
                    showCustomToast("请先打开读写权限");
                }
                break;
        }
    }

    private void httpUpdateHead(String tag, String filepath) {
        showLoadDialog();
        filepath = "data:image\\/jpg;base64," + CompressionImage.bitmapToString(filepath);
        API.getSingleton().setImg(tag, Config.PublicParams.usid, filepath, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast(msg);
                hasChange = true;
                Config.PublicParams.signature = System.currentTimeMillis() + "";
                SPUtils.put(PersonSettingActivity.this, "signature", Config.PublicParams.signature);
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


    private PopupWindow sharePop;

    private void initSharepop() {
        LogUtils.e("初始化pop");
        View view = LayoutInflater.from(this).inflate(R.layout.pop_share, null, false);
        view.findViewById(R.id.ll_share_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //新浪分享
                share(SHARE_MEDIA.SINA, "白菜哦");
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.ll_share_wx_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //微信朋友圈
                share(SHARE_MEDIA.WEIXIN_CIRCLE, "白菜哦");
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.ll_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //微信好友
                share(SHARE_MEDIA.WEIXIN, "白菜哦");
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.ll_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //qq好友
                share(SHARE_MEDIA.QQ, "白菜哦");
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.ll_qq_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //qq空间
                share(SHARE_MEDIA.QZONE, "白菜哦");
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.ll_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //复制链接
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData  
                ClipData mClipData = ClipData.newPlainText("Label", "http://www.baicaio.com");
// 将ClipData内容放到系统剪贴板里。  
                cm.setPrimaryClip(mClipData);
                showCustomToast("已复制到剪切板");
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.pop_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePop.dismiss();
            }
        });

        sharePop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        sharePop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sharePop.setFocusable(true);
        sharePop.setOutsideTouchable(true);
        LogUtils.e("pop初始化完成");
    }

    private UMWeb getShareWeb(Context context, String url, String title, String description) {
        UMImage thumb = new UMImage(context, R.mipmap.ic_logo);
        UMWeb web = new UMWeb(url);
        web.setThumb(thumb);
        web.setDescription(description);
        web.setTitle(title);
        return web;
    }

    private void share(SHARE_MEDIA share_media, String title) {
        new ShareAction(this).withMedia(getShareWeb(this, "http://www.baicaio.com/item/275534.html", title, "分享一个特价网站")).setPlatform(share_media).setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
            }
        }).share();
    }


}
