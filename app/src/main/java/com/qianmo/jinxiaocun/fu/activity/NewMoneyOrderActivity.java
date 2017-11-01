package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新建钱流单
 */
public class NewMoneyOrderActivity extends BaseActivity {

    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_choose_project)
    TextView tvChooseProject;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        if (type != null && type.equals("income")) {
            //新建收入单
            toolBar = new MyToolBar(this, R.mipmap.zoujiant, "新建收入单", "提交");
        } else if (type != null && type.equals("expenditure")) {
            //新建费用单
            toolBar = new MyToolBar(this, R.mipmap.zoujiant, "新建费用单", "提交");
        }
        setContentView(requestView(R.layout.activity_new_money_order));
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (type != null && type.equals("income")) {
            //新建收入单
            tvOrderType.setText("收入单");
        } else if (type != null && type.equals("expenditure")) {
            //新建费用单
            tvOrderType.setText("支出单");
        }
    }

    @Override
    public void requestInit() {

    }


    @OnClick({R.id.tv_choose_project})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_project:
                startActivity(ChooseCostProjectActivity.class,false);
                break;
        }
    }
}
