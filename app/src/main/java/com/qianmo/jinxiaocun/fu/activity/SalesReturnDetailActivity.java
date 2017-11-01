package com.qianmo.jinxiaocun.fu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qianmo.jinxiaocun.R;
import com.qianmo.jinxiaocun.fu.adapter.ListBaseAdapter;
import com.qianmo.jinxiaocun.fu.adapter.SuperViewHolder;
import com.qianmo.jinxiaocun.fu.decoration.FuDividerDecoration;
import com.qianmo.jinxiaocun.fu.widget.ForbiddenSwipeRefreshLayout;
import com.qianmo.jinxiaocun.main.base.BaseActivity;
import com.qianmo.jinxiaocun.main.base.MyToolBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 销售退货单详情界面
 */
public class SalesReturnDetailActivity extends BaseActivity {


    @BindView(R.id.main_swipe_refresh)
    ForbiddenSwipeRefreshLayout mainSwipeRefresh;
    @BindView(R.id.sales_product_detail)
    RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter = null;//数据适配器
    private ArrayList<String> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = new MyToolBar(this, R.mipmap.zoujiant, "销售退货单详情", -1);
        setContentView(requestView(R.layout.activity_sales_return_detail));
        ButterKnife.bind(this);
        initData();
        initView();
    }
    private void initData() {

        //模拟组装10个数据
        datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add("");
        }

    }

    private void initView() {
        mainSwipeRefresh.setRefreshing(true);
        dismissSwipeRefresh(mainSwipeRefresh, 1000);

        mTaskAdapter = new TaskAdapter(this);//实例化适配器
        mTaskAdapter.setDataList(datas);
        //setLayoutManager放在setAdapter之前配置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mTaskAdapter);
        FuDividerDecoration divider = new FuDividerDecoration.Builder(this)
                .setHeight(R.dimen.line_height_size)
                //  .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color._eeeeee)
                .setHeaderDivide(true)
                .build();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void requestInit() {

    }

    //设置RecycleView的适配器
    private class TaskAdapter extends ListBaseAdapter<String> {

        public TaskAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.fu_sales_detail_item;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {

        }

    }
}
