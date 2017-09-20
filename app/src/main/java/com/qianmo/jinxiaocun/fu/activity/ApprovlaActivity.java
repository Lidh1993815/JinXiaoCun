package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovlaActivity extends BaseActivity {

    @BindView(R.id.my_approval_layout)
    LinearLayout myApprovalLayout;
    @BindView(R.id.my_release_layout)
    LinearLayout myReleaseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "审批", -1);
        setContentView(requestView(R.layout.activity_approvla));
        ButterKnife.bind(this);
    }

    @OnClick({R.id.my_approval_layout, R.id.my_release_layout})
    public void cliclAction(View view) {
        switch (view.getId()) {
            case R.id.my_approval_layout:
                startActivity(MyApprovalActivity.class, false);
                break;
            case R.id.my_release_layout:
                startActivity(MyReleaseActivity.class, false);
                break;
        }
    }

    @Override
    public void requestInit() {

    }
}
