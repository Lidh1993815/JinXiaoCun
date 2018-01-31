package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.ReimbursementDetailBean;
import com.qianmo.jinxiaocun.fu.decoration.FullyLinearLayoutManager;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 报销详情界面
 */
public class ReimbursementDetailActivity extends BaseActivity implements OnActionListener {

    @BindView(R.id.rv_add_reim)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_showProgressPrompt)
    TextView mTvShowProgressPrompt;
    @BindView(R.id.tv_startName)
    TextView mTvStartName;
    @BindView(R.id.tv_start_firstName)
    TextView mTvStartFirstName;
    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;
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

    private ReimAdapter mReimAdapter = null;//数据适配器
    private int mAPplyClockId;
    private static final String TAG = "ReimbursementDetailActi";
    private int mApprovalType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mAPplyClockId = intent.getIntExtra("aPplyClockId", 0);
        mApprovalType = intent.getIntExtra("approvalType", 0);

        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "报销详情", -1);
        setContentView(requestView(R.layout.activity_reimbursement_detail));
        ButterKnife.bind(this);
        initView();
        requestData();
    }

    @Override
    public void requestInit() {

    }


    private void initView() {
        mTvShowProgressPrompt.setText("审核进度");
        if (mApprovalType == 1) {
            mLlApplyDetailProgressLayout.setVisibility(View.GONE);
            mLlApplyDetailBottomLayout.setVisibility(View.VISIBLE);
        } else {
            mLlApplyDetailProgressLayout.setVisibility(View.VISIBLE);
            mLlApplyDetailBottomLayout.setVisibility(View.GONE);
        }
        mReimAdapter = new ReimAdapter(this);

        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mReimAdapter);
        mRecyclerView.setHasFixedSize(true);
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
                    ReimbursementDetailBean reimbursementDetailBean = (ReimbursementDetailBean) JsonUitl.stringToObject(ret, ReimbursementDetailBean.class);
                    List<ReimbursementDetailBean.ApplyReimbursesBean> applyReimburses = reimbursementDetailBean.getApplyReimburses();
                    ReimbursementDetailBean.ClockViewBean clockView = reimbursementDetailBean.getClockView();
                    setupAuditView(clockView);//设置审核详情视图
                    addItems(applyReimburses);
                } else {
                    ToastUtils.MyToast(this, "请求出错！");
                }
                break;
        }
    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {

    }

    @Override
    public void onActionException(int actionId, String exception) {

    }

    private void setupAuditView(ReimbursementDetailBean.ClockViewBean clockView) {
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

    private void addItems(List<ReimbursementDetailBean.ApplyReimbursesBean> list) {
        mReimAdapter.addAll(list);
        mRecyclerView.setAdapter(mReimAdapter);

    }

    @OnClick({R.id.tv_applyDetail_execute, R.id.tv_applyDetail_continueExecute})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_applyDetail_execute:
                //执行
                Intent intent = new Intent(this, ExecuteOpinionActivity.class);
                intent.putExtra("aPplyClockId", mAPplyClockId);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_applyDetail_continueExecute:
                break;
        }
    }

    //设置RecycleView的适配器
    private class ReimAdapter extends ListBaseAdapter<ReimbursementDetailBean.ApplyReimbursesBean> {

        public ReimAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_reim_detail_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, final int position) {
            TextView positionText = holder.getView(R.id.reim_detail_position);
            TextView rvDetailFee = holder.getView(R.id.tv_reDetail_fee);
            TextView rvDetailType = holder.getView(R.id.tv_reDetail_type);
            TextView rvDetailContent = holder.getView(R.id.tv_reDetail_content);

            int num = position + 1;
            positionText.setText("报销明细（" + num + "）");//设置显示的文字

            ReimbursementDetailBean.ApplyReimbursesBean applyReimbursesBean = mDataList.get(position);
            int rMoney = applyReimbursesBean.getRMoney();//报销费用
            rvDetailFee.setText(rMoney + "");
            String rType = applyReimbursesBean.getRType();//报销类型
            if (!TextUtils.isEmpty(rType)) {
                rvDetailType.setText(rType);
            }
            String rContent = applyReimbursesBean.getRContent();//费用明细
            if (!TextUtils.isEmpty(rContent)) {
                rvDetailContent.setText(rContent);
            }
        }

    }
}
