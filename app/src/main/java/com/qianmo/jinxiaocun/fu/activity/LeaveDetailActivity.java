package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.bean.ApplyLeaveDetailBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 请假详情
 */
public class LeaveDetailActivity extends BaseActivity implements OnActionListener {

    @BindView(R.id.tv_leaveDetail_startTime)
    TextView mTvLeaveDetailStartTime;
    @BindView(R.id.tv_leaveDetail_endTime)
    TextView mTvLeaveDetailEndTime;
    @BindView(R.id.tv_leaveDetail_type)
    TextView mTvLeaveDetailType;
    @BindView(R.id.tv_leaveDetail_days)
    TextView mTvLeaveDetailDays;
    @BindView(R.id.tv_leaveDetail_content)
    TextView mTvLeaveDetailContent;
    @BindView(R.id.tv_showProgressPrompt)
    TextView mTvShowProgressPrompt;
    @BindView(R.id.tv_startName)
    TextView mTvStartName;
    @BindView(R.id.tv_start_firstName)
    TextView mTvStartFirstName;
    @BindView(R.id.tv_startTime)
    TextView mTvStartTime;
    @BindView(R.id.tv_audit_firstName)
    TextView mTvAuditFirstName;
    @BindView(R.id.tv_auditorName)
    TextView mTvAuditorName;
    @BindView(R.id.tv_auditStatus)
    TextView mTvAuditStatus;
    @BindView(R.id.tv_auditTime)
    TextView mTvAuditTime;
    @BindView(R.id.ll_applyDetail_progressLayout)
    ConstraintLayout mLlApplyDetailProgressLayout;
    @BindView(R.id.ll_applyDetail_bottomLayout)
    LinearLayout mLlApplyDetailBottomLayout;
    @BindView(R.id.tv_applyDetail_execute)
    TextView mTvApplyDetailExecute;
    @BindView(R.id.tv_applyDetail_continueExecute)
    TextView mTvApplyDetailContinueExecute;
    private int mAPplyClockId;
    private static final String TAG = "LeaveDetailActivity";
    private int mApprovalType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mAPplyClockId = intent.getIntExtra("aPplyClockId", 0);
        mApprovalType = intent.getIntExtra("approvalType", 0);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "请假详情", -1);
        setContentView(requestView(R.layout.activity_leave_detail));
        ButterKnife.bind(this);
        requestData();
        initView();
    }

    private void initView() {
        if (mApprovalType == 1) {
            mLlApplyDetailProgressLayout.setVisibility(View.GONE);
            mLlApplyDetailBottomLayout.setVisibility(View.VISIBLE);
        } else {
            mLlApplyDetailProgressLayout.setVisibility(View.VISIBLE);
            mLlApplyDetailBottomLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void requestInit() {

    }

    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("aPplyClockId", mAPplyClockId + "");
        OkhttpUtils.sendRequest(1001, 0, ApiConfig.APPLY_CLOCK_DETAILS_ID, okhttpParam, this);
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                Log.i(TAG, "1001: " + ret);
                if (!TextUtils.isEmpty(ret)) {
                    ApplyLeaveDetailBean applyLeaveDetailBean = (ApplyLeaveDetailBean) JsonUitl.stringToObject(ret, ApplyLeaveDetailBean.class);
                    ApplyLeaveDetailBean.ApplyLeaveBean applyLeave = applyLeaveDetailBean.getApplyLeave();
                    ApplyLeaveDetailBean.ClockViewBean clockView = applyLeaveDetailBean.getClockView();
                    setupAuditView(clockView);//设置审核详情视图
                    String startTime = applyLeave.getStartTime();//请假开始时间
                    String endTime = applyLeave.getEndTime();//请假结束时间
                    int leaveType = applyLeave.getLeaveType();//请假类型
                    double applyDays = applyLeave.getApplyDays();//请假天数
                    String leaveContent = applyLeave.getLeaveContent();//请假内容
                    if (!TextUtils.isEmpty(startTime)) {
                        mTvLeaveDetailStartTime.setText(startTime);
                    }

                    if (!TextUtils.isEmpty(endTime)) {
                        mTvLeaveDetailEndTime.setText(endTime);
                    }

                    String leaveTypeString = int2LeaveType(leaveType);
                    if (!TextUtils.isEmpty(leaveTypeString)) {
                        mTvLeaveDetailType.setText(leaveTypeString);
                    }

                    mTvLeaveDetailDays.setText(applyDays + "");
                    if (!TextUtils.isEmpty(leaveContent)) {
                        mTvLeaveDetailContent.setText(leaveContent);
                    }
                } else {
                    ToastUtils.MyToast(this, "请求出错！");
                }
                break;
        }
    }

    private String int2LeaveType(int leaveType) {
        //请假类型(1 事假 2病假 3休假 4产假 5丧假 6其他)
        switch (leaveType) {
            case 1:
                return "事假";
            case 2:
                return "病假";
            case 3:
                return "休假";
            case 4:
                return "产假";
            case 5:
                return "丧假";
            case 6:
                return "其他";
        }
        return null;
    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {

    }

    @Override
    public void onActionException(int actionId, String exception) {

    }

    private void setupAuditView(ApplyLeaveDetailBean.ClockViewBean clockView) {
        String staffName = clockView.getStaffName();//申请人名称
        String cTime = clockView.getCTime();//发起任务时间
        String executorName = clockView.getExecutorName();//审核人名称
        int applyStatus = clockView.getApplyStatus();//审核状态
        String auditorTime = clockView.getAPplyAuditorTime();
        if (!TextUtils.isEmpty(staffName)) {
            mTvStartName.setText(staffName);
            String substring = staffName.substring(0, 1);
            if (!TextUtils.isEmpty(substring)) {
                mTvStartFirstName.setText(substring);

            }
        }

        if (!TextUtils.isEmpty(cTime)) {
            mTvStartTime.setText(cTime);
        }
        if (!TextUtils.isEmpty(executorName)) {
            mTvAuditorName.setText(executorName);
            String substring = executorName.substring(0, 1);
            if (!TextUtils.isEmpty(substring)) {
                mTvAuditFirstName.setText(substring);
            }
        }
        if (!TextUtils.isEmpty(int2Status(applyStatus))) {
            mTvAuditorName.setText(executorName);
        }

        if (TextUtils.isEmpty(auditorTime)) {
            mTvAuditTime.setVisibility(View.GONE);
        } else {
            mTvAuditTime.setVisibility(View.VISIBLE);
            mTvAuditTime.setText(auditorTime);
        }
    }

    private String int2Status(int applyStatus) {
        switch (applyStatus) {
            case 1:
                return "待审核";
            case 2:
                return "已经审核";
        }
        return null;
    }

    @OnClick({R.id.tv_applyDetail_execute, R.id.tv_applyDetail_continueExecute})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_applyDetail_execute:
                //执行
                Intent intent = new Intent(this, ExecuteOpinionActivity.class);
                intent.putExtra("aPplyClockId", mAPplyClockId);
                startActivity(intent);
                break;
            case R.id.tv_applyDetail_continueExecute:
                break;
        }
    }
}
