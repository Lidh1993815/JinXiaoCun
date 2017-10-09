package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.widget.WrapSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 进货历史和销售历史界面
 */
public class PurchaseOrSalesHistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

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

    @BindView(R.id.rv_purchase_order_list)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    WrapSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    private TaskAdapter mTaskAdapter = null;//数据适配器
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;//增强版的Adapter

    private ArrayList<String> datas = new ArrayList<>();
    private Handler handler;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        setContentView(R.layout.activity_purchase_history);
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).fitsSystemWindows(true).init();
        ButterKnife.bind(this);
        ButterKnife.bind(this);
        setupToolbar();
        initData();
        initView();
        initEvent();
    }

    private void setupToolbar() {
        //左箭头事件
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.search_and_add_menu);

        if (type != null && type.equals("sales")) {
            //销售历史
            TextView toolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("销售历史");
        }
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search_menu:
                        if (type != null && type.equals("sales")) {
                            startActivity(SalesSearchOrderActivity.class, false);
                        } else {
                            startActivity(SearchOrderActivity.class, false);
                        }
                        break;
                    case R.id.add_menu:
                        if (type != null && type.equals("sales")) {
                            startActivity(NewSalesOrdersActivity.class, false);
                        } else {
                            startActivity(PurchaseOrdersActivity.class, false);
                        }
                        break;
                }

                return true;
            }
        });


    }

    @Override
    public void requestInit() {

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
                if (type != null && type.equals("sales")) {

                    startActivity(SalesOrdersDetailActivity.class);
                }
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
        mTaskAdapter = new TaskAdapter(this);//实例化适配器
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mTaskAdapter);
        LuDividerDecoration divider = new LuDividerDecoration.Builder(this, mLuRecyclerViewAdapter)
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.purchase_order_item;
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
