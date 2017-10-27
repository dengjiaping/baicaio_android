package com.cnxxp.cabbagenet.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FileUtils {
    /**
     * 读取表情配置文件
     *
     * @param context
     * @return
     */
    public static List<String> getEmojiFile(Context context) {
        try {
            List<String> list = new ArrayList<String>();
            InputStream in = context.getResources().getAssets().open("emoji");
            BufferedReader br = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // first SDCard is in the device, if yes, the pic will be stored in the SDCard, folder "HaHa_Picture"
    // second if SDCard not exist, the picture will be stored in /data/data/HaHa_Picture
    // file will be named by the customer
    public static String saveImage(Context context, String strFileName, ImageView imageView) {
        Bitmap bitmap = convertViewToBitmap(imageView);
        String strPath = getSDPath();
        String filePath = "";
        File imageFile = null;
        String fileName = "";
        try {
            File destDir = new File(strPath);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            fileName = strFileName + ".jpg";
            imageFile = new File(strPath + "/" + fileName);
            imageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            filePath = imageFile.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    imageFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
        return filePath;
    }

    private static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    public static String getSDPath() {
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            return Environment.getExternalStorageDirectory().toString();
        } else
            return "/data/data/com.cnxxp.mystory/";
    }


    public static String saveMyBitmap(String bitName, Bitmap mBitmap) throws IOException {
        File f = new File(getSDPath() + File.separator + bitName + ".png");
        f.createNewFile();
        FileOutputStream fOut = null;
        fOut = new FileOutputStream(f);
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();
        return f.toString();
    }

    public static String saveMyBitmapcover(String file, Bitmap mBitmap) throws IOException {
        File f = new File(file);
        f.createNewFile();
        FileOutputStream fOut = null;
        fOut = new FileOutputStream(f);
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();
        return f.toString();
    }

    public static String getCameraPath() {

        String file = Environment.getExternalStorageDirectory() + "/CabbageNet/";
        File f = new File(file);
        if (!f.exists()) {
            f.mkdirs();
        }
        return file;

    }


}
