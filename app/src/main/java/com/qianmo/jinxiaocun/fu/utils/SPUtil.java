package com.qianmo.jinxiaocun.fu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static com.qianmo.jinxiaocun.main.application.MyApplication.context;


/**
 * Created by wizardev on 17-7-13.
 */

public class SPUtil {
    private static SPUtil instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String STAFF_ID = "staffId";
    public static final String USER_URL = "url";
    public static final String USER_HEAD_IMG = "headImg";
    public static final String USER_NICK_NAME = "nickName";
    public static final String USER_ID = "id";
    public static final String ACCOUNT_BALANCE = "accountBalance";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";

    private final static String FUTURE_INFORMATION = "futureInformation";


    public static SPUtil getInstance() {
        if (instance == null) {
            instance = new SPUtil();
        }
        return instance;
    }

    private SPUtil() {

        sharedPreferences = context.getSharedPreferences(FUTURE_INFORMATION,
                Activity.MODE_PRIVATE);
    }

    public String getString(Context context, String key) {

        return sharedPreferences.getString(key, "");
    }
    public int getInt(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(FUTURE_INFORMATION,
                Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }

    public void putString(Context context, String key, String value) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public void putInt(Context context, String key, int value) {

        editor = sharedPreferences.edit();
        editor.putInt(key, value);

        editor.apply();
    }

    public void putDouble(Context context, String key, int value) {

        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void putLong(Context context, String key, long value) {

        editor = sharedPreferences.edit();
        editor.putLong(key, value);

        editor.apply();
    }

    public long getFuturesType(Context context, String key) {

        return sharedPreferences.getLong(key, 1);
    }

    public String getStaffId() {

        return sharedPreferences.getString(STAFF_ID, "");
    }

    public String getUrl() {
        return sharedPreferences.getString(USER_URL, "");
    }
}
