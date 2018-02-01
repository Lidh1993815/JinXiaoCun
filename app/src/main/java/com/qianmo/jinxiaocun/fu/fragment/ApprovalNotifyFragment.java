package com.qianmo.jinxiaocun.fu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.activity.ForMeTaskDetailActivity;
import com.qianmo.jinxiaocun.fu.activity.TaskDetailActivity;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.TaskForMeBean;
import com.qianmo.jinxiaocun.fu.utils.DateUtil;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
import com.qianmo.jinxiaocun.fu.widget.WrapSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseFragment;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/18
 * desc   :
 * version: 1.0
 */
public class ApprovalNotifyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnActionListener {
    /**
     * 服务器端一共多少条数据
     */
    private static final int TOTAL_COUNTER = 34;

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 15;

    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 0;
    @BindView(R.id.rv_approval_list)
    LuRecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    WrapSwipeRefreshLayout mSwipeRefreshLayout;

    private TaskAdapter mTaskAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter
    private static final String TAG = "ApprovalNotifyFragment";
    private int approvalStatus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            approvalStatus = arguments.getInt("approvalStatus");//分为“待我执行”和“我发布的”
        }
    }

    public static ApprovalNotifyFragment newInstance(int status) {
        ApprovalNotifyFragment fragment = new ApprovalNotifyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("approvalStatus", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = requestView(inflater, R.layout.fu_approval_notify_fragment);
        unbinder = ButterKnife.bind(this, view);
//        initData();//初始化数据
        initView();
        initEvent();
        return view;
    }

    @Override
    public void requestInit() {

    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(this);//监听下拉刷新
        mLuRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TaskForMeBean.DataBean dataBean = mTaskAdapter.getDataList().get(position);
                int taskId = dataBean.getTaskId();
                if (approvalStatus == 0) {
                    //待我执行的界面
                    Intent intent = new Intent(getContext(), ForMeTaskDetailActivity.class);
                    intent.putExtra("taskId", taskId);
                    startActivity(intent);

                } else {
                    //我发布的界面
                    Intent intent = new Intent(getContext(), TaskDetailActivity.class);
                    intent.putExtra("taskId", taskId);
                    startActivity(intent);
                }


            }

        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    requestDataFromNet();//从网络获取数据
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });
        onRefresh();
    }

    private void initView() {
        mTaskAdapter = new TaskAdapter(getContext());//实例化适配器
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mTaskAdapter);
        LuDividerDecoration divider = new LuDividerDecoration.Builder(getContext(), mLuRecyclerViewAdapter)
                .setHeight(R.dimen._6dp)
                //  .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color._eeeeee)
                .setHeaderDivide(true)
                .build();
        setupRecycleView(mRecyclerView, mLuRecyclerViewAdapter, divider);//创建RecycleView

    }

    @Override
    public void onRefresh() {
        mCurrentCounter = 0;
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);//同时调用LuRecyclerView的setRefreshing方法
        //  mTaskAdapter.setDataList(datas);
        // dismissSwipeRefresh(mSwipeRefreshLayout,3000);
        requestDataFromNet();
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (!TextUtils.isEmpty(ret)) {
                    Log.d(TAG, "onActionSuccess: " + ret);
                    TaskForMeBean taskForMeBean = (TaskForMeBean) JsonUitl.stringToObject(ret, TaskForMeBean.class);
                    List<TaskForMeBean.DataBean> data = taskForMeBean.getData();
                    mTaskAdapter.setDataList(data);
                    mLuRecyclerViewAdapter.notifyDataSetChanged();
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

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<TaskForMeBean.DataBean> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_task_recycle_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            TaskForMeBean.DataBean dataBean = mDataList.get(position);

            TextView mTvTitle = holder.getView(R.id.tv_title);
            TextView mTvTime = holder.getView(R.id.tv_time);
            TextView mTvStatus = holder.getView(R.id.tv_status);

            mTvTitle.setText(StringUtil.getString(dataBean.getTitle()));
            mTvTime.setText(StringUtil.getString(dataBean.getCTime()));
            mTvStatus.setText(statusInt2String(dataBean.getExecuteStatus()));

        }

    }

    private String statusInt2String(int executeStatus) {
        //执行状态1待执行，2执行中3完成

        switch (executeStatus) {
            case 1:
                return "待执行";
            case 2:
                return "执行中";

            case 3:
                return "完成 ";

        }
        return "";
    }

    private void notifyDataSetChanged() {
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(ArrayList<TaskForMeBean.DataBean> list) {
        mTaskAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    /**
     * 模拟请求网络
     */
    private void requestDataFromNet() {

        if (approvalStatus == 0) {
            //待我执行
            requestTaskOfMine();
        } else {
            //我发布的
            requestNewTaskOfMine();
        }
    }

    private void requestNewTaskOfMine() {
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("staffId", SPUtil.getInstance().getStaffId());
        OkhttpUtils.sendRequest(1001, OkhttpUtils.POST, ApiConfig.MY_TASK, okhttpParam, this);
    }

    private void requestTaskOfMine() {
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("staffId", SPUtil.getInstance().getStaffId());
        OkhttpUtils.sendRequest(1001, OkhttpUtils.POST, ApiConfig.MY_WAIT_FOR_TASK, okhttpParam, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
