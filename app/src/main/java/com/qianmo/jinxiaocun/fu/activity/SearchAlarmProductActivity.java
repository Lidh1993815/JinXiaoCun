package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

/**
 * 查询库存报警界面
 */
public class SearchAlarmProductActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "查询", -1);
        setContentView(requestView(R.layout.activity_search_alarm_product));
    }

    @Override
    public void requestInit() {

    }
}
