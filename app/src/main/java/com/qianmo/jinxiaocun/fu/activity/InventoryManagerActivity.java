package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

/**
 * 库存管理界面
 */
public class InventoryManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "库存管理", -1);
        setContentView(requestView(R.layout.activity_inventory_manager));
    }

    @Override
    public void requestInit() {

    }
}
