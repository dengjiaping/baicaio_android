package com.cnxxp.cabbagenet.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class CompressionImage {
	//把bitmap转换成String
	public static String bitmapToString(String filePath) {
         Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        try {
            if(baos!=null){
                baos.close();
                baos=null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(b, Base64.DEFAULT);

    }

    /**
     * 
     * @param filePath 文件路径
     * @param present  压缩比例 1-100
     * @return
     */
    public static String bitmapToString(String filePath, int present) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, present, baos);
        byte[] b = baos.toByteArray();
        try {
            if(baos!=null){
                baos.close();
                baos=null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(b, Base64.DEFAULT);

    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
