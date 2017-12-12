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

public class ChooseLeaveTypeActivity extends BaseActivity {

    @BindView(R.id.rl_chooseType_thing)
    RelativeLayout mRlChooseTypeThing;
    @BindView(R.id.rl_chooseType_sick)
    RelativeLayout mRlChooseTypeSick;
    @BindView(R.id.rl_chooseType_rest)
    RelativeLayout mRlChooseTypeRest;
    @BindView(R.id.rl_chooseType_other)
    RelativeLayout mRlChooseTypeOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "请假类型", -1);
        setContentView(requestView(R.layout.activity_choose_leave_type));
        ButterKnife.bind(this);
    }

    @Override
    public void requestInit() {

    }

    @OnClick({R.id.rl_chooseType_thing, R.id.rl_chooseType_sick, R.id.rl_chooseType_rest, R.id.rl_chooseType_other})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_chooseType_thing:
                intent.putExtra("leaveType", 1);
                intent.putExtra("leaveTitle", "事假");
                setResult(RESULT_OK, intent);
                break;
            case R.id.rl_chooseType_sick:
                intent.putExtra("leaveType", 2);
                intent.putExtra("leaveTitle", "病假");

                setResult(RESULT_OK, intent);
                break;
            case R.id.rl_chooseType_rest:
                intent.putExtra("leaveType", 3);
                intent.putExtra("leaveTitle", "休假");

                setResult(RESULT_OK, intent);
                break;
            case R.id.rl_chooseType_other:
                intent.putExtra("leaveType", 4);
                intent.putExtra("leaveTitle", "其他");

                setResult(RESULT_OK, intent);
                break;
        }
        finish();
    }
}
