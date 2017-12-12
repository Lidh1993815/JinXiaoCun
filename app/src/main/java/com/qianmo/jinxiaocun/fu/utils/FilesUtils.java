package com.qianmo.jinxiaocun.fu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-11-15
 * desc :
 * version : 1.0
 */

public class FilesUtils {
    private static Context context;
    private static FilesUtils filesUtils;

    public static FilesUtils instance(Context context) {
        FilesUtils.context = context;
        if (filesUtils == null) {
            return new FilesUtils();
        } else {
            return filesUtils;
        }
    }

    //建立保存头像的路径及名称
    public File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getApplicationContext().getPackageName()
                + "/Files");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile;
        String mImageName = "image.png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    //保存图像
    public void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            /*Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());*/
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            //    Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            //  Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }
}
