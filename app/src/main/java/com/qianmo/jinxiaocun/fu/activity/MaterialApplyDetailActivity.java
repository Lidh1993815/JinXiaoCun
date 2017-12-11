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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.ApplyMaterialDetailBean;
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

/**
 * 物料申请详情界面
 */
public class MaterialApplyDetailActivity extends BaseActivity implements OnActionListener {

    @BindView(R.id.rv_material_detail)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_showProgressPrompt)
    TextView mTvShowProgressPrompt;
    @BindView(R.id.tv_startName)
    TextView mTvStartName;
    @BindView(R.id.tv_startTime)
    TextView mTvStartTime;
    @BindView(R.id.tv_auditorName)
    TextView mTvAuditorName;
    @BindView(R.id.tv_auditStatus)
    TextView mTvAuditStatus;
    @BindView(R.id.tv_auditTime)
    TextView mTvAuditTime;
    @BindView(R.id.tv_start_firstName)
    TextView mTvStartFirstName;
    @BindView(R.id.tv_audit_firstName)
    TextView mTvAuditFirstName;
    @BindView(R.id.ll_applyDetail_progressLayout)
    ConstraintLayout mLlApplyDetailProgressLayout;
    @BindView(R.id.ll_applyDetail_bottomLayout)
    LinearLayout mLlApplyDetailBottomLayout;
    private MaterialApplyAdapter mMaterialApplyAdapter = null;//数据适配器
    private static final String TAG = "MaterialApplyDetailActi";
    private int mAPplyClockId;
    private int mApprovalType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mAPplyClockId = intent.getIntExtra("aPplyClockId", 0);
        mApprovalType = intent.getIntExtra("approvalType", 0);

        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "物料申请详情", -1);
        setContentView(requestView(R.layout.activity_material_apply_detail));
        ButterKnife.bind(this);
        initView();
        requestData();
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

    private void initView() {
        mTvShowProgressPrompt.setText(R.string.audit_progress);
        if (mApprovalType == 1) {
            mLlApplyDetailProgressLayout.setVisibility(View.GONE);
            mLlApplyDetailBottomLayout.setVisibility(View.VISIBLE);
        } else {
            mLlApplyDetailProgressLayout.setVisibility(View.VISIBLE);
            mLlApplyDetailBottomLayout.setVisibility(View.GONE);
        }
        mMaterialApplyAdapter = new MaterialApplyAdapter(this);
        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                Log.i(TAG, "1001: " + ret);
                if (!TextUtils.isEmpty(ret)) {
                    ApplyMaterialDetailBean applyMaterialDetailBean = (ApplyMaterialDetailBean) JsonUitl.stringToObject(ret, ApplyMaterialDetailBean.class);
                    List<ApplyMaterialDetailBean.ApplyMaterielBean> applyMateriels = applyMaterialDetailBean.getApplyMateriels();
                    ApplyMaterialDetailBean.ClockViewBean clockView = applyMaterialDetailBean.getClockView();
                    setupAuditView(clockView);//设置审核详情视图
                    addItems(applyMateriels);
                } else {
                    ToastUtils.MyToast(this, "请求出错！");
                }
                break;
        }
    }

    private void setupAuditView(ApplyMaterialDetailBean.ClockViewBean clockView) {
        String staffName = clockView.getStaffName();//申请人名称
        String cTime = clockView.getCTime();//发起任务时间
        String executorName = clockView.getExecutorName();//审核人名称
        int applyStatus = clockView.getApplyStatus();//审核状态
        String auditorTime = clockView.getaPplyAuditorTime();
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


    private void addItems(List<ApplyMaterialDetailBean.ApplyMaterielBean> list) {
        mMaterialApplyAdapter.addAll(list);
        mRecyclerView.setAdapter(mMaterialApplyAdapter);

    }

    //设置RecycleView的适配器
    class MaterialApplyAdapter extends ListBaseAdapter<ApplyMaterialDetailBean.ApplyMaterielBean> {

        public MaterialApplyAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_material_detail_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, final int position) {
            TextView materialPosition = holder.getView(R.id.material_detail_position);
            TextView mTvMaterialApplyDetailMaterialName = holder.getView(R.id.tv_materialApplyDetail_materialName);
            TextView mTvMaterialApplyDetailMaterialNum = holder.getView(R.id.tv_materialApplyDetail_materialNum);
            TextView mTvMaterialApplyDetailMaterialUse = holder.getView(R.id.tv_materialApplyDetail_materialUse);
            int num = position + 1;
            materialPosition.setText("物品明细（" + num + "）");//设置显示的文字

            ApplyMaterialDetailBean.ApplyMaterielBean applyMaterielBean = mDataList.get(position);
            String mName = applyMaterielBean.getMName();
            int mSum = applyMaterielBean.getMSum();
            String mRemake = applyMaterielBean.getMRemake();

            if (!TextUtils.isEmpty(mName)) {
                mTvMaterialApplyDetailMaterialName.setText(mName);
            }
            mTvMaterialApplyDetailMaterialNum.setText(mSum + "");
            if (!TextUtils.isEmpty(mRemake)) {
                mTvMaterialApplyDetailMaterialUse.setText(mRemake);
            }
        }

    }
}
