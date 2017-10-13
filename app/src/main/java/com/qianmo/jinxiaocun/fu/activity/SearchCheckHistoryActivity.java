package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

/**
 * 查询盘点历史界面
 */
public class SearchCheckHistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "查询", -1);
        setContentView(requestView(R.layout.activity_search_check_history));
    }

    @Override
    public void requestInit() {

    }
}
