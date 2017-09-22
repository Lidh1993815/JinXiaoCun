package com.qianmo.jinxiaocun.fu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.widget.ImageAndText;
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
    @BindView(R.id.card_apply)
    ImageAndText cardApply;
    @BindView(R.id.leave_apply)
    ImageAndText leaveApply;
    @BindView(R.id.reim_apply)
    ImageAndText reimApply;
    @BindView(R.id.material_apply)
    ImageAndText materialApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "审批", -1);
        setContentView(requestView(R.layout.activity_approvla));
        ButterKnife.bind(this);
    }

    @OnClick({R.id.my_approval_layout, R.id.my_release_layout,R.id.card_apply,R.id.leave_apply,R.id.reim_apply,R.id.material_apply})
    public void cliclAction(View view) {
        switch (view.getId()) {
            //我要审批的
            case R.id.my_approval_layout:
                startActivity(MyApprovalActivity.class, false);
                break;
                //我发起的
            case R.id.my_release_layout:
                startActivity(MyReleaseActivity.class, false);
                break;
                //补卡申请
            case R.id.card_apply:
                startActivity(CardApplyActivity.class, false);
                break;
                //请假申请
            case R.id.leave_apply:
                startActivity(LeaveApplyActivity.class, false);
                break;
                //报销申请
            case R.id.reim_apply:
                startActivity(ReimbursementActivity.class, false);
                break;
                //物料申请
            case R.id.material_apply:
                startActivity(MaterialActivity.class, false);
                break;
        }
    }

    @Override
    public void requestInit() {

    }
}
