package com.qianmo.jinxiaocun.fu.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

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
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
