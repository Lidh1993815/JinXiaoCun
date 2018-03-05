package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.ApiConfig;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.bean.StoreBean;
import com.qianmo.jinxiaocun.fu.bean.WaitTourShopBean;
import com.qianmo.jinxiaocun.fu.http.NoLoadingCallback;
import com.qianmo.jinxiaocun.fu.http.OkHttpHelper;
import com.qianmo.jinxiaocun.fu.utils.StringUtil;
import com.qianmo.jinxiaocun.fu.widget.WrapSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;
import com.qianmo.jinxiaocun.main.okhttp.params.OkhttpParam;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class ChooseStoreActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    WrapSwipeRefreshLayout mSwipeRefreshLayout;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter;
    private int mCurrentPage;
    private int mPageSize = 15;
    private long mTotalCount;
    private long mCurrentCounter;
    private StoreAdapter mStoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "选择门店", -1);
        setContentView(requestView(R.layout.activity_choose_store));
        ButterKnife.bind(this);
        initView();
        requestData();
    }

    private void initView() {

        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mStoreAdapter = new StoreAdapter(this);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mStoreAdapter);
        setupRecycleView(mRecyclerView, mLuRecyclerViewAdapter, R.dimen._1dp);
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

        mLuRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                StoreBean.DataBean dataBean = mStoreAdapter.getDataList().get(position);
                Intent intent = new Intent();
                intent.putExtra("storeName", dataBean.getStoreName());
                intent.putExtra("storeId", dataBean.getStoreId());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void requestInit() {

    }

    @Override
    public void requestData() {
        super.requestData();
        OkhttpParam okhttpParam = new OkhttpParam();
        okhttpParam.putString("start", mCurrentPage);
        okhttpParam.putString("length", mPageSize);
        OkHttpHelper.getInstance().post(ApiConfig.STORE, okhttpParam, new NoLoadingCallback<StoreBean>() {
            @Override
            public void onSuccess(Response response, StoreBean storeBean) {
                dismissSwipeRefresh();
                mTotalCount = storeBean.getRecordsTotal();
                List<StoreBean.DataBean> data = storeBean.getData();
                if (mStoreAdapter.getDataList().size() > mTotalCount) {
                    return;
                }
                addItems(data);
                mRecyclerView.refreshComplete(mPageSize);
                mLuRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                dismissSwipeRefresh();
            }
        });
    }

    private void dismissSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        mPageSize = 1;
        mCurrentCounter = 0;
        mStoreAdapter.clear();
        requestData();
    }

    class StoreAdapter extends ListBaseAdapter<StoreBean.DataBean> {
        public StoreAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_store_name;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            StoreBean.DataBean dataBean = mDataList.get(position);
            TextView tvStoreName = holder.getView(R.id.tv_item_storeName);
            tvStoreName.setText(StringUtil.getString(dataBean.getStoreName()));
        }
    }

    private void addItems(List<StoreBean.DataBean> list) {
        mStoreAdapter.addAll(list);
        mCurrentCounter += list.size();
    }
}
