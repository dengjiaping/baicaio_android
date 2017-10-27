package com.cnxxp.cabbagenet.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cnxxp.cabbagenet.utils.EmojiDataBaoMan;
import com.cnxxp.cabbagenet.utils.EmojiDataCommen;
import com.cnxxp.cabbagenet.utils.EmojiDataFunny;
import com.cnxxp.cabbagenet.xrecyclerview.callback.RecyclerViewItemClickListener;

/**
 * Created by admin on 2017/8/30 0030.
 */
public class EmojiPagerAdapter extends PagerAdapter {
    private EmojiSelectInterface emojiSelectInterface;


    private Context mContext;

    private String[] titles;

    public interface EmojiSelectInterface {
        void emojiItemClick(String content, int img);
    }


    public void setEmojiSelectInterface(EmojiSelectInterface emojiSelectInterface) {
        this.emojiSelectInterface = emojiSelectInterface;
    }

    public EmojiPagerAdapter(Context context) {
        this.mContext = context;
        titles = new String[]{"常用", "暴走漫画", "搞笑"};
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        RecyclerView recyclerView = new RecyclerView(mContext);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 8));
        final EmojiItemAdapter adapter = new EmojiItemAdapter(mContext);
        recyclerView.setAdapter(adapter);
        if (position == 0) {
            adapter.setListData(EmojiDataCommen.getEmojiList());
        } else if (position == 1) {
            adapter.setListData(EmojiDataBaoMan.getEmojiList());
        } else {
            adapter.setListData(EmojiDataFunny.getEmojiList());
        }
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (emojiSelectInterface != null) {
                    emojiSelectInterface.emojiItemClick(adapter.mList.get(postion).getContent(), adapter.mList.get(postion).getImageUri());
                }
            }
        });
        ((ViewPager) container).addView(recyclerView);
        return recyclerView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RecyclerView) object);
    }
}
