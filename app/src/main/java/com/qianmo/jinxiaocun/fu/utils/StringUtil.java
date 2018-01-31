package com.qianmo.jinxiaocun.fu.utils;

import android.text.TextUtils;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 18-1-29
 * desc :
 * version : 1.0
 */

public class StringUtil {
    public static String getString(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        } else {
            return content;
        }
    }
}
