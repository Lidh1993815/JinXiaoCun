package com.qianmo.jinxiaocun.fu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.SwipeMenuAdapter;
import com.qianmo.jinxiaocun.fu.bean.WaitTourShopBean;
import com.qianmo.jinxiaocun.fu.utils.JsonUitl;
import com.qianmo.jinxiaocun.fu.utils.SPUtil;
import com.qianmo.jinxiaocun.fu.widget.WrapSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseFragment;
import com.qianmo.jinxiaocun.main.okhttp.OkhttpUtils;
import com.qianmo.jinxiaocun.main.okhttp.base.OkhttpBase;
import com.qianmo.jinxiaocun.main.okhttp.listener.OnActionListener;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;

import java.lang.ref.WeakReference;
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
public class WaitTourShopFragment extends BaseFragment implements OnActionListener {

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;

    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 0;
    @BindView(R.id.rv_approval_list)
    LRecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    WrapSwipeRefreshLayout mSwipeRefreshLayout;
    private long mTotalCount;
    private SwipeMenuAdapter mDataAdapter = null;//可以右划删除的数据适配器
    private int mApprovalStatus;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private static final String TAG = "WaitTourShopFragment";
    private boolean isRefresh = false;//用来判断是上拉还是下拉刷新

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mApprovalStatus = arguments.getInt("approvalStatus");
        }
    }

    public static WaitTourShopFragment newInstance(int status) {
        WaitTourShopFragment fragment = new WaitTourShopFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("approvalStatus", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = requestView(inflater, R.layout.fu_tour_shop_fragment);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initEvent();
//        requestData();
        return view;
    }

    @Override
    public void requestInit() {

    }

    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        //http://hzq.s1.natapp.cc/app/patrol_store?start=1&length=20&patrolStoreStatus=1&staffId=1
        okhttpParam.putString("start", mCurrentPage);
        okhttpParam.putString("length", REQUEST_COUNT);
        okhttpParam.putString("patrolStoreStatus", mApprovalStatus);
        okhttpParam.putString("staffId", SPUtil.getInstance().getStaffId());

        OkhttpUtils.sendRequest(1001, 0, ApiConfig.PATROL_STORE, okhttpParam, this);
    }

    private void initEvent() {

        mDataAdapter.setOnDelListener(new SwipeMenuAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos) {
                Toast.makeText(getContext(), "删除:" + pos, Toast.LENGTH_SHORT).show();

                //RecyclerView关于notifyItemRemoved的那点小事 参考：http://blog.csdn.net/jdsjlzx/article/details/52131528
                mDataAdapter.getDataList().remove(pos);
                mDataAdapter.notifyItemRemoved(pos);//推荐用这个

                if (pos != (mDataAdapter.getDataList().size())) { // 如果移除的是最后一个，忽略 注意：这里的mDataAdapter.getDataList()不需要-1，因为上面已经-1了
                    mDataAdapter.notifyItemRangeChanged(pos, mDataAdapter.getDataList().size() - pos);
                }
                //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
            }

            @Override
            public void onTop(int pos) {//置顶功能有bug，后续解决
                /*TLog.error("onTop pos = " + pos);
                ItemModel itemModel = mDataAdapter.getDataList().get(pos);

                mDataAdapter.getDataList().remove(pos);
                mDataAdapter.notifyItemRemoved(pos);
                mDataAdapter.getDataList().add(0, itemModel);
                mDataAdapter.notifyItemInserted(0);*/


                if (pos != (mDataAdapter.getDataList().size())) { // 如果移除的是最后一个，忽略
                    mDataAdapter.notifyItemRangeChanged(0, mDataAdapter.getDataList().size() - 1, "jdsjlzx");
                }
                mRecyclerView.scrollToPosition(0);
            }
        });

    }

    private void initView() {

        mDataAdapter = new SwipeMenuAdapter(getContext());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //     mDataAdapter.clear();
                //  mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                mCurrentCounter = 0;
                mCurrentPage = 1;
                mDataAdapter.clear();
                requestData();
            }
        });
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < mTotalCount) {
                    mCurrentPage++;
                    requestData();//从网络获取数据
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });
        mRecyclerView.refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<WaitTourShopBean.DataBean> list) {
        mDataAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    @Override
    public void onActionSuccess(int actionId, String ret) {
        Log.d(TAG, "onActionSuccess: " + ret);
        switch (actionId) {
            case 1001:
                if (!TextUtils.isEmpty(ret)) {
                    WaitTourShopBean waitTourShopBean = (WaitTourShopBean) JsonUitl.stringToObject(ret, WaitTourShopBean.class);
                    mTotalCount = waitTourShopBean.getRecordsTotal();
                    if (mDataAdapter.getDataList().size() > mTotalCount) {
                        return;
                    }
                    List<WaitTourShopBean.DataBean> data = waitTourShopBean.getData();
                    addItems(data);
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onActionServerFailed(int actionId, int httpStatus) {
        Log.d(TAG, "onActionServerFailed: " + httpStatus);
    }

    @Override
    public void onActionException(int actionId, String exception) {
        Log.d(TAG, "onActionException: " + exception);
    }
}
