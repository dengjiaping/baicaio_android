package com.cnxxp.cabbagenet.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by admin on 2017/5/9 0009.
 */
public class WebActivity extends BaseActivity {
    private static final String TAG = "WebActivity";
    @Bind(R.id.web_link)
    WebView webLink;
    @Bind(R.id.myProgressBar)
    ProgressBar progress;
    private String link;
    
    private Dialog loadDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("返回白菜哦");
        initthisDialog();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        link = getIntent().getExtras().getString("link");
        if (TextUtils.isEmpty(link)) {
            showCustomToast("暂无数据");
        } else {
            showthisLoadDialog();
            loadLink(link);
        }

    }


    private void loadLink(String link) {
        // 在当前的浏览器中响应
        WebSettings webSettings = webLink.getSettings();
        webSettings.setJavaScriptEnabled(true);
//      mWebView.getSettings().setDefaultFontSize(14);
        webSettings.setAppCacheEnabled(false);
        // // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        // //自适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        webLink.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progress != null) {
                    if (newProgress == 100) {
                        progress.setVisibility(View.GONE);
                        dismissthisLoadDialog();
                    } else {
                        progress.setProgress(newProgress);
                    }
                }

                super.onProgressChanged(view, newProgress);
            }
        });

        webLink.setWebViewClient(new WebViewClient() {
            // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                }
                return true;
            }
        });

        //打开链接
        webLink.loadUrl(link);
    }


    private void initthisDialog() {
        View mView = LayoutInflater.from(this).inflate(R.layout.web_progress_bar, null);
        loadDialog = new Dialog(this, R.style.dialog);
        loadDialog.setCanceledOnTouchOutside(false);
        loadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadDialog.getWindow().setContentView(mView);
    }

    /**
     * 显示r.strings 文本
     */
    private void showthisLoadDialog() {
        if (loadDialog != null && !loadDialog.isShowing()) {
            loadDialog.show();
        }
    }

    /**
     * 隐藏 dialog
     */
    protected void dismissthisLoadDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

}
