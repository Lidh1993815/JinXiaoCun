package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 进货管理界面
 */
public class PurchaseManagerActivity extends BaseActivity {

    @BindView(R.id.purchase_order_history)
    RelativeLayout purchaseOrderHistory;
    @BindView(R.id.purchase_order_inquire)
    RelativeLayout purchaseOrderInquire;
    @BindView(R.id.return_layout)
    LinearLayout returnLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "进货管理", -1);
        setContentView(requestView(R.layout.activity_purchase_manager));
        ButterKnife.bind(this);
    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.purchase_order_history, R.id.purchase_order_inquire,R.id.return_layout})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.purchase_order_history:
                startActivity(PurchaseOrSalesHistoryActivity.class, false);
                break;
            case R.id.return_layout:
                //跳转到退货历史界面
                startActivity(ReturnHistoryActivity.class, false);

                break;
            case R.id.purchase_order_inquire:
                break;

        }
    }
}
