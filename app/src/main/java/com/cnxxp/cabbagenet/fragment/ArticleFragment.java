package com.cnxxp.cabbagenet.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.adapter.photoChangeAdapter;
import com.cnxxp.cabbagenet.base.BaseFragment;
import com.cnxxp.cabbagenet.utils.PopupwindowUtils;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.yuyh.library.imgsel.common.Constant.config;

/**
 * Created by admin on 2017/4/8 0008.
 */

public class ArticleFragment extends BaseFragment {
    public View inflate;
    @Bind(R.id.tv_article_class)
    TextView tvArticleClass;
    @Bind(R.id.et_article_title)
    EditText etArticleTitle;
    @Bind(R.id.et_article_detail)
    EditText etArticleDetail;
    @Bind(R.id.iv_add)
    GridView ivAdd;
    private photoChangeAdapter madapter;
    //requestCode
    private int PICTURE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_article, null);
        ButterKnife.bind(this, inflate);
        super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }

    @Override
    protected void initViews() {
        madapter = new photoChangeAdapter(getActivity(), loader);
        ivAdd.setAdapter(madapter);
    }


    @Override
    protected void initEvents() {
        ivAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp = madapter.selectedPicture.get(position);
                if (temp.equals("add")) {
                    config = initPhotoSelect(4 - madapter.selectedPicture.size() + 1);
                    ImgSelActivity.startActivity(ArticleFragment.this, config, PICTURE);
                }
            }
        });
        ivAdd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String temp = madapter.selectedPicture.get(position);
                if (!temp.equals("add")) {
                    PopupwindowUtils.initPopupWindow(getView(), LayoutInflater.from(getActivity()), "是否删除图片?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopupwindowUtils.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopupwindowUtils.delete();
                            removePhoto(position);
                        }
                    });
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void removePhoto(int index) {
        madapter.selectedPicture.remove(index);
        if (madapter.selectedPicture.size() < 4 && !madapter.selectedPicture.contains("add")) {
            madapter.selectedPicture.add("add");
        }
        madapter.notifyDataSetChanged();
    }


    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    private ImgSelConfig initPhotoSelect(int size) {
// 自由配置选项
        ImgSelConfig config = new ImgSelConfig.Builder(getActivity(), loader)
                // 是否多选, 默认 true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当 multiSelect 为 true 的时候配置，默认为 true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.parseColor("#f97300"))
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#0E8EE7"))
                // 返回图标 ResId
                .backResId(R.mipmap.shop_image_back)
                // 标题
                .title("选择图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar 背景色
                .titleBgColor(Color.parseColor("#0E8EE7"))
                // 裁剪大小。needCrop 为 true 的时候配置
                //.cropSize(1, 1, 600, 400)
                .needCrop(false)
                // 第一个是否显示相机，默认 true
                .needCamera(true)
                // 最大选择图片数量，默认 9
                .maxNum(size)
                .build();
        return config;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_cancle, R.id.tv_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                //取消发表
                break;
            case R.id.tv_release:
                //立即发表
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);

            madapter.selectedPicture.addAll(madapter.selectedPicture.size() - 1, pathList);
            if (madapter.selectedPicture.size() > 4) {
                madapter.selectedPicture.remove("add");
            }
            madapter.notifyDataSetChanged();
        }

    }
}
