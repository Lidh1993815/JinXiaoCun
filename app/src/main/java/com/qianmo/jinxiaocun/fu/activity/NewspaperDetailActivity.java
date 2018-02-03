package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.MyPostTaskDetailBean;
import com.qianmo.jinxiaocun.fu.bean.NewspaperDetail;
import com.qianmo.jinxiaocun.fu.bean.TaskDetailBean;
import com.qianmo.jinxiaocun.fu.http.LoadingCallback;
import com.qianmo.jinxiaocun.fu.http.NoLoadingCallback;
import com.qianmo.jinxiaocun.fu.http.OkHttpHelper;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;
import com.qianmo.jinxiaocun.main.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class NewspaperDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;

    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentCounter = 0;
    private int mCurrentPage = 1;


    @BindView(R.id.recyclerView)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LuRecyclerViewAdapter mLRecyclerViewAdapter;
    private NewspaperAdapter mNewspaperAdapter;
    private int mCarryOutTaskId;
    private int totalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String staffName = getIntent().getStringExtra("staffName");
        mCarryOutTaskId = getIntent().getIntExtra("carryOutTaskId", 0);
        if (staffName != null) {
            toolBar = new MyToolBar(this, R.mipmap.zoujiant, staffName + "的日报详情", -1);
        }
        setContentView(requestView(R.layout.activity_newspaper_detail));
        ButterKnife.bind(this);
        setupLRecycleView();
        requestNewspaperData();//获取日报数据
        initEvent();//初始化监听事件
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(this);//监听下拉刷新
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < totalCount) {
                    // loading more
                    mCurrentPage = mCurrentPage + 1;
                    requestNewspaperData();//从网络获取数据
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });
    }

    private void requestNewspaperData() {
        OkhttpParam param = new OkhttpParam();
        param.putString("carryOutId", mCarryOutTaskId);
        param.putString("start", mCurrentPage);
        param.putString("length", REQUEST_COUNT);
        OkHttpHelper.getInstance().post(ApiConfig.GETDAILY_CARRYOUTID, param, new NoLoadingCallback<NewspaperDetail>() {


            @Override
            public void onSuccess(Response response, NewspaperDetail newspaperDetail) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (newspaperDetail != null) {
                    totalCount = newspaperDetail.getRecordsTotal();
                    List<NewspaperDetail.DataBean> data = newspaperDetail.getData();
                    int currentSize = mNewspaperAdapter.getItemCount();
                    //判断adapter总的条目数是否大于总共返回的数据
                    if (currentSize >= totalCount) {
                        mRecyclerView.setNoMore(true);
                    } else {
                        mRecyclerView.setNoMore(false);
                        addItems(data);
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void addItems(List<NewspaperDetail.DataBean> list) {
        mNewspaperAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    private void setupLRecycleView() {
        mNewspaperAdapter = new NewspaperAdapter(this);
        mLRecyclerViewAdapter = new LuRecyclerViewAdapter(mNewspaperAdapter);
        setupRecycleView(mRecyclerView,mLRecyclerViewAdapter);
    }

    @Override
    public void requestInit() {

    }

    @Override
    public void onRefresh() {
        mCurrentCounter = 0;
        mCurrentPage = 1;
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);//同时调用LuRecyclerView的setRefreshing方法
        requestNewspaperData();
    }

    class NewspaperAdapter extends ListBaseAdapter<NewspaperDetail.DataBean> {
        public NewspaperAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_newspaper_detail;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            TextView mTvNewspaperDetailDate = holder.getView(R.id.tv_newspaperDetail_date);
            TextView mTvNewspaperDetailContent = holder.getView(R.id.tv_newspaperDetail_content);
        }
    }
}
