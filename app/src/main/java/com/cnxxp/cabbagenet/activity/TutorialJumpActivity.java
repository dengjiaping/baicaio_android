package com.cnxxp.cabbagenet.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.bean.AdBean;
import com.cnxxp.cabbagenet.bean.GoodsDetailBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.config.Config;
import com.cnxxp.cabbagenet.db.SearchDBHelper;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.utils.HtmlUtil;
import com.cnxxp.cabbagenet.utils.TimeUtil;
import com.cnxxp.cabbagenet.view.tagview.entity.Tag;
import com.cnxxp.cabbagenet.view.tagview.widget.TagCloudLinkView;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yuyh.library.imgsel.utils.LogUtils;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;

public class TutorialJumpActivity extends BaseActivity {
    public static final String TAG = "TutorialJumpActivity";
    private static final int SHARE_PR_CODE = 1;


    @Bind(R.id.tv_html)
    WebView tvHtml;

    @Bind(R.id.tv_comments)
    TextView tvComments;
    @Bind(R.id.tv_zan)
    TextView tvZan;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_likes)
    TextView tvLikes;
    @Bind(R.id.tv_from_time)
    TextView tvFromTime;
    @Bind(R.id.activity_more_image)
    ImageView activityMoreImage;
    @Bind(R.id.iv_likes)
    ImageView ivLikes;

    @Bind(R.id.tag_first)
    TagCloudLinkView Taglist;
    @Bind(R.id.activity_goods_detail)
    RelativeLayout rlRoot;
    @Bind(R.id.tv_org)
    TextView tvOrg;
    @Bind(R.id.iv_ad)
    ImageView ivAd;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.ll_comment)
    LinearLayout llComment;
    private GoodsDetailBean mBean;
    private String shopid;
    private SearchDBHelper dbHelper;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("商品详情");
        findViewById(R.id.activity_back_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
                finish();
            }
        });

    }

    @Override
    protected void initEvents() {


    }

    @Override
    protected void initData() {

        shopid = getIntent().getExtras().getString("shopid");
        if(getIntent().getExtras().getBoolean("type")){
            type = "bl";
            llComment.setVisibility(View.GONE);
        }else {
            type = "";
        }
        HttpGoodsDetail(TAG, shopid,type);
        httpAd(TAG);
        initSharepop();
    }

    private void HttpGoodsDetail(String tag, String id, String type) {
        showLoadDialog();
        API.getSingleton().shopItem(tag, id, Config.PublicParams.usid, type, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<GoodsDetailBean> bean = mGson.fromJson(object.toString(), new TypeToken<MyBean<GoodsDetailBean>>() {
                }.getType());
                mBean = bean.getData();
                setData(mBean);
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

    //设置网络数据
    private void setData(final GoodsDetailBean bean) {
        setHtmlData(bean.getContent());
        String title = bean.getTitle().replace("<span>", "<font color='#ff4444'>");
        title = title.replace("</span>", "</font>");
        tvTitle.setText(Html.fromHtml(title));
        tvPrice.setText(bean.getPrice());
        tvZan.setText(bean.getZan());
        tvComments.setText(bean.getComments());
        tvLikes.setText(bean.getLikes());
        if (bean.getMylike() == 1) {
            ivLikes.setSelected(true);
        } else {
            ivLikes.setSelected(false);
        }
        String result = TimeUtil.programTimes(bean.getAdd_time());
        if (result.length() > 8) {
            result = TimeUtil.transationSysTime(Long.parseLong(result));
        }
        if (bean.getOrig() != null) {

            tvFromTime.setText(bean.getOrig().getName() + " | " + result);
        } else {
            tvFromTime.setText(result);
        }
        setTagData(bean.getTag_cache());
        if (bean.getOrig() != null) {
            tvOrg.setText(bean.getOrig().getName());
            tvOrg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("tag", bean.getOrig().getName());
                    bundle.putString("orig_id", bean.getOrig().getId());
                    startActivity(ShopSearchActivity.class, bundle);
                }
            });
        }

    }

    private void setTagData(String tag_cache) {

        String[] tags = tag_cache.split("\\|");
        for (int i = 0; i < tags.length; i++) {
            if (!TextUtils.isEmpty(tags[i].trim())) {
                Taglist.add(new Tag(1, tags[i]));
            }
        }
        Taglist.drawTags();
        Taglist.setOnTagSelectListener(new TagCloudLinkView.OnTagSelectListener() {
            @Override
            public void onTagSelected(Tag tag, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("tag", tag.getText());
                startActivity(TagSearchActivity.class, bundle);
            }
        });

    }


    //设置web数据
    private void setHtmlData(String content) {
        
        tvHtml.loadData(HtmlUtil.getNewContent("<p>"+content+"</p>"), "text/html; charset=UTF-8", null);
    }

    @OnClick({R.id.ll_comment, R.id.ll_like, R.id.ll_zan, R.id.tv_link, R.id.activity_more_image_bg})
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_comment:
                bundle = new Bundle();
                bundle.putString("from", "goods");
                bundle.putString("shopid", shopid);
                startActivity(AllCommentActivity.class, bundle);
                break;
            case R.id.ll_zan:

                if (dbHelper == null) {
                    dbHelper = SearchDBHelper.getInstance(TutorialJumpActivity.this);
                }
                if (dbHelper.HasZanData(Config.PublicParams.usid, shopid)) {
                    showCustomToast("您已经点赞过该商品");
                } else {
                    httpZan(TAG, shopid, "1");
                    dbHelper.insertZan(Config.PublicParams.usid, shopid);
                }
                break;
            case R.id.tv_link:
                if (mBean != null) {
                    bundle = new Bundle();
                    bundle.putString("type", "0");
                    if (mBean.getGo_link() != null) {
                        bundle.putString("link", mBean.getGo_link().getLink());
                    } else {
                        bundle.putString("link", "");
                    }
                    startActivity(WebActivity.class, bundle);
                }
                break;
            case R.id.activity_more_image_bg:
                if (Build.VERSION.SDK_INT >= 23) {
                    if ((ContextCompat.checkSelfPermission(TutorialJumpActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                            (ContextCompat.checkSelfPermission(TutorialJumpActivity.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            ) {
                        if (sharePop.isShowing()) {
                            sharePop.dismiss();
                        } else {

                            sharePop.showAtLocation(rlRoot, Gravity.BOTTOM, 0, 0);
                        }

                    } else {
                        //请求获取摄像头权限
                        ActivityCompat.requestPermissions(TutorialJumpActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, SHARE_PR_CODE);

                    }
                } else {
                    if (sharePop.isShowing()) {
                        sharePop.dismiss();
                    } else {

                        sharePop.showAtLocation(rlRoot, Gravity.BOTTOM, 0, 0);
                    }
                }

                break;
            case R.id.ll_like:
                if (TextUtils.isEmpty(Config.PublicParams.usid)) {
                    startActivity(LoginActivity.class);
                } else {
                    httpCollect(TAG, shopid);
                }
                break;

        }
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
                share(SHARE_MEDIA.WEIXIN_CIRCLE, mBean.getTitle());
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
                share(SHARE_MEDIA.QZONE, mBean.getTitle());
                sharePop.dismiss();
            }
        });
        view.findViewById(R.id.ll_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //复制链接
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData  
                ClipData mClipData = ClipData.newPlainText("Label", mBean.getFenxiang());
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
        UMImage thumb = new UMImage(context, mBean.getImg());
        UMWeb web = new UMWeb(url);
        web.setThumb(thumb);
        web.setDescription(description);
        web.setTitle(title);
        return web;
    }

    private void share(SHARE_MEDIA share_media, String title) {
        new ShareAction(this).withMedia(getShareWeb(this, mBean.getFenxiang(), title, mBean.getTitle())).setPlatform(share_media).setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                httpShare(TAG);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
        API.getSingleton().setLikes(tag, id, "1", Config.PublicParams.usid, new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<String> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<String>>() {
                }.getType());
                if (myBean.getData().equals("收藏成功")) {
                    tvLikes.setText((Integer.parseInt(tvLikes.getText().toString()) + 1) + "");
                    ivLikes.setSelected(true);
                } else {
                    tvLikes.setText((Integer.parseInt(tvLikes.getText().toString()) - 1) + "");
                    ivLikes.setSelected(false);
                }
                showCustomToast(myBean.getData());
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

    private void httpShare(String tag) {
        API.getSingleton().share(tag, Config.PublicParams.usid, new VolleyInterface(this) {
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


    private void httpAd(String tag) {
        API.getSingleton().adB(tag, "20", new VolleyInterface(this) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                final MyBean<List<AdBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<AdBean>>>() {
                }.getType());
                Glide.with(TutorialJumpActivity.this).load(myBean.getData().get(0).getContent()).into(ivAd);
                ivAd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("link", myBean.getData().get(0).getUrl());
                        startActivity(WebActivity.class, bundle);
                    }
                });

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
