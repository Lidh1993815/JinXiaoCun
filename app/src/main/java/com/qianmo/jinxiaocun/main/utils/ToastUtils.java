package com.qianmo.jinxiaocun.main.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import com.qianmo.jinxiaocun.R;

/**
 * Created by Administrator on 2016/2/26.
 * 自定义吐司
 */
public class ToastUtils {
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
            view.setBackgroundResource(R.drawable.shape_toast_background);
            view.setAlpha(1f);
            view.setPadding(DPUtils.dip2px(context, 15), DPUtils.dip2px(context, 8),
                    DPUtils.dip2px(context, 15), DPUtils.dip2px(context, 8));
            view.setTextSize(15);
            view.setTextColor(Color.parseColor("#FFffff"));
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
