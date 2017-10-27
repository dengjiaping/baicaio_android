package com.cnxxp.cabbagenet.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.activity.MainActivity;
import com.cnxxp.cabbagenet.adapter.DiscountAdapter;
import com.cnxxp.cabbagenet.api.API;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.bean.DiscountBean;
import com.cnxxp.cabbagenet.bean.MyBean;
import com.cnxxp.cabbagenet.httputils.VolleyInterface;
import com.cnxxp.cabbagenet.xrecyclerview.ProgressStyle;
import com.cnxxp.cabbagenet.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class DiscountInfoFragment extends BaseFragment {
    private static final String TAG = "DiscountInfoFragment";
    private View inflate;
    XRecyclerView mXRecyclerView;
    private DiscountAdapter mdDiscountAdapter;
    private int page;//数据刷新的页码
    private int index;
    private MainActivity mainActivity;
    private String cid;
    private String origId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_discountinfo, null);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {
        mainActivity = (MainActivity) getActivity();
        mXRecyclerView = (XRecyclerView) inflate.findViewById(R.id.mXRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getIndexData(index);

            }

            @Override
            public void onLoadMore() {
                getIndexData(index);

            }
        });
        mdDiscountAdapter = new DiscountAdapter(getActivity());
        mXRecyclerView.setAdapter(mdDiscountAdapter);

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        if (mainActivity.switchDiscount != -1 && mainActivity.switchDiscount != 5) {
            index = mainActivity.switchDiscount;
        } else {
            index = 0;
        }
        page = 1;

        cid = "";
        getIndexData(index);
    }

    public void setIndex(int index) {
        if (this.index != index) {
            this.index = index;
            page = 1;
            mXRecyclerView.onRefresh();
        }

    }


    public void setCid(String cid, String origid) {
        this.cid = cid;
        this.origId = origid;
        page = 1;
        getIndexData(index);
    }

    /**
     * @param index
     */
    private void getIndexData(int index) {
        switch (index) {
            case 0:
                HttpHomeAbroad(TAG, "2", cid, page);
                break;
            case 1:
                HttpHomeAbroad(TAG, "0", cid, page);
                break;
            case 2:
                HttpHomeAbroad(TAG, "1", cid, page);
                break;
            case 3:
                HttpFreeShipping(TAG, page, cid);
                break;
            case 4:

                break;
        }
    }

    private void HttpHomeAbroad(String tag, String tp, String cid, final int page) {
        showLoadDialog();
        API.getSingleton().shopList(tag, tp, cid, origId, String.valueOf(page), "", "", "", new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {
                if(mGson!=null){
                    MyBean<List<DiscountBean>> bean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<DiscountBean>>>() {
                    }.getType());
                    if (DiscountInfoFragment.this.page == 1) {
                        mdDiscountAdapter.setListData(bean.getData());
                    } else {
                        mdDiscountAdapter.addList(bean.getData());
                    }
                    DiscountInfoFragment.this.page++;
                }
               
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                if (page == 1) {
                    mdDiscountAdapter.clearList();
                }
            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                if (mXRecyclerView != null) {
                    mXRecyclerView.loadMoreComplete();
                    mXRecyclerView.refreshComplete();
                }

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


    //包邮
    private void HttpFreeShipping(String tag, final int page, String cid) {
        showLoadDialog();
        API.getSingleton().tagsearch(tag, page, cid, "", "9.9包邮", new VolleyInterface(getActivity()) {
            @Override
            public void onStateSuccess(String msg, JSONObject object) {

                MyBean<List<DiscountBean>> bean = mGson.fromJson(object.toString(), new TypeToken<MyBean<List<DiscountBean>>>() {
                }.getType());
                if (DiscountInfoFragment.this.page == 1) {
                    mdDiscountAdapter.setListData(bean.getData());
                } else {
                    mdDiscountAdapter.addList(bean.getData());
                }
                DiscountInfoFragment.this.page++;
            }

            @Override
            public void onStateSuccessDataNull(String msg) {
                if (page == 1) {
                    mdDiscountAdapter.clearList();
                }
            }

            @Override
            public void onStateFinish() {
                dismissLoadDialog();
                mXRecyclerView.refreshComplete();
                mXRecyclerView.loadMoreComplete();
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
