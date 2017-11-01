package com.qianmo.jinxiaocun.main.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qianmo.jinxiaocun.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * Created by zkl on 2016/3/30.
 * 工具类:
 * 1、getBytesFromFile 把文件读取出来转成字节数组
 * 2、dateFormate 把时间字符串转成毫秒数，如1992-12-01 18:22:32转成175515211125毫秒
 * 3、把指定时间字符串转成毫秒数，如1992-12-01 18:22:32转成175515211125毫秒
 * 4、根据ImageView控件的大小压缩本地图片
 * 5、得到缓存目录，并创建指定文件夹
 * 6、获取到当前应用程序的versionCode
 * 7、获取到当前应用程序的versionName
 * 8、json转成对象
 * 9、判断手机是否有SD卡。
 * 10、判断是否连接网络
 * 11、模糊图片
 * 12、获取应用缓存图片大小
 * 13、获取缓存目录
 * 14、删除缓存目录下指定文件
 * 15、尺寸转换类
 * 16、获得屏幕宽度
 * 17、获得屏幕高度
 * 18、拷贝文字到剪切板
 * 19、 字符串转unicode
 * 20、unicode 转字符串
 * 21、压缩bitmap
 * 22、获取状态栏高度，注意，要在onWindowFocusChanged中调用，在onCreate中获取高度为0
 * 23、判断手机连接的网络类型(2G,3G,4G)
 * 24.获取网络类型
 * 25、判断当前App处于前台还是后台状态
 * 26、判断当前是否是WIFI连接状态
 * 27.进度框
 * 28.传进来一个double格式化保留2位
 * 29.隐藏虚拟键盘
 * 30.显示虚拟键盘
 * 31.md5加密 大写
 * 32.自定义吐司
 * 33.隐藏手机号中间四位
 * 34.去掉doub值后面的.0
 * 35.跳转到指定界面
 * 36.获取当前年月日 格式 yyyy-MM-dd
 * 37.获取当月中第一个是星期几，二得到日历中需要上个月几天来填充
 * 38.根据年月日获取所有的天
 * 39.复制到剪切板
 * 40.格式化时间，根据提供的毫秒值，转换成 xx天xx时xx分xx秒
 * 41.把英文单词按字母首字母从a到z排序
 * 42压缩Bitmap，有损质量的压缩方法
 */
public class ZhUtils {

