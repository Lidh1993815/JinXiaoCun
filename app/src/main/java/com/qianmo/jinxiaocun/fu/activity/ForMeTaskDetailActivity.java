package com.qianmo.jinxiaocun.fu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.bean.TaskDetailBean;
import com.qianmo.jinxiaocun.fu.http.LoadingCallback;
import com.qianmo.jinxiaocun.fu.http.OkHttpHelper;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 待我执行任务详情界面
 */
public class ForMeTaskDetailActivity extends BaseActivity {

    @BindView(R.id.sponsor)
    TextView mSponsor;
    @BindView(R.id.task_number)
    TextView mTaskNumber;
    @BindView(R.id.task_status)
    TextView mTaskStatus;

    @BindView(R.id.tv_auditStatus)
    TextView mTvAuditStatus;
    @BindView(R.id.expectec_bonus)
    TextView mExpectecBonus;
    @BindView(R.id.expected_fine)
    TextView mExpectedFine;
    @BindView(R.id.stop_time)
    TextView mStopTime;
    @BindView(R.id.btn_bottom)
    Button mBtnBottom;
    @BindView(R.id.tv_taskDetail_taskTitle)
    TextView mTvTaskDetailTaskTitle;
    @BindView(R.id.tv_taskDetail_content)
    TextView mTvTaskDetailContent;
    private int mTaskId;
    private String mStaffName;
    private int mCarryOutTaskId;
    private TextView mRightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId = getIntent().getIntExtra("taskId", 0);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "任务详情", "填写日报");
        mRightText = toolBar.getRightTextBtn();
        setContentView(requestView(R.layout.activity_for_me_task_detail));
        ButterKnife.bind(this);
// TODO: 18-2-3 完成任务点击事件
        requestTaskDetail();//获取任务详情
    }

    private void requestTaskDetail() {
        OkhttpParam param = new OkhttpParam();
        param.putString("staffId", SPUtil.getInstance().getStaffId());
        param.putString("taskId", mTaskId);
        OkHttpHelper.getInstance().post(ApiConfig.TASK_DETAILS, param, new LoadingCallback<TaskDetailBean>(this) {

            @Override
            public void onSuccess(Response response, TaskDetailBean taskDetailBean) {
                if (taskDetailBean.getState().equals("00000")) {
                    mCarryOutTaskId = taskDetailBean.getData().getCarryOutTaskId();
                    setupView(taskDetailBean.getData());//获取数据后，将数据添加到视图
                } else {
                    ToastUtils.MyToast(ForMeTaskDetailActivity.this, taskDetailBean.getMsg());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void setupView(TaskDetailBean.DataBean data) {
        mStaffName = data.getStaffName();
        if (!TextUtils.isEmpty(mStaffName)) {
            mRightText.setVisibility(View.VISIBLE);
            if (mRightText != null) {
                mRightText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ForMeTaskDetailActivity.this, NewspaperActivity.class);
                        intent.putExtra("staffName", mStaffName);
                        intent.putExtra("carryOutTaskId", mCarryOutTaskId);
                        startActivity(intent);
                    }
                });
            }
        } else {
            mRightText.setVisibility(View.GONE);

        }


        mSponsor.setText(StringUtil.getString(mStaffName));//发起人
        mTaskNumber.setText(StringUtil.getString(data.getTaskNumber()));//任务编号
        //执行状态(1待执行，2执行中3完成)
        mTaskStatus.setText(StringUtil.getString(int2TaskStatus(data.getExecuteStatus())));
        mTvTaskDetailTaskTitle.setText(StringUtil.getString(data.getTitle()));//任务标题
        mTvTaskDetailContent.setText(StringUtil.getString(data.getContent()));//任务内容
        mExpectecBonus.setText(data.getBonus()+"");//任务奖金
        mExpectedFine.setText(data.getPenalty()+"");//预计罚款
        mStopTime.setText(StringUtil.getString(data.getUptoTime()));//截至时间
    }

    private String int2TaskStatus(int executeStatus) {
        switch (executeStatus) {
            case 1:
                return "待执行";
            case 2:
                return "执行中";
            case 3:
                return "完成";
        }
        return null;
    }

    @Override
    public void requestInit() {

    }
}
