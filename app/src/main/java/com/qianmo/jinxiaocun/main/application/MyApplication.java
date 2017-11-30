package com.qianmo.jinxiaocun.main.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.qianmo.jinxiaocun.main.okhttp.base.OkhttpBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Application0 一般 1 折扣 2 限时抢购 3 团购 4预筹 5种你喜欢 6私人定制 7即将上市 8养你喜欢
 */
public class MyApplication extends Application {

    private static List<Activity> activities;
    public static Application application;
    public static Context context;

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        context=getApplicationContext();
        application = this;
////        内网 地址
//        OkhttpBase.BASE_URL = "http://192.168.0.118/git/nobaba_server_PHP/api/";

        //外网
        OkhttpBase.BASE_URL = "http://touzigang.haohuajinrong.cn/HKoption";

//        OkhttpBase.BASE_URL = "http://192.168.0.133:8080";
//        OkhttpBase.H5 = "http://www.nongbabi.com/";//h5地址
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * 把Activity放入集合当中，统一管理。
     *
     * @param activity 要添加的Activity对象
     */
    public static void addActivity(Activity activity) {
        if (activities == null)
            activities = new ArrayList<>();
        activities.add(activity);
    }

    /**
     * 关闭集合中所有的activity界面
     */
    public static void killAllActivity() {
        if (activities != null && activities.size() > 0) {
            for (Activity activity : activities) {
                activity.finish();
            }
        }
    }

    /**
     * 关闭应用，关闭所有的界面，并结束进程
     */
    public static void closeApplication() {
        killAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
