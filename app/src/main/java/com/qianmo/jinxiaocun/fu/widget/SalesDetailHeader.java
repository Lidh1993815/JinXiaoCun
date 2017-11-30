package com.qianmo.jinxiaocun.fu.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.qianmo.jinxiaocun.R;

/**
 * RecyclerView的FooterView，简单的展示一个TextView
 */
public class SalesDetailHeader extends RelativeLayout {

    public SalesDetailHeader(Context context) {
        super(context);
        init(context);
    }

    public SalesDetailHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SalesDetailHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        inflate(context, R.layout.sales_report_header_layout, this);
    }
}