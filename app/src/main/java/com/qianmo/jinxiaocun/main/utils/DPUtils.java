package com.qianmo.jinxiaocun.main.utils;

import android.content.Context;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/08/28
 * desc   : 像素转换工具
 * version: 1.0
 */
public class DPUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
