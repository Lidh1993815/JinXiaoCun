package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

public class TaskDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "任务详情", -1);

        setContentView(requestView(R.layout.activity_task_detail));
    }

    @Override
    public void requestInit() {

    }
}