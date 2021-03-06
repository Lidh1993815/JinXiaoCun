package com.qianmo.jinxiaocun.fu.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.qianmo.jinxiaocun.main.utils.DPUtils;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/15
 * desc   : 禁止SwipeRefreshLayout下拉刷新的功能
 * version: 1.0
 */
public class ForbiddenSwipeRefreshLayout extends SwipeRefreshLayout {
    public ForbiddenSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ForbiddenSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置刷新突变距离顶部的距离
        setProgressViewOffset(false, 0, DPUtils.dip2px(context,48));
        setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
