package com.cnxxp.cabbagenet.activity;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.base.BaseApplication;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.bean.AttentionBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.bean.VersionBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.dialog.MyDialog;
import com.cnxxp.cabbagenet.fragment.AttentionFragment;
import com.cnxxp.cabbagenet.fragment.DiscountFragment;
import com.cnxxp.cabbagenet.fragment.HomeFragment;
import com.cnxxp.cabbagenet.fragment.PersonFragment;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.PopupwindowUtils;
import com.cnxxp.cabbagenet.utils.SPUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity {


    private static final int CODE_LOGIN_MAIN = 1;
    @Bind(R.id.iv_home)
    ImageView ivHome;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.iv_discount)
    ImageView ivDiscount;
    @Bind(R.id.tv_discount)
    TextView tvDiscount;
    @Bind(R.id.iv_original)
    ImageView ivOriginal;
    @Bind(R.id.tv_original)
    TextView tvOriginal;
    @Bind(R.id.iv_person)
    ImageView ivPerson;
    @Bind(R.id.tv_person)
    TextView tvPerson;
    private ImageView[] imageViews;
    public int clickIndex;
    public BaseFragment[] fragments;
    private int currentTabIndex;
    private TextView[] textViews;
    public int switchDiscount;
    private TextView mDialogText;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

    }


    public void switchFragment(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = this.getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fl_main, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            imageViews[currentTabIndex].setSelected(false);
            textViews[currentTabIndex].setSelected(false);
            imageViews[index].setSelected(true);
            textViews[index].setSelected(true);
            currentTabIndex = index;
        }
    }


    @Override
    protected void initViews() {
        clickIndex = -1;
        fragments = new BaseFragment[]{new HomeFragment(), new DiscountFragment(), new AttentionFragment(), new PersonFragment()};
        textViews = new TextView[]{tvHome, tvDiscount, tvOriginal, tvPerson};
        imageViews = new ImageView[]{ivHome, ivDiscount, ivOriginal, ivPerson};
        imageViews[0].setSelected(true);
        textViews[0].setSelected(true);
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_main, fragments[0])
                .show(fragments[0])
                .commit();
        currentTabIndex = 0;

    }

    @Override
    protected void initEvents() {
    }

    @Override
    protected void initData() {
        switchDiscount = -1;
        boolean sousuo = false;
        httpVersion();
        Config.PublicParams.uname = (String) SPUtils.get(MainActivity.this, "uname", "");
        Config.PublicParams.usid = (String) SPUtils.get(MainActivity.this, "usid", "");
        Config.PublicParams.portrait = (String) SPUtils.get(MainActivity.this, "portrait", "");
        Config.PublicParams.signature = (String) SPUtils.get(MainActivity.this, "signature", System.currentTimeMillis() + "");
        if (!TextUtils.isEmpty(Config.PublicParams.usid)) {
            httpTags();
        }
        JPushInterface.getAllTags(getApplicationContext(),1);
    }

    private void httpTags() {
        API.getSingleton().notifyTagByUser(TAG, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<AttentionBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<AttentionBean>>>() {
                }.getType());
                Config.PublicParams.u_attention = new HashMap<String, AttentionBean>();
                for (int i = 0; i < myBean.getData().size(); i++) {
                    Config.PublicParams.u_attention.put(myBean.getData().get(i).getTag().toLowerCase(), myBean.getData().get(i));
                }
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                Config.PublicParams.u_attention = new HashMap<String, AttentionBean>();
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


    @OnClick({R.id.ll_home_bg, R.id.ll_discount_bg, R.id.ll_original_bg, R.id.ll_person_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home_bg:
                switchFragment(0);
                break;
            case R.id.ll_discount_bg:
                switchFragment(1);
                break;
            case R.id.ll_original_bg:
                if (TextUtils.isEmpty(Config.PublicParams.usid)) {
                    startActivity(LoginActivity.class);
                } else {
                    if (Config.PublicParams.u_attention == null) {
                        Config.PublicParams.u_attention = new HashMap<>();
                    }
                    switchFragment(2);
                }
                break;
            case R.id.ll_person_bg:
                switchFragment(3);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                PopupwindowUtils.initPopupWindow(getWindow().getDecorView(), LayoutInflater.from(this), "是否退出应用?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupwindowUtils.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PopupwindowUtils.delete();
                        BaseApplication.getInstance().exit();
                    }
                });
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 通知栏进度条
    private NotificationManager mNotificationManager = null;

    private Notification mNotification;

    /**
     * 文件大小
     */
    private int fileSize;
    /**
     * 下载文件大小
     */
    private int downLoadFileSize;

    private String filename;

    public void downFile(String url, String path) throws IOException {
        // // 下载函数
        filename = path + "freshbar" + System.currentTimeMillis() + ".apk";

        // 获取文件名
        URL myURL = new URL(url);
        URLConnection conn = myURL.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        this.fileSize = conn.getContentLength();// 根据响应获取文件大小
        if (this.fileSize <= 0)
            throw new RuntimeException("无法获知文件大小 ");
        if (is == null)
            throw new RuntimeException("stream is null");
        File destDir = new File(path);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(filename);
        // showCustomToast(path + filename);
        // 把数据存入路径+文件名
        byte buf[] = new byte[1024];
        downLoadFileSize = 0;
        sendMsg(0);
        int numread = 0;

        timer = new Timer(true);
        timer.schedule(task, 1000, 1000); // 延时1000ms后执行，1000ms执行一次
        while ((numread = is.read(buf)) != -1) {
            fos.write(buf, 0, numread);
            downLoadFileSize += numread;
        }

        sendMsg(2);// 通知下载完成
        try {
            fos.close();
            is.close();
        } catch (Exception ex) {
        }

    }

    private TimerTask task = new TimerTask() {
        public void run() {
            sendMsg(1);
        }
    };

    private Timer timer;

    private void sendMsg(int flag) {
        Message msg = new Message();
        msg.what = flag;
        handlerUpdate.sendMessage(msg);
    }

    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filename)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void notificationInit() {
        mNotificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        mNotification = new Notification();
        mNotification.icon = R.mipmap.ic_logo;
        mNotification.tickerText = "开始下载";
        mNotification.contentView = new RemoteViews(getPackageName(),
                R.layout.notification_update);// 通知栏中进度布局
    }

    private Handler handlerUpdate = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 定义一个Handler，用于处理下载线程与UI间通讯
            switch (msg.what) {
                case 1:
                    long result = downLoadFileSize * 100L / fileSize;
                    mNotification.contentView.setTextViewText(
                            R.id.content_view_text1, result + "%");
                    mNotification.contentView.setProgressBar(
                            R.id.content_view_progress, fileSize, downLoadFileSize,
                            false);
                    mNotificationManager.notify(0, mNotification);
                    Log.e("size", "文件" + downLoadFileSize + ":" + fileSize + ":"
                            + result);
                    mDialogText.setText(result + "%");
                    break;
                case 2:
                    result = downLoadFileSize * 100L / fileSize;
                    mNotification.contentView.setTextViewText(
                            R.id.content_view_text1, result + "%");
                    mNotification.contentView.setProgressBar(
                            R.id.content_view_progress, fileSize, downLoadFileSize,
                            false);
                    mNotificationManager.notify(0, mNotification);
                    showCustomToast("文件下载完成");
                    mNotificationManager.cancel(0);
                    try {
                        timer.cancel(); // 退出计时器
                    } catch (Exception e) {
                    }

                    mDialogText.setText(100 + "%");
//                    dismissCurrentDialog();
                    update();
                    break;

                case -1:
                    mNotification.contentView.setTextViewText(
                            R.id.content_view_text1, "下载失败");
                    // mNotification.contentView.setProgressBar(R.id.content_view_progress,
                    // fileSize, downLoadFileSize, false);
                    mNotification.contentView.setViewVisibility(
                            R.id.content_view_progress, View.GONE);
                    mNotificationManager.cancel(0);
                    mNotification.tickerText = "下载失败";
                    mNotificationManager.notify(1, mNotification);
                    try {
                        timer.cancel(); // 退出计时器
                    } catch (Exception e) {
                    }
                    mDialogText.setText("下载失败");
//                    dismissCurrentDialog();
                    break;

                default:
                    break;
            }
        }
    };

    private static final String TAG = "MainActivity";
    private Dialog dialog;

    public void httpVersion() {
        API.getSingleton().VersionUpData(TAG, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {

                final MyBean<VersionBean> bean = mGson.fromJson(
                        object.toString(),
                        new TypeToken<MyBean<VersionBean>>() {
                        }.getType());
                View layout = LayoutInflater.from(
                        MainActivity.this).inflate(
                        R.layout.dialog_update, null);
                dialog = MyDialog.getDialog(MainActivity.this,
                        layout, 0);

                dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                dialog.setCancelable(false);
                TextView tv_immediately = (TextView) layout
                        .findViewById(R.id.tv_immediately);
                TextView tv_back = (TextView) layout
                        .findViewById(R.id.tv_back);
                TextView tv_content = (TextView) layout
                        .findViewById(R.id.tv_content);

                tv_content.setText(bean.getData().getContent());
                tv_immediately.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        showCustomToast("下载中...");
                        showCurrentDialog("下载中...");
//                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        notificationInit();
                        new Thread() {
                            public void run() {
                                try {
                                    downFile(
                                            bean.getData().getUrl(),
                                            Environment
                                                    .getExternalStorageDirectory()
                                                    + "/baicaio/file/");
                                } catch (IOException e) {
                                    // block
                                    e.printStackTrace();
                                    sendMsg(-1);
                                }
                            }

                            ;
                        }.start();
                        dialog.dismiss();
                    }
                });
                tv_back.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onStateSuccessDataNull(String msg) {

            }

            @Override
            public void onError(VolleyError error, String msg) {

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
        });
    }

    //网络加载
    public void initDialog() {
        View mView = LayoutInflater.from(this).inflate(R.layout.progress_bar,
                null);
        mDialogText = (TextView) mView.findViewById(R.id.dialog_text);
        mDialog = new Dialog(this, R.style.dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setContentView(mView);
    }

    protected void showCurrentDialog(String text) {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mDialog == null) {
            initDialog();
        }
        mDialogText.setText(text);
        mDialog.show();
    }


    public void dismissCurrentDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}
