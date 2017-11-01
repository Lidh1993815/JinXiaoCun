package com.qianmo.jinxiaocun.fu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/15
 * desc   : 解决与scrollView冲突的问题
 * version: 1.0
 */

public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

