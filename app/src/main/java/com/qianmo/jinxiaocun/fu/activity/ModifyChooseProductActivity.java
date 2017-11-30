package com.qianmo.jinxiaocun.fu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

public class ModifyChooseProductActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "修改选择商品", -1);
        setContentView(requestView(R.layout.activity_modify_choose_product));
    }

    @Override
    public void requestInit() {

    }
}
