package com.cnxxp.cabbagenet.glidemodule;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by Administrator on 2016/10/25.
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);//设置为ARGB_8888
        final int diskCache = 100 * 1024 * 1024;//文件缓存的最大值
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();//默认的缓存大小
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();//默认的bitmap管理池大小
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        builder.setMemoryCache( new LruResourceCache(customMemoryCacheSize) );
        builder.setBitmapPool( new LruBitmapPool(customBitmapPoolSize ));
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                File cacheLocation = new File(context.getExternalCacheDir(),
                        "kk_image_cache");
                if (!cacheLocation.mkdirs()) {
                    cacheLocation.mkdirs();
                }
                return DiskLruCacheWrapper.get(cacheLocation,diskCache);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
