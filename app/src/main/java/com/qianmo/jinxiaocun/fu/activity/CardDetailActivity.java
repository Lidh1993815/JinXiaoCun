package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
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
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.CardDetailBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.widget.ForbiddenSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CardDetailActivity extends BaseActivity implements OnActionListener {
    /*   @BindView(R.id.rv_task_progress_list)
       RecyclerView mRecyclerView;*/
    Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    ForbiddenSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tv_cardDetail_date)
    TextView mTvCardDetailDate;
    @BindView(R.id.tv_cardDetail_content)
    TextView mTvCardDetailContent;
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

    private static final String TAG = "CardDetailActivity";
    @BindView(R.id.tv_applyDetail_execute)
    TextView mTvApplyDetailExecute;
    @BindView(R.id.tv_applyDetail_continueExecute)
    TextView mTvApplyDetailContinueExecute;
    private int mAPplyClockId;
    private int mApprovalType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mAPplyClockId = intent.getIntExtra("aPplyClockId", 0);
        mApprovalType = intent.getIntExtra("approvalType", 0);

        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "补打卡详情", -1);
        setContentView(requestView(R.layout.activity_card_detail));
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

    /* private void initView() {
            mMaterialApplyAdapter = new MaterialApplyAdapter(this);
            mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setHasFixedSize(true);
        }*/


    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
//        mTaskAdapter.setDataList(datas);
        dismissSwipeRefresh(mSwipeRefreshLayout, 1000);
        //requestDataFromNet();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                Log.i(TAG, "1001: " + ret);
                if (!TextUtils.isEmpty(ret)) {
                    CardDetailBean cardDetailBean = (CardDetailBean) JsonUitl.stringToObject(ret, CardDetailBean.class);
                    CardDetailBean.ApplyFillCardBean applyFillCard = cardDetailBean.getApplyFillCard();
                    CardDetailBean.ClockViewBean clockView = cardDetailBean.getClockView();
                    setupAuditView(clockView);//设置审核详情视图
                    String fTime = applyFillCard.getFTime();
                    String fContent = applyFillCard.getFContent();
                    if (!TextUtils.isEmpty(fTime)) {
                        mTvCardDetailDate.setText(fTime);
                    }

                    if (!TextUtils.isEmpty(fContent)) {
                        mTvCardDetailContent.setText(fContent);
                    }
                } else {
                    ToastUtils.MyToast(this, "请求出错！");
                }
                break;
        }
    }

    private void setupAuditView(CardDetailBean.ClockViewBean clockView) {
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

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {

    }

    @Override
    public void onActionException(int actionId, String exception) {

    }

    @OnClick({R.id.tv_applyDetail_execute, R.id.tv_applyDetail_continueExecute})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.tv_applyDetail_execute:
                //执行
                Intent intent = new Intent(this, ExecuteOpinionActivity.class);
                intent.putExtra("aPplyClockId", Contents.CARD);
                startActivity(intent);
                break;
            case R.id.tv_applyDetail_continueExecute:
                //继续审核
                break;
        }
    }

}