    /**
     * 1、把文件读取出来转成字节数组
     *
     * @param file 目标文件
     * @return 文件字节数组
     * @throws IOException
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        // 获取文件大小
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // 文件太大，无法读取
            throw new IOException("File is to large " + file.getName());
        }
        // 创建一个数据来保存文件数据
        byte[] bytes = new byte[(int) length];
        // 读取数据到byte数组中
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }

    /**
     * 2、把时间字符串转成毫秒数，如1992-12-01 18:22:32转成175515211125毫秒
     * 默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @param dates 时间
     * @return
     */
    public static long dateFormate(String dates) {
        return dateFormate(dates, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 3、把指定时间字符串转成毫秒数，如1992-12-01 18:22:32转成175515211125毫秒
     * dates和format要统一
     *
     * @param dates  时间
     * @param format 用户指定的格式 如yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long dateFormate(String dates, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // 此处会抛异常
        Date date = null;
        try {
            date = sdf.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 获取毫秒数
        long longDate = date.getTime();
        return longDate;
    }

    /**
     * 4、根据ImageView控件的大小压缩本地图片
     */
    public static class ImageZip {
        /**
         * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
         *
         * @param options   压缩条件，包含图片的宽高信息
         * @param reqWidth  压缩的目标宽度
         * @param reqHeight 压缩的目标高度
         * @return 压缩比例
         */
        private static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                                int reqHeight) {
            int width = options.outWidth;// 实际宽度
            int height = options.outHeight;
            int inSampleSize = 1;
            // 宽高大于显示大小
            if (width > reqWidth || height > reqHeight) {
                int widthRadio = Math.round(width * 1.0f / reqWidth);
                int heightRadio = Math.round(height * 1.0f / reqHeight);
                // inSampleSize越大压的越厉害。
                // 在这里去大的更省内存。取消的会是一部分不显示
                inSampleSize = Math.min(widthRadio, heightRadio);//取小的不会导致小图片有一边出现空白
                // inSampleSize = Math.max(widthRadio, heightRadio);
            } else {

            }
            return inSampleSize;
        }

        /**
         * 根据图片需要显示的宽和高对图片进行压缩
         *
         * @param path   本地图片路径
         * @param width  控件的宽
         * @param height 控件的高（实际要现实的高）
         * @return 图片bitmap
         */
        public static Bitmap decodeSampledBitmapFromPath(String path, int width,
                                                         int height) {
            // 获取图片的宽和高，并不把图片加载到内存当中。
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);// options获得实际的宽和高
            options.inSampleSize = caculateInSampleSize(options, width, height);
            // 使用获取到的inSampleSize
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            return bitmap;
        }

        /**
         * 根据imageview的宽高，压缩path路径下的图片
         *
         * @param path      目标文件路径
         * @param imageView 图片控件
         * @return 压缩后的图片bitmap
         */
        public static Bitmap decodeSampledBitmapFromPath(String path, ImageView imageView) {
            // 获取图片的宽和高，并不把图片加载到内存当中。
            ImageSize imageViewSize = getImageViewSize(imageView);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);// options获得实际的宽和高
            options.inSampleSize = caculateInSampleSize(options, imageViewSize.width, imageViewSize.height);
            // 使用获取到的inSampleSize
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            return bitmap;
        }

        /**
         * getMaxWidth 是在api16以上，所以通过反射获取imgaeview的某个属性值
         *
         * @param object
         * @param fileName
         * @return
         */
        private static int getImageViewFieldVaule(Object object, String fileName) {

            int value = 0;

            try {
                Field field = ImageView.class.getDeclaredField(fileName);
                field.setAccessible(true);

                int fieldVaue = field.getInt(object);
                if (fieldVaue > 0 && fieldVaue < Integer.MAX_VALUE) {
                    value = fieldVaue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return value;
        }

        public static class ImageSize {
            public int width;
            public int height;
        }

        /**
         * 根据imageview获得适当的压缩的宽和高
         *
         * @param imageView
         * @return
         */
        public static ImageSize getImageViewSize(ImageView imageView) {
            ImageSize imageSize = new ImageSize();
            DisplayMetrics displayMetrics = imageView.getContext().getResources()
                    .getDisplayMetrics();

            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
            // 获取宽
            // int width = (lp.width ==
            // LayoutParams.WRAP_CONTENT?0:imageView.getWidth());
            int width = imageView.getWidth();// 获取imageview的实际宽度

            if (width <= 0) {// 等于0说明宽度是wrap_ccontent或fill_parent
                width = lp.width;// 获取imagview在layout中申明的宽度
            }
            if (width <= 0) {
                // width = imageView.getMaxWidth();// 检查最大值
                width = getImageViewFieldVaule(imageView, "mMaxWidth");
            }

            if (width <= 0) {
                // 最不幸，宽度等于屏幕宽度
                width = displayMetrics.widthPixels;
            }

            int height = imageView.getHeight();// 获取imageview的实际宽度

            if (height <= 0) {
                height = lp.height;// 获取imagview在layout中申明的宽度
            }
            if (height <= 0) {
                // height = imageView.getMaxHeight();// 检查最大值
                height = getImageViewFieldVaule(imageView, "mMaxHeight");
            }

            if (height <= 0) {
                // 最不幸，宽度等于屏幕宽度
                height = displayMetrics.heightPixels;
            }

            imageSize.height = height;
            imageSize.width = width;

            return imageSize;
        }
    }


    /**
     * 5、得到缓存目录，并创建指定文件夹
     *
     * @param context    上下文
     * @param folderName 文件夹名 可以为空
     * @return 自定义缓存文件的路径
     */
    public static String getDiskCacheDir(Context context, String folderName) {
        File cachePath = getCacheDir(context);
        if (folderName.length() > 0) {
            File file = new File(cachePath, folderName);
            if (!file.exists()) {
                file.mkdir();
            }
            return file.getAbsolutePath();
        } else
            return cachePath.getAbsolutePath();
    }

    /**
     * 6、获取到当前应用程序的versionCode
     *
     * @param context
     * @return
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 7、获取到当前应用程序的versionName
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 8、json转成对象
     *
     * @param s: json
     * @return json对象
     */
    public static <T> T string2Object(Class<T> t, String s) {

        JSONObject jsonObject = JSONObject.parseObject(s);
        T object = JSON.parseObject(jsonObject.toJSONString(), t);
        return object;
    }

    /**
     * 9、判断手机是否有SD卡。
     *
     * @return 有SD卡返回true，没有返回false。
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 10、判断是否连接网络
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 11、模糊图片
     *
     * @param bm
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blurImage(Bitmap bm, Context context) {
        Bitmap bitmap = bm.copy(bm.getConfig(), true);
        final RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, bm, Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(20 /* e.g. 3.f */);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);
        return bitmap;
    }

    /**
     * 12、获取应用缓存图片大小
     *
     * @param context 上下文
     * @return 文件大小 byte
     */
    public static long getCacheSize(Context context) {
        long leng = 0;
        File cacheDir = getCacheDir(context);
        File[] listFiles = cacheDir.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].getName().endsWith(".png") ||
                    listFiles[i].getName().endsWith(".jpg")
                    || listFiles[i].getName().endsWith(".jpeg") || listFiles[i].getName().endsWith(".bmp"))
                leng += listFiles[i].length();
        }
        return leng;
    }

    /**
     * 13、获取缓存目录
     *
     * @param context 上下文
     * @return 缓存目录File对象
     */
    public static File getCacheDir(Context context) {
        File root = null;
        if (hasSDCard() || !Environment.isExternalStorageRemovable()) {
            root = context.getExternalCacheDir();
        } else {
            root = context.getCacheDir();
        }
        return root;
    }

    /**
     * 14、删除缓存目录下指定文件
     *
     * @param context
     * @param type    0:删除所有文件 1：删除图片
     */
    public static void deleteAllCacheImageFile(Context context, int type) {
        File fileDir = getCacheDir(context);
        File[] listFiles = fileDir.listFiles();
        if (type == 1) {
            for (int i = 0; i < listFiles.length; i++) {
                String name = listFiles[i].getName().toLowerCase();
                if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".bmp")) {
                    listFiles[i].delete();
                }
            }
        } else {
            for (int i = 0; i < listFiles.length; i++) {
                listFiles[i].delete();
            }
        }
    }

    /**
     * 14、删除缓存目录下所有文件
     *
     * @param context
     */
    public static void deleteAllCacheImageFile(Context context) {
        deleteAllCacheImageFile(context, 0);
    }

    /**
     * 15、尺寸转换类
     */
    public static class DimenTrans {
        /**
         * 将px值转换为dip或dp值，保证尺寸大小不变
         *
         * @param pxValue 像素值
         * @return
         */
        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        /**
         * 将dip或dp值转换为px值，保证尺寸大小不变
         *
         * @param dipValue dp尺寸
         * @return
         */
        public static int dip2px(Context context, float dipValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        }

        /**
         * 将px值转换为sp值，保证文字大小不变
         *
         * @param pxValue 像素值
         * @return
         */
        public static int px2sp(Context context, float pxValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f);
        }

        /**
         * 将sp值转换为px值，保证文字大小不变
         *
         * @param spValue 字体大小值
         * @return
         */
        public static int sp2px(Context context, float spValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }
    }

    /**
     * 16、获得屏幕宽度
     *
     * @param context
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 17、获得屏幕高度
     *
     * @param context
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 18、拷贝文字到剪切板
     *
     * @param content 拷贝内容
     * @param context
     */
    public static void copy2Clipboard(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.toString().trim());
    }

    /**
     * 19、 字符串转unicode
     *
     * @param string 要转成unicode的字符串
     * @return
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\U" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /**
     * 20、unicode 转字符串
     *
     * @param unicode 要转成字符串的unicode
     * @return
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\U");
        if (unicode.contains("\\u") || unicode.contains("\\U")) {

            for (int i = 1; i < hex.length; i++) {

                // 转换出每一个代码点
                int data = Integer.parseInt(hex[i], 16);

                // 追加成string
                string.append((char) data);
            }
        } else {
            return unicode;
        }

        return string.toString();
    }

    /**
     * 21、压缩bitmap
     *
     * @param image bitmap
     * @return 压缩过的bitmap
     */
    public static Bitmap bitmapCompress(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 300) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        // be
        // 1 / 2;// 设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;// 降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

        return bitmap;// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 22、获取状态栏高度，注意，要在onWindowFocusChanged中调用，在onCreate中获取高度为0
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 23、判断手机连接的网络类型(2G,3G,4G)
     */
    static class Constants {
        /**
         * Unknown network class
         */
        public static final int NETWORK_CLASS_UNKNOWN = 0;

        /**
         * wifi net work
         */
        public static final int NETWORK_WIFI = 1;

        /**
         * "2G" networks
         */
        public static final int NETWORK_CLASS_2_G = 2;

        /**
         * "3G" networks
         */
        public static final int NETWORK_CLASS_3_G = 3;

        /**
         * "4G" networks
         */
        public static final int NETWORK_CLASS_4_G = 4;

    }

    /**
     * 24.获取网络类型
     *
     * @param context 上下文
     * @return
     */
    public static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return Constants.NETWORK_CLASS_2_G;

            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return Constants.NETWORK_CLASS_3_G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return Constants.NETWORK_CLASS_4_G;

            default:
                return Constants.NETWORK_CLASS_UNKNOWN;
        }
    }

    /**
     * 25、判断当前App处于前台还是后台状态
     *
     * @param context
     * @return
     */
    public static boolean isApplicationBackground(final Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 26、判断当前是否是WIFI连接状态
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 27.进度框
     */
    public static class ProgressDialog {
        /**
         * 加载中的弹窗
         *
         * @param context    上下文
         * @param content    加载中的内容
         * @param canCelable 是否可以取消
         * @return
         */
        public static android.app.ProgressDialog showProgressDialog(Context context, String content, boolean canCelable) {
            android.app.ProgressDialog pd = new android.app.ProgressDialog(context);
            pd.setMessage(content);
//            pd.setCancelable(canCelable);
            pd.setCanceledOnTouchOutside(canCelable);//点击其他区域不消失，点击返回键消失
            return pd;
        }

        /**
         * 加载中的弹窗，默认内容：“加载中，请稍后...”
         *
         * @param context    上下文
         * @param canCelable 是否可以取消
         * @return
         */
        public static android.app.ProgressDialog showProgressDialog(Context context, boolean canCelable) {
            return showProgressDialog(context, "加载中,请稍后...", canCelable);
        }

        /**
         * 加载中的弹窗，默认内容：“加载中，请稍后...”，不能取消
         *
         * @param context 上下文
         * @return
         */
        public static android.app.ProgressDialog showProgressDialog(Context context) {
            return showProgressDialog(context, "加载中,请稍后...", false);
        }
    }

    /**
     * 28.传进来一个double格式化保留2位
     *
     * @param d
     * @return
     */
    public static String keep2Double(double d) {
        return (new DecimalFormat("0.00").format(d));
    }

    public static String keep1Double(float d) {
        return (new DecimalFormat("0.0").format(d));
    }

    /**
     * 29.隐藏虚拟键盘
     */
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }
    }

    /**
     * 30.显示虚拟键盘
     */
    public static void ShowKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

    }

    /**
     * 31.md5加密
     *
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 32.自定义吐司
     */
    static class ToastUtils {
        public static Toast mToast;
        public static TextView view;

        /**
         * 自定义吐司样式，方便统一修改
         *
         * @param context
         * @param msg
         */
        public static void MyToast(Context context, String msg) {
            if (mToast == null) {
                mToast = new Toast(context);
            }
            if (view == null) {
                view = new TextView(context);
                //TODO
//                view.setBackgroundResource(R.drawable.gray4dp_circle_shape);
//                view.setAlpha(1f);
                view.setPadding(10, 5, 10, 5);
                view.setTextSize(15);
                view.setTextColor(Color.parseColor("#ffffff"));
                view.setGravity(Gravity.CENTER);
            }
            view.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(view);
            mToast.show();
        }

        /**
         * 自定义吐司样式，方便统一修改
         *
         * @param context
         * @param msgId
         */
        public static void MyToast(Context context, int msgId) {
            MyToast(context, context.getResources().getString(msgId));
        }
    }

    /**
     * 33.隐藏手机号中间4位
     *
     * @param code
     * @return
     * @throws Exception
     */
    public static String getCode(String code) throws Exception {
        if (code != null && !TextUtils.isEmpty(code) && code.length() == 11) {

            return code.substring(0, 3) + "****" + code.substring(code.length() - 4);
        } else throw new Exception("手机号码有误");
    }

    /**
     * 34.去掉doub值后面的.0
     *
     * @param d
     * @return
     */
    public static String clearDoubleZero(double d) {
        String dStr = d + "";
        if (dStr.endsWith(".0")) {
            return dStr.substring(0, dStr.length() - 2);
        } else {
            return dStr;
        }
    }

    /**
     * 35.跳转到指定界面
     *
     * @param context 上下文
     * @param intent  目标activity
     * @param network 是否检查网络
     */
    public static void jump2Activity(Context context, Intent intent, boolean network) {
        if (network) {
            //检查网络
            if (!isNetworkConnected(context)) {
                ToastUtils.MyToast(context, R.string.net_error);
                return;
            }
            //可以进行其他判断
            context.startActivity(intent);
        }
    }

    /**
     * 36.获取当前年月日 格式 yyyy-MM-dd
     *
     * @param MonthD 月份的加减 负数减
     * @param dayD   天数的加减
     * @return
     */
    public static String getCurrentDate(int MonthD, int dayD) {
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, MonthD);
        calendar.add(Calendar.DATE, dayD);//增加和减少dayD天
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = day + "";
        if (day < 10)
            dayStr = "0" + day;
        String monthStr = month + "";
        if (month < 10) {
            monthStr = "0" + month;
        }
        return year + "-" + monthStr + "-" + dayStr;
    }

    /**
     * 37.获取当月中第一个是星期几，二得到日历中需要上个月几天来填充
     *
     * @return
     */
    public static int getDaysFirstOfMoth() {
        GregorianCalendar now = new GregorianCalendar();
        int intent = 0;
        now.set(Calendar.DAY_OF_MONTH, 1);
        int week = now.get(Calendar.DAY_OF_WEEK);
        int firstDayOfWeek = now.getFirstDayOfWeek();
        while (firstDayOfWeek != week) {
            ++intent;
            now.add(Calendar.DAY_OF_MONTH, -1);
            week = now.get(Calendar.DAY_OF_WEEK);
        }
        return intent;
    }

    /**
     * 38.根据年月日获取所有的天
     *
     * @param date 年月日 yyyy-MM-dd
     * @return
     */
    public static List<Integer> getMonthDays(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) - 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= maxDay; i++) {
            list.add(i);//[i-1] = i;
        }
        return list;
    }

    /**
     * 39.复制到剪切板
     *
     * @param context
     * @param content
     */
    public static void copy2ClipBoard(Context context, String content) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(content);
    }

    /**
     * 40.格式化时间，根据提供的毫秒值，转换成 xx天xx时xx分xx秒
     *
     * @param ms
     * @return
     */
    public static String formatTime(long ms, String unitHour, String unitMinute, String unitSecond, String unit) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        if (unit != null)
            return strHour + unit + strMinute + unit + strSecond;
        else
            return strHour + unitHour + strMinute + unitMinute + strSecond + unitSecond;
    }

    public static String formatTimeByDay(long ms, String unitDay, String unitHour, String unitMinute, String unitSecond) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        if (unitDay != null) {
            return strDay + unitDay + strHour + unitHour + strMinute + unitMinute + strSecond + unitSecond;
        } else {
            return strHour + unitHour + strMinute + unitMinute + strSecond + unitSecond;
        }
    }

    public static String formatTime(long ms, String unitHour, String unitMinute, String unitSecond) {
        return formatTime(ms, unitHour, unitMinute, unitSecond, null);
    }

    public static String formatTime(long ms, String unit) {
        return formatTime(ms, null, null, null, unit);
    }

    /**
     * 41.把英文单词按字母首字母从a到z排序
     *
     * @param list
     * @return
     */
    public static List<Object> sortWordA2Z(List<String> list) {
        // Collator 类是用来执行区分语言环境的 String 比较的，这里选择使用CHINA
        Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        // 使根据指定比较器产生的顺序对指定对象数组进行排序。
        Object[] objects = list.toArray();
        Arrays.sort(objects, comparator);
        return Arrays.asList(objects);
    }

    /**
     * 42压缩Bitmap，有损质量的压缩方法
     * 最开始使用这个来进行压缩，但是始终压缩不到32k这么小。后来看高手的解释才明白，这种压缩方法之所以称之为质量压缩，
     * 是因为它不会减少图片的像素。它是在保持像素的前提下改变图片的位深及透明度等，来达到压缩图片的目的。
     * 进过它压缩的图片文件大小会有改变，但是导入成bitmap后占得内存是不变的。因为要保持像素不变，所以它就无法无限压缩，
     * 到达一个值之后就不会继续变小了。显然这个方法并不适用与缩略图，其实也不适用于想通过压缩图片减少内存的适用，
     * 仅仅适用于想在保证图片质量的同时减少文件大小的情况而已。
     *
     * @param bitmap 要压缩的Bitmap
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressImageQuality(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        //定义一个质量值，用于压缩Bitmap
        int quality = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            //把图片的大小压成100kb以内
            if (quality <= 0)
                break;
            quality -= 10;
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        //压缩好的Bitmap放入字节数组输入流
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(bais);
    }

    /**
     * 43.给定秒值，返回时分秒
     *
     * @param seconds 秒
     * @return
     */
    public static String getTime(int seconds) {
        int second = 0;
        int minute = 0;
        int hour = 0;
        String time = "";
        if (seconds > 60) {
            second = seconds % 60;//秒
            minute = seconds / 60;//分
            if (minute > 60) {//够一个小时
                hour = minute / 60;//小时
                minute = minute % 60;//分，显示用的
            }
        } else {
            second = seconds;
        }
        if (hour > 0) {
            if (hour < 10) {
                time += "0" + hour;
            } else {
                time += "" + hour;
            }
        } else {
            time += "00";
        }

        if (minute > 0) {
            if (minute < 10) {
                time += ":0" + minute;
            } else {
                time += ":" + minute;
            }
        } else {
            time += ":00";
        }
        if (second > 0) {
            if (second < 10) {
                time += ":0" + second;
            } else {
                time += ":" + second;
            }
        } else {
            time += ":00";
        }
        return time;
    }

    /**
     * 43.给定秒值，返回时分秒
     *
     * @param seconds 秒
     * @return 返回三个时间的集合
     */
    public static List<String> getTimeReturnList(int seconds) {
        List<String> mData = new ArrayList<>();
        int second = 0;
        int minute = 0;
        int hour = 0;
        if (seconds > 60) {
            second = seconds % 60;//秒
            minute = seconds / 60;//分
            if (minute > 60) {//够一个小时
                hour = minute / 60;//小时
                minute = minute % 60;//分，显示用的
            }
        } else {
            second = seconds;
        }
        if (hour > 0) {
            if (hour < 10) {
                mData.add("0" + hour);
            } else {
                mData.add(hour + "");
            }
        } else {
            mData.add("00");
        }

        if (minute > 0) {
            if (minute < 10) {
                mData.add("0" + minute);
            } else {
                mData.add(minute + "");
            }
        } else {
            mData.add("00");
        }
        if (second > 0) {
            if (second < 10) {
                mData.add("0" + second);
            } else {
                mData.add("" + second);
            }
        } else {
            mData.add("00");
        }
        return mData;
    }

    /**
     * 四舍五入保留两位小数
     * @param d
     * @return
     */
    public static float formatDouble2(float d) {

        BigDecimal bg = new BigDecimal(d).setScale(1, RoundingMode.UP);
        return bg.floatValue();
    }

    /**
     * 是否含有特殊符号
     *
     * @param str 待验证的字符串
     * @return 是否含有特殊符号
     */
    public static boolean hasSpecialCharacter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
}


