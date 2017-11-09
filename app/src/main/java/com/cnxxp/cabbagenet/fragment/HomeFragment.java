package com.cnxxp.cabbagenet.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.CouponChangeActivity;
import com.cnxxp.cabbagenet.activity.MainActivity;
import com.cnxxp.cabbagenet.activity.OriginalActivity;
import com.cnxxp.cabbagenet.activity.QuanActivity;
import com.cnxxp.cabbagenet.activity.ScoreChangeActivity;
import com.cnxxp.cabbagenet.activity.SearchActivity;
import com.cnxxp.cabbagenet.activity.WebActivity;
import com.cnxxp.cabbagenet.adapter.HomeAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.bean.AdBean;
import com.cnxxp.cabbagenet.bean.DiscountBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.imageloader.GlideImageLoader;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.cnxxp.cabbagenet.R.id.mXRecyclerView;

/**
 * 首页Fragment
 * Created by Administrator on 2017/3/21 0021.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    public MainActivity mainActivity;
    private View inflate;
    private XRecyclerView mRecyclerView;
    private View mHeadView;
    private HomeAdapter mHomeAdapter;
    //  private MarqueeView marqueeView;
    private Banner vpHome;
    private LinearLayout llbg1;
    private LinearLayout llbg2;
    private LinearLayout llbg3;
    private LinearLayout llbg4;
    private LinearLayout llbg5;
    private LinearLayout llbg6;
    private LinearLayout llbg7;
    private LinearLayout llbg8;
    private LinearLayout llSousuo;
    private int page;
    private List<DiscountBean> dataList;
    private List<String> bannerData;
    private boolean isHasBannerData;
    private List<AdBean> adBean;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_home, null);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {
        mainActivity = (MainActivity) getActivity();
        initHeadView();
        llSousuo = (LinearLayout) inflate.findViewById(R.id.rl_sousou);
        llSousuo.setOnClickListener(this);
        mRecyclerView = (XRecyclerView) inflate.findViewById(mXRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView.addHeaderView(mHeadView);
        mHomeAdapter = new HomeAdapter(getActivity());
        mRecyclerView.setAdapter(mHomeAdapter);
    }


    @Override
    protected void initEvents() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                dataList.clear();
                // httpGetShopList(TAG, page);
                httpGetHourDay(TAG, "1");
                httpGetAd(TAG, "16");
            }

            @Override
            public void onLoadMore() {
                httpGetShopList(TAG, page);
            }
        });
        vpHome.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (isHasBannerData) {
                    Bundle bundle = new Bundle();
                    bundle.putString("link", adBean.get(position).getUrl());
                    startActivity(WebActivity.class, bundle);
                }
            }
        });
    }

    @Override
    protected void initData() {
        initVp();
        page = 1;
        dataList = new ArrayList<>();
        httpGetHourDay(TAG, "1");
        // httpGetShopList(TAG, page);
        httpGetAd(TAG, "16");
    }


    private void initHeadView() {
        mHeadView = View.inflate(getActivity(), R.layout.head_home, null);
        vpHome = (Banner) mHeadView.findViewById(R.id.vp_home);
        mHeadView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vpHome.getLayoutParams();
        lp.width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 3;
        vpHome.setLayoutParams(lp);
        vpHome.setVisibility(View.GONE);
        llbg1 = (LinearLayout) mHeadView.findViewById(R.id.ll_class1);
        llbg1.setOnClickListener(this);
        llbg2 = (LinearLayout) mHeadView.findViewById(R.id.ll_class2);
        llbg2.setOnClickListener(this);
        llbg3 = (LinearLayout) mHeadView.findViewById(R.id.ll_class3);
        llbg3.setOnClickListener(this);
        llbg4 = (LinearLayout) mHeadView.findViewById(R.id.ll_class4);
        llbg4.setOnClickListener(this);
        llbg5 = (LinearLayout) mHeadView.findViewById(R.id.ll_class5);
        llbg5.setOnClickListener(this);
        llbg6 = (LinearLayout) mHeadView.findViewById(R.id.ll_class6);
        llbg6.setOnClickListener(this);
        llbg7 = (LinearLayout) mHeadView.findViewById(R.id.ll_class7);
        llbg7.setOnClickListener(this);
        llbg8 = (LinearLayout) mHeadView.findViewById(R.id.ll_class8);
        llbg8.setOnClickListener(this);
      
    
    }

    @Override
    public void onStart() {
        super.onStart();
        vpHome.startAutoPlay();
        // marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        vpHome.stopAutoPlay();
        //  marqueeView.stopFlipping();
    }

    private void initVp() {
        isHasBannerData = false;
        bannerData = new ArrayList<>();
        vpHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        vpHome.setImageLoader(new GlideImageLoader());
        //设置图片集合
        //设置banner动画效果
        vpHome.setBannerAnimation(Transformer.DepthPage);
        vpHome.isAutoPlay(true);
        //设置轮播时间
        vpHome.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        vpHome.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
    }


    @Override
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_class1:
                //国内
                mainActivity.switchFragment(1);
                mainActivity.switchDiscount = 1;
                break;
            case R.id.ll_class2:
                //海淘
                mainActivity.switchFragment(1);
                mainActivity.switchDiscount = 2;
                break;
            case R.id.ll_class3:
                //积分兑换
                startActivity(ScoreChangeActivity.class);
                break;
            case R.id.ll_class4:
                //9.9包邮
                mainActivity.switchDiscount = 3;
                mainActivity.switchFragment(1);
                break;
            case R.id.ll_class5:
                //马爸爸的券coupons
//                bundle = new Bundle();
//                bundle.putInt("index", 2);
//                startActivity(OriginalActivity.class, bundle);
                startActivity(QuanActivity.class);

                break;
            case R.id.ll_class6:
                //热门攻略
                bundle = new Bundle();
                bundle.putInt("index", 1);
                startActivity(OriginalActivity.class, bundle);
                break;
            case R.id.ll_class7:
                //热门爆料
                mainActivity.switchDiscount = 4;
                mainActivity.switchDiscount = 4;
                mainActivity.switchFragment(1);
                break;
            case R.id.ll_class8:
                //优惠券
                startActivity(CouponChangeActivity.class);
                break;
            case R.id.rl_sousou:
                //搜索
                startActivity(SearchActivity.class);
                break;

        }

    }

    private void httpGetAd(String tag, String s) {
        API.getSingleton().adB(tag, s, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<AdBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<AdBean>>>() {
                }.getType());
                vpHome.setVisibility(View.VISIBLE);
                isHasBannerData = true;
                adBean = myBean.getData();
                bannerData.clear();
                for (int i = 0; i < adBean.size(); i++) {
                    bannerData.add(adBean.get(i).getContent());
                }
                vpHome.setImages(bannerData);
                vpHome.start();
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                vpHome.setVisibility(View.GONE);
            }

            @Override
            public void onStateFinish() {

            }

            @Override
            public void onAppKeyError() {

            }

            @Override
            public void onStateFail(String msg) {
                bannerData.add("R.drawable.banner1");
                bannerData.add("R.drawable.banner2");
                bannerData.add("R.drawable.banner3");
                vpHome.setImages(bannerData);
                vpHome.start();
            }

            @Override
            public void onError(VolleyError error, String msg) {

            }
        });
    }

    private void httpGetHourDay(String tag, final String type) {
        API.getSingleton().hourDay(tag, type, new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<DiscountBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<DiscountBean>>>() {
                }.getType());
                if (type.equals("1")) {
                    if (myBean.getData().size() < 5) {
                        mHomeAdapter.setHours(myBean.getData().size());
                        dataList.addAll(myBean.getData());
                    } else {
                        for (int i = 0; i < 5; i++) {
                            dataList.add(myBean.getData().get(i));
                        }
                        mHomeAdapter.setHours(5);
                    }
                    httpGetHourDay(TAG, "0");
                } else {
                    if (myBean.getData().size() < 5) {
                        mHomeAdapter.setDay(myBean.getData().size());
                        dataList.addAll(myBean.getData());
                    } else {
                        for (int i = 0; i < 5; i++) {
                            dataList.add(myBean.getData().get(i));
                        }
                        mHomeAdapter.setDay(5);
                    }

                    httpGetShopList(TAG, page);
                }
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                if (type.equals("1")) {
                    mHomeAdapter.setHours(0);
                    httpGetHourDay(TAG, "0");
                } else {
                    mHomeAdapter.setDay(0);
                    httpGetShopList(TAG, page);
                }
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
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }
        });
    }

    private void httpGetShopList(String tag, final int page) {

        API.getSingleton().shopList(tag, "2", "", "", String.valueOf(page), "", "", "", new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                MyBean<List<DiscountBean>> myBean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<DiscountBean>>>() {
                }.getType());

                if (page == 1) {
                    dataList.addAll(myBean.getData());
                    mHomeAdapter.setListData(dataList);
                } else {
                    mHomeAdapter.addList(myBean.getData());
                }
                HomeFragment.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                showCustomToast(msg);
            }

            @Override
            public void onStateFinish() {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.refreshComplete();
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
