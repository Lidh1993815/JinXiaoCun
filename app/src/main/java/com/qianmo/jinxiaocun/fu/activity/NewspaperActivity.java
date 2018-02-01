package com.qianmo.jinxiaocun.fu.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 填写日报界面
 */
public class NewspaperActivity extends BaseActivity {


    @BindView(R.id.tv_start_time_choose)
    TextView mTvStartTimeChoose;
    @BindView(R.id.et_newspaper_content)
    EditText mEtNewspaperContent;
    @BindView(R.id.tv_newspaper_reportPeople)
    TextView mTvNewspaperReportPeople;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "填写日报", "完成");
        setContentView(requestView(R.layout.activity_newspaper));
        ButterKnife.bind(this);
        // TODO: 18-2-1 添加日报时间如何选择的
    }


    @Override
    protected void rightTextAction() {
        if (checkParam()) {
            commitData();//提交数据
        }
    }

    private void commitData() {

    }

    /**
     * 判断参数是否合理
     *
     * @return
     */
    private boolean checkParam() {
        return false;
    }

    @Override
    public void requestInit() {

    }

    @OnClick(R.id.tv_start_time_choose)
    public void clickAction(View view) {
//        DatePickerDialog d
    }

}
