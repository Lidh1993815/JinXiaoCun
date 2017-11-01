package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
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
 * 销售管理界面
 */
public class SalesManagerActivity extends BaseActivity {

    @BindView(R.id.sales_order)
    RelativeLayout salesOrder;
    @BindView(R.id.sales_order_return)
    RelativeLayout salesOrderReturn;
    @BindView(R.id.sales_report)
    RelativeLayout salesReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "销售管理", -1);

        setContentView(requestView(R.layout.activity_sales_manager));
        ButterKnife.bind(this);
    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.sales_order, R.id.sales_order_return, R.id.sales_report})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.sales_order:
                Intent intent = new Intent(SalesManagerActivity.this, PurchaseOrSalesHistoryActivity.class);
                intent.putExtra("type", "sales");
                startActivity(intent);
                // startActivity(PurchaseOrSalesHistoryActivity.class,false);
                break;
            case R.id.sales_order_return:
                Intent i = new Intent(SalesManagerActivity.this, SalesOrPurchaseReturnHistoryActivity.class);
                i.putExtra("type", "sales");
                startActivity(i);
                break;
            case R.id.sales_report:
                //用户报表
                startActivity(SalesDetailOfShopActivity.class,false);
                break;
        }
    }
}
