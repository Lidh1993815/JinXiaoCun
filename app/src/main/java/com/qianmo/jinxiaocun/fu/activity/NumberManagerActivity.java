package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

/**
 * 会员管理界面
 */
public class NumberManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "会员管理", R.mipmap.add);
        setContentView(requestView(R.layout.activity_number_manager));
    }

    @Override
    public void requestInit() {

    }

    @Override
    protected void rightImageAction() {
        startActivity(AddNumberActivity.class,false);
    }
}
