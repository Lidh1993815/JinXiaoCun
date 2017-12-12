package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.Contents;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.ApplyClockDetailBean;
import com.qianmo.jinxiaocun.fu.bean.ApprovalListBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.widget.WrapSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : wizardev
 * e-mail : wizarddev@163.com
 * time   : 2017/09/20
 * desc   :
 * version: 1.0
 */
public class MyReleaseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnActionListener {

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 15;

    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 0;

    private static final String TAG = "MyReleaseActivity";

    @BindView(R.id.rv_task_list)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    WrapSwipeRefreshLayout mSwipeRefreshLayout;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter
    private int mCurrentPage = 1;
    private int totalCount;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "我发起的", -1);
        setContentView(requestView(R.layout.activity_task_notify));
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(this);//监听下拉刷新
        mLuRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ApprovalListBean.DataBean dataBean = mTaskAdapter.getDataList().get(position);
                int aType = dataBean.getAType();//申请的类型
                switch (aType) {
                    //请假
                    case 1: {
                        Intent intent = new Intent(MyReleaseActivity.this, LeaveDetailActivity.class);
                        //待我审批
                        intent.putExtra("aPplyClockId", dataBean.getAPplyClockId());
                        startActivity(intent);
                    }

                    break;
                    //补卡
                    case 2: {
                        Intent intent = new Intent(MyReleaseActivity.this, CardDetailActivity.class);
                        //待我审批
                        intent.putExtra("aPplyClockId", dataBean.getAPplyClockId());
                        startActivity(intent);
                    }
                    break;
                    //物料
                    case 3: {
                        Intent intent = new Intent(MyReleaseActivity.this, MaterialApplyDetailActivity.class);
                        //待我审批
                        intent.putExtra("aPplyClockId", dataBean.getAPplyClockId());
                        startActivity(intent);
                    }
                    break;
                    //报销
                    case 4: {
                        Intent intent = new Intent(MyReleaseActivity.this, ReimbursementDetailActivity.class);
                        //待我审批
                        intent.putExtra("aPplyClockId", dataBean.getAPplyClockId());
                        startActivity(intent);
                    }
                    break;
                }
            }

        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < totalCount) {
                    // loading more
                    mCurrentPage++;
                    requestDataFromNet();
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });
        onRefresh();
    }

    private void initView() {
        mTaskAdapter = new TaskAdapter(this);//实例化适配器
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mTaskAdapter);
        setupRecycleView(mRecyclerView, mLuRecyclerViewAdapter);//创建RecycleView
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
    }

    @Override
    public void requestInit() {

    }

    @Override
    public void onRefresh() {
        mCurrentCounter = 0;
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);//同时调用LuRecyclerView的setRefreshing方法
        requestDataFromNet();
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        switch (actionId) {
            case 1001:
                if (!TextUtils.isEmpty(ret)) {
                    Log.i(TAG, "1001: " + ret);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mTaskAdapter.clear();
                        mCurrentCounter = 0;
                    }
                    ApprovalListBean approvalListBean = (ApprovalListBean) JsonUitl.stringToObject(ret, ApprovalListBean.class);
                    List<ApprovalListBean.DataBean> mDataList = approvalListBean.getData();
                    totalCount = approvalListBean.getRecordsTotal();
                    int currentSize = mTaskAdapter.getItemCount();
                    if (currentSize >= totalCount) {
                        refreshFinish();
                        break;
                    }
                    addItems(mDataList);
                    refreshFinish();
                    notifyDataSetChanged();
                    break;
                } else {
                    ToastUtils.MyToast(this, "获取数据失败！");
                }
                break;
        }
    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {
        Log.i(TAG, "onActionServerFailed: " + httpStatus);
    }

    @Override
    public void onActionException(int actionId, String exception) {
        Log.i(TAG, "onActionException: " + exception);
    }

    private void refreshFinish() {
        mRecyclerView.refreshComplete(REQUEST_COUNT);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<ApprovalListBean.DataBean> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_task_recycle_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            TextView tvTitle = holder.getView(R.id.tv_title);
            TextView tvTime = holder.getView(R.id.tv_time);
            TextView tvStatus = holder.getView(R.id.tv_status);
            ApprovalListBean.DataBean dataBean = mDataList.get(position);
            int aType = dataBean.getAType();

            String staffName = dataBean.getStaffName();
            if (!TextUtils.isEmpty(staffName)) {
                tvTitle.setText(staffName + int2ApplyType(aType));
            }
            String cTime = dataBean.getCTime();
            if (!TextUtils.isEmpty(cTime)) {
                tvTime.setText(cTime);
            }
            if (!TextUtils.isEmpty(int2StringStatus(dataBean.getAPplyStatus()))) {
                tvStatus.setText(int2StringStatus(dataBean.getAPplyStatus()));
            }
        }

    }

    private void notifyDataSetChanged() {
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<ApprovalListBean.DataBean> list) {
        mTaskAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    private String int2ApplyType(int aType) {
        //审批类型 1,请假,2补卡,3物料,4报销
        switch (aType) {
            case 1:
                return "的请假申请";
            case 2:
                return "的补卡申请";
            case 3:
                return "的物料申请";
            case 4:
                return "的报销申请";
        }
        return null;
    }

    private String int2StringStatus(int aType) {
        switch (aType) {
            case 1:
                return "待审批";
            case 2:
                return "已经审批";
        }
        return null;
    }

    /**
     * 模拟请求网络
     */
    private void requestDataFromNet() {
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("staffId", SPUtil.getInstance().getStaffId());
        okhttpParam.putString("start", mCurrentPage + "");
        okhttpParam.putString("length", REQUEST_COUNT + "");
        OkhttpUtils.sendRequest(1001, 0, ApiConfig.MY_RELEASE_CHECK, okhttpParam, this);
    }
}
