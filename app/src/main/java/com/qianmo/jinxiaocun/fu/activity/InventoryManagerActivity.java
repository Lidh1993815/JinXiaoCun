package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 库存管理界面
 */
public class InventoryManagerActivity extends BaseActivity {

    @BindView(R.id.inventory_search)
    RelativeLayout inventorySearch;
    @BindView(R.id.inventory_check)
    RelativeLayout inventoryCheck;
    @BindView(R.id.inventory_alarm)
    RelativeLayout inventoryAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "库存管理", -1);
        setContentView(requestView(R.layout.activity_inventory_manager));
        ButterKnife.bind(this);
    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.inventory_search,R.id.inventory_check,R.id.inventory_alarm})
    public void clickAction(View view) {
        switch (view.getId()) {
            //库存搜索
            case R.id.inventory_search:
                startActivity(SearchInventoryActivity.class,false);
                break;
            //库存盘点
            case R.id.inventory_check:
                break;
            //库存警报
            case R.id.inventory_alarm:
                break;
        }
    }
}
