package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.MyPostTaskDetailBean;
import com.qianmo.jinxiaocun.fu.http.LoadingCallback;
import com.qianmo.jinxiaocun.fu.http.OkHttpHelper;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class TaskDetailActivity extends BaseActivity {

    @BindView(R.id.sponsor)
    TextView mSponsor;
    @BindView(R.id.task_number)
    TextView mTaskNumber;
    @BindView(R.id.task_status)
    TextView mTaskStatus;
    @BindView(R.id.tv_taskDetail_taskTitle)
    TextView mTvTaskDetailTaskTitle;
    @BindView(R.id.tv_taskDetail_content)
    TextView mTvTaskDetailContent;
    @BindView(R.id.tv_auditStatus)
    TextView mTvAuditStatus;
    @BindView(R.id.expectec_bonus)
    TextView mExpectecBonus;
    @BindView(R.id.expected_fine)
    TextView mExpectedFine;
    @BindView(R.id.stop_time)
    TextView mStopTime;
    @BindView(R.id.recyclerView)
    LRecyclerView mRecyclerView;
    private int mTaskId;
    private ExecutiveAdapter mExecutiveAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "任务详情", -1);
        mTaskId = getIntent().getIntExtra("taskId", 0);
        setContentView(requestView(R.layout.activity_task_detail));
        ButterKnife.bind(this);
        setupRecyclerView();
        requestTaskDetail();//获取任务详情

    }

    private void setupRecyclerView() {
        mExecutiveAdapter = new ExecutiveAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mExecutiveAdapter);
        DividerDecoration dividerDecoration = new DividerDecoration.Builder(this)
                .setColorResource(R.color._eeeeee)
                .setHeight(R.dimen._1dp)
                .build();

        mRecyclerView.addItemDecoration(dividerDecoration);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadMoreEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MyPostTaskDetailBean.CarryOutViewsBean carryOutViewsBean = mExecutiveAdapter.getDataList().get(position);
                Intent intent = new Intent(TaskDetailActivity.this, NewspaperDetailActivity.class);
                intent.putExtra("staffName", carryOutViewsBean.getStaffName());
                intent.putExtra("carryOutTaskId", carryOutViewsBean.getCarryOutTaskId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void requestInit() {

    }

    private void requestTaskDetail() {
        Map<String, Object> param = new HashMap<>();
        param.put("staffId", SPUtil.getInstance().getStaffId());
        param.put("taskId", mTaskId);
        OkHttpHelper.getInstance().post(ApiConfig.MY_RELEASE_TASK_DETAILS, param, new LoadingCallback<MyPostTaskDetailBean>(this) {

            @Override
            public void onSuccess(Response response, MyPostTaskDetailBean myPostTaskDetailBean) {
                if (myPostTaskDetailBean != null) {
                    setupView(myPostTaskDetailBean.getData());//获取数据后，将数据添加到视图
                    if (myPostTaskDetailBean.getCarryOutViews() != null) {
                        mExecutiveAdapter.setDataList(myPostTaskDetailBean.getCarryOutViews());
                    }
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void setupView(MyPostTaskDetailBean.DataBean data) {
        mSponsor.setText(StringUtil.getString(data.getStaffName()));//发起人
        mTaskNumber.setText(StringUtil.getString(data.getTaskNumber()));//任务编号
        //执行状态(1待执行，2执行中3完成)
        mTaskStatus.setText(StringUtil.getString(int2TaskStatus(data.getExecuteStatus())));
        mTvTaskDetailTaskTitle.setText(StringUtil.getString(data.getTitle()));//任务标题
        mTvTaskDetailContent.setText(StringUtil.getString(data.getContent()));//任务内容
        mExpectecBonus.setText(data.getBonus() + "");//任务奖金
        mExpectedFine.setText(data.getPenalty() + "");//预计罚款
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

    class ExecutiveAdapter extends ListBaseAdapter<MyPostTaskDetailBean.CarryOutViewsBean> {

        public ExecutiveAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_task_executive;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            MyPostTaskDetailBean.CarryOutViewsBean carryOutViewsBean = mDataList.get(position);
            String staffName = carryOutViewsBean.getStaffName();
            String resultName = "";
            if (staffName != null && staffName.length() > 2) {
                resultName = staffName.substring(staffName.length() - 2, staffName.length());
            } else {
                resultName = staffName;
            }
            Log.d("wizardev", "onBindItemHolder: "+resultName);
            TextView mTvAvatarName = holder.getView(R.id.tv_avatar_name);
            mTvAvatarName.setText(resultName);

            TextView mTvName = holder.getView(R.id.tv_name);
            mTvName.setText(StringUtil.getString(carryOutViewsBean.getStaffName()));//执行人
            //审核状态(1待审核 2 审核通过 3 审核不通过)
            TextView mTvPosition = holder.getView(R.id.tv_position);//
            switch (carryOutViewsBean.getCarryOutStatus()) {
                case 1:
                    mTvPosition.setTextColor(getResources().getColor(R.color.colorAccent));
                    mTvPosition.setText("待审核");
                case 2:
                    mTvPosition.setTextColor(getResources().getColor(R.color._8c969f));
                    mTvPosition.setText("审核通过");
                case 3:
                    getResources().getColor(R.color._8c969f);
                    mTvPosition.setText("审核不通过");

            }
        }


    }



}
