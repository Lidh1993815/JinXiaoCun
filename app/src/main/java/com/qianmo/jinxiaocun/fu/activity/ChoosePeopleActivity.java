package com.qianmo.jinxiaocun.fu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

/**
 * 选择人员界面
 */
public class ChoosePeopleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "选择人员", -1);
        setContentView(requestView(R.layout.activity_choose_people));
    }

    @Override
    public void requestInit() {

    }
}
