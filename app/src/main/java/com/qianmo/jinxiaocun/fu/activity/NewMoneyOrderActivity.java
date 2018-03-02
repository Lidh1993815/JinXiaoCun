package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
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
    private String mMoneyType;
    private int type;//(1收入单 2支出单)
    private static final int INTENT_TYPE = 333;
    private int moneyFlowsType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoneyType = getIntent().getStringExtra("type");
        if (mMoneyType != null && mMoneyType.equals("income")) {
            //新建收入单
            toolBar = new MyToolBar(this, R.mipmap.zoujiant, "新建收入单", "提交");
            type = 1;

        } else if (mMoneyType != null && mMoneyType.equals("expenditure")) {
            //新建费用单
            toolBar = new MyToolBar(this, R.mipmap.zoujiant, "新建费用单", "提交");
            type = 2;

        }
        setContentView(requestView(R.layout.activity_new_money_order));
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (mMoneyType != null && mMoneyType.equals("income")) {
            //新建收入单
            tvOrderType.setText("收入单");
        } else if (mMoneyType != null && mMoneyType.equals("expenditure")) {
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
                Intent intent = new Intent(this, ChooseCostProjectActivity.class);
//                startActivity(ChooseCostProjectActivity.class,false);
                startActivityForResult(intent, INTENT_TYPE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_TYPE && resultCode == RESULT_OK && data != null) {

            moneyFlowsType = data.getIntExtra("moneyFlowsType", 0);
            String typeName = data.getStringExtra("typeName");
            tvChooseProject.setText(StringUtil.getString(typeName));
        }
    }
}
