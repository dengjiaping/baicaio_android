package com.cnxxp.cabbagenet.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.ArticleDetailBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.db.SearchDBHelper;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.HtmlUtil;
import com.cnxxp.cabbagenet.utils.ImageViewAutoHeightUtils;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;

public class ArticleDetailActivity extends BaseActivity {
    private static final String TAG = "ArticleDetailActivity";
    private static final int SHARE_PR_CODE = 1;
    @Bind(R.id.iv_share_detail_face)
    ImageView ivShareDetailFace;
    @Bind(R.id.tv_hot_share_name)
    TextView tvHotShareName;
    @Bind(R.id.tv_hot_share_title)
    TextView tvHotShareTitle;
    @Bind(R.id.tv_hot_share_time)
    TextView tvHotShareTime;
    @Bind(R.id.iv_pic)
    ImageView ivPic;
    @Bind(R.id.wv_info)
    WebView wvInfo;

    @Bind(R.id.tv_likes)
    TextView tvLikes;
    @Bind(R.id.tv_zan)
    TextView tvZan;
    @Bind(R.id.tv_comments)
    TextView tvComments;
    @Bind(R.id.iv_likes)
    ImageView ivLikes;
    @Bind(R.id.activity_more_image)
    ImageView activityMoreImage;
    private String articleid;
    private ArticleDetailBean mArticleDetailBean;
    private SearchDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_share_order_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("晒单详情");
        wvInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Bundle bundle = new Bundle();
                bundle.putString("link", url);
                startActivity(WebActivity.class, bundle);
                return true;
            }
        });
        setTitleMoreImageVisibility(View.VISIBLE);
        activityMoreImage.setImageResource(R.mipmap.ic_share);

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        articleid = getIntent().getExtras().getString("articleid");
        HttpGoodsDetail(TAG, articleid);
        initSharepop();
    }

    private void HttpGoodsDetail(String tag, String articleid) {
        showLoadDialog();
        API.getSingleton().articleItem(tag, articleid, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<ArticleDetailBean> bean = mGson.fromJson(object.toString(), new TypeToken<MyBean<ArticleDetailBean>>() {
                }.getType());
                mArticleDetailBean = bean.getData();
                setData(bean.getData());
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

    private void setData(ArticleDetailBean data) {
        tvHotShareName.setText(data.getAuthor());
        String result = TimeUtil.programTimes(data.getAdd_time());
        if (result.length() > 8) {
            result = TimeUtil.transationSysTime(Long.parseLong(result));
        }
        if (data.getMylike() == 1) {
            ivLikes.setSelected(true);
        } else {
            ivLikes.setSelected(false);
        }
        tvHotShareTime.setText(result);
        String title = data.getTitle().replace("<span>", "<font color='#ff4444'>");
        title = title.replace("</span>", "</font>");
        tvHotShareTitle.setText(Html.fromHtml(title));
        wvInfo.loadData(HtmlUtil.getNewContent(data.getInfo()), "text/html; charset=UTF-8", null);
        tvLikes.setText(data.getLikes());
        tvZan.setText(data.getZan());
        tvComments.setText(data.getComments());
        ImageViewAutoHeightUtils.loadIntoUseFitWidth(this, data.getImg(), R.drawable.ic_original_pic, ivPic);
    }

    @OnClick({R.id.ll_zan, R.id.ll_likes, R.id.ll_comment, R.id.activity_more_image_bg})
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_zan:
                //点赞
                dianZan();
                break;
            case R.id.ll_likes:
                //收藏
                if (TextUtils.isEmpty(Config.PublicParams.usid)) {
                    startActivity(LoginActivity.class);
                } else {
                    httpCollect(TAG, getIntent().getExtras().getString("articleid"));
                }
                break;
            case R.id.ll_comment:
                //展开评论
                bundle = new Bundle();
                bundle.putString("from", "share");
                bundle.putString("shopid", articleid);
                startActivity(AllCommentActivity.class, bundle);
                break;
            case R.id.activity_more_image_bg:
                if (Build.VERSION.SDK_INT >= 23) {
                    if ((ContextCompat.checkSelfPermission(ArticleDetailActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                            (ContextCompat.checkSelfPermission(ArticleDetailActivity.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            ) {
                        if (sharePop.isShowing()) {
                            sharePop.dismiss();
                        } else {

                            sharePop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                        }
                    } else {
                        //请求获取摄像头权限
                        ActivityCompat.requestPermissions(ArticleDetailActivity.this,
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

        }
    }

    private void dianZan() {
        if (dbHelper == null) {
            dbHelper = SearchDBHelper.getInstance(ArticleDetailActivity.this);
        }
        if (dbHelper.HasZanData(Config.PublicParams.usid, articleid)) {
            showCustomToast("您已经点赞过该商品");
        } else {
            httpZan(TAG, getIntent().getExtras().getString("articleid"), "2");
            dbHelper.insertZan(Config.PublicParams.usid, articleid);
        }
    }

    private void httpZan(String tag, String id, String type) {
        API.getSingleton().zan(tag, id, type, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                tvZan.setText((Integer.parseInt(tvZan.getText().toString()) + 1) + "");
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

            }
        });
    }

    private void httpCollect(final String tag, String id) {
        API.getSingleton().setLikes(tag, id, "3", Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                showCustomToast(msg);
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                if (myBean.getData().equals("收藏成功")) {
                    tvLikes.setText((Integer.parseInt(tvLikes.getText().toString()) + 1) + "");
                    ivLikes.setSelected(true);
                } else {
                    tvLikes.setText((Integer.parseInt(tvLikes.getText().toString()) - 1) + "");
                    ivLikes.setSelected(false);
                }
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

            }
        });
    }

    private PopupWindow sharePop;

    private void initSharepop() {
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
                share(SHARE_MEDIA.WEIXIN_CIRCLE, mArticleDetailBean.getTitle());
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
                share(QQ, "白菜哦");
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.ll_qq_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //qq空间
                share(SHARE_MEDIA.QZONE, mArticleDetailBean.getTitle());
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.ll_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //复制链接
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData  
                ClipData mClipData = ClipData.newPlainText("Label", mArticleDetailBean.getFenxiang());
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
        new ShareAction(this).withMedia(getShareWeb(this, mArticleDetailBean.getFenxiang(), title, mArticleDetailBean.getTitle())).setPlatform(share_media).setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                httpShare();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        }).share();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SHARE_PR_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            if (sharePop == null) {
                initSharepop();
            }
            sharePop.showAsDropDown(getWindow().getDecorView());
        } else {
            showCustomToast("请先打开读写权限");
        }

    }

    private void httpShare() {
        API.getSingleton().share(TAG, Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {

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
