package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.widget.WrapSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskNotifyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

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

    private static final String TAG = "TaskNotifyActivity";

    @BindView(R.id.rv_task_list)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    WrapSwipeRefreshLayout mSwipeRefreshLayout;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter

    private ArrayList<String> datas = new ArrayList<>();
    private Handler handler;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "任务通知", -1);
        setContentView(requestView(R.layout.activity_task_notify));
        ButterKnife.bind(this);
        initData();//初始化数据
        initView();
        initEvent();
    }

    private void initData() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {

                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mTaskAdapter.clear();
                        mCurrentCounter = 0;
                    }

                    int currentSize = mTaskAdapter.getItemCount();

                    //模拟组装10个数据
                    datas = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        if (datas.size() + currentSize >= TOTAL_COUNTER) {
                            break;
                        }
                        datas.add("");
                    }


                    addItems(datas);

                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                }

            }
        };
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(this);//监听下拉刷新
        mLuRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

        });

        mLuRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.i(TAG, "onLoadMore: ");
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
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
        setupRecycleView();//创建RecycleView



    }

    private void setupRecycleView() {
        //setLayoutManager放在setAdapter之前配置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mTaskAdapter);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);

        LuDividerDecoration divider = new LuDividerDecoration.Builder(this, mLuRecyclerViewAdapter)
                .setHeight(R.dimen._6dp)
                //  .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color._eeeeee)
                .build();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);//设置RecycleView的分割线
        mRecyclerView.setLoadMoreEnabled(true);
        //如果使用了自动加载更多，就不要添加FooterView了
        //mLuRecyclerViewAdapter.addFooterView(new SampleFooter(this));
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorPrimary, R.color.gray, R.color._eeeeee);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "别扯了，到底了！", "网络不给力啊，点击再试一次吧");
    }

    @Override
    public void requestInit() {

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

    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_task_recycle_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {

        }

    }

    private void notifyDataSetChanged() {
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(ArrayList<String> list) {
        mTaskAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    /**
     * 模拟请求网络
     */
    private void requestDataFromNet() {
        Log.i(TAG, "requestDataFromNet: ");
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
