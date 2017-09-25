package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

public class TourDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "巡店查询详情", "完成");
        setContentView(requestView(R.layout.activity_tour_detail));
    }

    @Override
    public void requestInit() {

    }
}
