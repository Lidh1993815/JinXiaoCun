package com.qianmo.jinxiaocun.fu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.activity.CardDetailActivity;
import com.qianmo.jinxiaocun.fu.activity.LeaveDetailActivity;
import com.qianmo.jinxiaocun.fu.activity.MaterialApplyDetailActivity;
import com.qianmo.jinxiaocun.fu.activity.ReimbursementDetailActivity;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.ApprovalListBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.widget.WrapSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseFragment;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

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
public class MyApprovalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnActionListener {
    /**
     * 服务器端一共多少条数据
     */


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

    private ApprovalAdapter mDataAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter
    private int mApplyStatus;
    private static final String TAG = "MyApprovalFragment";
    private int totalCount;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mApplyStatus = arguments.getInt("applyStatus");
        }
    }

    public static MyApprovalFragment newInstance(int status) {
        MyApprovalFragment fragment = new MyApprovalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("applyStatus", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = requestView(inflater, R.layout.fu_approval_notify_fragment);
        unbinder = ButterKnife.bind(this, view);
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
                ApprovalListBean.DataBean dataBean = mDataAdapter.getDataList().get(position);
                int aType = dataBean.getAType();//申请的类型
                switch (aType) {
                    //请假
                    case 1: {
                        Intent intent = new Intent(getContext(), LeaveDetailActivity.class);
                        intent.putExtra("aPplyClockId", dataBean.getAPplyClockId());
                        startActivity(intent);
                    }

                    break;
                    //补卡
                    case 2: {
                        Intent intent = new Intent(getContext(), CardDetailActivity.class);
                        intent.putExtra("aPplyClockId", dataBean.getAPplyClockId());
                        startActivity(intent);
                    }
                    break;
                    //物料
                    case 3: {
                        Intent intent = new Intent(getContext(), MaterialApplyDetailActivity.class);
                        intent.putExtra("aPplyClockId", dataBean.getAPplyClockId());
                        startActivity(intent);
                    }
                    break;
                    //报销
                    case 4: {
                        Intent intent = new Intent(getContext(), ReimbursementDetailActivity.class);
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
        mDataAdapter = new ApprovalAdapter(getContext());//实例化适配器
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mDataAdapter);
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
        requestDataFromNet();
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {

        switch (actionId) {
            case 1001:
                if (!TextUtils.isEmpty(ret)) {
                    Log.i(TAG, "1001: " + ret);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mDataAdapter.clear();
                        mCurrentCounter = 0;
                    }
                    ApprovalListBean approvalListBean = (ApprovalListBean) JsonUitl.stringToObject(ret, ApprovalListBean.class);
                    List<ApprovalListBean.DataBean> mDataList = approvalListBean.getData();
                    totalCount = approvalListBean.getRecordsTotal();
                    int currentSize = mDataAdapter.getItemCount();
                    if (currentSize >= totalCount) {
                        refreshFinish();
                        break;
                    }
                    addItems(mDataList);
                    refreshFinish();
                    notifyDataSetChanged();
                    break;
                }
                ToastUtils.MyToast(getContext(), "获取数据失败！");
                break;
           /* case 1002:
                if (!TextUtils.isEmpty(ret)) {
                    ResponseBean responseBean = (ResponseBean) JsonUitl.stringToObject(ret, ResponseBean.class);
                    int status = responseBean.getCode();

                    switch (status) {
                        case 100:
                            SearchStockActivity.this.finish();
                            break;
                        case 401:
                            ToastUtils.MyToast(this, responseBean.getMsg());
                            break;
                    }
                    break;
                }*/
        }

    }

    private void refreshFinish() {
        mRecyclerView.refreshComplete(REQUEST_COUNT);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {

    }

    @Override
    public void onActionException(int actionId, String exception) {

    }

    //设置RecycleView的适配器
    private class ApprovalAdapter extends ListBaseAdapter<ApprovalListBean.DataBean> {

        public ApprovalAdapter(Context context) {
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
            String staffName = dataBean.getStaffName();
            if (!TextUtils.isEmpty(staffName)) {
                tvTitle.setText(staffName);
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

    private String int2StringStatus(int aType) {
        switch (aType) {
            case 1:
                return "待审批";
            case 2:
                return "已经审批";
        }
        return null;
    }

    private void notifyDataSetChanged() {
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<ApprovalListBean.DataBean> list) {
        mDataAdapter.addAll(list);
        mCurrentCounter += list.size();
    }


    /**
     * 获取数据
     */
    private void requestDataFromNet() {
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("aAuditor", SPUtil.getInstance().getStaffId());
        okhttpParam.putString("aPplyStatus", mApplyStatus + "");
        OkhttpUtils.sendRequest(1001, 1, ApiConfig.MY_APPLY_CLOCK_DETAILS_LIST, okhttpParam, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
